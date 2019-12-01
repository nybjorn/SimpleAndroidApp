extern crate jni;

use jni::JNIEnv;
use jni::objects::{JClass, JString};
use jni::sys::jstring;
use jni::sys::jint;
//use jni::sys::jintArray;

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

#[no_mangle]
#[allow(non_snake_case)]
pub extern "C" fn Java_com_example_simplerustlibrary_RustKt_mandelrust(
    env: JNIEnv,
    _: JClass,
    j_width: jint,
    j_height: jint
) -> jstring {
    let width = j_width as i32;
    let height = j_height as i32;
    let mut pixels : Vec<jint> = vec![0; (width * height) as usize];
    let mut large_n: i32;
    for x in 0..width - 1 {
        for y in 0..height - 1 {
            let mut a = map_over(x as f32, 0.0, width as f32, -2.0, 2.0);
            let mut b = map_over(y as f32, 0.0, height as f32, -2.0, 2.0);
            let large_a = a;
            let large_b = b;
            let max_iterations = 1000;
            for n in 0..max_iterations {
                let ab = a * a - b * b;
                let bb = 2.0 * a * b;
                a = ab + large_a;
                b = bb + large_b;
                large_n = n;
                if a * a + b * b > 4 as f32 {
                    break;
                }
                let pixel = x + y * width;
                let brightness: i32;
                if large_n == max_iterations {
                    brightness = 0;
                } else {
                    brightness = map_over((large_n as f32 / max_iterations as f32).sqrt(), 0.0, 1.0, 0.0, 255.0) as i32;
                }

                let color = color(255, brightness, brightness * 2, brightness);
                pixels[pixel as usize] = color;
            }
        }
    }
    let out_array = env
        .new_int_array(pixels.len() as i32)
        .expect("Couldn't create a Java int array!");
 /*   env
        .set_int_array_region(out_array, pixels.len() as i32, &pixels)
        .expect("Couldn't fill a Java int array!");

    out_array
    */
    let output = env
        .new_string(format!("Hello from Rust: {}", "Test"))
        .expect("Couldn't create a Java string!");
    output.into_inner()
}

fn color(alpha: i32, red: i32, green: i32, blue: i32) -> i32 {
    return (alpha << 24) | (red << 16) | (green << 8) | blue;
}

fn map_over(n: f32, start1: f32, end1: f32, start2: f32, end2: f32) -> f32 {
    ((n - start1) / (end1 - start1)) * (end2 - start2) + start2
}


