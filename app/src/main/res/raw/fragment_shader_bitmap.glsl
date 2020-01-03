#version 300 es

precision mediump float;//定义一个精度
uniform sampler2D vTexture;
in vec2 aCoordinate;
out vec4 fragColor;

void main(){
    fragColor = texture(vTexture, aCoordinate);
}
