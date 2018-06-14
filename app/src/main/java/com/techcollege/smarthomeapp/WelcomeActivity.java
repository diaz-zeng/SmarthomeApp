package com.techcollege.smarthomeapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try
                {
                    Thread.sleep(3000);
                    startActivity(new Intent(WelcomeActivity.this,ParentActivity.class));
                    WelcomeActivity.this.finish();
                }
                catch (Exception ex)
                {

                }
            }
        });
        thread.start();
    }

}
