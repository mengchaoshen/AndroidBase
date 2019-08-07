package com.smc.androidbase.opengles;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.opengl.GLES30;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.smc.androidbase.R;
import com.smc.androidbase.utils.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author shenmengchao
 * @version 1.0.0
 *          <p>
 *          OpenGLES+EGL渲染出一个三角形的流程:
 *          1.初始化EGL并确保makeCurrent
 *          2.初始化opengl program
 *          3.开始渲染
 */
public class TriangleActivity extends Activity {

    private final static String TAG = TriangleActivity.class.getSimpleName();
    @BindView(R.id.surfaceview)
    SurfaceView mSurfaceview;

    private EGLDisplay mEGLDisplay;
    private EGLContext mEGLContext;
    private EGLSurface mEGLSurface;
    private EGLConfig mEGLConfig;
    int programObject;

    public static void launch(Context context) {
        Intent intent = new Intent(context, TriangleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tri);
        ButterKnife.bind(this);

        mSurfaceview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                EsContext esContext = new EsContext();
                UserData userData = new UserData();
                userData.setVobIds(new int[3]);
                userData.setVaoIds(new int[3]);
                esContext.setUserData(userData);
                esContext.setWidth(mSurfaceview.getWidth());
                esContext.setHeight(mSurfaceview.getHeight());
                esMain(esContext);
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {

            }
        });
    }

    private void esMain(EsContext esContext) {
        if (!initEgl(mSurfaceview.getHolder().getSurface())) {
            throw new RuntimeException("initEgl 失败");
        }
        boolean current = EGL14.eglMakeCurrent(mEGLDisplay, mEGLSurface, mEGLSurface, mEGLContext);
        //在init opengles之前必须要先初始化好EGLContext 并且要调用eglMakeCurrent()
        if (!init(esContext)) {
            ToastUtil.showToast("init 失败");
            throw new RuntimeException("init 失败");
        }

        if (current) {
            draw(esContext);
            EGL14.eglSwapBuffers(mEGLDisplay, mEGLSurface);
        }
    }

    /**
     * 初始化program
     *
     * @param esContext
     * @return
     */
    private boolean init(EsContext esContext) {
        UserData userData = esContext.getUserData();
        //顶点着色器
        String vShaderStr =
                "#version 300 es \n" +
                        "layout(location = 0) in vec4 a_color       ;\n" +
                        "layout(location = 1) in vec4 a_position     ;\n" +//定义vec4向量
                        "float specularAtten = 1.0                  ;\n" +//定义float标量 这里不能赋值为1必须是1.0否则会报错"Type mismatch, cannot convert from 'int' to 'float'"
                        "int myInt = 1                              ;\n" +//定义int类型的标量 同理不能赋值为1.0，否则会报错
                        "mat4 mViewProjection                       ;\n" +//定义4*4的矩阵
                        "ivec2 vOffset                              ;\n" +//定义一个基于int类型的2分向量
                        "bool myBool = true                         ;\n" +
                        //如果要在定义的时候初始化，必须使用const关键字，否则会报错
                        "const vec4 myVec4 = vec4(1.0)                    ;\n" +//把所有参数都设置为1.0，相当于vec4(1.0, 1.0, 1.0, 1.0)
                        "const vec3 myVec3 = vec3(1.0, 0.0, 1.3)          ;\n" +//设置vec3向量
                        "const vec2 myVec2 = vec2(myVec3.x, myVec3.y)     ;\n" +//设置myVec2的值为myVec3[0]和myVec3[1]
                        "const mat3 myMat3 = mat3(1.0, 0.0, 0.0," +
                        "                         1.0, 0.0, 0.0," +
                        "                         1.0, 0.0, 0.0)    ;\n" +//初始化一个3*3的矩阵
                        "const mat4 myMat4 = mat4(1.0)              ;\n" +//初始化一个4*4的矩阵，对角线上的参数设置为1.0
                        "const vec4 col0 = myMat4[0]                ;\n" +
                        "vec3 temp                                  ;\n" +
                        "out vec4 v_color                      ;\n" +
                        "invariant gl_Position                      ;\n" + //不可变性，保证不同编译器输出的结果一致
                        "void main()                                \n" +
                        "{                                          \n" +
                        "temp = myVec3.xyz                          ;\n" +//vec3(1.0, 0.0, 1.3)
                        "temp = myVec3.xxx                          ;\n" +//vec3(1.0, 1.0, 1.0)
                        "temp = myVec3.zyx                          ;\n" +//vec3(1.3, 0.0, 1.0)
                        "specularAtten = float(myBool)              ;\n" +//convert from bool --> float
                        "specularAtten = float(myInt)               ;\n" +//convert from int --> float
                        "myBool = bool(myInt)                       ;\n" +//convert from int --> bool
                        "gl_Position = a_position                    ;\n" +
                        "v_color = a_color         ;\n" +
                        "}                                          \n";

        //片段着色器
        String fShaderStr =
                "#version 300 es                                    \n" +
                        "#define _a_ 1.0                            \n" +//使用宏定义
                        "precision mediump float                    ;\n" +//全局float精度定义为mediump，可以选择为highp lowp
                        "out vec4 fragColor                         ;\n" +
                        "in vec4 v_color                       ;\n" +//这里可以用smooth flat 来修饰
                        "vec4 myVec4 = vec4(1.0)                    ;\n" +
                        "uniform vec4 uColor                        ;\n" +//统一变量，在全局作用域中申明
                        //统一缓冲区默认为std140 可以设置的参数有 shared packed column_major row_major
                        "layout(shared,column_major) uniform ColorBlock{                        \n" +//统一缓冲区对象申明
                        "vec4 bColor                                ;\n" +//这里可以使用block.bColor的方式来获取参数
                        "}block                                     ;\n" +
                        "const vec4 v1 = vec4(0.5, 0.5, 0.5, 0.5)         ;\n" +
                        "const vec4 v2 = vec4(1.0, 1.0, 1.0, 1.0)         ;\n" +
                        "const mat4 myMat4 = mat4(1.0)              ;\n" +//定义的是一个对角线为1.0的矩阵
                        "const mat4 myMat41 = mat4(1.0, 1.0, 1.0, 1.0,1.0, 1.0, 1.0, 1.0,1.0, 1.0, 1.0, 1.0,1.0, 1.0, 1.0, 1.0)              ;\n" +
                        "const vec4 col0 = myMat4[0]                ;\n" +//拿出myMat4的第一列，赋值给col0
                        "const vec4 col1 = myMat4[1]                ;\n" +
                        "const vec4 col2 = myMat4[2]                ;\n" +
                        "const vec4 col3 = myMat4[3]                ;\n" +
                        "const vec3 v31 = vec3(1.0, 1.0, 1.0)       ;\n" +
                        "vec4 color                                 ;\n" +
                        "struct fogStruct{                          \n" +//定义一个struct结构体
                        "vec4 color                                 ;\n" +
                        "float start                                ;\n" +
                        "float end                                  ;\n" +
                        "}fogVar                                    ;\n" +
                        "const float[4] a = float[](1.0, 1.0, 1.0, 1.0);\n" +//定义和初始化一个float数组
                        "const float[4] b = float[4](1.0, 1.0, 1.0, 1.0);\n" +
                        "const vec4[2] c = vec4[2](col0, col1)      ;\n" +//定义和初始化一个vec4的数组
                        "vec4 myFun(in mat4 myMat4, out vec4 myVec4){ \n" +//in表示输入的参数 out表示输出的参数 inout表示输入输出参数
                        "myVec4 = myMat4[2]                         ;\n" +
                        "return myVec4                              ;\n" +
                        "}                                          \n" +
                        "void main()                                \n" +
                        "{                                          \n" +
                        "color = vec4(0.0)                          ;\n" +
                        "fogVar = fogStruct(col0, 0.5, 1.0);        \n" +//初始化结构体
//                        "fragColor = vec4(0.0, myVec4.y, 0.0, myVec4.w)                   ;\n"+
//                        "fragColor = vec4(fogVar.color.x, fogVar.start, fogVar.end, 1.0)                     ;\n" +//使用结构体里面的参数
//                        "fragColor = vec4(a[0], a[1], a[2], a[3])   ;\n"+
//                        "fragColor = col1 * 0.5                           ;\n"+//向量与float的乘法运算
//                        "fragColor = col0 * dot(v31, v31)                  ;\n"+//dot()是计算两个向量的点积
//                        "fragColor = myMat4 * col2                      ;\n"+//
//                        "fragColor = v1 * v2                        ;\n"+//v1*v2=(v1.x*v2.x, v1.y*v2.y, v1.z*v2.z, v1.w*v2.w)
//                        "myFun(myMat4, fragColor)                        ;\n"+
//                        "if(color.r < 0.25){                        \n"+
//                        "color.r = color.r + 0.5                    ;\n"+
//                        "}\n"+
//                        "fragColor = block.bColor                          ;\n"+
//                        "fragColor = v_color                        ;\n"+
//                        "fragColor = vec4(1.0, 1.0, 0.0, 1.0)           ;\n" +
                        "fragColor = v_color                        ;\n" +
                        "}                                          \n";
        int vertexShader;
        int fragmentShader;
//        int programObject;
        int[] linked = new int[1];
        //加载一个顶点着色器
        String vertexShaderStr = ShaderUtil.readRawTxt(this, R.raw.vertex_shader);
        vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexShaderStr);
        //加载一个片段着色器
        String fragmentShaderStr = ShaderUtil.readRawTxt(this, R.raw.fragment_shader);
        fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentShaderStr);

        //创建一个program
        programObject = GLES30.glCreateProgram();
        if (programObject == 0) {
            Log.e(TAG, "glCreateProgram error ");
            return false;
        }
        //关联program与顶点着色器和片段着色器
        GLES30.glAttachShader(programObject, vertexShader);
        GLES30.glAttachShader(programObject, fragmentShader);


        GLES30.glLinkProgram(programObject);

        //添加需要捕捉到变换反馈区的顶点属性
//        final String[] varyings = {"gl_Position", "v_color"};
//        GLES30.glTransformFeedbackVaryings(programObject, varyings, GLES30.GL_INTERLEAVED_ATTRIBS);
//        GLES30.glLinkProgram(programObject);

        //获取program连接状态
        GLES30.glGetProgramiv(programObject, GLES30.GL_LINK_STATUS, linked, 0);
        if (linked[0] == 0) {
            int[] infoLen = new int[1];
            GLES30.glGetProgramiv(programObject, GLES30.GL_INFO_LOG_LENGTH, infoLen, 0);
            if (infoLen[0] > 1) {
                String infoLog = GLES30.glGetProgramInfoLog(programObject);
                Log.e(TAG, "init glLinkProgram error : " + infoLog);
            }
            GLES30.glDeleteProgram(programObject);
            return false;
        }
        //把program设置进去
        userData.setProgramObject(programObject);
        //清除缓存数据
        GLES30.glClearColor(0f, 0f, 0f, 0f);
        return true;
    }

    /**
     * 初始化EGL
     *
     * @param surface
     * @return
     */
    private boolean initEgl(Surface surface) {
        //选择EGLConfig所用到的参数
        int[] configAttribs = {
                EGL14.EGL_SURFACE_TYPE, EGL14.EGL_WINDOW_BIT,
                EGL14.EGL_RED_SIZE, 8,
                EGL14.EGL_GREEN_SIZE, 8,
                EGL14.EGL_BLUE_SIZE, 8,
                EGL14.EGL_DEPTH_SIZE, 24,
                EGL14.EGL_NONE
        };
        //创建EGLContext所用到的参数
        int[] contextAttribs = {
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 3,
                EGL14.EGL_NONE
        };
        //创建EGLSurface所用到的参数
        int[] surfaceAttribs = {
                EGL14.EGL_NONE
        };
        //获取EGLDisplay对象
        mEGLDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
        if (mEGLDisplay == EGL14.EGL_NO_DISPLAY) {
            return false;
        }
        int[] major = new int[1];
        int[] minor = new int[1];
        //初始化EGL
        if (!EGL14.eglInitialize(mEGLDisplay, major, 0, minor, 0)) {
            return false;
        }
        EGLConfig[] config = new EGLConfig[1];
        int[] numConfigs = new int[1];
        //把参数传递给EGL让他选择一个EGLConfig
        if (!EGL14.eglChooseConfig(mEGLDisplay, configAttribs, 0, config, 0, 1, numConfigs, 0)) {
            return false;
        }
        mEGLConfig = config[0];
        //创建一个EGLSurface
        mEGLSurface = EGL14.eglCreateWindowSurface(mEGLDisplay, config[0], surface, surfaceAttribs, 0);
        if (mEGLSurface == EGL14.EGL_NO_SURFACE) {
            return false;
        }
        //创建一个EGLContext
        mEGLContext = EGL14.eglCreateContext(mEGLDisplay, config[0], EGL14.EGL_NO_CONTEXT, contextAttribs, 0);
        if (mEGLContext == EGL14.EGL_NO_CONTEXT) {
            return false;
        }
        return true;
    }

    /**
     * 开始绘制
     *
     * @param esContext
     */
    private void draw(EsContext esContext) {
        UserData userData = esContext.getUserData();
        //确定渲染位置
        GLES30.glViewport(0, 0, esContext.getWidth(), esContext.getHeight());
        //清除数据
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        //使用program
        GLES30.glUseProgram(userData.getProgramObject());


//        DrawUtil.drawColorPositionSeparate();
//        drawColorPositionTogether();
//        drawTogetherWithVbos(esContext);
//        drawSeparateWithVbos(esContext);
//        drawTogetherWithVaos(esContext);
//        DrawUtil.drawMapBuffer(esContext);

//        DrawUtil.drawTriangles();
//        DrawUtil.drawTriangleStrip();
//        DrawUtil.drawTriangleFan();
//        DrawUtil.drawLines();
//        DrawUtil.drawLineStrip();
//        DrawUtil.drawLineLoop();
//        DrawUtil.drawCubeByTriangles();
//        DrawUtil.drawElementsCubeTriangles();
//        DrawUtil.drawElementTriangleStrip();
//        DrawUtil.drawPolygon();
//        TestUtil.testMaxVertexAttribs();

//        DrawUtil.testFeedback(programObject);
//        DrawUtil.drawTexture();

        DrawUtil.drawTextureImage(programObject, this);
        checkGlError("draw");
    }

    /**
     * Block的参数设置
     *
     * @param esContext
     */
    private void setBlock(EsContext esContext) {
        UserData userData = esContext.getUserData();
        int program = userData.getProgramObject();
        //初始化block参数
        float colorData[] = {
                1.0f, 1.0f, 0.0f, 1.0f
        };
        int blockId;
        int bindingPoint = 1;
        int blockSize[] = new int[1];
        int bufferId[] = new int[1];
        //根据名称获取Block的index
        blockId = GLES30.glGetUniformBlockIndex(program, "ColorBlock");
        //将program中的blockId与bindingPoint绑定起来
        GLES30.glUniformBlockBinding(program, blockId, bindingPoint);
        //获取block的长度
        GLES30.glGetActiveUniformBlockiv(program, blockId, GLES30.GL_UNIFORM_BLOCK_DATA_SIZE, blockSize, 0);

        //生成buffer
        GLES30.glGenBuffers(1, bufferId, 0);
        //绑定buffer为bindBuffer类型
        GLES30.glBindBuffer(GLES30.GL_UNIFORM_BUFFER, bufferId[0]);
        //把colorData的数值设置到buffer中去
        GLES30.glBufferData(GLES30.GL_UNIFORM_BUFFER, blockSize[0], DrawUtil.getFloatBuffer(colorData), GLES30.GL_DYNAMIC_DRAW);
        //把buffer与bindingPoint进行绑定，也就是把colorData的数值设置到program中去
        GLES30.glBindBufferBase(GLES30.GL_UNIFORM_BUFFER, bindingPoint, bufferId[0]);
    }

    /**
     * 设置uniform参数
     *
     * @param esContext
     */
    private void setUniform(EsContext esContext) {
        UserData userData = esContext.getUserData();
        int[] numUniforms = new int[1];
        int[] maxUniformLength = new int[1];
        int program = userData.getProgramObject();
        //获取uniforms的数量
        GLES30.glGetProgramiv(program, GLES30.GL_ACTIVE_UNIFORMS, numUniforms, 0);
        //获取uniforms参数的最大长度
        GLES30.glGetProgramiv(userData.getProgramObject(), GLES30.GL_ACTIVE_UNIFORM_MAX_LENGTH, maxUniformLength, 0);

        //uniform参数必须要使用，不使用的话，会被编译器优化掉，导致无法获取到
        for (int i = 0; i < numUniforms[0]; i++) {
            int[] size = new int[1];
            int[] type = new int[1];
            int[] length = new int[1];
            byte[] name = new byte[maxUniformLength[0]];
            int location = -1;
            //根据index获取uniform的类型和名称
            GLES30.glGetActiveUniform(userData.getProgramObject(), i, maxUniformLength[0], length, 0,
                    size, 0, type, 0, name, 0);
            String uniformName = new String(name, 0, length[0]);
            //根据uniform的名称获取location
            location = GLES30.glGetUniformLocation(userData.getProgramObject(), uniformName);
            Log.d(TAG, "location=" + location);
            switch (type[0]) {
                case GLES30.GL_FLOAT_VEC4:
                    //根据location和type设置参数
                    GLES30.glUniform4f(location, 0.0f, 1.0f, 0.0f, 1.0f);
                    break;
                default:
                    break;
            }
        }
    }

    private void shutdown(EsContext esContext) {
        UserData userData = esContext.getUserData();
        GLES30.glDeleteProgram(userData.getProgramObject());
    }

    /**
     * 加载shader
     *
     * @param type      具体shader的类型
     * @param shaderSrc shader源码
     * @return
     */
    private int loadShader(int type, String shaderSrc) {
        int shader;
        int[] compiled = new int[1];
        //创建shader
        shader = GLES30.glCreateShader(type);
        checkGlError("loadShader");
        Log.d(TAG, "loadShader glCreateShader=" + shader);
        if (shader == 0) {
            return 0;
        }
        //把shader源码加载进去
        GLES30.glShaderSource(shader, shaderSrc);
        //编译shader源码
        GLES30.glCompileShader(shader);
        //获取shader源码编译的结果
        GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, compiled, 0);
        //0表示异常
        if (compiled[0] == 0) {
            int[] infoLen = new int[1];
            //获取shader log的长度
            GLES30.glGetShaderiv(shader, GLES30.GL_INFO_LOG_LENGTH, infoLen, 0);
            if (infoLen[0] > 1) {
                //获取shader log具体内容
                String infoLog = GLES30.glGetShaderInfoLog(shader);
                Log.e(TAG, "loadShader error : " + infoLog);
            }
            //删除shader
            GLES30.glDeleteShader(shader);
            return -1;
        }
        return shader;
    }

    public static void checkGlError(String op) {
        int error = GLES30.glGetError();
        if (error != GLES30.GL_NO_ERROR) {
            String msg = op + ": glError 0x" + Integer.toHexString(error);
            Log.e(TAG, msg);
        }
    }

}
