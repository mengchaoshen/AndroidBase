package com.smc.androidbase.opengles;

import android.opengl.GLES20;
import android.opengl.GLES30;

/**
 * @author shenmengchao
 * @version 1.0.0
 */

public class MrtsHelper {

    static int[] attachments = {
            GLES30.GL_COLOR_ATTACHMENT0,
            GLES30.GL_COLOR_ATTACHMENT1,
            GLES30.GL_COLOR_ATTACHMENT2,
            GLES30.GL_COLOR_ATTACHMENT3
    };

    static float vVertices[] = {-1.0f, 1.0f, 0.0f,
            -1.0f, -1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            1.0f, 1.0f, 0.0f,
    };
    static short indices[] = {0, 1, 2, 0, 2, 3};


    public static void draw(EsContext esContext) {
        init(esContext);
        Draw(esContext);
    }

    /**
     * 将FBO和Texture进行初始化和绑定
     *
     * @param esContext
     */
    static void init(EsContext esContext) {
        UserData userData = esContext.getUserData();
        int[] defaultFrameBuffer = new int[1];
        GLES30.glGetIntegerv(GLES30.GL_FRAMEBUFFER_BINDING, defaultFrameBuffer, 0);
        GLES30.glGenFramebuffers(1, userData.getFbo(), 0);
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, userData.getFbo()[0]);
        GLES30.glGenTextures(4, userData.getColorTexId(), 0);
        for (int i = 0; i < 4; i++) {
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, userData.getColorTexId()[i]);
            GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D, 0, GLES30.GL_RGBA, esContext.getWidth(), esContext.getHeight(),
                    0, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, null);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_NEAREST);
            GLES30.glFramebufferTexture2D(GLES30.GL_DRAW_FRAMEBUFFER, attachments[i], GLES30.GL_TEXTURE_2D,
                    userData.getColorTexId()[i], 0);
        }
        GLES30.glDrawBuffers(4, DrawUtil.getIntBuffer(attachments));
        if (GLES30.GL_FRAMEBUFFER_COMPLETE != GLES30.glCheckFramebufferStatus(GLES30.GL_FRAMEBUFFER)) {
            return;
        }
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, defaultFrameBuffer[0]);
    }

    /**
     * 这里调用DrawGeometry()绘制大正方形，blitTextures渲染不同的小正方形
     *
     * @param esContext
     */
    static void Draw(EsContext esContext) {
        UserData userData = esContext.getUserData();
        int[] defaultFrameBuffer = new int[1];
        GLES30.glGetIntegerv(GLES30.GL_FRAMEBUFFER_BINDING, defaultFrameBuffer, 0);
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, userData.getFbo()[0]);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
        GLES30.glDrawBuffers(4, DrawUtil.getIntBuffer(attachments));
        DrawGeometry(esContext);
        GLES30.glBindFramebuffer(GLES30.GL_FRAMEBUFFER, defaultFrameBuffer[0]);
        blitTextures(esContext);
    }

    /**
     * 将attachements里面的数据渲染出来
     *
     * @param esContext
     */
    static void blitTextures(EsContext esContext) {
        UserData userData = esContext.getUserData();
        GLES30.glBindFramebuffer(GLES30.GL_READ_FRAMEBUFFER, userData.getFbo()[0]);

        GLES30.glReadBuffer(GLES30.GL_COLOR_ATTACHMENT0);
        GLES30.glBlitFramebuffer(0, 0, esContext.getWidth(), esContext.getHeight(),
                0, 0, esContext.getWidth() / 2, esContext.getHeight() / 2,
                GLES30.GL_COLOR_BUFFER_BIT, GLES30.GL_LINEAR);

        GLES30.glReadBuffer(GLES30.GL_COLOR_ATTACHMENT1);
        GLES30.glBlitFramebuffer(0, 0, esContext.getWidth(), esContext.getHeight(),
                esContext.getWidth() / 2, 0, esContext.getWidth(), esContext.getHeight() / 2,
                GLES30.GL_COLOR_BUFFER_BIT, GLES30.GL_LINEAR);

        GLES30.glReadBuffer(GLES30.GL_COLOR_ATTACHMENT2);
        GLES30.glBlitFramebuffer(0, 0, esContext.getWidth(), esContext.getHeight(),
                0, esContext.getHeight() / 2, esContext.getWidth() / 2, esContext.getHeight(),
                GLES30.GL_COLOR_BUFFER_BIT, GLES30.GL_LINEAR);

        GLES30.glReadBuffer(GLES30.GL_COLOR_ATTACHMENT3);
        GLES30.glBlitFramebuffer(0, 0, esContext.getWidth(), esContext.getHeight(),
                esContext.getWidth() / 2, esContext.getHeight() / 2, esContext.getWidth(), esContext.getHeight(),
                GLES30.GL_COLOR_BUFFER_BIT, GLES30.GL_LINEAR);
    }

    /**
     * 绘制大的正方形
     *
     * @param esContext
     */
    static void DrawGeometry(EsContext esContext) {
        UserData userData = esContext.getUserData();
        // Set the viewport
        GLES30.glViewport(0, 0, esContext.getWidth(), esContext.getHeight());

        // Clear the color buffer
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);

        // Use the program object
        GLES30.glUseProgram(userData.getProgramObject());

        // Load the vertex position
        GLES30.glVertexAttribPointer(0, 3, GLES30.GL_FLOAT,
                false, 3 * Float.BYTES, DrawUtil.getFloatBuffer(vVertices));
        GLES30.glEnableVertexAttribArray(0);

        // Draw a quad
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, 6, GLES30.GL_UNSIGNED_SHORT, DrawUtil.getShortBuffer(indices));
    }


}
