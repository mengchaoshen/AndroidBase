#version 300 es
precision mediump float;//定义一个精度
layout(location = 0) out vec4 fragData0;
layout(location = 1) out vec4 fragData1;
layout(location = 2) out vec4 fragData2;
layout(location = 3) out vec4 fragData3;
void main() {
    fragData0 = vec4(1, 0, 0, 1);
    fragData1 = vec4(0, 1, 0, 1);
    fragData2 = vec4(0, 0, 1, 1);
    fragData3 = vec4(0.5, 0.5, 0.5, 1);
}
