package com.gooproper.util;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class EncryptionUtil {
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";

    public static String decrypt(String encryptedData, String key) throws Exception {
        String[] parts = encryptedData.split(",");
        byte[] encryptedBytes = Base64.decode(parts[0], Base64.DEFAULT);
        byte[] iv = Base64.decode(parts[1], Base64.DEFAULT);

        IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), ALGORITHM);

        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] original = cipher.doFinal(encryptedBytes);
        return new String(original);
    }
}
