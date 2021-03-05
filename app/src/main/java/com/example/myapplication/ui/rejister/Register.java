package com.example.myapplication.ui.rejister;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.ui.login.Login;
import com.example.myapplication.utils.UITools;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private static final String TAG = "SubWayLogin";
    private static final String LOGIN_URL ="http://127.0.0.1:8080/users/register";

    ConstraintLayout constraintLayout;
    EditText username;
    EditText password;
    MaterialButton registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        constraintLayout = findViewById(R.id.register_page);
        registerBtn = findViewById(R.id.register_btn);
        username = findViewById(R.id.usernameRegister);
        password = findViewById(R.id.passwordRegister);
        UITools.makeStatusBarTransparent(this);
        UITools.MIUISetStatusBarLightMode(this,true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

    }

    private void register() {
        String url = "http://192.168.137.1:4000/users/register";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response).getJSONObject("form");
                            String site = jsonResponse.getString("site"),
                                    network = jsonResponse.getString("network");
                            System.out.println("Site: "+site+"\nNetwork: "+network);
                            Toast.makeText(getApplicationContext(),"注册成功", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent();
                            intent.setClass(getApplicationContext(), Login.class);
                            finish();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"注册失败", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                // the POST parameters:
                params.put("username", username.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

}
