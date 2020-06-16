#include <jni.h>

int test(){
    return 123;
}

//com.assess15.ndk.NDKActivity
jint Java_com_assess15_ndk_NDKActivity_nativeTest(JNIEnv *env,jobject instance){
    return test();
}


