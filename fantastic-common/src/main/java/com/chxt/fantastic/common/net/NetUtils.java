package com.chxt.fantastic.common.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NetUtils {
    public static String getCurrentIp() {
        // 这里使用jsonip.com第三方接口获取本地IP
        String jsonip = "https://jsonip.com/";
        // 接口返回结果
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            // 使用HttpURLConnection网络请求第三方接口
            URL url = new URL(jsonip);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            in = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        }
        // 正则表达式，提取xxx.xxx.xxx.xxx，将IP地址从接口返回结果中提取出来
        String rexp = "(\\d{1,3}\\.){3}\\d{1,3}";
        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(result.toString());
        String res="";
        if (mat.find()) {
            res=mat.group();
        }
        return res;
    }

    public static void main(String[] args) {
        String currentIp = getCurrentIp();
        System.out.println(currentIp);
    }
}
