package com.deplinux.smarthome.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.deplinux.smarthome.R;
import com.deplinux.smarthome.Util.ConstUtil;
import com.deplinux.smarthome.Util.HttpUtil;

import org.json.JSONObject;

/**
 * Created by Landscape on 2016/6/10.
 */
public class CurtainActivity extends Activity {

    private ImageView img_middle;
    private ImageView img_left;
    private ImageView img_right;
    private ImageView img_top;
    private ImageView img_bottom;
    private JSONObject cmd;
    private Handler handler;
    public static final int SEND_SUCCESS=0x123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dv_curtain);
        //初始化
        init();
        //左右窗帘控制
        //拉开窗帘
        img_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendCmd(operateDevice(ConstUtil.DV_OPERATE_ON));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        //关闭窗帘
        img_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendCmd(operateDevice(ConstUtil.DV_OPERATE_OFF));
                }catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
        //百叶窗窗帘控制

    }
    private void init(){
        img_middle = (ImageView)findViewById(R.id.img_middle);
        img_left = (ImageView)findViewById(R.id.img_left);
        img_right = (ImageView)findViewById(R.id.img_right);
        img_top = (ImageView)findViewById(R.id.img_top);
        img_bottom = (ImageView)findViewById(R.id.img_bottom);
    }

    private void sendCmd(final JSONObject cmd){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    String res = HttpUtil.postRequest(HttpUtil.BASE_URL + "/cgi-bin/controldevice",
                            cmd);
                    System.out.println(cmd.toString()+"操作结果：----------->"+res.toString());
                }catch (Exception e)
                {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    /**
     * 向服务器发送操作命令
     * @param cmd
     * @return String
     * @throws Exception
     */
    private JSONObject operateDevice(String cmd) throws Exception
    {
        JSONObject mData = new JSONObject();
        mData.put("telephoneID","050002");
        mData.put("phoneNumber","15802948422");
        mData.put("imei","866486025282363");
        mData.put("versionID","35");
        mData.put("deviceID","070001");
        mData.put("commandType",cmd);
        mData.put("command","0");
        return mData;
    }
}
