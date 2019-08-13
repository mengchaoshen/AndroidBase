#version 300 es

precision mediump float;//定义一个精度
in vec4 v_color;
out vec4 outColor;
uniform vec4 uColor;
void main() {
    outColor = uColor;
}
