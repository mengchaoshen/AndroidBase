#version 300 es
layout(location = 0) in vec4 a_color;
layout(location = 1) in vec4 a_position; //位置坐标
layout(location = 2) in vec2 af_position;//纹理坐标
out vec2 v_texPo;
out vec4 v_color;


void main() {
    gl_Position = a_position;
    v_color = a_color;
    v_texPo = af_position;
}
