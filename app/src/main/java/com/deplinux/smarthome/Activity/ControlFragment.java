package com.deplinux.smarthome.Activity;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.deplinux.smarthome.Adapter.MyAdapter;
import com.deplinux.smarthome.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by Landscape on 2016/5/23.
 */
public class ControlFragment extends ListFragment{

    private SimpleAdapter adapter = null;
    private MyAdapter myAdapter = null;
    private ListView list;
    private ImageView imgMenu = null;
    private TextView tv_item_name;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int[] imgs = {R.mipmap.icon_livingroom,R.mipmap.icon_bedroom,R.mipmap.icon_kitchen,R.mipmap.icon_toilet};
        String[] locations = {"客厅","卧室","厨房","洗手间"};
        //myAdapter = new MyAdapter(locations,getActivity());
        adapter = new SimpleAdapter(getActivity(), getData(locations,imgs), R.layout.list_item, new String[]{"location","icon"}, new int[]{R.id.location,R.id.img_icon});
        setListAdapter(adapter);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View controlLayout = inflater.inflate(R.layout.fg_control,
                container, false);
        list = (ListView) controlLayout.findViewById(android.R.id.list);
        imgMenu = (ImageView)controlLayout.findViewById(R.id.add_location);
        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getActivity(),imgMenu);
                popupMenu.getMenuInflater().inflate(R.menu.locaiton_popup_menu,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener(){
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId())
                        {
                            case R.id.add_district:
                                Toast.makeText(getActivity(),menuItem.getTitle(),Toast.LENGTH_SHORT).show();
                                break;
                            default:
                        }
                        return true;
                    }
                });

                popupMenu.show();
            }
        });
        return controlLayout;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent intent = new Intent(this.getActivity(), DeviceActivity.class);
        Bundle bd =  new Bundle();
        tv_item_name = (TextView)v.findViewById(R.id.location);
        bd.putString("topbar_title",tv_item_name.getText().toString());
        intent.putExtras(bd);
        startActivity(intent);
        super.onListItemClick(l, v, position, id);
    }

    private List<? extends Map<String, ?>> getData(String[] strs,int[] imgs) {
        List<Map<String ,Object>> list = new ArrayList<Map<String,Object>>();

        for (int i = 0; i < strs.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("location", strs[i]);
            map.put("icon", imgs[i]);
            list.add(map);

        }

        return list;
    }
}
