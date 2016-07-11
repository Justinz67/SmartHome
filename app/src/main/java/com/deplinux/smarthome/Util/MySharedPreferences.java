package com.deplinux.smarthome.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Landscape on 2016/7/4.
 */
public class MySharedPreferences {

    private Context context;

    public MySharedPreferences(){

    }

    public MySharedPreferences(Context context) {
        this.context = context;
    }

    //数据保存
    public void save(String phoneNum,String outerIP,String innerIP,String imei){
        SharedPreferences sp = context.getSharedPreferences("mysp",context.MODE_PRIVATE);
        SharedPreferences.Editor editor =sp.edit();
        editor.putString("phoneNum",phoneNum);
        editor.putString("outerIP",outerIP);
        editor.putString("innerIP",innerIP);
        editor.putString("imei",imei);
        editor.commit();
        Toast.makeText(context,"配置信息保存成功",Toast.LENGTH_SHORT).show();
    }
    //数据读取
    public Map<String,String> read(){
        Map<String,String> data = new HashMap<>();
        SharedPreferences sp = context.getSharedPreferences("mysp",context.MODE_PRIVATE);
        data.put("phoneNum",sp.getString("phoneNum",""));
        data.put("outerIP",sp.getString("outerIP",""));
        data.put("innerIP",sp.getString("innerIP",""));
        data.put("imei",sp.getString("imei",""));
        return data;
    }

}
