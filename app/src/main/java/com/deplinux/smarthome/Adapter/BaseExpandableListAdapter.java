package com.deplinux.smarthome.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.deplinux.smarthome.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Landscape on 2016/6/12.
 */
public class BaseExpandableListAdapter extends android.widget.BaseExpandableListAdapter {

    private List<Map<String,?>> gData;
    private List<List<Map<String,?>>> iData;
    private Context mContext;

    public BaseExpandableListAdapter(List<Map<String,?>> gData,List<List<Map<String,?>>> iData, Context mContext) {
        this.gData = gData;
        this.iData = iData;
        this.mContext = mContext;
    }
    @Override
    public int getGroupCount() {
        return gData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return iData.get(i).size();
    }

    @Override
    public Object getGroup(int i) {
        return gData.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return iData.get(i).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        View groupView = LayoutInflater.from(mContext).inflate(
                R.layout.fg_security_item_group,viewGroup,false);
        TextView tv_group_name = (TextView)groupView.findViewById(R.id.tv_group_name);
        tv_group_name.setText(gData.get(i).get("gName").toString());

        return groupView;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        View childView = LayoutInflater.from(mContext).inflate(
          R.layout.fg_security_item_item,viewGroup,false);
        ImageView explist_img_icon = (ImageView)childView.findViewById(R.id.explist_img_icon);
        TextView explist_device_name = (TextView)childView.findViewById(R.id.explist_device_name);
        explist_img_icon.setImageResource((Integer) iData.get(i).get(i1).get("cIcon"));
        explist_device_name.setText(iData.get(i).get(i1).get("cName").toString());
        ToggleButton toggleButton =(ToggleButton)childView.findViewById(R.id.sensor_toggle);
        toggleButton.setChecked(false);
        return childView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
