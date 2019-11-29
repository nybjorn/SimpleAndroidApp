extern crate jni;

use jni::JNIEnv;
use jni::objects::{JClass, JString};
use jni::sys::jstring;

/*
pub extern "system"
pub extern "C"
*/
#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_com_example_simplerustlibrary_RustKt_hello(
    env: JNIEnv,
    _: JClass,
    input: JString,
) -> jstring {
    let input: String = env
        .get_string(input)
        .expect("Couldn't get Java string!")
        .into();
    let output = env
        .new_string(format!("Hello from Rust: {}", input))
        .expect("Couldn't create a Java string!");
    output.into_inner()
}
