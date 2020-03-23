package pbouda.flamegraph;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OffCpuThreadPool {

    public static void main(String[] args) throws InterruptedException {
        Runnable task = () -> {
            Encrypt.encrypt();
            method1(1);
        };

        ExecutorService executor = Executors.newFixedThreadPool(4, new NamedThreadFactory("monitored"));
        for (int i = 0; true; i++) {
            executor.execute(task);
            if (i % 2 == 0) {
                Thread.sleep(1000);
            }
        }
    }

    private static void method1(int i) {
        method2(i);
    }

    private static void method2(int i) {
        method3(i);
    }

    private static void method3(int i) {
        method4(i);
    }

    private static void method4(int i) {
        method5(i);
    }

    private static void method5(int i) {
        method6(i);
    }

    private static void method6(int i) {
        method7(i);
    }

    private static void method7(int i) {
        method8(i);
    }

    private static void method8(int i) {
        method9(i);
    }

    private static void method9(int i) {
        method10(i);
    }

    private static void method10(int i) {
        waiting();
    }

    private static void waiting() {
        try {
            Thread.sleep(1000);
            System.out.println("DONE");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
