package com.database.society;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private ProgressBar progress_bar;
    private FloatingActionButton fab;
    private View parent_view;
    TextInputEditText username,password;
    CheckBox saveLoginCheckBox;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;
    private Boolean saveLogin;
    ImageView imageView;
    String URL = "http://hawarecenturionnerul.com/android/login/new";
    String res,user;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        parent_view = findViewById(android.R.id.content);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        username = (TextInputEditText) findViewById(R.id.username);
        password = (TextInputEditText) findViewById(R.id.password);
        imageView=(ImageView)findViewById(R.id.imageView);
        saveLoginCheckBox = (CheckBox) findViewById(R.id.saveLoginCheckBox);

        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin.equals(true)) {
            //startActivity(new Intent(Login.this, HomePage.class));
            //finish();
        }
        else {
            fab = (FloatingActionButton) findViewById(R.id.fab);


            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    progress_bar.setVisibility(View.VISIBLE);

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(username.getWindowToken(), 0);





                    StringRequest request = new StringRequest(Request.Method.POST,URL,new Response.Listener<String>() {
                        @Override
                        public void onResponse(String s) {
                         progress_bar.setVisibility(View.GONE);
                         Toast.makeText(getApplicationContext(),s.toString(),Toast.LENGTH_SHORT).show();
                            final String username1 = username.getText().toString();
                            final String password1 = password.getText().toString();
                            try {
                                JSONObject obj = new JSONObject(s);
                                String status = obj.getString("status");
                                //Toast.makeText(Login.this, status, Toast.LENGTH_LONG).show();

                                 if (status.equals("success")) {
                                 String user_type = obj.getString("user_type");
                                 String unit_nos = obj.getString("unit_nos");
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                                //Toast.makeText(Login.this, user_type, Toast.LENGTH_LONG).show();
                                Intent i = new Intent(Login.this,HomePage.class);
                                i.putExtra("username",username.getText().toString());
                                     SharedPreferences settings = getSharedPreferences("pref",MODE_PRIVATE);
                                     editor= settings.edit();
                                     editor.putString("username",username.getText().toString());
                                     editor.putString("user_type",user_type);
                                     editor.putString("unit_nos",unit_nos);
                                     editor.apply();

                                startActivity(i);
                                finish();
                            } else {
                                Toast.makeText(Login.this, "Incorrect Details", Toast.LENGTH_LONG).show();

                            }

                            }catch (Exception e){}


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(Login.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("username", username.getText().toString());
                            parameters.put("password", password.getText().toString());
                            return parameters;
                        }
                    };

                    RequestQueue rQueue = Volley.newRequestQueue(Login.this);
                    rQueue.add(request);


                }
            });

        }
    }


    private void searchAction() {
        progress_bar.setVisibility(View.VISIBLE);
        fab.setAlpha(0f);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progress_bar.setVisibility(View.GONE);
                fab.setAlpha(1f);
                Snackbar.make(parent_view, "Login data submitted", Snackbar.LENGTH_SHORT).show();
            }
        }, 1000);
    }
}
