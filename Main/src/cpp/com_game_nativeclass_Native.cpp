#include <bits/stdc++.h>
#include <jni.h>
#include "com_game_nativeclass_Native.h"

JNIEXPORT jint JNICALL Java_com_game_nativeclass_Native_getTable (JNIEnv * env, jobject obj, jintArray table, int N){
        jint *prev = env -> GetIntArrayElements(table, NULL);
        jint t[N];
        t[1] = 69;
        std::cout << t[1] << std::endl;
        env -> ReleaseIntArrayElements(table, t, 0);
        return 0;
}