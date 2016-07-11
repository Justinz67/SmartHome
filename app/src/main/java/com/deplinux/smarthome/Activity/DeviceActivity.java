package com.deplinux.smarthome.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.deplinux.smarthome.R;
import com.deplinux.smarthome.Util.HttpUtil;
import com.deplinux.smarthome.Util.PostUtils;
import com.deplinux.smarthome.Util.SendDataToServer;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import org.w3c.dom.Text;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Landscape on 2016/5/30.
 */
public class DeviceActivity extends Activity {

    private ImageButton img_btn;
    private GridView device_grid;
    private Context myContext;
    private TextView tv_topbar;
    private TextView tv_device_name;
    private ImageView img_menu;
    private List<Map<String, Object>> data_list;
    private SimpleAdapter myAdapter;
    private int[] devIcon = { R.mipmap.icon_device_switch,R.mipmap.icon_device_sensor,R.mipmap.icon_device_alert,
            R.mipmap.icon_device_infrared,R.mipmap.icon_device_socket,R.mipmap.icon_device_door,
            R.mipmap.icon_device_curtain};
    private String[] devName ={"墙壁开关","传感器","报警器","红外探测器","插座","门禁","房间窗帘"};
    private int focus = 0;
    private boolean on_off = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myContext = DeviceActivity.this;
        this.setContentView(R.layout.device_list);

        /**
         * 设置topbar_title
         */
        tv_topbar = (TextView)findViewById(R.id.txt_topbar);
        Intent it = getIntent();
        Bundle bd = it.getExtras();
        String topbar_title = bd.getString("topbar_title");
        tv_topbar.setText(topbar_title);
        /**
         * 后退操作
         */
        img_btn = (ImageButton)findViewById(R.id.device_back);
        img_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(DeviceActivity.this,"返回",Toast.LENGTH_SHORT).show();
                DeviceActivity.this.finish();
                startActivity(new Intent(DeviceActivity.this,MainActivity.class));
            }
        });

        /**
         * GridView显示设备列表
         */
        //设备网格
        device_grid = (GridView)findViewById(R.id.device_grid);
        //新建List
        data_list = new ArrayList<Map<String, Object>>();
        //获取数据
        getData();
        //新建适配器
        String [] from ={"image","text"};
        int [] to = {R.id.image,R.id.text};
        myAdapter = new SimpleAdapter(DeviceActivity.this, data_list, R.layout.device_list_item, from, to);
        //配置适配器
        device_grid.setAdapter(myAdapter);

        device_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //根据Item中设备名称信息跳转到相应Activity
                tv_device_name = (TextView)view.findViewById(R.id.text);
                switch (tv_device_name.getText().toString())
                {
                    case "墙壁开关":
                        Intent it_switch = new Intent(DeviceActivity.this,SwitchActivity.class);
                        startActivity(it_switch);
                        break;
                    case "传感器":
                        Intent it_sensor = new Intent(DeviceActivity.this,SensorActivity.class);
                        startActivity(it_sensor);
                        break;
                    case "报警器":
                        Intent it_alert = new Intent(DeviceActivity.this,AlertActivity.class);
                        startActivity(it_alert);
                        break;
                    case "红外探测器":
                        Intent it_infrared = new Intent(DeviceActivity.this,InfraredActivity.class);
                        startActivity(it_infrared);
                        break;
                    case "插座":
                        Intent it_plug = new Intent(DeviceActivity.this,PlugActivity.class);
                        startActivity(it_plug);
                        break;
                    case "门禁":
                        Intent it_door = new Intent(DeviceActivity.this,DoorActivity.class);
                        startActivity(it_door);
                        break;
                    case "房间窗帘":
                        Intent it_curtain = new Intent(DeviceActivity.this,CurtainActivity.class);
                        startActivity(it_curtain);
                        break;
                    default:
                        Toast.makeText(DeviceActivity.this,"未找到相应的设备页面！",Toast.LENGTH_SHORT).show();

                }

 //               ImageView currentImg = (ImageView) device_grid.getChildAt(i).findViewById(R.id.image);
//                if(on_off=!on_off){
//
//                    //成功打开后更新图标为打开状态
//                    currentImg.setImageResource(R.mipmap.wall_switch_pressed);
//                    //Toast.makeText(DeviceActivity.this,"墙壁开关"+(i+1)+"已打开",Toast.LENGTH_SHORT).show();
//                    //向服务器发送打开命令请求
//                    try {
//
//
////                        Log.d("json",mData.toString());
////                        System.out.println("---------------->"+mData);
//                        String result = openDevice("41");
//                        Toast.makeText(DeviceActivity.this, "----->"+result.toString(), Toast.LENGTH_SHORT).show();
//                    }catch (Exception ex){
//                        Toast.makeText(DeviceActivity.this, "服务器异常，请稍后再试！", Toast.LENGTH_SHORT).show();
//                        ex.printStackTrace();}
//                }else {
//                    currentImg.setImageResource(R.mipmap.wall_switch_normal);
//                    //Toast.makeText(DeviceActivity.this, "墙壁开关" + (i + 1) + "已关闭", Toast.LENGTH_SHORT).show();
//                }

            }

        });
        /**
         * popMenu实现设备添加
         */
        img_menu = (ImageView)findViewById(R.id.add_device);
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(DeviceActivity.this, img_menu);
                popupMenu.getMenuInflater().inflate(R.menu.device_popup_menu, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.menu_add_device:
                                Toast.makeText(DeviceActivity.this, menuItem.getTitle(), Toast.LENGTH_SHORT).show();
                                break;
                            default:
                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });

    }
    //向服务器发送打开操作命令
    private  String openDevice(String cmd) throws Exception
    {
//        String url = HttpUtil.BASE_URL+"/cgi-bin/controldevice";
//        JSONObject mData = new JSONObject();
//        mData.put("telephoneID","050002");
//        mData.put("phoneNumber","15802948422");
//        mData.put("imei","866486025282363");
//        mData.put("versionID","32");
//        mData.put("deviceID","020001");
//        mData.put("commandType","41");
//        mData.put("command","0");
//        return HttpUtil.postRequest(url, mData);
        return "";
    }

    public List<Map<String, Object>> getData(){
        for(int i=0;i<devIcon.length;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", devIcon[i]);
            map.put("text", devName[i]);
            data_list.add(map);
        }

        return data_list;
    }

}
