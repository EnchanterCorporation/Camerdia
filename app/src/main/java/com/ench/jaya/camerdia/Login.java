package com.ench.jaya.camerdia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ncorti.slidetoact.SlideToActView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText edlogemail,edlogpass;
    Button butlog1;
    TextView txgetpass,txgetacc;
SlideToActView sta1;
    private List<SlideToActView> mSlideList;
    String sedlogemail,sedlogpass;
    SlideToActView  sta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edlogemail=(EditText)findViewById(R.id.lgemail);
        edlogpass=(EditText)findViewById(R.id.lgpass);

        sta1   = (SlideToActView) findViewById(R.id.example1);
   sta   = (SlideToActView) findViewById(R.id.example);

        sta1.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NotNull SlideToActView view) {

                Intent mainIntent= new Intent(Login.this,registration.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(mainIntent);
                sta1.resetSlider();

            }
        });


        sta1.setOnSlideResetListener(new SlideToActView.OnSlideResetListener() {
            @Override
            public void onSlideReset(@NotNull SlideToActView view) {
                sta1.resetSlider();
            }
        });
        sta.setOnSlideCompleteListener(new SlideToActView.OnSlideCompleteListener() {
            @Override
            public void onSlideComplete(@NotNull SlideToActView view) {

                sedlogemail=edlogemail.getText().toString();
                sedlogpass=edlogpass.getText().toString();
                if (sedlogemail.isEmpty() ||sedlogemail.isEmpty() )
                {
                    Toast.makeText(Login.this, "Enter a valid input", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loginset(sedlogemail,sedlogpass);
                }
                if (sedlogpass.isEmpty() ||sedlogpass.isEmpty() )
                {
                    Toast.makeText(Login.this, "password cannot be empty ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loginset(sedlogemail,sedlogpass);
                }


            }
        });


    }
    public void loginset(final String sedlogemail1, final String sedlogpass1) {
        StringRequest stringreqs=new StringRequest(Request.Method.POST, Global_url.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean abc = jObj.getBoolean("exits");
                    if (abc)
                    {
                        JSONObject users = jObj.getJSONObject("user_det");
                        String email = users.getString("email");
                        String email1 = users.getString("password");


                        Intent mainIntent=new Intent(Login.this,Bus_Booking.class);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        mainIntent.putExtra("username",email);
                        mainIntent.putExtra("mobile_number",email1);
                        startActivity(mainIntent);
                        //  Toast.makeText(getApplicationContext(),email+"/"+username,Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        String messageofserver = jObj.getString("messege");
                        sta.getOnSlideResetListener();
                        Toast.makeText(getApplicationContext(),messageofserver,Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"INTERNET CONNECTION NOT AVAILABLE",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> uandme=new HashMap<String, String>();
                uandme.put("email",sedlogemail1);
                uandme.put("password",sedlogpass1);
                return uandme;
            }
        };
        AppController.getInstance().addToRequestQueue(stringreqs);
    }

}

