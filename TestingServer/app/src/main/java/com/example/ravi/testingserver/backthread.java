package com.example.ravi.testingserver;

import android.text.Html;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ravi on 12-Mar-16.
 */
public class backthread {
    MainActivity mainActivity;
   public String message = mainActivity.messsage;
    String msend="";
    public static InputStream DIN;

    public void upd(){
        new Thread(new Runnable()
        {

            @Override
            public void run() {

                try {
                    DIN=mainActivity.socket.getInputStream();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                BufferedReader receiveRead = new BufferedReader(new InputStreamReader(DIN));


                String receiveMessage;
                while(true){


                    try {

                        if((receiveMessage = receiveRead.readLine()) != null){
                          //  tv3.setText(Html.fromHtml(receiveMessage));



                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    } }
            }

        }).start();
    }


}
