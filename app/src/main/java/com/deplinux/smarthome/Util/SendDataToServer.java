package com.deplinux.smarthome.Util;

import android.os.Handler;

import org.json.JSONObject;


import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Landscape on 2016/6/5.
 */
public class SendDataToServer {
        private static String url="http://202.120.223.109:8888";
        public static final int SEND_SUCCESS=0x123;
        public static final int SEND_FAIL=0x124;
        private Handler handler;
        public SendDataToServer(Handler handler) {
            this.handler=handler;
        }
        /**
          * 通过POST方式向服务器发送数据
          * @param name 用户名
          * @param pwd  密码
          */
        public void SendDataToServer(String name,String pwd) {
            final Map<String, String> map=new HashMap<String, String>();
                map.put("name", name);
                map.put("pwd", pwd);
            new Thread(new Runnable() {
                @Override
                public void run() {
                try {
                    if (sendPostRequest(map,url,"utf-8")) {
                       handler.sendEmptyMessage(SEND_SUCCESS);//通知主线程数据发送成功
                       }else {
                                //将数据发送给服务器失败
                                }
                    } catch (Exception e) {
                    e.printStackTrace();
                    }
                }
            }).start();
        }
        /**
          * 发送POST请求
          * @param map 请求参数
          * @param url 请求路径
          * @return
          * @throws Exception
          */
        private  boolean sendPostRequest(Map<String,String> param, String url,String encoding) throws Exception {
            byte[]data="ZZZZZZZZ".toString().getBytes();
            HttpURLConnection conn=(HttpURLConnection) new URL(url).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("POST");//设置请求方式为POST
            conn.setDoOutput(true);//允许对外传输数据
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 设置窗体数据编码为名称/值对
            conn.setRequestProperty("Content-Length", data.length+"");
            OutputStream outputStream=conn.getOutputStream();//打开服务器的输入流
            outputStream.write(data);//将数据写入到服务器的输出流
            outputStream.flush();
            if (conn.getResponseCode()==200) {
                return true;
                }
                return false;
            }
    /**
     * 向服务器发送操作命令
     * @param cmd
     * @return String
     * @throws Exception
     */
    public static JSONObject operateDevice(String cmd) throws Exception
    {
        //String url = HttpUtil.BASE_URL+"/cgi-bin/controldevice";
        JSONObject mData = new JSONObject();
        mData.put("telephoneID","050002");
        mData.put("phoneNumber","15802948422");
        mData.put("imei","866486025282363");
        mData.put("versionID","35");
        mData.put("deviceID","020001");
        mData.put("commandType",cmd);
        mData.put("command","0");
        return mData;
    }
}
