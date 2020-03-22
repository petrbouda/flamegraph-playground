package pbouda.flamegraph;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalTime;

public class ComputeTask implements Runnable {

    private static final String MESSAGE =
            "Flamescope is a new open source performance visualization tool that uses subsecond" +
            " offset heat maps and flame graphs to analyze periodic activity, variance, and perturbations. " +
            "We posted this on the Netflix TechBlog, Netflix FlameScope, and the tool is on github. " +
            "While flame graphs are well understood, subsecond offset heat maps are not " +
            "(they are another visualization I invented a while ago). FlameScope should help adoption.";

//    public static void main(String[] args) {
//        long start = System.nanoTime();
//        for (int i = 0; i < 10_000; i++) {
//            encrypt(MESSAGE + " " + i);
//        }
//        System.out.println(Duration.ofNanos(System.nanoTime() - start).toMillis());
//    }

    @Override
    public void run() {
        long start = System.nanoTime();
        for (int i = 0; i < 50_000; i++) {
            Encrypt.encrypt(MESSAGE + " " + i);
        }
        long duration = Duration.ofNanos(System.nanoTime() - start).toMillis();
        System.out.println("Time: " + LocalTime.now() + " - Duration: " + duration + "ms");
    }
}
