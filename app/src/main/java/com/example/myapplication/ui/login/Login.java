package com.example.myapplication.ui.login;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.R;
import com.example.myapplication.ui.personalcenter.PersonalCenter;
import com.example.myapplication.ui.rejister.Register;
import com.example.myapplication.utils.UITools;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {

    private static final String TAG = "SubWayLogin";
    private static final String LOGIN_URL ="http://127.0.0.1:8080/users/login";

    private String subwayCallback;
    private String subwayResponse;
    private String subwayToken;

    ConstraintLayout constraintLayout;
    EditText username;
    EditText password;
    MaterialButton loginBtn;
    MaterialButton registerBtn;
    WebView logonWebView;

    public static Login newInstance() {
        return new Login();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        constraintLayout = findViewById(R.id.logopage);
        loginBtn = findViewById(R.id.loginbtn);
        registerBtn = findViewById(R.id.register);
        username = findViewById(R.id.usernameEdit);
        password = findViewById(R.id.passwordEdit);
        logonWebView = findViewById(R.id.loginWebView);
        UITools.makeStatusBarTransparent(this);
        UITools.MIUISetStatusBarLightMode(this,true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), Register.class);
                startActivityForResult(intent, 2);
                finish();
            }
        });
    }

    private void login() {
        String url = "http://10.18.209.35:4000/users/login";

        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            String jwt_token = jsonResponse.getString("jwt_token");
                            Toast.makeText(getApplicationContext(),"登录成功", Toast.LENGTH_SHORT).show();
                            Log.e("token",jwt_token);
                            //返回token到LogonActivity
                            Intent intent = new Intent();
                            intent.putExtra("token", jwt_token);
                            setResult(3, intent);
                            finish();
                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),"账号或密码错误", Toast.LENGTH_SHORT).show();
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
