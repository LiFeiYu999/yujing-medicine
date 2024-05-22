package com.yujing.medicine.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * @author 栗振飞
 * @version 1.0
 * @date 2024/5/13 19:05
 * 对注册的账号密码进行MD5加密
*/
public class MD5Util {
    public static String hashPassword(String password) {
        try {
            // 创建MessageDigest对象，指定使用MD5算法
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 将密码转换为byte数组
            byte[] passwordBytes = password.getBytes();
            // 使用指定的byte数组更新摘要
            md.update(passwordBytes);
            // 获取摘要的字节数组
            byte[] digest = md.digest();
            // 将字节数组转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean verify(String password, String hashedPassword) {
        // 对输入的密码进行加密
        String encryptedPassword = hashPassword(password);
        // 验证加密后的密码与哈希值是否相等
        return hashedPassword.equals(encryptedPassword);
    }

    // 检查用户输入的密码是否与存储的哈希密码匹配
    public static boolean checkPassword(String candidatePassword, String storedHashedPassword) throws NoSuchAlgorithmException {
        // 对候选密码进行哈希
        String hashedCandidatePassword = hashPassword(candidatePassword);
        // 比较哈希值是否相同
        return hashedCandidatePassword.equals(storedHashedPassword);
    }

}
