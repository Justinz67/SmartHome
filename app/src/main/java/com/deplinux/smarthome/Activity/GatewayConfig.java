package com.deplinux.smarthome.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.deplinux.smarthome.Entity.Config;
import com.deplinux.smarthome.R;
import com.deplinux.smarthome.Util.MySharedPreferences;

import java.util.Map;


/**
 * Created by Landscape on 2016/5/31.
 */
public class GatewayConfig extends Activity {

      private static String  phone_IMEI;
      private Button btn_save;
      private EditText et_phoneNum;
      private EditText et_outerIP;
      private EditText et_innerIP;
      private TextView imeiTextView;
      private Context context;
      private MySharedPreferences mysp;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.gateway_config);

        //设置信息初始化
        init();
        //设置信息保存
        btn_save = (Button)findViewById(R.id.btn_save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mysp.save(et_phoneNum.getText().toString(),et_outerIP.getText().toString(),
                        et_innerIP.getText().toString(),imeiTextView.getText().toString());
//                Config config = new Config();
//                config.setPhoneNum(et_phoneNum.getText().toString());
//                config.setInnerIP(et_innerIP.getText().toString());
//                config.setOuterIP(et_outerIP.getText().toString());
                Intent intent = new Intent();
                intent.setClass(GatewayConfig.this,LoginActivity.class);
//                intent.putExtra("config",config);
                startActivity(intent);

            }
        });

    }

    private void init(){
        //显示本机IMEI号
        TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        phone_IMEI = tm.getDeviceId();
        imeiTextView = (TextView)findViewById(R.id.phone_IMEI);
        imeiTextView.setText(phone_IMEI);
        imeiTextView.setTextColor(getResources().getColor(R.color.text_topbar));
        imeiTextView.setTextSize(18);
        context = getApplicationContext();
        mysp = new MySharedPreferences(context);
        et_phoneNum = (EditText) findViewById(R.id.phone_NUM);
        et_outerIP = (EditText) findViewById(R.id.outer_ip);
        et_innerIP = (EditText) findViewById(R.id.inner_ip);
        //sp中读取用户配置信息
        Map<String,String> data  = mysp.read();
        et_phoneNum.setText(data.get("phoneNum"));
        et_outerIP.setText(data.get("outerIP"));
        et_innerIP.setText(data.get("innerIP"));
    }


}
