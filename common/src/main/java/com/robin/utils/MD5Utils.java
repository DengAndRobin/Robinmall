package com.robin.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    public static String md5(String password){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(password.getBytes());
            return new BigInteger(1,messageDigest.digest()).toString(16);
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
