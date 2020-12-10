package aes;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class AESEngine {

    static String[] keyTypes = {"128 bits", "192 bits", "256 bits"};
    static String[] hashTypes = {"MD5", "SHA-1", "SHA-256", "SHA-384", "SHA-512"};
    private SecretKeySpec key;
    private int mode;
    private String hash;
    private int keySize;
    private boolean save; // for encryption
    private boolean isSaved; // for decryption
    private static int sizeOfMeta = 7;

    public AESEngine(String key, String hash, int keySize, int mode) {   // keySize should be in byte
        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        this.key = keygen(Arrays.copyOf(messageDigest.digest(key.getBytes()), keySize));
        this.keySize = keySize;
        this.hash = hash;
        this.mode = mode;
    }


    public void setSaved(boolean saved) {
        isSaved = saved;
    }

    public void setSave(boolean save) {
        this.save = save;
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
            byte[] temp;
            if (isSaved) {
                temp = cipher.doFinal(Arrays.copyOfRange(data, sizeOfMeta, data.length));
            } else {
                temp = cipher.doFinal(data);
            }
            FileOutputStream outputStream = new FileOutputStream(file);
            if (save) {
                byte[] savedArr = new byte[temp.length + sizeOfMeta];
                String text = "saved" + getKeyByte() +""+ getHashByte();
                byte[] saveText = text.getBytes();
                System.arraycopy(saveText, 0, savedArr, 0, sizeOfMeta);
                System.arraycopy(temp, 0, savedArr, sizeOfMeta, temp.length);
                outputStream.write(savedArr);
            } else {
                outputStream.write(temp);
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte getKeyByte() {
        switch (keySize) {
            case 16:
                return 0;
            case 24:
                return 1;
            case 32:
                return 2;
            default:
                return 3;
        }
    }

    private byte getHashByte() {
        switch (hash) {
            case "MD5":
                return 0;
            case "SHA-1":
                return 1;
            case "SHA-256":
                return 2;
            case "SHA-384":
                return 3;
            case "SHA-512":
                return 4;
            default:
                return 5;
        }
    }
    /**
     * ---------rules for meta data adding------------
     * <p>
     * If first 5 bytes of the file represent "saved" there should be meta data for decrypting
     * else get key size and hashing algorithm from the user.
     * <p>
     * After "saved" there should be two bytes of data. first one for key size and
     * second one for hashing algorithm
     * <p>
     * For key size
     * 0 - 128 bits
     * 1 - 192 bits
     * 2 - 256 bits
     * <p>
     * For Hashing algo
     * 0 - MD5
     * 1 - SHA-1
     * 2 - SHA-256
     * 3 - SHA-384
     * 4 - SHA-512
     */

    public static boolean checkForSave(File file) {
        boolean ret = false;
        byte[] data = new byte[(int) file.length()];
        try {
            File file1 = file;
            if (file.isDirectory()) {
                file1 = file.listFiles()[0];
            }
            FileInputStream fileInputStream = new FileInputStream(file1);
            fileInputStream.read(data);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        byte[] word = new byte[5];
        System.arraycopy(data, 0, word, 0, 5);
        if (new String(word).equals("saved"))
            ret = true;
        return ret;

    }

    public static byte [] getSavedData(File inputFile){
        FileInputStream stream = null;
        byte [] temp = new byte[sizeOfMeta];
        try {
            stream = new FileInputStream(inputFile);
            stream.read(temp,0,sizeOfMeta);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(temp);
      return  Arrays.copyOfRange(temp,5,sizeOfMeta);
    }

}
