package com.deplinux.smarthome.Service;

import android.content.Intent;
import android.util.Log;

import com.deplinux.smarthome.Activity.LoginActivity;
import com.deplinux.smarthome.Activity.MainActivity;
import com.deplinux.smarthome.Util.ConstUtil;

import static android.support.v4.app.ActivityCompat.startActivity;

/**
 * Created by Landscape on 2016/6/10.
 */
public class UserServiceImpl implements UserService {

    private static  final String TAG = "UserServiceImpl";
    /**
     * 用户登录
     * @param username
     * @param password
     * @throws Exception
     */
    @Override
    public void userLogin(String username, String password) throws Exception {

        Log.d(TAG,username);
        Log.d(TAG,password);
        Thread.sleep(3000);
        if (username.equals("1")&&password.equals("1")){

        }else{
                throw new ServiceRulesException(ConstUtil.MSG_LOGIN_FAILED);
        }
    }

    @Override
    public void cmdSend(String cmd) throws Exception {

    }
}
