package com.deplinux.smarthome.Activity;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.deplinux.smarthome.Adapter.BaseExpandableListAdapter;
import com.deplinux.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/25.
 */
public class SecurityFragment extends Fragment{

    private List<Map<String,?>> gData = null;
    private List<List<Map<String,?>>> iData = null;
    private Context mContext;
    private ExpandableListView exlist;
    private BaseExpandableListAdapter myAdapter = null;
    private String[] gName ={"传感器","摄像头","门锁"};
    private int[][] cIcon ={
            {R.mipmap.icon_device_lock},
            {R.mipmap.icon_device_cam},
            {R.mipmap.icon_device_lock,R.mipmap.icon_device_lock,R.mipmap.icon_device_lock}
    };
    private String[][] cName ={
            {"一键布防"},
            {"摄像头"},
            {"门锁1","门锁2","门锁3"}
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View securityLayout = inflater.inflate(R.layout.fg_security,
                container, false);
        mContext = securityLayout.getContext();
        exlist = (ExpandableListView)securityLayout.findViewById(R.id.exlist);
        gData = new ArrayList<>();
        iData = new ArrayList<>();
        //列表数据获取
        for(int i=0;i<gName.length;i++){
            Map<String, String> map1 = new HashMap<>();
            map1.put("gName", gName[i]);
            gData.add(map1);

            List<Map<String,?>> itemList = new ArrayList<>();
            for(int j=0;j<cIcon[i].length;j++){
                Map<String,Object> map2 = new HashMap<>();
                map2.put("cName",cName[i][j]);
                map2.put("cIcon",cIcon[i][j]);
                itemList.add(map2);
            }
            iData.add(itemList);
        }

        myAdapter = new BaseExpandableListAdapter(gData,iData,mContext);
        exlist.setAdapter(myAdapter);
        return securityLayout;
    }


}
