/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parallelproofofwork;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 *
 * @author up788458
 */
public class ParallelProofOfWork {
    
    private int RandomNumber() {
        throw new UnsupportedOperationException();
    }
    
    private static String HashMessage(String msg) throws NoSuchAlgorithmException {
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");
        return BytesToHex(
                hasher.digest(
                        msg.getBytes()
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException {
        String Difficulty = "00000";
        
        Random rand = new Random();
        String message = "This message is being hashed";
        int iterations = 0;
        
        long startTime = System.currentTimeMillis();
        String result;
        do {
            iterations++;
            int nonce = rand.nextInt(1000000);
            result = HashMessage(message + Integer.toString(nonce));
        } while (!result.startsWith(Difficulty));
        
        long timeToComplete = System.currentTimeMillis() - startTime;
        System. out. println(iterations + " iterations, " + timeToComplete + "ms");
        
        
    }
    
}
