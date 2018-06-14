package com.techcollege.smarthomeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.bizideal.smarthometest.lib.Json_data;


public class DashboardFragment extends Fragment {

    View root;

    TextView labTemp,labHumidity,labIllumation,labCo2,labGas,labPM25,labAirPressure;

    ToggleButton tbLamp,tbWarningLight,tbFan,tbDoor;

    Button btnDVDPower,btnDVDOn,btnCurtainOn,btnCurtainOff,btnCurtainStop;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_dashboard,null);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        init();
    }

    void init()
    {
        final ParentActivity activity = (ParentActivity)getActivity();
        labTemp = root.findViewById(R.id.OverviewTemp);
        labHumidity = root.findViewById(R.id.OverviewHumidity);
        labIllumation = root.findViewById(R.id.OverviewIllumation);
        labCo2 = root.findViewById(R.id.OverviewCo2);
        labGas = root.findViewById(R.id.OverviewGas);
        labPM25 = root.findViewById(R.id.OverviewPM25);
        labAirPressure = root.findViewById(R.id.OverviewAirpressure);
        tbLamp = root.findViewById(R.id.tbLamp);
        tbWarningLight = root.findViewById(R.id.tbWarningLight);
        tbFan = root.findViewById(R.id.tbFan);
        tbDoor = root.findViewById(R.id.tbDoor);
        btnDVDPower = root.findViewById(R.id.btnDVDpower);
        btnDVDOn = root.findViewById(R.id.btnDVDopen);
        btnCurtainOn = root.findViewById(R.id.btnCurtainOn);
        btnCurtainStop = root.findViewById(R.id.btnCurtainStop);
        btnCurtainOff = root.findViewById(R.id.btnCurtainClose);
        tbLamp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                activity.controller.control(Json_data.Lamp,0 ,b?1:0);
            }
        });
        tbWarningLight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                activity.controller.control(Json_data.WarningLight,0 ,b?1:0);
            }
        });
        tbFan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                activity.controller.control(Json_data.Fan,0 ,b?1:0);
            }
        });
        tbDoor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                activity.controller.control(Json_data.RFID_Open_Door, 0, b ? 1 : 0);
            }
        });
        btnDVDPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.controller.control(Json_data.InfraredLaunch,0,1);
            }
        });
        btnDVDOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.controller.control(Json_data.InfraredLaunch,0,2);
            }
        });

        btnCurtainOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.controller.control(Json_data.Curtain ,0,1);
            }
        });
        btnCurtainStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.controller.control(Json_data.Curtain ,0,2);
            }
        });
        btnCurtainOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.controller.control(Json_data.Curtain ,0,3);
            }
        });
    }
}
