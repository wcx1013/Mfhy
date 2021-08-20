package com.xzq.module_base.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.util.Locale;

/**
 * 字符串相关工具类
 */
public class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 字符串拼接,线程安全
     */
    public static String buffer(String... array) {
        StringBuffer s = new StringBuffer();
        for (String str : array) {
            s.append(str);
        }
        return s.toString();
    }

    /**
     * 字符串拼接,线程不安全,效率高
     */
    public static String builder(String... array) {
        StringBuilder s = new StringBuilder();
        for (String str : array) {
            s.append(str);
        }
        return s.toString();
    }


    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断两字符串是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断两字符串忽略大小写是否相等
     *
     * @param a 待校验字符串a
     * @param b 待校验字符串b
     * @return {@code true}: 相等<br>{@code false}: 不相等
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        return (a == b) || (b != null) && (a.length() == b.length()) && a.regionMatches(true, 0, b, 0, b.length());
    }

    /**
     * null转为长度为0的字符串
     *
     * @param s 待转字符串
     * @return s为null转为长度为0字符串，否则不改变
     */
    public static String null2Length0(String s) {
        return s == null ? "" : s;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 首字母大写
     *
     * @param s 待转字符串
     * @return 首字母大写字符串
     */
    public static String upperFirstLetter(String s) {
        if (isEmpty(s) || !Character.isLowerCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) - 32)) + s.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param s 待转字符串
     * @return 首字母小写字符串
     */
    public static String lowerFirstLetter(String s) {
        if (isEmpty(s) || !Character.isUpperCase(s.charAt(0))) return s;
        return String.valueOf((char) (s.charAt(0) + 32)) + s.substring(1);
    }

    /**
     * 反转字符串
     *
     * @param s 待反转字符串
     * @return 反转字符串
     */
    public static String reverse(String s) {
        int len = length(s);
        if (len <= 1) return s;
        int mid = len >> 1;
        char[] chars = s.toCharArray();
        char c;
        for (int i = 0; i < mid; ++i) {
            c = chars[i];
            chars[i] = chars[len - i - 1];
            chars[len - i - 1] = c;
        }
        return new String(chars);
    }

    /**
     * 转化为半角字符
     *
     * @param s 待转字符串
     * @return 半角字符串
     */
    public static String toDBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == 12288) {
                chars[i] = ' ';
            } else if (65281 <= chars[i] && chars[i] <= 65374) {
                chars[i] = (char) (chars[i] - 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }

    /**
     * 转化为全角字符
     *
     * @param s 待转字符串
     * @return 全角字符串
     */
    public static String toSBC(String s) {
        if (isEmpty(s)) return s;
        char[] chars = s.toCharArray();
        for (int i = 0, len = chars.length; i < len; i++) {
            if (chars[i] == ' ') {
                chars[i] = (char) 12288;
            } else if (33 <= chars[i] && chars[i] <= 126) {
                chars[i] = (char) (chars[i] + 65248);
            } else {
                chars[i] = chars[i];
            }
        }
        return new String(chars);
    }


    /**
     * 字符串转换成int
     *
     * @param strValue 字符串
     * @return 转换结果
     */
    public static int toInt(final String strValue) {
        return toInt(strValue, 0);
    }

    /**
     * 字符串转换成int
     *
     * @param strValue     字符串
     * @param defaultValue 默认值
     * @return 转换结果
     */
    public static int toInt(final String strValue, final int defaultValue) {
        if (strValue == null || strValue.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(strValue);
        } catch (Exception e) {
            try {
                return Double.valueOf(strValue).intValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }

    /**
     * 字符串转换成long
     *
     * @param strValue     字符串
     * @param defaultValue 默认值
     * @return 转换结果
     */
    public static long toLong(final String strValue, final long defaultValue) {
        if (strValue == null || strValue.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Long.valueOf(strValue);
        } catch (Exception e) {
            try {
                return Double.valueOf(strValue).longValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }

    /**
     * 字符串转换成double
     *
     * @param strValue     字符串
     * @param defaultValue 默认值
     * @return 转换结果
     */
    public static double toDouble(final String strValue, final double defaultValue) {
        if (strValue == null || strValue.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.valueOf(strValue);
        } catch (Exception e1) {
            return defaultValue;
        }
    }

    /**
     * 字符串中包含unicode的部分转中文
     *
     * @param src unicode
     * @return 中文
     */
    public static String unicode2Chinese(String src) {
        //{"error_code":"1",
        // "message":"\u64cd\u4f5c\u6210\u529f",
        // "data":[
        // {"id":"1","title":"\u6700\u65b0\u6d3b\u52a8\uff0190\u540e\u8f70\u8db4\u6d3b\u52a8\uff01"},
        // {"id":"2","title":"\u6700\u6700\u6700\u65b0\u6d3b\u52a8\uff0110\u540e\u7ec4\u5efa\u7684\u8f70\u8db4\uff01"}]}
        if (TextUtils.isEmpty(src)) {
            return src;
        }
        try {
            int index = src.indexOf("\\u");
            while (index != -1) {
                //\u64cd
                String unicodeStr = src.substring(index, index + 6);
                //64cd
                String hexStr = src.substring(index + 2, index + 6);
                int intVal = Integer.parseInt(hexStr, 16);
                String chineseStr = Character.valueOf((char) intVal).toString();
                src = src.replace(unicodeStr, chineseStr);
                index = src.indexOf("\\u");
            }
        } catch (Exception e) {
            return null;
        }
        return src;
    }

    /**
     * MD5加码
     *
     * @param data      待计数据
     * @param lowerCase 是否小写
     * @return MD5值
     */
    public static String MD5(byte[] data, boolean lowerCase) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(data);
            StringBuilder builder = new StringBuilder();
            for (byte b : md5.digest()) {
                builder.append(Integer.toHexString((b >> 4) & 0xf));
                builder.append(Integer.toHexString(b & 0xf));
            }
            if (lowerCase)
                return builder.toString();
            else
                return builder.toString().toUpperCase(Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * MD5加码
     *
     * @param str         待计算字符串
     * @param charsetName 编码格式
     * @param lowerCase   是否小写
     * @return MD5值
     */
    public static String MD5(String str, String charsetName, boolean lowerCase) {
        try {
            return MD5(str.getBytes(charsetName), lowerCase);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 手机号码，身份证证等隐私内容加*
     *
     * @param str       要加*的字符串
     * @param frontLen  *前面预留长度
     * @param behindLen *后面预留长度
     * @return 加*后的字符串
     */
    public static String convert2Star(String str, int frontLen, int behindLen) {
        if (str == null) {
            return null;
        }
        int strLen = str.length();
        if (frontLen >= strLen || behindLen >= strLen || frontLen + behindLen >= strLen) {
            return str;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(str.substring(0, frontLen));
        int starCount = strLen - frontLen - behindLen;
        for (int i = 0; i <= starCount; i++) {
            builder.append("\u002A");
        }
        return builder.append(str.substring(strLen - behindLen, strLen)).toString();
    }

    /**
     * 手机号码加*
     *
     * @param str 手机号码
     * @return 加*后的机号码
     */
    public static String mobileNum2Star(String str) {
        return convert2Star(str, 4, 4);
    }

    public static String toEmptyIfNull(String s) {
        return s == null ? "" : s;
    }
}