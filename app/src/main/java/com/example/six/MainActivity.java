package com.example.six;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = null;
    private float dollarRate=0.12f;
    private float euroRate=0.23f;
    private float wonRate=120;

    Handler handler;
    private Object InputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //开启子线程
        Thread t=new Thread(this::openconfig);
        t.start();

        handler =new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                if(msg.what==5){
                    String str=(String) msg.obj;
                }
                super.handleMessage(msg);
            }
        };
    }
    public void Click(View btn){
        EditText input=findViewById(R.id.text_input);
        String str=input.getText().toString();
        TextView result=findViewById(R.id.textView);
        if(str.length()==0){
            Toast.makeText(this, "请输入金额", Toast.LENGTH_SHORT).show();
            return;
        }
        float inp= Float.parseFloat(str);
        float r=0.0f;
        if(btn.getId()==R.id.butdoll){
            result.setText("结果："+ inp*dollarRate);
        }
        if(btn.getId()==R.id.buthan){
            result.setText("结果："+ inp*euroRate);
        }
        if(btn.getId()==R.id.butjpan){
            result.setText("结果："+ inp*wonRate);
        }
    }
    public  void open(View btn){

        openconfig();
    }

    private void openconfig() {
        Intent config=new Intent(this,configActivity.class);
        config.putExtra("dollar_key",dollarRate);
        config.putExtra("euro_key",euroRate);
        config.putExtra("won_key",wonRate);
        startActivityForResult(config,3);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.rate_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(item.getItemId()==R.id.settings)
            return super.onOptionsItemSelected(item);
        return false;
    }
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==3&&requestCode==6){
                Bundle bundle=data.getExtras();
                dollarRate=bundle.getFloat("key_dollar2");
                euroRate=bundle.getFloat("key_euro2");
                wonRate=bundle.getFloat("key_won2");
                //保存汇率文件
                /*SharedPreferences sp=getSharedPreferences("myrate",Activity);
                SharedPreferences.Editor editor=sp.edit();
                editor.putFloat("dollar_rate",dollarRate);
                editor.putFloat("euro_rate",euroRate);
                editor.putFloat("won_rate",wonRate);
                editor.apply();*/
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
    public void run() throws MalformedURLException {
         for(int i=1;i<6;i++){
             try{
                 Thread.sleep(2000);
             }catch(InterruptedException e){
                 e.printStackTrace();
             }
         }
         Message msg=handler.obtainMessage(5);
         msg.obj="HeLLO";
         handler.sendMessage(msg);

         URL url=null;
         try{
             url=new URL("http://www.usd-cny.com/icbc.htm");
             HttpURLConnection http=(HttpURLConnection) url.openConnection();
             InputStream in= http.getInputStream();

             String html=inputStream2String(in);
             Log.i(TAG,"run:html=");
         }catch (MalformedURLException e){
             e.printStackTrace();
         }catch(IOException e){
             e.printStackTrace();
         }
    }
    public String inputStream2String(InputStream inputStream)throws IOException{
        final int bufferSize=1024;
        final char[] buffer=new char[bufferSize];
        final StringBuilder out=new StringBuilder();
        Reader in=new InputStreamReader((java.io.InputStream) InputStream,"utf-8");
        for(; ; ){
            int rsz=in.read(buffer,0,buffer.length);
            if(rsz<0)
                break;
            out.append(buffer,0,rsz);
        }
        return out.toString();
    }
}