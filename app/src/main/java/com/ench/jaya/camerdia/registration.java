package com.ench.jaya.camerdia;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity {
    EditText edname, edpass, edemail, edconpass;
    Button button;
    TextView jaaa;
    String sedmail, sedname, sedpass, sedconpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        edname = (EditText) findViewById(R.id.edusrname);
        edemail = (EditText) findViewById(R.id.edmai);
        edpass = (EditText) findViewById(R.id.edpas);
        edconpass = (EditText) findViewById(R.id.edconpass);
        button = (Button) findViewById(R.id.btreg);
        jaaa=(TextView) findViewById(R.id.tap) ;
        jaaa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(registration.this, Login.class);
                startActivity(intent);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sedname = edname.getText().toString();

                sedpass = edpass.getText().toString();
                sedmail = edemail.getText().toString();
                if(sedname.length()==0)
                {
                    edname.requestFocus();
                    edname.setError("FIELD CANNOT BE EMPTY");
                }

                if(sedpass.length()==0)
                {
                    edpass.requestFocus();
                    edpass.setError("FIELD CANNOT BE EMPTY");
                }

                if(sedmail.length()==0)
                {
                    edemail.requestFocus();
                    edemail.setError(" FIELDjaaaa CANNOT BE EMPTY");

                }

                if(sedpass.length()<6)
                {
                    edpass.requestFocus();
                    edpass.setError("Password should be more than 6 characters");
                }
                if(sedpass==sedconpass)
                {
                    edconpass.requestFocus();
                    edconpass.setError("password should be equal to confirm password");
                }

                insertnew(sedname, sedpass, sedmail);
            }
        });


    }




    public void insertnew(final String sedname1, final String sedpass1, final String sedmail1) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,Global_url.reg, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean abc = jObj.getBoolean("exists");
                    if (abc)
                    {
                        JSONObject users = jObj.getJSONObject("users");
                        String uname1 = users.getString("username");
                        String uage1 = users.getString("mobile_number");
                        Intent intent=new Intent(registration.this,Login.class);
                        intent.putExtra("ghtw",uname1);
                        intent.putExtra("sssw",uage1);
                        startActivity(intent);
                        //   Toast.makeText(getApplicationContext(),mobile_number,Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(),"Server Busy",Toast.LENGTH_SHORT).show();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> inserth = new HashMap<String, String>();
                inserth.put("username", sedname1);
                inserth.put("password", sedpass1);
                inserth.put("email", sedmail1);

                return inserth;

            }
        };
        AppController.getInstance().addToRequestQueue(stringRequest);

    }

}



