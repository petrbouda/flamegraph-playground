package pbouda.flamegraph;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypt {

    private static final String TEXT =
            "Flamescope is a new open source performance visualization tool that uses subsecond" +
            " offset heat maps and flame graphs to analyze periodic activity, variance, and perturbations. " +
            "We posted this on the Netflix TechBlog, Netflix FlameScope, and the tool is on github. " +
            "While flame graphs are well understood, subsecond offset heat maps are not " +
            "(they are another visualization I invented a while ago). FlameScope should help adoption.";

    public static String encrypt() {
        return encrypt(TEXT);
    }

    public static String encrypt(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512");
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            return hashtext;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}