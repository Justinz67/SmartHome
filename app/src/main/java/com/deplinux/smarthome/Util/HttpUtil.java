package com.deplinux.smarthome.Util;

import com.deplinux.smarthome.Activity.LoginActivity;
import com.deplinux.smarthome.Service.ServiceRulesException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by Landscape on 2016/6/6.
 */
public class HttpUtil {
    //创建HttpClient对象
    public  static HttpClient httpClient = new DefaultHttpClient();
    public  static final  String BASE_URL=
            "http://202.120.223.109:8888";
    //发送GET请求
    public static  String getRequest(final String url) throws Exception{

        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        //创建HttpGet对象
                        HttpGet get = new HttpGet(url);
                        //发送GET请求
                        HttpResponse httpResponse = httpClient.execute(get);
                        //如果服务器成功地返回响应
                        if(httpResponse.getStatusLine().getStatusCode()==200)
                        {
                            //获取服务器响应字符串
                            String result = EntityUtils.toString(httpResponse.getEntity());
                            return  result;
                        }
                        return null;
                    }
                });
        new Thread(task).start();
        return task.get();
    }
    //发送POST请求
    public static  String postRequest(final String url, final JSONObject json)throws Exception
    {
        FutureTask<String> task = new FutureTask<String>(
                new Callable<String>() {
                    @Override
                    public String call() throws Exception {
                        //创建HttpPost对象
                        HttpPost post = new HttpPost(url);
//                        List<NameValuePair> params = new ArrayList<NameValuePair>();
//                        for(String key:rawParams.keySet())
//                        {
//                            params.add(new BasicNameValuePair(key,rawParams.get(key)));
//                        }
                        //JSONObject response = null;
//                        for(String key:rawParams.keySet()) {
//                            jsonData.put(key,rawParams.get(key));
//                        }
                        StringEntity strEntity = new StringEntity(json.toString());
                        strEntity.setContentType("application/json");
                        strEntity.setContentEncoding("UTF-8");
                        //设置请求参数
                        post.setEntity(strEntity);
                        //发送POST请求
                        HttpResponse httpResponse = httpClient.execute(post);
                        //服务器返回响应
                        if(httpResponse.getStatusLine().getStatusCode()==200)
                        {
                            String result =EntityUtils.toString(httpResponse.getEntity());
                            return  result;
                        }else{
                            throw new ServiceRulesException(ConstUtil.MSG_SERVER_ERROR);
                        }
                    }
                });
        new Thread(task).start();
        return task.get();
    }
}
