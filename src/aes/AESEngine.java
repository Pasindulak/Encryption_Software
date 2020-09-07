package aes;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AESEngine {
    private SecretKeySpec key;
    private boolean file ; // true for file and false for folder

    public AESEngine(String key, String hash, int keySize, boolean file) {   // keySize should be in byte
        MessageDigest messageDigest=null;
        try {
            messageDigest = MessageDigest.getInstance(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
          this.key = keygen(Arrays.copyOf(messageDigest.digest(key.getBytes()),keySize));

        this.file = file;
    }

    private SecretKeySpec keygen(byte [] arr){
       return new SecretKeySpec(arr , "AES");
    }
}
