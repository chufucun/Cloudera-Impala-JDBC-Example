package com.cloudera.utils;

public class StringHelper {

    public static String paddedByteString(byte b) {
        int extended = (b | 256) & 511;
        return Integer.toHexString(extended).toLowerCase().substring(1);
    }

    /**
     * 获得guid字符串，用于查询
     *
     * @param buf guid字节数组，长度为16
     * @return 格式为："xxx:xxx"
     */
    public static String getGuid(byte[] buf) {
        StringBuilder ret = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        int limit = buf.length;
        for (int i = limit - 1; i >= 0; --i) {
            if (i == 7) {
                ret.append(":").append(sb.toString());
                sb = new StringBuilder();
            }
            sb.append(paddedByteString(buf[i]));
        }
        ret.insert(0, sb.toString());
        return ret.toString();
    }
}
