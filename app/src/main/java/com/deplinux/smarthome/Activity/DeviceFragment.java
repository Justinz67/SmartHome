package com.deplinux.smarthome.Activity;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.deplinux.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/25.
 */
public class DeviceFragment extends ListFragment {

    private SimpleAdapter adapter = null;
    private ListView list;
    private int[] devIcon = { R.mipmap.icon_device_infrared,R.mipmap.icon_device_socket,R.mipmap.icon_device_sensor,
            R.mipmap.icon_device_door,R.mipmap.icon_device_curtain,R.mipmap.icon_device_switch,
            R.mipmap.icon_device_infrared,R.mipmap.icon_device_alert,R.mipmap.icon_device_switch};
    private String[] devName ={"红外设备","插座","传感器","门禁","窗帘","墙壁开关","生物红外探测器","报警","模拟控制"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new SimpleAdapter(getActivity(), getData(), R.layout.device_item, new String[]{"devIcon","devName"}, new int[]{R.id.device_icon,R.id.device_name});
        setListAdapter(adapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View settingLayout = inflater.inflate(R.layout.fg_device,
                container, false);
        return settingLayout;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
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
}
