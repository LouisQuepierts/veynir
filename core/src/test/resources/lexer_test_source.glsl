
struct data {
    vec3 position;
    vec3 velocity;
    vec3 acceleration;
};

uniform float u_simple;
uniform data u_data;
layout(binding = 3) uniform vec2 u_layouted;
layout(binding = 1) uniform u_block {
    int index;
    vec3 color;
};

in float in_simple;
layout(location = 0) in vec2 in_layouted;
layout(location = 1) in IN {
    float in_block;
    vec3 position;
} in_block;

out float out_simple;
layout(location = 0) out vec2 out_layouted;
layout(location = 1) out OUT {
    float out_block;
    vec3 position;
} out_block;

float field_simple;
const float field_constant = 4.0;
void main() {
    bool a = true;
    bool b = a;

    int c = 1;
    int d = c;
}

int function(in vec3 a, out int b) {
    int c[2], d = b;
    c[0] = int(a.x);
    vec2 uv = a.rb;
    return int(uv.x);
}