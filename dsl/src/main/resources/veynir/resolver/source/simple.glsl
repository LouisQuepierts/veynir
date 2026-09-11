#include "veynir.glsl"

uniform sampler1D u_source;

in float time;
out vec3 position;

void main() {
    veynir_sample(u_source, time);

    vec4 result = veynir_sampler(u_source, time);

    float array[] = float[2]();
    vec3 a;
    float b = 2f;
    int i = 0x00;
}
