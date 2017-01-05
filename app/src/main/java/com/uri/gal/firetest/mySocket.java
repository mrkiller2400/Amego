package com.uri.gal.firetest;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;


public final class mySocket {

    public static String data;
    public static Socket s = null;

    public static void connected()throws IOException {
        try {
            s = new Socket("192.168.43.132", 45654);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static void sendMessage(String msg)
    {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
            //send message to the serve
            bw.write(msg);
            bw.newLine();
            bw.flush();
        }catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public static void getMessageFromServer() {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            data = br.readLine();

            br.close();
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
    public static void disconected()
    {
        try {
            s.close();
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
    public static String getData()    {
        return data;
    }
}