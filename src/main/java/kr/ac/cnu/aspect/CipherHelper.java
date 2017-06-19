package kr.ac.cnu.aspect;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * Created by rokim on 2017. 6. 12..
 */
@Slf4j
public class CipherHelper {
    public static final SecretKey secretKey;
    private static final String KEY_STRING = "MZygpewJsCpRrfOr";
    private static Cipher encryptCipher;
    private static Cipher drcryptCipher;

    static {
        secretKey = generateMySQLAESKey(KEY_STRING, "UTF-8");

        try {
            encryptCipher = Cipher.getInstance("AES");
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);

            drcryptCipher = Cipher.getInstance("AES");
            drcryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            encryptCipher = null;
            drcryptCipher = null;
            e.printStackTrace();
        }
    }

    public static String decryptHexAES128(String string) {
        try {
            byte[] decVal = Hex.decodeHex(string.toCharArray());
            byte[] bytes = drcryptCipher.doFinal(decVal);

            return new String(bytes);
        } catch (DecoderException | IllegalBlockSizeException | BadPaddingException e) {
            log.error("AES decrypt 오류 : KEY[" + secretKey + "] VALUE : [" + string + "]", e);
            e.printStackTrace();
            return null;
        }
    }

    public static String encryptHexAES128(String string) {
        try {
            byte[] encVal = encryptCipher.doFinal(string.getBytes());
            char[] chars = Hex.encodeHex(encVal);

            return new String(chars);
        } catch (IllegalBlockSizeException | BadPaddingException e) {
            log.error("AES encrypt 오류 : KEY[" + secretKey + "] VALUE : [" + string + "]", e);
            e.printStackTrace();
            return null;
        }
    }

    private static SecretKeySpec generateMySQLAESKey(final String key, final String encoding) {
        try {
            final byte[] finalKey = new byte[16];
            int i = 0;
            for(byte b : key.getBytes(encoding)) {
                finalKey[i++%16] ^= b;
            }
            return new SecretKeySpec(finalKey, "AES");
        } catch(UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
