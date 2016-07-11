package com.deplinux.smarthome.Service;

/**
 * Created by Landscape on 2016/6/10.
 */
public interface UserService {

    public void userLogin(String username,String password) throws Exception;

    public void cmdSend(String cmd) throws  Exception;
}
