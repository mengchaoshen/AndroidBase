#version 300 es

precision mediump float;//定义一个精度
in vec2 v_texCoord;
layout(location=0) out vec4 outColor;
uniform sampler2D s_baseMap;
uniform sampler2D s_lightMap;

in float v_clipDist;
void main() {
    vec4 baseColor = texture(s_baseMap, v_texCoord);
    vec4 lightColor = texture(s_lightMap, v_texCoord);
//    outColor = baseColor * (lightColor + 0.25);
    if (v_clipDist < 0.0){
        discard;
    } else {
        outColor = baseColor * (lightColor + 0.25);
    }
}
