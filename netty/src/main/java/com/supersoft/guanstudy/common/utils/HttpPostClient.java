package com.supersoft.guanstudy.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpPostClient {

    public static String doHttpPost(String url, Map<String, String> m) {
        HttpClient httpclient = new DefaultHttpClient();

        StringBuffer buffer = new StringBuffer();
        List<NameValuePair> param = null;
        if ((m != null) && (!m.isEmpty())) {
            param = new ArrayList<NameValuePair>();
            for (String key : m.keySet())
                param.add(new BasicNameValuePair(key, (String) m.get(key)));
        }
        try {
            HttpPost httpPost = new HttpPost(url);
            if (param != null) {
                HttpEntity paramEntity = new UrlEncodedFormEntity(param, "UTF-8");
                httpPost.setEntity(paramEntity);
            }
            HttpResponse response = httpclient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(responseEntity.getContent(), "UTF-8"));
                String str = "";
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                reader.close();
                responseEntity.consumeContent();
                httpPost.abort();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String doHttpGet(String url) {
        HttpClient httpclient = new DefaultHttpClient();
        StringBuffer buffer = new StringBuffer();
        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse response = httpclient.execute(httpGet);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(responseEntity.getContent(), "UTF-8"));
                String str = "";
                while ((str = reader.readLine()) != null) {
                    buffer.append(str);
                }
                reader.close();
                responseEntity.consumeContent();
                httpGet.abort();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    public static String doHttpPostIO(String url, String param) {
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl
                    .openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (StringUtils.isNotEmpty(param)) {
                conn.getOutputStream().write(param.getBytes("utf-8"));
            }
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(), "utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result = result + line;
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (in != null)
                    in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}