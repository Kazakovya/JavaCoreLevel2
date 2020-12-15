package ru.geekbrains;

public class Main {
    static final int SIZE = 1_000_0000;
    static final int H = SIZE / 2;
    public static void main(String[] args) throws InterruptedException {
        float[] arr = new float[SIZE];
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1f;
        }
        long a = System.currentTimeMillis();
        method1(arr);
        System.out.println("Time1: " + (System.currentTimeMillis() - a));
        long b = System.currentTimeMillis();
        method2(arr);
        System.out.println("Time2: " + (System.currentTimeMillis() - b));
    }

    public static void method1(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    }
    public static void method2(float[] arr) throws InterruptedException {
        float[] arr1 = new float[H];
        float[] arr2 = new float[H];
        System.arraycopy(arr, 0, arr1, 0, H);
        System.arraycopy(arr, H, arr2, 0, H);
        Thread thread1 = new Thread(() ->{
            for (int i = 0; i < H; i++) {
                arr1[i] = (float)(arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        Thread thread2 = new Thread(() ->{
            for (int i = 0; i < H; i++) {
                arr2[i] = (float)(arr2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.arraycopy(arr1, 0, arr, 0, H);
        System.arraycopy(arr2, 0, arr, H, H);
    }
}
