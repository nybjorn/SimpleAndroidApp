#[macro_use]
extern crate criterion;
extern crate simplerust;

use criterion::{black_box, criterion_group, criterion_main, Criterion};
use simplerust::mandelbrot;

fn criterion_benchmark(c: &mut Criterion) {
    c.bench_function("mandelbrot 200", |b| b.iter(|| mandelbrot(black_box(200), black_box(200))));
}

criterion_group!(benches, criterion_benchmark);
criterion_main!(benches);