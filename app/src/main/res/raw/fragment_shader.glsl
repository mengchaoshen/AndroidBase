#version 300 es

precision mediump float;
in vec2 v_texPo;
out vec4 fragColor;
uniform sampler2D sTexture;
void main() {
    //根据传入的纹理坐标和sTexture生成color
    fragColor = texture(sTexture, v_texPo);
}
