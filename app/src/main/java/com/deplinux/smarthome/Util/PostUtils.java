package com.deplinux.smarthome.Util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Landscape on 2016/6/3.
 */
public class PostUtils {
    public static String defaultUrl = "http://202.120.223.109:8888";
    public static String doPost(String strURL,String content)
    {
        String result="";
        try{
            HttpURLConnection conn = (HttpURLConnection) new URL(strURL).openConnection();
            //设置请求方式,请求超时信息
            conn.setRequestMethod("POST");
            conn.setReadTimeout(5000);
            conn.setConnectTimeout(5000);
            //设置输入输出采用字节流
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            //Post方式不能缓存,需手动设置为false
            conn.setUseCaches(false);
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("Charset","utf-8");
            conn.connect();
            //我们请求的数据:
            String data = content;
            //获取输出流
            DataOutputStream dop = new DataOutputStream(conn.getOutputStream());
            dop.write(data.getBytes());
            dop.flush();
            dop.close();
            if (conn.getResponseCode()==200) {
                System.out.println("----------------->发送成功！");
            }else{System.out.println("----------------->发送失败！");}



            //接受服务器数据
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String readLine = null;
            while((readLine=bufferedReader.readLine())!=null) {
                result += readLine;
            }
            bufferedReader.close();
            conn.disconnect();
            //返回数据
            return  result;
//            if (conn.getResponseCode() == 200) {
//                // 获取响应的输入流对象
//                InputStream is = conn.getInputStream();
//                // 创建字节输出流对象
//                ByteArrayOutputStream message = new ByteArrayOutputStream();
//                // 定义读取的长度
//                int len = 0;
//                // 定义缓冲区
//                byte buffer[] = new byte[1024];
//                // 按照缓冲区的大小，循环读取
//                while ((len = is.read(buffer)) != -1) {
//                    // 根据读取的长度写入到os对象中
//                    message.write(buffer, 0, len);
//                }
//                // 释放资源
//                is.close();
//                message.close();
//                // 返回字符串
//                content = new String(message.toByteArray());
//                return result;
//            }
        }catch(Exception e){e.printStackTrace();}
        return  result;
    }
}
