package com.supersoft.guanstudy.hack.http;

import com.alibaba.fastjson.JSONObject;
import com.supersoft.guanstudy.common.utils.HttpPostClient;

import java.io.IOException;

/**
 * Created by guanjunpu on 2016/1/28.
 */
public class HackVIP {
    public static void main(String[] args) throws IOException {

        JSONObject params = new JSONObject();
        params.put("account", "18501268090");
        params.put("password", "123456");
        String resp = HttpPostClient.doHttpPostIO("https://sso.letv.com/user/loginsubmit", params.toJSONString());
        System.out.println(resp);
    }
}
