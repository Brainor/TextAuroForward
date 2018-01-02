package com.brainor.textforward.textautoforward;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 欧伟科 on 2016/10/12.
 */

class NetworkConnect {
    /**
     * 利用网址建立连接
     *
     * @param URL      网址
     * @param postData POST数据
     * @return response 返回页面
     */
    private String 网络连接(String URL, String postData) {
        java.net.URL url;
        try {
            url = new java.net.URL(URL);
        } catch (MalformedURLException e) {
            return e.getMessage();
        }
        HttpURLConnection request;
        try {
            request = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            return e.getMessage();
        }
        if (!postData.equals("")) {
            request.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            request.setDoOutput(true);
            request.setRequestProperty("Content-Length", Integer.toString(postData.length()));
            try {
                request.setRequestMethod("POST");
                try (java.io.DataOutputStream dataOutputStream = new java.io.DataOutputStream(request.getOutputStream())) {
                    dataOutputStream.write(postData.getBytes(StandardCharsets.UTF_8));
                    dataOutputStream.flush();
                }
            } catch (IOException e) {
                return e.getMessage();
            }
        }
        //分析网页部分
        String line;StringBuilder ResponseFromServer = new StringBuilder();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "gb2312"));//专门为这个网站设置charset
            while ((line = bufferedReader.readLine()) != null) {
                ResponseFromServer.append(line).append("\n");
            }
        } catch (IOException e) {
            return e.getMessage();
        }
        request.disconnect();
        return ResponseFromServer.toString();
    }

    String 连接网络通(String userID, String Password) {
        String URL = "http://wlt.ustc.edu.cn/cgi-bin/ip";
        Matcher matcher = Pattern.compile("(?<=name=ip\\svalue=)[\\d.]+(?=>)").matcher(网络连接(URL, ""));
        String IP地址;
        if (matcher.find()) IP地址 = matcher.group();
        else return "信息：当前未连接网络</td>";
        matcher = Pattern.compile("(?<=信息：).*(?=<)").matcher(网络连接(URL, "cmd=login&url=URL&ip=" + IP地址 + "&name=" + userID + "&password=" + Password + "&savepass=on&set=%D2%BB%BC%FC%C9%CF%CD%F8"));
        if (matcher.find()) return matcher.group().replaceAll("^<.*?>", "");
        else return "出现未知问题, 请联系老公";
    }
}
