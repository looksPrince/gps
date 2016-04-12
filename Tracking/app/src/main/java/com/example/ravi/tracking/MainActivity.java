package com.example.ravi.tracking;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    Button button;
    TextView showloc;
    private Socket socket;
    public static String latlong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        showloc = (TextView) findViewById(R.id.textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

       final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                String h=(msg.obj).toString();
                latlong = h;
                showloc.setText(h);
                //super.handleMessage(msg);
            }
        };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clicked(View view) {

        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("172.25.35.157",1852);

                    OutputStream outToServer = socket.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToServer);
                    out.writeUTF("hi");

                    DataInputStream in =
                            new DataInputStream(socket.getInputStream());
//                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                   // showloc.setText(in.readUTF());
                    String value = in.readUTF();
                    Log.e("Error", value);
                    Message msg = handler.obtainMessage();

                    msg.obj = value;
                    handler.sendMessage(msg);


                } catch (UnknownHostException e) {
                    e.printStackTrace();
            }
        });

        t2.start();



    }

    public void mapdikha(View view){
        Intent i = new Intent(this,MapsActivity.class);
        i.putExtra("latlong",latlong);
        startActivity(i);
    }


}
