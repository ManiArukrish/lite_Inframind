package com.example.afinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Activity3 extends AppCompatActivity {
    private Button button;
    private Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_3);
        button=(Button) findViewById(R.id.selectfile);
        send=(Button) findViewById(R.id.upload);
        button.setOnClickListener( v -> {
            files();
        });
        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view){
                Toast.makeText(Activity3.this,"Uploaded",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void files(){
        Intent intent=new Intent();
        intent.setType("applilcation/pdf");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"PDF FILE SELECT"),12);
    }
}