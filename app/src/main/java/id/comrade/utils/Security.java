package id.comrade.utils;

import android.annotation.SuppressLint;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Security {
    @SuppressLint("GetInstance")
    public static String decrypt(String seed, String encrypted)  {
        byte[] keyb;
        try {
            keyb = seed.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "";
        }

        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            return "";
        }

        byte[] thedigest = md.digest(keyb);
        SecretKeySpec skey = new SecretKeySpec(thedigest, "AES/ECB/PKCS7Padding");
        Cipher dcipher;
        byte[] cleayByte;
        try {
            dcipher = Cipher.getInstance("AES/ECB/PKCS7Padding");
            dcipher.init(Cipher.DECRYPT_MODE, skey);
            cleayByte = dcipher.doFinal(toByte(encrypted));
        } catch (Exception e) {
            return "";
        }

        return new String(cleayByte);
    }

    private static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        }

        return result;
    }
}