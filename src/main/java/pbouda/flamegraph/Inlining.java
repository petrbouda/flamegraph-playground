package pbouda.flamegraph;

import java.io.IOException;

public class Inlining {

    public static void main(String[] args) throws InterruptedException, IOException {
        System.in.read();

        for (int i = 0; i < 60_000; i++) {
            method1(i);
            if (i % 100 == 0) {
                Thread.sleep(100);
            }
        }
    }

    private static void method1(int i) {
//        Encrypt.encrypt(TEXT);
        method2(i);
    }

    private static void method2(int i) {
//        Encrypt.encrypt(TEXT);
        method3(i);
    }

    private static void method3(int i) {
//        Encrypt.encrypt(TEXT);
        method4(i);
    }

    private static void method4(int i) {
//        Encrypt.encrypt(TEXT);
        method5(i);
    }

    private static void method5(int i) {
//        Encrypt.encrypt(TEXT);
        method6(i);
    }

    private static void method6(int i) {
//        Encrypt.encrypt(TEXT);
        method7(i);
    }

    private static void method7(int i) {
//        Encrypt.encrypt(TEXT);
        method8(i);
    }

    private static void method8(int i) {
//        Encrypt.encrypt(TEXT);
        method9(i);
    }

    private static void method9(int i) {
//        Encrypt.encrypt(TEXT);
        method10(i);
    }

    private static void method10(int i) {
//        Encrypt.encrypt(TEXT);
        System.out.println("Iteration: " + i);
    }
}
