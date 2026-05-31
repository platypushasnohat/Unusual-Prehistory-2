#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;
varying vec2 oneTexel;

uniform vec2 BlurDir;
uniform float Radius;

void main() {
    vec4 blurred = vec4(0.0);
    float totalAlpha = 0.0;
    float totalSamples = 0.0;

    for (float r = -Radius; r <= Radius; r += 1.0) {
        vec4 texSample = texture2D(
            DiffuseSampler,
            texCoord + oneTexel * r * BlurDir
        );

        blurred += texSample;
        totalAlpha += texSample.a;
        totalSamples += 1.0;
    }

    blurred.rgb /= totalSamples;
    float avgAlpha = totalAlpha / totalSamples;

    gl_FragColor = vec4(blurred.rgb, avgAlpha);
}