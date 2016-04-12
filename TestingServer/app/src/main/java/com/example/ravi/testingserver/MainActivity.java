package com.example.ravi.testingserver;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {
    public static Socket socket;
    public static String nick = "";
    public static OutputStream DOS;
    public static PrintWriter p;
    // public static String messsage;
    Thread t1;
    private EditText textField;
    private Button button;
    public String messsage;
    private LocationManager locman;
    private LocationListener loclis;
    TextView loc;
    static StringBuilder buider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        MainActivity obj = new MainActivity();

        loc = (TextView) findViewById(R.id.textView);
      //  textField = (EditText) findViewById(R.id.editText); //reference to the text field
        buider = new StringBuilder();
        buider.setLength(3);
        button = (Button) findViewById(R.id.button);
        locman = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
     //  buider.append(172.522);
        loclis = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                // dbaccess tracking = new dbaccess(location.getLatitude(),location.getLongitude());
                //handle.dbadd(tracking);
//                dbaccess tracking1 = new dbaccess(2.0555,53.0255);
//                handle.dbadd(tracking1);

                loc.append("\n" + location.getLatitude() + " " + location.getLongitude() + "");
                buider.append(location.getLatitude());
                buider.append("  ");
                buider.append(location.getLongitude());
                // obj.cicked(buider.toString());
                // Toast.makeText(getApplicationContext(),"ghvhj",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
                // Log.e(TAG,"gps nahi hain");
            }
        };

    }

    public void gpscord(View view) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locman.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100000, 0, loclis);
    }

    public void clicked(View view){
       // Toast.makeText(this,"aass",Toast.LENGTH_SHORT).show();
        //messsage = textField.getText().toString(); //get the text message on the text field
        Thread t1= new Thread()
        {
           //Toast.makeText(this,messsage,Toast.LENGTH_SHORT).show();

            @Override
            public void run() {
                try {
                    // Toast.makeText(getApplicationContext(),"ajkass",Toast.LENGTH_SHORT).show();
                    socket = new Socket("172.25.12.227", 1852);
                    //  Thread.sleep(500);
                    Log.e("Hello", "inside try");
//                    DOS = socket.getOutputStream();
//                    p = new PrintWriter(DOS, true);
//                    p.println(messsage + (DateFormat.format("dd-MM-yyyy hh:mm:ss", new java.util.Date()).toString()));       // sending to server
//                    p.flush();

                    OutputStream outToServer = socket.getOutputStream();
                    DataOutputStream out = new DataOutputStream(outToServer);
                    out.writeUTF(buider.toString());

                } catch (Exception e) {
                    e.printStackTrace();//Toast.makeText(getApplicationContext(),"galti hain",Toast.LENGTH_SHORT).show();
                    Log.e("Hello", "inside exception");
//restartActivity();
                }

            }    };

        Log.e("Hello","starting");
        t1.start();
    }
    void restartActivity()
    {t1.stop();
        MainActivity.this.finish();
        Intent mIntent = new Intent(MainActivity.this, MainActivity.class);
        startActivity(mIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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
}
