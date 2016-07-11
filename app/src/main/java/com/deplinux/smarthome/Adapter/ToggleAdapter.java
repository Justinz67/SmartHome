package com.deplinux.smarthome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.deplinux.smarthome.Activity.PlugActivity;
import com.deplinux.smarthome.R;
import com.deplinux.smarthome.Util.ConstUtil;
import com.deplinux.smarthome.Util.HttpUtil;
import com.deplinux.smarthome.Util.SendDataToServer;

import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Landscape on 2016/6/12.
 */
public class ToggleAdapter extends BaseAdapter {

    private List<Map<String, Object>> mData;
    private Context mContext;
    public ToggleAdapter(){}

    public ToggleAdapter(List<Map<String, Object>> mData,Context mContext){
        this.mData = mData;
        this.mContext=mContext;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        final View itemRootView = LayoutInflater.from(mContext).inflate(R.layout.dv_plug_list_item,null);
        TextView plug_name = (TextView) itemRootView.findViewById(R.id.plug_name);
        plug_name.setText(mData.get(i).get("plugName").toString());
        ToggleButton toggleButton =(ToggleButton)itemRootView.findViewById(R.id.btn_toggle);
        toggleButton.setChecked(false);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final CompoundButton compoundButton, boolean b) {

//                if (compoundButton.isChecked())
//                {
//                    //开线程
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                JSONObject cmd = new JSONObject();
//                                cmd = SendDataToServer.operateDevice(ConstUtil.DV_OPERATE_OFF);
//                                String result = HttpUtil.postRequest(HttpUtil.BASE_URL + "/cgi-bin/controldevice",
//                                    cmd);
//                                System.out.println("cmd"+cmd.toString()+"关闭的操作结果：----------->"+result.toString());
//
//                            }catch (Exception e)
//                            {
//                                e.printStackTrace();
//                            }
//                        }
//                    }).start();
//
//                }else{
//                    //关线程
//                    new Thread(new Runnable() {
//                        @Override
//                        public void run() {
//                            try {
//                                JSONObject cmd = new JSONObject();
//                                cmd = SendDataToServer.operateDevice(ConstUtil.DV_OPERATE_OFF);
//                                String result = HttpUtil.postRequest(HttpUtil.BASE_URL + "/cgi-bin/controldevice",
//                                        cmd);
//                                System.out.println("关闭的操作结果：----------->"+result.toString());
//                            }catch (Exception e)
//                            {
//                                e.printStackTrace();}
//                        }
//                    }).start();
//
//
//                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject cmd = new JSONObject();
                            if(compoundButton.isChecked())
                            {
                                cmd = SendDataToServer.operateDevice(ConstUtil.DV_OPERATE_ON);
                                System.out.println(">>>>>>>>>>>>>>>>>>>>开");
                            }else{
                                System.out.println(">>>>>>>>>>>>>>>>>>>>关");
                                cmd = SendDataToServer.operateDevice(ConstUtil.DV_OPERATE_OFF);
                            }
                            String result = HttpUtil.postRequest(HttpUtil.BASE_URL + "/cgi-bin/controldevice",
                                    cmd);
                            System.out.println(cmd.toString()+"操作结果：----------->"+result.toString());

                        }catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        return  itemRootView;
    }

}

