package com.deplinux.smarthome.Activity;

import android.app.Activity;
import android.os.Bundle;

import com.deplinux.smarthome.R;

/**
 * Created by Landscape on 2016/6/10.
 */
public class InfraredActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dv_infrared);
    }
}

