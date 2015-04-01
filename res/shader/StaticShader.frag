#version 400 core
in vec2 pass_texCoord;

out vec4 out_Color;

uniform sampler2D textureSampler;
uniform float isHighlighted;

void main(void){
	out_Color = texture(textureSampler, pass_texCoord) * vec4(1-(isHighlighted*0.1), 1-(isHighlighted*0.5), 1-isHighlighted, 1);
}