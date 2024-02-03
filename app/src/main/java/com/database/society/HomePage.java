package com.database.society;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    GridView androidGridView;
    SharedPreferences loginPreferences;
    SharedPreferences.Editor loginPrefsEditor;
    RequestQueue requestQueue;
    String username = "";
    String unit_nos = "";
    String[] gridViewString = {"Notice", "Complaint", "Rule", "Bills", "Facility", "Polling", "Visitors"};
    TextView tv;
    int[] gridViewImageId = {R.drawable.notice, R.drawable.complaint, R.drawable.rule, R.drawable.maintenance, R.drawable.facility, R.drawable.polling, R.drawable.visitor};
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        tv = (TextView) findViewById(R.id.marqueetext);

        Animation a = AnimationUtils.loadAnimation(this, R.anim.ltr);
        a.setRepeatCount(Animation.INFINITE);
        tv.startAnimation(a);


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        Bundle extra = getIntent().getExtras();
        username = extra.getString("username");
        unit_nos = extra.getString("unit_nos");

        Toast.makeText(this, username, Toast.LENGTH_SHORT).show();
        Toast.makeText(this, unit_nos, Toast.LENGTH_SHORT).show();

        requestQueue = Volley.newRequestQueue(this);
        androidGridView = (GridView) findViewById(R.id.grid_view_image_text);

        CustomGridViewActivity adapterViewAndroid = new CustomGridViewActivity(HomePage.this, gridViewString, gridViewImageId);

        androidGridView.setAdapter(adapterViewAndroid);
        adapterViewAndroid.notifyDataSetChanged();

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long id) {
                Toast.makeText(HomePage.this, "GridView Item: " + gridViewString[+i], Toast.LENGTH_LONG).show();

                if (i == 0) {
                    Intent i2 = new Intent(HomePage.this, MainActivity.class);
                    i2.putExtra("username", username);
                    startActivity(i2);
                }
                if (i == 1) {
/*
                    Intent i2 = new Intent(HomePage.this, Complaint.class);
                    i2.putExtra("username", username);
                    startActivity(i2);
*/
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(HomePage.this);
                    View mView = getLayoutInflater().inflate(R.layout.dialog_complain, null);

                    Drawable d = new ColorDrawable(Color.BLACK);
                    d.setAlpha(130);
                    ImageView imageView = (ImageView) mView.findViewById(R.id.imageView);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });

                    Button rcomp = (Button) mView.findViewById(R.id.btnLogin1);

                    Button vcomp = (Button) mView.findViewById(R.id.btnLogin);

                    mBuilder.setView(mView);
                    dialog = mBuilder.create();
                    dialog.getWindow().setBackgroundDrawable(d);
                    dialog.show();

                    rcomp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Toast.makeText(v.getContext(),complaintid.getText().toString(),Toast.LENGTH_SHORT).show();
                            Intent intent= new Intent(HomePage.this,RaiseComplain.class);
                            intent.putExtra("username",username);
                            intent.putExtra("unit_nos",unit_nos);
                            // intent.putExtra("unit_nos",unit_no);
                            startActivity(intent);
                        }
                    });

                    vcomp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent1= new Intent(HomePage.this,ViewComplain.class);
                            intent1.putExtra("username",username);
                            startActivity(intent1);
                        }
                    });
                }

                if (i == 2) {

                    Intent i2 = new Intent(HomePage.this, Rule.class);
                    startActivity(i2);

                }

                if (i == 3) {

                    Intent i2 = new Intent(HomePage.this, Maintenance.class);
                    startActivity(i2);

                }
                if (i == 4) {

                    Intent i2 = new Intent(HomePage.this, Facility.class);
                    startActivity(i2);

                }
                if (i == 5) {

                    Intent i2 = new Intent(HomePage.this, Polling.class);
                    startActivity(i2);

                }
                if (i == 6) {

                    Intent i2 = new Intent(HomePage.this, Visitor.class);
                    startActivity(i2);

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_logout) {

            // dialogue fragment for logout operation
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to logout?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //session.logoutUser();
                            Toast.makeText(getApplicationContext(), "Log Out Successful", Toast.LENGTH_SHORT).show();
                            Intent i =new Intent(HomePage.this,Login.class);
                            startActivity(i);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent i;

        if (id == R.id.home) {
            i=new Intent(HomePage.this,HomePage.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.notice) {
            i=new Intent(HomePage.this,MainActivity.class);
            startActivity(i);
        } else if (id == R.id.complain) {

        }
        else if (id == R.id.submenu1) {
            Intent intent= new Intent(HomePage.this,RaiseComplain.class);
            intent.putExtra("username",username);
            // intent.putExtra("unit_nos",unit_no);
            startActivity(intent);
        }else if (id == R.id.submenu2) {
            i=new Intent(HomePage.this,ViewComplain.class);
            startActivity(i);
        }
        else if (id == R.id.rule) {
            i=new Intent(HomePage.this,Rule.class);
            startActivity(i);

        } else if (id == R.id.maintenance) {

        } else if (id == R.id.polling) {
            i=new Intent(HomePage.this,Polling.class);
            startActivity(i);

        } else if (id == R.id.visitors){
            i=new Intent(HomePage.this,Visitor.class);
            startActivity(i);

        } else if (id == R.id.facility){
            i=new Intent(HomePage.this,Facility.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}