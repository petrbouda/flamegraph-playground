package pbouda.flamegraph;

import java.io.IOException;

public class Inlining {

    private static final String TEXT =
            "Flamescope is a new open source performance visualization tool that uses subsecond" +
            " offset heat maps and flame graphs to analyze periodic activity, variance, and perturbations. " +
            "We posted this on the Netflix TechBlog, Netflix FlameScope, and the tool is on github. " +
            "While flame graphs are well understood, subsecond offset heat maps are not " +
            "(they are another visualization I invented a while ago). FlameScope should help adoption.";

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
