package com.order.util.http;

/**
 * @Author: yangfeng
 * @Date: 2019/4/10
 */
public class UrlUtil {

    public static String getUrl(String ip, Integer port, String apiPath) {
        if (apiPath.startsWith("/")) {
            return String.format("http://%s:%d%s", ip, port, apiPath);
        } else {
            return String.format("http://%s:%d/%s", ip, port, apiPath);
        }
    }

    public static String getUrl(String ip, Integer port) {
        return String.format("http://%s:%d", ip, port);
    }

    public static String getUrl(String url) {
        return String.format("http://%s", url);
    }

}
