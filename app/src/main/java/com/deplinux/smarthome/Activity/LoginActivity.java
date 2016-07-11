package com.deplinux.smarthome.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.deplinux.smarthome.R;
import com.deplinux.smarthome.Service.ServiceRulesException;
import com.deplinux.smarthome.Service.UserService;
import com.deplinux.smarthome.Service.UserServiceImpl;
import com.deplinux.smarthome.Util.ConstUtil;
import com.deplinux.smarthome.Util.HttpUtil;

import org.json.JSONArray;
import net.sf.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Landscape on 2016/5/23.
 */
public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView gate_config;
    private Button btn_login;
    private EditText et_username;
    private EditText et_password;
    private UserService userService = new UserServiceImpl();
    private static ProgressDialog dialog;

    public void  init(){
        gate_config = (TextView)findViewById(R.id.gate_config);
        btn_login = (Button)findViewById(R.id.btn_login);
        et_username = (EditText)findViewById(R.id.et_username);
        et_password = (EditText)findViewById(R.id.et_password);
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.user_login);
        /**
         * 初始化操作
         */
        this.init();

        /**
         * 用户登录
         */
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = et_username.getText().toString().trim();
                final String password = et_password.getText().toString().trim();
                //输入值验证
                //loading
                if(dialog==null)
                {
                    dialog = new ProgressDialog(LoginActivity.this);
                }
                dialog.setTitle("请等待");
                dialog.setMessage("登录中...");
                dialog.setIcon(R.mipmap.icon_bulb);
                dialog.setCancelable(false);
                dialog.show();
                //登录线程
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                                userService.userLogin(username,password);
                                handler.sendEmptyMessage(ConstUtil.FLAG_LOGIN_SUCCESS);
                                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(intent);
                        }catch (ServiceRulesException e){
                            e.printStackTrace();
                            Message msg = new Message();
                            Bundle data = new Bundle();
                            data.putSerializable("ErrorMsg",e.getMessage());
                            msg.setData(data);
                            handler.sendMessage(msg);
                        }catch(Exception e){
                            e.printStackTrace();
                            Message msg = new Message();
                            Bundle data = new Bundle();
                            data.putSerializable("ErrorMsg",ConstUtil.MSG_LOGIN_ERROR);
                            msg.setData(data);
                            handler.sendMessage(msg);

                        }
                    }
                });
                thread.start();

//                if(validate())
//                {
//                    if(login())
//                    {
//                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                    else
//                    {
//                        Toast.makeText(LoginActivity.this,"用户名或密码错误，请重新输入！",Toast.LENGTH_SHORT).show();
//                    }
//                }
            }
        });

        /**
         * 网关配置
         */
        gate_config.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,GatewayConfig.class));
            }
        });


    }
    //handler消息通知
    private  void showTip(String str){

        Toast.makeText(this,str,Toast.LENGTH_SHORT).show();
    }
    private static class IHandler extends Handler{
        private  final WeakReference<Activity> mActivity;

        public IHandler(LoginActivity activity){
            mActivity = new WeakReference<Activity>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            if (dialog != null){
                dialog.dismiss();
            }
            int flag = msg.what;
            switch (flag){
                case 0:
                    String errorMsg = (String)msg.getData().getSerializable("ErrorMsg");
                    ((LoginActivity)mActivity.get()).showTip(errorMsg);
                    break;
                case ConstUtil.FLAG_LOGIN_SUCCESS:
                    ((LoginActivity)mActivity.get()).showTip(ConstUtil.MSG_LOGIN_SUCCESS);
                    break;
                default:
                    break;
            }
        }
    }
    private  IHandler handler = new IHandler(this);

    @Override
    public void onClick(View v) {

    }
    //发送请求
    private JSONObject query(String username,String pwd) throws Exception {

        Map<String,String> map = new HashMap<String,String>();
        map.put("user",username);
        map.put("pwd",pwd);
        JSONObject mData = new JSONObject();
        mData.put("user",username);
        mData.put("pwd",pwd);
        String url = HttpUtil.BASE_URL;
        //POST方式发送请求
        //return  new JSONObject(HttpUtil.postRequest(url,mData));
        return null;

    }
    //登录校验
    private  boolean validate(){
        String username = et_username.getText().toString().trim();
        if(username.equals(""))
        {
            Toast.makeText(LoginActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
            return  false;
        }
        String password = et_password.getText().toString().trim();
        if(password.equals(""))
        {
            Toast.makeText(LoginActivity.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
            return  false;
        }

        return true;
    }
    //服务器验证登录信息
    private boolean login(){
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();
        JSONObject jsonObject;
        try{
                if(username.equals("1")&&password.equals("1"))
                    return true;
//                jsonObject = query(username,password);
//                if(jsonObject.getInt("user")>0)
//                {
//                    return true;
//                }
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return  false;
    }
}

