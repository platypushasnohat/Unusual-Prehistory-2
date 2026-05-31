#version 120

uniform sampler2D DiffuseSampler;
uniform float Exposure;

varying vec2 texCoord;

void main() {
    vec4 color = texture2D(DiffuseSampler, texCoord);

    color.rgb = vec3(1.0) - exp(-color.rgb * Exposure);

    gl_FragColor = color;
}