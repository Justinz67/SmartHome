package com.deplinux.smarthome.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.deplinux.smarthome.Adapter.ToggleAdapter;
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
public class PlugActivity extends Activity {

    private ListView lv_plug;
    private ToggleButton tbn_plug;
    private ArrayList<Map<String, Object>> list_plug;
    private String[] plugName ={"插座1","插座2","插座3"};
    private SimpleAdapter simpleAdapter;
    private ToggleAdapter toggleAdapter;
    private Context context;
    private final static String url = HttpUtil.BASE_URL+"/cgi-bin/controldevice";
    private JSONObject cmd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dv_plug);

        lv_plug = (ListView)findViewById(android.R.id.list);
        list_plug = new ArrayList<Map<String,Object>>();
        cmd = new JSONObject();
        context = PlugActivity.this;
        toggleAdapter = new ToggleAdapter(getData(),context);
        lv_plug.setAdapter(toggleAdapter);
//        simpleAdapter = new SimpleAdapter(PlugActivity.this, list_plug, R.layout.dv_plug_list_item,
//                new String[]{"plugName"}, new int[]{R.id.plug_name});
//        lv_plug.setAdapter(simpleAdapter);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                tbn_plug.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                        if(compoundButton.isChecked()){
//                            try {
//                                cmd = operateDevice(ConstUtil.DV_OPERATE_ON);
//                                String result = HttpUtil.postRequest(url, cmd);
//                                System.out.println("操作结果：----------->"+result.toString());
//                            }catch (Exception e)
//                            {
//                                e.printStackTrace();
//                            }
//                        }else
//                        {
//                            try {
//                                cmd = operateDevice(ConstUtil.DV_OPERATE_OFF);
//                                String result = HttpUtil.postRequest(url, cmd);
//                                System.out.println("操作结果：----------->"+result.toString());
//                            }catch (Exception e)
//                            {
//                                e.printStackTrace();
//                            }
//                        }
//
//
//                    }
//                });
//            }
//        }).start();


    }
    /**
     * 向服务器发送操作命令
     * @param cmd
     * @return String
     * @throws Exception
     */
    private JSONObject operateDevice(String cmd) throws Exception
    {
        //String url = HttpUtil.BASE_URL+"/cgi-bin/controldevice";
        JSONObject mData = new JSONObject();
        mData.put("telephoneID","050002");
        mData.put("phoneNumber","15802948422");
        mData.put("imei","866486025282363");
        mData.put("versionID","32");
        mData.put("deviceID","02001");
        mData.put("commandType",cmd);
        mData.put("command","0");
        return mData;
    }
    public List<Map<String, Object>> getData(){
        for(int i=0;i<plugName.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("plugName", plugName[i]);
            list_plug.add(map);
        }

        return list_plug;
    }
}
