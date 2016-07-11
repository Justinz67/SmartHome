package com.deplinux.smarthome.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.deplinux.smarthome.R;
import com.deplinux.smarthome.Util.ConstUtil;
import com.deplinux.smarthome.Util.HttpUtil;

import org.json.JSONObject;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Landscape on 2016/6/10.
 */
public class SwitchActivity extends Activity{

    private GridView gv_switch;
    private ArrayList<Map<String, Object>> list_switch;
    private String[] switchName ={"墙壁开关1","墙壁开关2","墙壁开关3"};
    private SimpleAdapter simpleAdapter;
    private boolean on_off = false;
    private void init(){
        gv_switch = (GridView)findViewById(R.id.switch_grid);
        list_switch = new ArrayList<Map<String, Object>>();

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dv_switch);
        /**
         * 初始化操作
         */
        init();
        /**
         * GridView显示设备列表
         */
        //获取simpleAdapter显示数据
        getData();
        simpleAdapter = new SimpleAdapter(SwitchActivity.this, list_switch, R.layout.dv_switch_list_item,
                new String[]{"switchName"}, new int[]{R.id.switch_name});
        gv_switch.setAdapter(simpleAdapter);
        gv_switch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ImageView currentImg = (ImageView) gv_switch.getChildAt(i).findViewById(R.id.switch_icon);
                final int branch = i;
                //控制线程
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            JSONObject cmd = new JSONObject();
                               if(on_off=!on_off){
                                   //打开
                                   cmd = operateDevice(ConstUtil.DV_OPERATE_ON,(branch+1));

                               }else
                               {
                                   //关闭
                                   cmd = operateDevice(ConstUtil.DV_OPERATE_OFF,(branch+1));
                               }
                            String result = HttpUtil.postRequest(HttpUtil.BASE_URL + "/cgi-bin/controldevice",
                                    cmd);
                            System.out.println(cmd.toString()+"操作结果：----------->"+result.toString());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();

//                if(on_off=!on_off){
//
//                    //成功打开后更新图标为打开状态
//                    currentImg.setImageResource(R.mipmap.wall_switch_pressed);
//                    //Toast.makeText(DeviceActivity.this,"墙壁开关"+(i+1)+"已打开",Toast.LENGTH_SHORT).show();
//                    //向服务器发送打开命令请求
//                    new Thread()
//                    {
//                        @Override
//                        public void run() {
//                            try {
//                                JSONObject cmd = operateDevice(ConstUtil.DV_OPERATE_ON,(branch+1));
//                                String result = HttpUtil.postRequest(HttpUtil.BASE_URL + "/cgi-bin/controldevice",
//                                        cmd);
//                                System.out.println(cmd.toString()+"打开操作：----------->"+result.toString());
//
//                            }catch (Exception e)
//                            {
//                                e.printStackTrace();}
//                        }
//                    }.start();
////                    try {
////
////
////
//////                        Log.d("json",mData.toString());
//////                        System.out.println("---------------->"+mData);
////                        String result = operateDevice("41");
////                        Toast.makeText(SwitchActivity.this, "----->"+result.toString(), Toast.LENGTH_SHORT).show();
////                    }catch (Exception ex){
////                        Toast.makeText(SwitchActivity.this, "服务器异常，请稍后再试！", Toast.LENGTH_SHORT).show();
////                        ex.printStackTrace();}
//                }else {
//                    currentImg.setImageResource(R.mipmap.wall_switch_normal);
//                    //Toast.makeText(DeviceActivity.this, "墙壁开关" + (i + 1) + "已关闭", Toast.LENGTH_SHORT).show();
//                    //向服务器发送打开命令请求
//                    new Thread()
//                    {
//                        @Override
//                        public void run() {
//                            try {
//                                JSONObject cmd = operateDevice(ConstUtil.DV_OPERATE_OFF,(branch+1));
//                                String result = HttpUtil.postRequest(HttpUtil.BASE_URL + "/cgi-bin/controldevice",
//                                        cmd);
//                                System.out.println(cmd.toString()+"关闭操作：----------->"+result.toString());
//                            }catch (Exception e)
//                            {
//                                e.printStackTrace();}
//                        }
//                    }.start();
////                    try {
////
////
//////                        Log.d("json",mData.toString());
//////                        System.out.println("---------------->"+mData);
////                        JSONObject result = operateDevice("5A");
////                        Toast.makeText(SwitchActivity.this, "----->"+result.toString(), Toast.LENGTH_SHORT).show();
////                    }catch (Exception ex){
////                        Toast.makeText(SwitchActivity.this, "服务器异常，请稍后再试！", Toast.LENGTH_SHORT).show();
////                        ex.printStackTrace();}
//                }

            }

        });
    }

    /**
     * 向服务器发送操作命令
     * @param cmd
     * @return String
     * @throws Exception
     */
    private  JSONObject operateDevice(String cmd,int branch) throws Exception
    {
        //String url = HttpUtil.BASE_URL+"/cgi-bin/controldevice";
        JSONObject mData = new JSONObject();
        mData.put("telephoneID","050002");
        mData.put("phoneNumber","15802948422");
        mData.put("imei","866486025282363");
        mData.put("versionID","32");
        mData.put("deviceID","080001");
        mData.put("commandType",cmd);
        mData.put("command","1");
        return mData;
    }

    /**
     * Adapter所需要的list内容
     * @return List
     */
    public List<Map<String, Object>> getData(){
        for(int i=0;i<switchName.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("switchName", switchName[i]);
            list_switch.add(map);
        }

        return list_switch;
    }
}
