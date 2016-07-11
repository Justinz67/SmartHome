package com.deplinux.smarthome.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;

import com.deplinux.smarthome.R;

/**
 * Created by Landscape on 2016/6/2.
 */
public class SceneAdd extends Activity {

    private AlertDialog alert = null;
    private AlertDialog.Builder builder = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.scene_add);


    }
}
