package com.gezonderelatiemeteten.afzal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NotificationDisplayActivity extends AppCompatActivity {

    String token,message;
    TextView tv;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_display);
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            flag=bundle.getInt("flag",-22);
            if (flag!=-22){
                message=bundle.getString("message","null body");
            }
            tv=(TextView) findViewById(R.id.explanation);
            Button close=(Button) findViewById(R.id.btn_find_me);
            close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            tv.setText(message);
        }
    }
}
