package com.deplinux.smarthome.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.deplinux.smarthome.Adapter.DrawerAdapter;
import com.deplinux.smarthome.R;
import com.deplinux.smarthome.Entity.Item;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //UI Object
    private TextView txt_topbar;
    private TextView txt_control;
    private TextView txt_security;
    private TextView txt_my;
    private TextView txt_setting;
    private FrameLayout ly_content;
    private DrawerLayout drawer_layout;
    private ListView list_left_drawer;
    private ArrayList<Item> menuLists;
    private DrawerAdapter<Item> myAdapter = null;
    //Fragment Object
    private ControlFragment controlFragment;
    private SecurityFragment securityFragment;
    private SceneFragment sceneFragment;
    private DeviceFragment deviceFragment;
    private FragmentManager fManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //设置抽屉
        drawer_layout = (DrawerLayout) findViewById(R.id.drawer_layout);
        list_left_drawer = (ListView) findViewById(R.id.list_left_drawer);
        menuLists = new ArrayList<Item>();
        menuLists.add(new Item(R.mipmap.ic_launcher,""));
        menuLists.add(new Item(R.mipmap.back,""));
        menuLists.add(new Item(R.mipmap.icon_drawer_me,"个人中心"));
        menuLists.add(new Item(R.mipmap.icon_drawer_setting,"设置管理"));
        menuLists.add(new Item(R.mipmap.icon_drawer_feedback,"意见反馈"));
        menuLists.add(new Item(R.mipmap.icon_drawer_us,"关于我们"));
        myAdapter = new DrawerAdapter<Item>(menuLists,R.layout.drawer_list_item) {
            @Override
            public void bindView(DrawerAdapter.ViewHolder holder, Item obj) {
                holder.setImageResource(R.id.drawer_list_icon,obj.getIconId());
                holder.setText(R.id.drawer_list_content, obj.getIconName());
            }
        };
        list_left_drawer.setAdapter(myAdapter);

        //设置Fragment
        fManager = getFragmentManager();
        bindViews();
        txt_control.performClick();   //模拟一次点击，既进去后选择第一项
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        txt_topbar = (TextView) findViewById(R.id.txt_topbar);
        txt_control = (TextView) findViewById(R.id.txt_control);
        txt_security = (TextView) findViewById(R.id.txt_security);
        txt_my = (TextView) findViewById(R.id.txt_my);
        txt_setting = (TextView) findViewById(R.id.txt_setting);
        ly_content = (FrameLayout) findViewById(R.id.ly_content);

        txt_control.setOnClickListener(this);
        txt_security.setOnClickListener(this);
        txt_my.setOnClickListener(this);
        txt_setting.setOnClickListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected(){
        txt_control.setSelected(false);
        txt_security.setSelected(false);
        txt_my.setSelected(false);
        txt_setting.setSelected(false);
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(controlFragment != null)fragmentTransaction.hide(controlFragment);
        if(securityFragment != null)fragmentTransaction.hide(securityFragment);
        if(sceneFragment != null)fragmentTransaction.hide(sceneFragment);
        if(deviceFragment != null)fragmentTransaction.hide(deviceFragment);
    }


    @Override
    public void onClick(View v) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (v.getId()){
            case R.id.txt_control:
                setSelected();
                txt_control.setSelected(true);
                if(controlFragment == null){
                    controlFragment = new ControlFragment();
                    fTransaction.add(R.id.ly_content,controlFragment);
                }else{
                    fTransaction.show(controlFragment);
                }
                break;
            case R.id.txt_security:
                setSelected();
                txt_security.setSelected(true);
                if(securityFragment == null){
                    securityFragment = new SecurityFragment();
                    fTransaction.add(R.id.ly_content,securityFragment);
                }else{
                    fTransaction.show(securityFragment);
                }
                break;
            case R.id.txt_my:
                setSelected();
                txt_my.setSelected(true);
                if(sceneFragment == null){
                    sceneFragment = new SceneFragment();
                    fTransaction.add(R.id.ly_content, sceneFragment);
                }else{
                    fTransaction.show(sceneFragment);
                }
                break;
            case R.id.txt_setting:
                setSelected();
                txt_setting.setSelected(true);
                if(deviceFragment == null){
                    deviceFragment = new DeviceFragment();
                    fTransaction.add(R.id.ly_content, deviceFragment);
                }else{
                    fTransaction.show(deviceFragment);
                }
                break;
        }
        fTransaction.commit();
    }
}