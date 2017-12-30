package com.example.kwanat.mma_mobilemanagementassistant;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;

/**
 * Created by Kamil on 25.10.2017.
 */

public class Sha1Crypt {
    private static final String TAG = "Sha1Crypt";
    public static String encryptPassword(String password)
    {
        String sha1 = "";
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            sha1 = byteToHex(crypt.digest());
        }
        catch(NoSuchAlgorithmException e)
        {
            Log.e(TAG, e.getMessage());
        }
        catch(UnsupportedEncodingException e)
        {
            Log.e(TAG, e.getMessage());
        }
        return sha1;
    }

    private static String byteToHex(final byte[] hash)
    {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
