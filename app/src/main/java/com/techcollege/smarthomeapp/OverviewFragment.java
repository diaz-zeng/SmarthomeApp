package com.techcollege.smarthomeapp;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bizideal.smarthometest.lib.Json_data;
import com.bizideal.smarthometest.lib.Updata_activity;


public class OverviewFragment extends Fragment {

    public ParentActivity activity;
    public TextView labSysState;
    public View root;
    public TextView labTemp,labHumidity,labIllumation,labSmoke,labGas,labCo2,labPM25,labAirPressure;
    public RadioGroup rgMode;
    public RadioButton modeIP,modeSecurity,modeNone;
    public static int HumanIR;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_overview, null);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
        initMode();
    }

    public void init(){
        labSysState = root.findViewById(R.id.labSystemState1);
        labTemp = root.findViewById(R.id.labTemp);
        labHumidity = root.findViewById(R.id.labHumidity);
        labIllumation = root.findViewById(R.id.labIllumation);
        labSmoke = root.findViewById(R.id.labSmoke);
        labCo2 = root.findViewById(R.id.labCo2);
        labGas = root.findViewById(R.id.labGas);
        labPM25 = root.findViewById(R.id.labPM25);
        labAirPressure = root.findViewById(R.id.labAirpressure);
        activity = (ParentActivity)getActivity();
        rgMode = root.findViewById(R.id.rgMode);
        modeIP = root.findViewById(R.id.rbModeIntelligentPerception);
        modeSecurity = root.findViewById(R.id.rbModeSecurity);
        modeNone = root.findViewById(R.id.rbModeOff);
//        Updata_activity.updatahandler = new Handler()
//        {
//            @Override
//            public void handleMessage(Message msg) {
//                super.handleMessage(msg);
//                if(activity.controller.receive())
//                {
//                    sysState = activity.sysState;
//                    if(sysState!=null)
//                    {
//                        switch (sysState)
//                        {
//                            case Online:labSysState.setText("已成功连接至服务器");break;
//                            case NetError:labSysState.setText("连接服务器失败:请检查手机网络设置");break;
//                            case ConfigError:labSysState.setText("连接服务器失败:网络参数设置错误");break;
//                            case Offline:labSysState.setText("未连接至服务器");break;
//                        }
//                        try
//                        {
//                            labTemp.setText(activity.controller.receive_data.getString(Json_data.Temp));
//                            labHumidity.setText(activity.controller.receive_data.getString(Json_data.Humidity));
//                            labIllumation.setText(activity.controller.receive_data.getString(Json_data.Illumination));
//                            labSmoke.setText(activity.controller.receive_data.getString(Json_data.Smoke));
//                            labGas.setText(activity.controller.receive_data.getString(Json_data.Gas));
//                            labCo2.setText(activity.controller.receive_data.getString(Json_data.Co2));
//                            labPM25.setText(activity.controller.receive_data.getString(Json_data.PM25));
//                            labAirPressure.setText(activity.controller.receive_data.getString(Json_data.AirPressure));
//                        }
//                        catch (Exception ex)
//                        {
//
//                        }
//                    }
//
//                }
//            }
//        };
//        Thread refreshOverview = new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//                try
//                {
//                    Thread.sleep(500);
//                    activity.runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                           if(sysState!=null)
//                           {
//
//                               try {
//                                   labTemp.setText(activity.Temp);
//                                   labHumidity.setText(activity.Humidity);
//                                   labIllumation.setText(activity.Illumation);
//                                   labSmoke.setText(activity.Smoke);
//                                   labGas.setText(activity.Gas);
//                                   labCo2.setText(activity.Co2);
//                                   labPM25.setText(activity.PM25);
//                                   labAirPressure.setText(activity.AirPressure);
//                               }
//                               catch (Exception ex)
//                               {
//
//                               }
//
//                           }
//                        }
//                    });
//                }
//                catch (Exception ex)
//                {
//
//                }
//            }
//        });
//        refreshOverview.start();

    }


    public static boolean LampState=false,WarniniLightState=false,FanState=false;

    public static float tvIllumation,tvGas,tvPM25,tvCo2;
    private void initMode()
    {
        rgMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                tvIllumation = activity.sensorPreferences.getFloat("Illumation",0);
                tvGas = activity.sensorPreferences.getFloat("Gas",0);
                tvCo2 =activity.sensorPreferences.getFloat("Co2",0);
                tvPM25 = activity.sensorPreferences.getFloat("PM25",0);
                switch (i)
                {
                    case R.id.rbModeIntelligentPerception:
                    {
                        FanState=false;
                        LampState=false;
                        WarniniLightState=false;
                        if(tvCo2!=0&&tvGas!=0&&tvIllumation!=0&&tvPM25!=0)
                        {
                            Thread thread = new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    while (modeIP.isChecked())
                                    {
                                        if(activity.Co2>tvCo2&&!FanState)
                                        {
                                            activity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    NotificationManager manager = (NotificationManager) activity.getSystemService(activity.NOTIFICATION_SERVICE);
                                                    Notification.Builder builder = new Notification.Builder(activity);
                                                    builder.setTicker("智能家居移动控制平台");
                                                    builder.setDefaults(Notification.DEFAULT_ALL);
                                                    builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
                                                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),android.R.drawable.sym_def_app_icon));
                                                    PendingIntent ClickPending = PendingIntent.getActivity(activity, 0, new Intent().setClass(activity, ParentActivity.class), 0);
                                                    builder.setContentIntent(ClickPending);
                                                    activity.df.tbFan.setChecked(true);
                                                    activity.controller.control(Json_data.Fan,0,1);
                                                    builder.setContentTitle("二氧化碳水平超过阈值");
                                                    builder.setContentText("空气中二氧化碳水平超过阈值，系统已自动执行操作");
                                                    builder.setOnlyAlertOnce(true);
                                                    Notification notification = builder.build();
                                                    manager.notify(1,notification);
                                                }
                                            });
                                            try
                                            {

                                                activity. controller.control(Json_data.Fan,0,1);
                                                Thread.sleep(500);
                                            }
                                            catch (Exception ex)
                                            {

                                            }
                                            FanState=true;
                                        }
                                        else if(activity.Gas>tvGas&&!WarniniLightState)
                                        {
                                            activity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {

                                                    NotificationManager manager = (NotificationManager) activity.getSystemService(activity.NOTIFICATION_SERVICE);
                                                    Notification.Builder builder = new Notification.Builder(activity);
                                                    builder.setTicker("智能家居移动控制平台");
                                                    builder.setDefaults(Notification.DEFAULT_ALL);
                                                    builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
                                                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),android.R.drawable.sym_def_app_icon));
                                                    PendingIntent ClickPending = PendingIntent.getActivity(activity, 0, new Intent().setClass(activity, ParentActivity.class), 0);
                                                    builder.setContentIntent(ClickPending);
                                                    activity.df.tbFan.setChecked(true);
                                                    activity.df.tbWarningLight.setChecked(true);
                                                    builder.setContentTitle("可燃气体水平超过阈值");
                                                    builder.setContentText("空气中可燃气体水平超过阈值，系统已自动执行操作");
                                                    builder.setOnlyAlertOnce(true);
                                                    Notification notification = builder.build();
                                                    manager.notify(1,notification);
                                                }
                                            });
                                            try
                                            {
                                                Thread.sleep(1000);
                                                activity. controller.control(Json_data.Fan,0,1);
                                                Thread.sleep(1000);
                                                activity. controller.control(Json_data.WarningLight,0,1);
                                            }
                                            catch (Exception ex)
                                            {

                                            }
                                            WarniniLightState=true;
                                        }
                                        else if(activity.Illumation<tvIllumation&&!LampState)
                                        {
                                           activity.runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    NotificationManager manager = (NotificationManager) activity.getSystemService(activity.NOTIFICATION_SERVICE);
                                                    Notification.Builder builder = new Notification.Builder(activity);
                                                    builder.setTicker("智能家居移动控制平台");
                                                    builder.setDefaults(Notification.DEFAULT_ALL);
                                                    builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
                                                    builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),android.R.drawable.sym_def_app_icon));
                                                    PendingIntent ClickPending = PendingIntent.getActivity(activity, 0, new Intent().setClass(activity, ParentActivity.class), 0);
                                                    builder.setContentIntent(ClickPending);
                                                    activity.df.tbLamp.setChecked(true);
                                                    builder.setContentTitle("光照度低于阈值");
                                                    builder.setContentText("光照度低于阈值，系统已自动执行操作");
                                                    builder.setOnlyAlertOnce(true);
                                                    Notification notification = builder.build();
                                                    manager.notify(1,notification);
                                                }
                                            });
                                           try{
                                               Thread.sleep(1000);
                                               activity.controller.control(Json_data.Lamp,0,1);
                                           }
                                           catch (Exception ex)
                                           {

                                           }
                                           LampState=true;
                                        }
                                    }
                                }
                            });
                            thread.start();
                        }
                        else
                        {
                            Toast.makeText(activity,"阈值参数不可用，请重新设置",Toast.LENGTH_LONG).show();
                        }
                        break;
                    }
                    case R.id.rbModeSecurity:
                    {
                        Thread thread = new Thread(new Runnable() {
                            @Override
                            public void run() {
                                while (modeSecurity.isChecked())
                                {
                                    if(HumanIR!=0)
                                    {
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                NotificationManager manager = (NotificationManager) activity.getSystemService(activity.NOTIFICATION_SERVICE);
                                                Notification.Builder builder = new Notification.Builder(activity);
                                                builder.setTicker("智能家居移动控制平台");
                                                builder.setDefaults(Notification.DEFAULT_ALL);
                                                builder.setSmallIcon(android.R.drawable.sym_def_app_icon);
                                                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),android.R.drawable.sym_def_app_icon));
                                                PendingIntent ClickPending = PendingIntent.getActivity(activity, 0, new Intent().setClass(activity, ParentActivity.class), 0);
                                                builder.setContentIntent(ClickPending);
                                                builder.setContentTitle("遭到入侵");
                                                builder.setContentText("遭到非法入侵，请立即报警");
                                                builder.setOnlyAlertOnce(true);
                                                Notification notification = builder.build();
                                                manager.notify(1,notification);
                                                activity.df.tbWarningLight.setChecked(true);

                                            }
                                        });
                                        try
                                        {
                                            Thread.sleep(1000);
                                            activity.controller.control(Json_data.WarningLight,0,1);
                                        }
                                        catch (Exception ex)
                                        {

                                        }
                                        break;
                                    }
                                }
                            }
                        });
                        thread.start();
                        break;
                    }
                }
            }
        });
    }

}
