#version 300 es
layout(location = 0) in vec4 a_position;//位置坐标

void main() {
    gl_Position = a_position;
}
