#version 150

#moj_import <projection.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in vec3 Normal;


uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform mat3 IViewRotMat;

out vec4 texProj0;
out vec2 texCoord0;
out vec4 vertexColor;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
    vec4 pos = ProjMat * ModelViewMat * vec4(Position * 100, 1.0);
    texProj0 = projection_from_position(pos);
    //texProj0 = vec4(UV0, UV0);//projection_from_position(gl_Position);
    texCoord0  = UV0;
    vertexColor = Color;
}
