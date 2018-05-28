package com.mrb.formatservice.utils;

public class ConverUtils {
	/**
     * 峰驼式转匈牙利命名
     *
     * @param cameString
     * @return
     */
    public static String changeCameToHung(String cameString) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cameString.length(); i++) {
            int c = cameString.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                sb.append("_").append((char) (c + 32));
                continue;
            }
            sb.append((char) c);
        }
        return sb.toString();
    }
    
    /**
     * 匈牙利转峰驼式命名
     *
     * @param cameString
     * @return
     */
    public static String changeHungToCame(String hungString) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < hungString.length(); i++) {
            int c = hungString.charAt(i);
            if (c == '_') {
            	i++;
            	c =  hungString.charAt(i);
                sb.append((char) (c - 32));
                continue;
            }
            sb.append((char) c);
        }
        return sb.toString();
    }
    
    /**
     * 首字母大写
     *
     * @param str
     * @return
     */
    public static String upperCase(String str) {
        return new StringBuilder(str.substring(0, 1).toUpperCase()).append(str.substring(1)).toString();
    }
}
