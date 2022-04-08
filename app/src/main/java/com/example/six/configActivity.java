package com.example.six;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class configActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        Intent intent=getIntent();
        float dollar2= intent.getFloatExtra("dollar_key",0.0f);
        float euro2= intent.getFloatExtra("euro_key",0.0f);
        float won2= intent.getFloatExtra("won_key",0.0f);
        EditText dol=findViewById(R.id.dollar);
        EditText eur=findViewById(R.id.euro);
        EditText won=findViewById(R.id.won);
        //修改
        String dol1=dol.getText().toString();
        String eur1=eur.getText().toString();
        String won1=dol.getText().toString();
        float dol3= Float.parseFloat(dol1);
        float eur3= Float.parseFloat(eur1);
        float won3= Float.parseFloat(won1);
    }

    public void save(View btn){
        EditText dol=findViewById(R.id.dollar);
        EditText eur=findViewById(R.id.euro);
        EditText won=findViewById(R.id.won);
        String dol1=dol.getText().toString();
        String eur1=eur.getText().toString();
        String won1=dol.getText().toString();
        float dol3= Float.parseFloat(dol1);
        float eur3= Float.parseFloat(eur1);
        float won3= Float.parseFloat(won1);
        Intent intent2 = new Intent(this, MainActivity.class);
        intent2.putExtra("ndollar", dol3);
        intent2.putExtra("neuro", eur3);
        intent2.putExtra("nwon", won3);
        startActivity(intent2);
    }
}