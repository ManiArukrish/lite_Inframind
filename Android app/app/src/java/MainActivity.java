package com.example.afinal;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.N)
public class MainActivity<min> extends AppCompatActivity {



    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    BluetoothSocket bluetoothSocket = null;
    InputStream inputStream;
    int i, heartbeat,spo2,temp;
    String sdfString;
    String deviceName = "";
    @SuppressLint("NewApi")
    SimpleDateFormat sdf = new SimpleDateFormat(  "dd-MM-yyyy-hh-mm-ss");

    private Button button,button2;

    Random rand = new Random();

    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textview1 = (TextView) findViewById(R.id.textview1);
        TextView textview2 = (TextView) findViewById(R.id.textView2);
        TextView textview3 = (TextView) findViewById(R.id.textview3);
        sdfString=sdf.format(new Date());
        try{
            BluetoothDevice hc05 = bluetoothAdapter.getRemoteDevice( "00:18:E4:00:43:6C" );
            bluetoothSocket = hc05.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            bluetoothSocket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if(pairedDevices.size() > 0){
            for(BluetoothDevice device : pairedDevices){
                if (device.getAddress().equals("00:18:E4:00:43:6C")){
                    deviceName += device.getName();
                    break;
                }
            }
        }
        Thread th = new Thread(){

            @Override
            public void run() {
                while (!isInterrupted()) {

                    try {
                        Thread.sleep(30000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    inputStream = bluetoothSocket.getInputStream();
                                    byte[] buffer = new byte[30];
                                    inputStream.read(buffer);
                                    for (i = 29; buffer[i] != 35 && buffer[i] != 64; i--) ;
                                    heartbeat = 0;
                                    spo2 = 0;
                                    if (buffer[i] == 64) {
                                        for (i--; buffer[i] != 35; i--)
                                            heartbeat = (heartbeat * 10) + (buffer[i] - 48);
                                        for (i--; buffer[i] != 64; i--)
                                            spo2 = (spo2 * 10) + (buffer[i] - 48);
                                    } else {
                                        for (i--; buffer[i] != 64; i--)
                                            heartbeat = (heartbeat * 10) + (buffer[i] - 48);
                                        for (i--; buffer[i] != 35; i--)
                                            spo2 = (spo2 * 10) + (buffer[i] - 48);
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                temp= 38;
                                textview1.setText(String.valueOf(heartbeat));
                                textview2.setText(String.valueOf(spo2));
                                textview3.setText(String.valueOf(temp));
                                //employeeRegister er= new employeeRegister();

                                database = FirebaseDatabase.getInstance();
                                reference = database.getReference("EmployeesData").child("Health Device Name").child(deviceName).child(sdfString);
                                workersdata status = new workersdata(heartbeat, spo2, temp);
                                reference.setValue(status);


                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
    };

th.start();


       button =(Button) findViewById(R.id.button3);
        button2 =(Button) findViewById(R.id.button5);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                records();

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                log();
            }
        });

    }
    public void records(){
        Intent packageContext;
        Intent intent = new Intent( this,Activity3.class);
        startActivity(intent);




    }
    public void log(){
        Intent packageContext;
        Intent intent = new Intent( this,Login.class);
        startActivity(intent);

    }



}