package aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AESEngine {
    private SecretKeySpec key;
    private int mode;


    public AESEngine(String key, String hash, int keySize, int mode) {   // keySize should be in byte
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.key = keygen(Arrays.copyOf(messageDigest.digest(key.getBytes()), keySize));


        this.mode = mode;
    }

    private SecretKeySpec keygen(byte[] arr) {
        return new SecretKeySpec(arr, "AES");
    }

    public boolean crypt(File inputFile) {
        try {
            if (inputFile.isDirectory())
                recuCryption(inputFile);
            else
                doCrypt(inputFile);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void recuCryption(File inputFile) {
        for (File file : inputFile.listFiles()) {
            if (file.isDirectory()) {
                recuCryption(file);
                continue;
            }
            doCrypt(file);
        }
    }

    private void doCrypt(File file) {

        try {
            byte[] data = new byte[(int) file.length()];
            FileInputStream inputStream = new FileInputStream(file);
            inputStream.read(data);
            inputStream.close();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(mode, key);
            byte[] temp = cipher.doFinal(data);
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(temp);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
