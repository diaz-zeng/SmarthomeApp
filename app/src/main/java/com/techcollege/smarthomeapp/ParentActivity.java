package com.techcollege.smarthomeapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bizideal.smarthometest.lib.Json_data;
import com.bizideal.smarthometest.lib.json_dispose;
import com.bizideal.smarthometest.lib.Updata_activity;
import com.bizideal.smarthometest.lib.SocketThread;
import com.bizideal.smarthometest.lib.MyThread;

import java.util.ArrayList;
import java.util.List;


public class ParentActivity extends FragmentActivity {

    OverviewFragment ov;
    SettingFragment set;
    DashboardFragment df;
    ViewPager viewPager;
    List<Fragment> Fragments;
    public SysState sysState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
        netPreferences = getSharedPreferences("netConfig",MODE_PRIVATE);
        sensorPreferences = getSharedPreferences("sensorConfig",MODE_PRIVATE);
        ov = new OverviewFragment();
        df = new DashboardFragment();
        set = new SettingFragment();
        viewPager = findViewById(R.id.viewPager);
        Fragments = new ArrayList<Fragment>();
        Fragments.add(ov);
        Fragments.add(df);
        Fragments.add(set);
        viewPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(),Fragments));
        loadNetworkConfig();
        init();
    }

    public int Temp,Humidity,Illumation,Smoke,Gas,PM25,Co2,AirPressure;
    public static int HumanIR;


    int port;
    String ip;
    public json_dispose controller = new json_dispose();
    Thread updata;
    boolean netState = false;
    SharedPreferences netPreferences,sensorPreferences;


    private void init()
    {

        updata = new Thread(new Updata_activity());
        updata.start();
        SocketThread.mHandlerSocketState = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(!netState)
                {
                    if(msg.getData().getString("SocketThread_State").equals("error"))
                    {
                        sysState = SysState.NetError;
                    }
                    else
                    {
                        netState =  true;
                        sysState = SysState.Online;
                    }
                }
            }
        };
        Updata_activity.updatahandler = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(controller.receive())
                {

                    try {
                        Temp = controller.receive_data.getInt(Json_data.Temp);
                        Humidity = controller.receive_data.getInt(Json_data.Humidity);
                        AirPressure = controller.receive_data.getInt(Json_data.AirPressure);
                        Illumation = controller.receive_data.getInt(Json_data.Illumination);
                        Co2 = controller.receive_data.getInt(Json_data.Co2);
                        Gas = controller.receive_data.getInt(Json_data.Gas);
                        PM25 = controller.receive_data.getInt(Json_data.PM25);
                        Smoke = controller.receive_data.getInt(Json_data.Smoke);
                        OverviewFragment.HumanIR = controller.receive_data.getInt(Json_data.StateHumanInfrared);
                    }
                    catch (Exception ex) {

                    }
                    try
                    {
                        switch (sysState)
                        {
                            case Online: {
                                ov.labSysState.setText("已成功连接至服务器");
                                set.labSysState.setText("已成功连接至服务器");
                                break;
                            }
                            case NetError: {
                                ov.labSysState.setText("连接服务器失败:请检查手机网络设置");
                                set.labSysState.setText("连接服务器失败:请检查手机网络设置");
                                break;
                            }
                            case ConfigError:
                                {
                                ov.labSysState.setText("连接服务器失败:网络参数设置错误");
                                set.labSysState.setText("连接服务器失败:网络参数设置错误");
                                break;
                            }
                            case Offline:
                            {
                                ov.labSysState.setText("未连接至服务器");
                                set.labSysState.setText("未连接至服务器");
                                break;
                            }
                        }
                        ov.labTemp.setText(controller.receive_data.getString(Json_data.Temp));
                        ov.labTemp.setText(controller.receive_data.getString(Json_data.Temp));
                        ov.labHumidity.setText(controller.receive_data.getString(Json_data.Humidity));
                        ov.labIllumation.setText(controller.receive_data.getString(Json_data.Illumination));
                        ov.labSmoke.setText(controller.receive_data.getString(Json_data.Smoke));
                        ov.labGas.setText(controller.receive_data.getString(Json_data.Gas));
                        ov.labCo2.setText(controller.receive_data.getString(Json_data.Co2));
                        ov.labPM25.setText(controller.receive_data.getString(Json_data.PM25));
                        ov.labAirPressure.setText(controller.receive_data.getString(Json_data.AirPressure));
                        df.labTemp.setText(controller.receive_data.getString(Json_data.Temp));
                        df.labTemp.setText(controller.receive_data.getString(Json_data.Temp));
                        df.labHumidity.setText(controller.receive_data.getString(Json_data.Humidity));
                        df.labIllumation.setText(controller.receive_data.getString(Json_data.Illumination));
                        df.labGas.setText(controller.receive_data.getString(Json_data.Gas));
                        df.labCo2.setText(controller.receive_data.getString(Json_data.Co2));
                        df.labPM25.setText(controller.receive_data.getString(Json_data.PM25));
                        df.labAirPressure.setText(controller.receive_data.getString(Json_data.AirPressure));

                    }
                    catch (Exception ex)
                    {

                    }
                }
            }
        };
    }



    private void loadNetworkConfig()
    {
        try {
            SocketThread.SocketIp = netPreferences.getString("Address","10.1.3.2");
            SocketThread.Port = Integer.parseInt(netPreferences.getString("Port","6006"));
        }
        catch (Exception ex)
        {
            sysState = SysState.ConfigError;
        }
    }






}
