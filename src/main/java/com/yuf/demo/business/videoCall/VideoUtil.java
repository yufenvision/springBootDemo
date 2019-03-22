package com.yuf.demo.business.videoCall;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import antlr.debug.InputBufferReporter;

/**
* @author 作者 dyf:
* @version 创建时间：2019年3月20日 上午10:34:29
*/
public class VideoUtil extends BaseVideoConfig{
	
	public static void main(String[] args) throws ClientProtocolException, IOException {
//		System.out.println(login("test2"));//{"code":200,"info":{"token":"b1ff8f9a1e4c6ee913c6f718d477fd3c","accid":"test2","name":""}}
		
		getRoom();
	}
	
	public static void getRoom() throws IOException{
		
		
		
	}
	
	
	//导入用户名到云信，获取云信生成的token
	public static String login(String userName) throws ParseException, IOException{
		@SuppressWarnings("deprecation")
		DefaultHttpClient httpClient = new DefaultHttpClient();
        String url = "https://api.netease.im/nimserver/user/create.action";
        HttpPost httpPost = new HttpPost(url);

        String appKey = APP_KEY;
        String appSecret = APP_SECRET;
        String nonce =  "12345";
        String curTime = String.valueOf((new Date()).getTime() / 1000L);
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce ,curTime);//参考 计算CheckSum的java代码

        // 设置请求的header
        httpPost.addHeader("AppKey", appKey);
        httpPost.addHeader("Nonce", nonce);
        httpPost.addHeader("CurTime", curTime);
        httpPost.addHeader("CheckSum", checkSum);
        httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        // 设置请求的参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("accid", userName));
        System.out.println(nvps);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

        // 执行请求
        HttpResponse response = httpClient.execute(httpPost);
      
        // 打印执行结果
        String result = EntityUtils.toString(response.getEntity(), "utf-8");
        return result;
	}
}
