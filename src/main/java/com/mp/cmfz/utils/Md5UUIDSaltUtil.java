package com.mp.cmfz.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class Md5UUIDSaltUtil {
    public static String uuid = UUID.randomUUID().toString().replace("-", "");
    //创建md5对象

    public static String createMd5Code(String code) {
        return DigestUtils.md5Hex(code);
    }

    //进行密码校验

    public static boolean checkPassword(String userCode, String dbCode) {
        if (dbCode.equals(createMd5Code(userCode))) {
            return true;
        } else {
            return false;
        }
    }

    public static String getUUID() {
        return uuid;
    }

    public static String getSalt() {
        String salt = uuid.substring(0, 5);
        return salt;
    }

    public static void main(String[] args) {
//        String password = "123";
////        String salt = "sgl";
////        String md5Code = createMd5Code(salt+password);
//        System.out.println("md5Code = " + md5Code);
        //6e0f535a5544717591d615d3a264d43e
//        boolean b = checkPassword(md5Code, "9712c44c90394925fc0d5d6ba7df7d43");
//        boolean b = checkPassword("sgl", "9712c44c90394925fc0d5d6ba7df7d43");
        System.out.println("达官贵人");
    }

}
