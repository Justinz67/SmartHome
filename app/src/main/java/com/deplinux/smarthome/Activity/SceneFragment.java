package com.deplinux.smarthome.Activity;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.deplinux.smarthome.Entity.Profile;
import com.deplinux.smarthome.R;
import com.deplinux.smarthome.Util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by Landscape on 2016/5/25.
 */
public class SceneFragment extends ListFragment {

    private ImageView scene_add;
    private RelativeLayout topLayout;
    private JSONObject sceneData = new JSONObject();
    private Profile profile = new Profile();
    private SimpleAdapter adapter = null;
    private ListView list;
    private int[] devIcon = { R.mipmap.icon_device_infrared};
    private String[] devName ={"早晨"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化数据
        init();
        adapter = new SimpleAdapter(getActivity(), getData(), R.layout.device_item, new String[]{"devIcon","devName"}, new int[]{R.id.device_icon,R.id.device_name});
        setListAdapter(adapter);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myLayout = inflater.inflate(R.layout.fg_scene,
                container, false);
        scene_add = (ImageView) myLayout.findViewById(R.id.add_scene);
        scene_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(),SceneAdd.class));
            }
        });

        topLayout = (RelativeLayout) this.getActivity().findViewById(R.id.ly_top_bar);

        return myLayout;
    }
    private void init(){

        //获取场景信息
        JSONObject cmd = new JSONObject();
        try {
            cmd =operateDevice();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject sceneInfo = new JSONObject();
        sceneInfo = getScene(cmd);


    }
    private JSONObject getScene(final JSONObject cmd){

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String res = HttpUtil.postRequest(HttpUtil.BASE_URL + "/cgi-bin/readprofile",
                            cmd);
                    Bundle bundle = new Bundle();
                    JSONObject data = new JSONObject(res);

                     //data.getJSONArray("profile").getJSONObject(0).get("name").toString();

                    System.out.println(cmd.toString()+"操作结果：----------->场景JSON--->"+data.getJSONArray("profile").getJSONObject(0).get("name").toString());
                    handler.sendEmptyMessage(1);
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
        return sceneData;
    }
    private JSONObject operateDevice() throws Exception
    {
        JSONObject mData = new JSONObject();
        mData.put("telephoneID","050002");
        mData.put("phoneNumber","15802948422");
        mData.put("imei","866486025282363");
        mData.put("versionID","35");
        mData.put("commandType","readp");
        return mData;
    }
    private List<? extends Map<String, ?>> getData() {
        List<Map<String ,Object>> list = new ArrayList<Map<String,Object>>();

        for (int i = 0; i < devIcon.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("devIcon",devIcon[i]);
            map.put("devName", devName[i]);
            list.add(map);

        }

        return list;
    }
    private Handler handler  = new Handler(){
       public  void handleMessage(Message msg)
       {
           switch (msg.what){
               case 1:
                   System.out.println("操作结果成功返回：----------->"+msg.getData().get("result"));
                   break;
               default:
                   break;
           }
       }
    };
}
