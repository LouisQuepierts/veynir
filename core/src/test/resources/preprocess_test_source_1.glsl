#define MACRO_1 1
#define MACRO_2 100
#define MACRO_3 400

#define MULTI_LINE_MACRO int x; \
int j; \
int k;

// this is a comment

#ifdef MACRO_1
int a = 1;
#endif
#ifdef MACRO_2
int b = 2;
#endif

#undef MACRO_3

#ifdef MACRO_3
int c = 3;
int d = 4;
#endif

#define MACRO_3 550

#if MACRO_1 == 1
int e = 5;
#elif MACRO_2 + MACRO_3 == 300
int f = 6;
#endif

/*
* this is a block comment
*/