#version 300 es
in vec4 vPosition;
in vec2 vCoordinate;
out vec2 aCoordinate;

void main(){
    gl_Position = vPosition;
    aCoordinate = vCoordinate;
}
