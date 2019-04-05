/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelproofofwork;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author up788458
 */
public class ParallelProofOfWork {
    
    final static String Difficulty = "0000000";
    final static String Message = "This message is being hashed";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // Calculat hashes per sec for number of threads
        System.out.println("Hashes Per Second: " + CalculateHashesPerSecond(Message));
            
        // count how many iterations it did before being successful
        int iterations = 0;

        // start the actually algorith, start counting the time
        long startTime = System.currentTimeMillis();
        String result;
        String nonce;
        do {
            iterations++;
            nonce = RandomNonce();
            result = HashMessage(Message + nonce);
        }
        while (!result.startsWith(Difficulty));

        // Print results
        long timeToComplete = System.currentTimeMillis() - startTime;
        System.out.println("\nProof Of Work Complete\n----------------------------");
        System.out.println(iterations + " iterations, " + timeToComplete + "ms, nonce: " + nonce + ", result: " + result);
    }
    
    
    
    
    private static String RandomNonce() {
        Random rand = new Random();
        return Integer.toString(
            rand.nextInt((int)Math.pow(2, 32)) // max integer size, do we need to change to big int?
        );
    }
    
    private static String HashMessage(String msg) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
        return BytesToHex(
                hasher.digest(
                        msg.getBytes("UTF-8")
                )
        );
    }
    
    private static String BytesToHex(byte[] bytes) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
        String hex = Integer.toHexString(0xff & bytes[i]);
        if(hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
    
    private static int CalculateHashesPerSecond(String msg) {
        int NumberOfHashes = 100000;
        
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < NumberOfHashes; i++) {
            try {
                HashMessage(msg);
            } catch (Exception ex) {
                System.out.println("HASHING ERROR!!!");
            }
        }
        
        long totalTime = System.currentTimeMillis() - startTime;
        double hashesPerMs = NumberOfHashes / totalTime; //get average hashes per 1ms
        return (int)Math.floor(hashesPerMs * 1000); // 1000ms in 1s
    }
    
}
