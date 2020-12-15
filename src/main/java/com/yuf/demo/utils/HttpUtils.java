package com.yuf.demo.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

@Slf4j
public class HttpUtils {


    public static String doGet(String url, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
            log.info("request url :  " + url + "\n  param :" + param.toString()
                    + "\n response code : " + response.getStatusLine().getStatusCode()
                    + "\n  response data :" + resultString);
        } catch (Exception e) {
            log.error("request url :  " + url + "\n param :" + param.toString(), e);
            return getExceptionJsonMsg(e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
//                return getExceptionJsonMsg(e);
            }
        }
        return resultString;
    }

    public static String doGet2(String url, Map<String, Object> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, String.valueOf(param.get(key)));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }

            log.info("request url :  " + url + "\n  param :" + param.toString()
                    + "\n response code : " + response.getStatusLine().getStatusCode()
                    + "\n  response data :" + resultString);
        } catch (Exception e) {
            log.error("request url :  " + url + "\n param :" + param.toString(), e);
            return getExceptionJsonMsg(e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
//                return getExceptionJsonMsg(e);
            }
        }
        return resultString;
    }

    public static String doGet(String url, Map<String, String> headers, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpGet.setHeader(key, headers.get(key));
                }
            }

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
            log.info("request url :  " + url + "\n header :" + headers.toString() + "\n  param :" + param.toString()
                    + "\n response code : " + response.getStatusLine().getStatusCode()
                    + "\n  response data :" + resultString);
        } catch (Exception e) {
            log.error("request url :  " + url + "\n param :" + param.toString(), e);
            return getExceptionJsonMsg(e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
//                return getExceptionJsonMsg(e);
            }
        }
        return resultString;
    }

    public static String doGetObjectheader(String url, Map<String, Object> headers, Map<String, String> param) {

        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            if (param != null) {
                for (String key : param.keySet()) {
                    builder.addParameter(key, param.get(key));
                }
            }
            URI uri = builder.build();

            // 创建http GET请求
            HttpGet httpGet = new HttpGet(uri);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpGet.setHeader(key, headers.get(key).toString());
                }
            }

            // 执行请求
            response = httpclient.execute(httpGet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }

            log.info("request url :  " + url + "\n header :" + headers.toString() + "\n  param :" + param.toString()
                    + "\n response code : " + response.getStatusLine().getStatusCode()
                    + "\n  response data :" + resultString);
        } catch (Exception e) {
            log.error("request url :  " + url + "\n param :" + param.toString(), e);
            return getExceptionJsonMsg(e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
//                return getExceptionJsonMsg(e);
            }
        }
        return resultString;
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }

    public static String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            if (response != null) {
                resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            }
            log.info("request url :  " + url
                    + "\n response code : " + response.getStatusLine().getStatusCode()
                    + "\n  response data :" + resultString);
        } catch (Exception e) {
            log.error("request url :  " + url + "\n param :" + param.toString(), e);
            return getExceptionJsonMsg(e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
//                return getExceptionJsonMsg(e);
            }
        }

        return resultString;
    }

    public static String doPost(String url, Map<String, Object> headers, Map<String, Object> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpPost.addHeader(key, String.valueOf(headers.get(key)));
                }
            }
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, String.valueOf(param.get(key))));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList, "utf-8");
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            log.info("request url :  " + url + "\n header :" + headers.toString() + "\n response code : " + response.getStatusLine().getStatusCode()
                    + "\n  response data :" + resultString);
        } catch (Exception e) {
            log.error("request url :  " + url + "\n param :" + param.toString(), e);
            return getExceptionJsonMsg(e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
//                return getExceptionJsonMsg(e);
            }
        }

        return resultString;
    }

    public static String doPost(String url) {
        return doPost(url, null);
    }

    public static String doPostByJson(String url, Map<String, Object> headers, String data) {
//        log.info("请求体bodys信息:"+data);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        if (headers != null) {
            log.info("请求头headers信息:" + headers.toString());
            for (String key : headers.keySet()) {
                post.addHeader(key, String.valueOf(headers.get(key)));
            }
        }
        String result = "";
        try {
            StringEntity s = new StringEntity(data, "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);
            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();
            result = strber.toString();
            log.info("request url :  " + url + "\n header :" + headers.toString() + "\n  response data :" + result);
        } catch (Exception e) {
            log.error("request url :  " + url + "\n param :" + data, e);
            return getExceptionJsonMsg(e);
        }

        return result;
    }

    public static String doPutByJson(String url, Map<String, Object> headers, String data) {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPut put = new HttpPut(url);
        if (headers != null) {
            log.info("请求头headers信息:" + headers.toString());
            for (String key : headers.keySet()) {
                put.addHeader(key, String.valueOf(headers.get(key)));
            }
        }
        String result = "";
        try {
            StringEntity s = new StringEntity(data, "utf-8");
            put.setEntity(s);
            put.addHeader(HTTP.CONTENT_TYPE, "application/json");
//            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));

            // 发送请求
            HttpResponse httpResponse = client.execute(put);
            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();
            result = strber.toString();
            log.info("request url :  " + url + "\n header :" + headers.toString()
                    + "\n  response data :" + result);
        } catch (Exception e) {
            log.error("request url :  " + url + "\n header :" + headers.toString() + "\n param :" + data, e);
            return getExceptionJsonMsg(e);
        }

        return result;

    }

    public static String doPostByJson2(String url, Map<String, String> headers, String data) {
//        log.info("请求体bodys信息:"+data);
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        if (headers != null) {
            log.info("请求头headers信息:" + headers.toString());
            for (String key : headers.keySet()) {
                post.addHeader(key, String.valueOf(headers.get(key)));
            }
        }
        String result = "";
        try {
            StringEntity s = new StringEntity(data, "utf-8");
            s.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
            post.setEntity(s);

            // 发送请求
            HttpResponse httpResponse = client.execute(post);
            // 获取响应输入流
            InputStream inStream = httpResponse.getEntity().getContent();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    inStream, "utf-8"));
            StringBuilder strber = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null)
                strber.append(line + "\n");
            inStream.close();
            result = strber.toString();
            log.info("request url :  " + url + "\n header :" + headers.toString()
                    + "\n  response data :" + result);
        } catch (Exception e) {
            log.error("request url :  " + url + "\n header :" + headers.toString() + "\n param :" + data, e);
            return getExceptionJsonMsg(e);
        }

        return result;
    }

    public static String doPostJson(String url, String json) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建请求内容
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
            log.info("request url :  " + url + "\n  param :" + json
                    + "\n  response data :" + resultString);
        } catch (Exception e) {
            log.error("request url :  " + url + "\n param :" + json, e);
            return getExceptionJsonMsg(e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
//                return getExceptionJsonMsg(e);
            }
        }

        return resultString;
    }


    public static String doJsonPost(String url, String params) throws Exception {
        URL postUrl = new URL(url);
        StringBuffer resultBuffer = new StringBuffer();
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");
        connection.setUseCaches(false);
        connection.setInstanceFollowRedirects(true);
        connection.setConnectTimeout(60000); //60s
        connection.setReadTimeout(60000); //60s
        connection.setInstanceFollowRedirects(true);
        connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
        out.writeBytes(new String(params.getBytes("UTF-8"), "UTF-8"));
        out.flush();
        out.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        String tempLine = null;
        while ((tempLine = reader.readLine()) != null) {
            resultBuffer.append(tempLine);
        }
        log.info("request url :  " + url + "\n  param :" + params
                + "\n  response data :" + resultBuffer.toString());
        return resultBuffer.toString();
    }

    public static String HttpPostWithJson(String url, Map<String, String> headers, String json) {
        String returnValue = "这是默认返回值，接口调用失败";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            //第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();

            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);

            //第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json, "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            if (headers != null) {
                log.info("请求头headers信息:" + headers.toString());
                for (String key : headers.keySet()) {
                    httpPost.setHeader(key, String.valueOf(headers.get(key)));
                }
            }
            httpPost.setEntity(requestEntity);

            //第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost, responseHandler); //调接口获取返回值时，必须用此方法
            log.info("request url :  " + url + "\n header :" + headers.toString()
                    + "\n  response data :" + returnValue);
        } catch (Exception e) {
            log.error("request url :  " + url + "\n header :" + headers + "\n param :" + json, e);
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //第五步：处理返回值
        return returnValue;
    }

    public static String doDelete(String url, Map<String, String> headers, String jsonStr) {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpDelete httpDelete = new HttpDelete(url);
        RequestConfig requestConfig =
                RequestConfig.custom().setConnectTimeout(35000).setConnectionRequestTimeout(35000).setSocketTimeout(60000).build();
        httpDelete.setConfig(requestConfig);

        if (headers != null) {
            log.info("请求头headers信息:" + headers.toString());
            for (String key : headers.keySet()) {
                httpDelete.setHeader(key, String.valueOf(headers.get(key)));
            }
        }
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpDelete);
            HttpEntity entity = httpResponse.getEntity();
            String result = EntityUtils.toString(entity);
            log.info("request url :  " + url + "\n header :" + headers.toString() + "\n  param :" + jsonStr
                    + "\n  response data :" + result);
            return result;
        } catch (Exception e) {
            log.error("request url :  " + url + "\n header :" + headers + "\n param :" + jsonStr, e);
        } finally {
            if (httpResponse != null) {
                try {
                    httpResponse.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (null != httpClient) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

//    public static String doGetPostGuanLin(RequestType requestType, String url, Map<String, String> param) {
//        String resultString = "";
//        try {
//            // 创建uri
//            URIBuilder builder = new URIBuilder(url);
//            param = Optional.ofNullable(param).orElse(new HashMap<>());
//            param.forEach((k, v) -> builder.addParameter(k, v));
//            URI uri = builder.build();
//            HttpUriRequest request = requestType == RequestType.GET ? new HttpGet(uri) : new HttpPost(uri);
//            resultString = getResponseString(request, param.toString(), url);
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//        return resultString;
//    }

    public static String doJsonPostMy(String url, String jsonStr) {
        HttpPost post = new HttpPost(url);
        StringEntity s = new StringEntity(jsonStr, Charset.forName("UTF-8"));//解决中文乱码
        s.setContentEncoding("UTF-8");
        s.setContentType("application/json");//发送json数据需要设置contentType
        post.setEntity(s);
        return getResponseString(post, jsonStr, url);
    }

    private static String getResponseString(HttpUriRequest httpRequet, String params, String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
//            httpRequet.setHeader("Authorization", "Basic " + GuanlinUtil.BASIC_AUTH);
            response = httpclient.execute(httpRequet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
            log.info("request url :  " + url + "\n  param :" + params
                    + "\n response code : " + response.getStatusLine().getStatusCode()
                    + "\n  response data :" + resultString);
        } catch (Exception e) {
            log.error("request url :  " + url + "\n param :" + params, e);
            return getExceptionJsonMsg(e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
//                return getExceptionJsonMsg(e);
            }
        }
        return resultString;
    }

    public static String getGetResultStr(Map<String, String> headers, String url, Map<String, String> param) {
        String resultString = "";
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            param = Optional.ofNullable(param).orElse(new HashMap<>());
            param.forEach((k, v) -> builder.addParameter(k, v));
            URI uri = builder.build();
            HttpUriRequest request = new HttpGet(uri);
            resultString = getResponseString(request, headers, param.toString(), url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    public static String getPostResultStr(String url, Map<String, String> headers, Map<String, String> param, String jsonStr) {
        String resultString = "";
        try {
            // 创建uri
            URIBuilder builder = new URIBuilder(url);
            param = Optional.ofNullable(param).orElse(new HashMap<>());
            param.forEach((k, v) -> builder.addParameter(k, v));
            URI uri = builder.build();
            HttpPost post = new HttpPost(uri);
            StringEntity s = new StringEntity(jsonStr, Charset.forName("UTF-8"));//解决中文乱码
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            resultString = getResponseString(post, headers, param + jsonStr, url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return resultString;
    }

    private static String getResponseString(HttpUriRequest httpRequet, Map<String, String> headers, String params, String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String resultString = "";
        CloseableHttpResponse response = null;
        try {
            if (headers != null) {
                log.info("请求头headers信息:" + headers.toString());
                headers.forEach((k, v) -> httpRequet.setHeader(k, v));
            }
            response = httpclient.execute(httpRequet);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
            log.info("request url :  " + url + "\n  param :" + params
                    + "\n response code : " + response.getStatusLine().getStatusCode()
                    + "\n  response data :" + resultString);
        } catch (IOException e) {
            log.error("request url :  " + url + "\n param :" + params, e);
            return getExceptionJsonMsg(e);
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
//                return getExceptionJsonMsg(e);
            }
        }
        return resultString;
    }

    private static String getExceptionJsonMsg(Exception e) {
        log.error("异常:", e);
        JSONObject errorData = new JSONObject();
        errorData.put("errorMsg", e.toString());
        return errorData.toString();
    }

}
