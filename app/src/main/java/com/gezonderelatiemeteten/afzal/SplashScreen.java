package com.gezonderelatiemeteten.afzal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.gezonderelatiemeteten.afzal.config.Config;
import com.google.firebase.iid.FirebaseInstanceId;
import java.util.HashMap;
import java.util.Map;

public class SplashScreen extends Activity {
    final int  SPLASH_TIME_OUT=2000;
    int flag=0;
    String token=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        SharedPreferences sPref=getSharedPreferences("TokenAnalyser",MODE_PRIVATE);
        if(sPref.getInt("tokenchecker",-22)==-22){
            SharedPreferences.Editor editor = sPref.edit();
            editor.commit();
            editor.putInt("tokenchecker", 1);
            // Get token
            token = FirebaseInstanceId.getInstance().getToken();
            if(token!=null) {
                sendTokenToServer(token);
                if (flag == 1) {
                    Log.d("flad", Integer.toString(flag + 1344));
                    editor.putInt("tokenchecker", 0);
                    editor.commit();
                }
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //send token first time durin te nstallation
                Intent i=new Intent(SplashScreen.this,DetailActivity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
    private void sendTokenToServer(final String token) {
        // Tag used to cancel the request
        String tag_string_req = "req_register";
        StringRequest strReq = new StringRequest(Request.Method.POST, Config.url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response",response);
                if(response.equals("added")){
                    flag=1;
                    Log.d("flad are ",Integer.toString(flag));
                }
                //Toast.makeText(getApplicationContext(), "User successfully registered. Try login now!", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", "Registration Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting params to register url
                Map<String, String> params = new HashMap<String, String>();
                //params.put("id","");
                params.put("token", token);
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
