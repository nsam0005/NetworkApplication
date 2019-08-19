package com.example.networkapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private ConnectivityManager mConnMgr;
    private NetworkReceiver mReceiver;

    Button networkButton;
    TextView networkStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkButton = findViewById(R.id.button);
        networkStatusTextView = findViewById(R.id.status_message);

        mConnMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        mReceiver = new NetworkReceiver();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver,filter);

        networkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkNetworkStatus(getApplicationContext());
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mReceiver != null){
            unregisterReceiver(mReceiver);
        }
    }

    void checkNetworkStatus(Context context){

        if ( mConnMgr!= null){
            NetworkInfo nInfo = mConnMgr.getActiveNetworkInfo();
            boolean isWifiAvailable = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();

            if(nInfo != null && isWifiAvailable){
                networkStatusTextView.setText("Connected to Wifi");
            }
            else{
                networkStatusTextView.setText("Not connected to wifi or is Connected to mobile data");
            }
        }
    }


    public class NetworkReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if ( mConnMgr!= null){
                NetworkInfo networkInfo = mConnMgr.getActiveNetworkInfo();
                boolean isWifiAvailable = mConnMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();

                if(networkInfo != null && isWifiAvailable){
                    networkStatusTextView.setText("Connected to Wifi");
                }
                else{
                    networkStatusTextView.setText("Not connected to wifi or maybe is Connected to mobile data");
                }
            }
        }
    }




}
