package com.techcollege.smarthomeapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SettingFragment extends Fragment {


    public EditText getPort,getIP,getIllumation,getPM25,getGas,getCo2;
    public Button setup;
    public View root;
    public TextView labSysState;
    ParentActivity activity;
    public SharedPreferences netPreferences,sensorPreferences;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_setting, null);
        init();
        return root;
    }
    public void init()
    {
        activity = (ParentActivity)getActivity();
        getPort = root.findViewById(R.id.txtGetPort);
        getIP = root.findViewById(R.id.txtGetAddress);
        getIllumation  = root.findViewById(R.id.txtGetIlluminationValue);
        getGas = root.findViewById(R.id.txtGetGasValue);
        getCo2 = root.findViewById(R.id.txtGetCo2Value);
        getPM25 = root.findViewById(R.id.txtGetPM25Value);
        setup = root.findViewById(R.id.btnSetup);
        labSysState = root.findViewById(R.id.labSystemState2);
        netPreferences =activity.netPreferences;
        getPort.setText(netPreferences.getString("Port",getPort.getText().toString()));
        getIP.setText(netPreferences.getString("Address",getIP.getText().toString()));
        sensorPreferences = activity.sensorPreferences;
        getIllumation.setText(String.valueOf(sensorPreferences.getFloat("Illumation",0)));
        getGas.setText(String.valueOf(sensorPreferences.getFloat("Gas",0)));
        getCo2.setText(String.valueOf(sensorPreferences.getFloat("Co2",0)));
        getPM25.setText(String.valueOf(sensorPreferences.getFloat("PM25",0)));
        setup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try
                {
                    //保存网络参数
                    SharedPreferences.Editor netEditor = netPreferences.edit();
                    netEditor.putString("Address",getIP.getText().toString().equals("")?"10.1.3.2":getIP.getText().toString());
                    netEditor.putString("Port",getPort.getText().toString().equals("")?"6006":getPort.getText().toString());
                    netEditor.commit();

                    //保存阈值参数
                    SharedPreferences.Editor sensorEditor = sensorPreferences.edit();
                    sensorEditor.putFloat("Illumation",Float.parseFloat(getIllumation.getText().toString()));
                    sensorEditor.putFloat("Gas",Float.parseFloat(getGas.getText().toString()));
                    sensorEditor.putFloat("Co2",Float.parseFloat(getGas.getText().toString()));
                    sensorEditor.putFloat("PM25",Float.parseFloat(getPM25.getText().toString()));
                    sensorEditor.commit();

                    Toast.makeText(activity,"保存成功，请重启...",Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {

                }
            }
        });
    }
}

