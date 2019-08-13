#version 300 es
layout(location = 0) in vec4 a_position;//位置坐标
layout(location = 1) in vec2 a_texCoord;//纹理坐标
out vec2 v_texCoord;

vec4 u_clipPlane = vec4(0.6, 0.5, 0.1, 0);
out float v_clipDist;

void main() {
    //计算裁剪平面之上
    v_clipDist = dot(a_position.xyz, u_clipPlane.xyz) + u_clipPlane.w;

    gl_Position = a_position;
    v_texCoord = a_texCoord;
}
