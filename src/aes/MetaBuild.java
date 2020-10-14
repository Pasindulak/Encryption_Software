package aes;

public class MetaBuild extends AESEngine{


       /**   ---------rules for meta data adding------------
        * If first 5 bytes of the file represent "saved" there should be meta data for decrypting
        * else get key size and hashing algorithm from the user.
        *
        * After "saved" there should be two bytes of data. first one for key size and
        * second one for hashing algorithm
        *
        * For key size
        *      0 - 128 bits
        *      1 - 192 bits
        *      2 - 256 bits
        *
        * For Hashing algo
        *      0 - MD5
        *      1 - SHA-1
        *      2 - SHA-256
        *      3 - SHA-384
        *      4 - SHA-512
        *
        * */



    public MetaBuild(String key, String hash, int keySize, int mode,boolean save) {
        super(key, hash, keySize, mode);
    }


}
