package com.database.society;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import java.util.List;

public class Notice_content extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    WebView webView;
    String id= null;
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_content);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.WHITE);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        webView = (WebView) findViewById(R.id.content);

        Bundle extra = getIntent().getExtras();
        id = extra.getString("notice_id");
       // Toast.makeText(getApplicationContext(), id, Toast.LENGTH_SHORT).show();
        url = "http://hawarecenturionnerul.com/android/notice/content/" + id;

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Movie movie = new Movie();

                movie.setContent(s);
                webView.loadData(movie.getContent(), "text/html; charset=utf-8", "UTF-8");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        })
        {

        };

        RequestQueue rQueue = Volley.newRequestQueue(Notice_content.this);
        rQueue.add(request);
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
                            Intent i =new Intent(Notice_content.this,Login.class);
                            startActivity(i);
                            finish();
                            Toast.makeText(getApplicationContext(), "Log Out Successful", Toast.LENGTH_SHORT).show();
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
            i=new Intent(Notice_content.this,HomePage.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.notice) {
            i=new Intent(Notice_content.this,MainActivity.class);
            startActivity(i);

        } else if (id == R.id.complain) {

        } else if (id == R.id.submenu1) {
            i=new Intent(Notice_content.this,RaiseComplain.class);
            startActivity(i);
        }else if (id == R.id.submenu2) {
            i=new Intent(Notice_content.this,ViewComplain.class);
            startActivity(i);
        }

        else if (id == R.id.rule) {
            i=new Intent(Notice_content.this,Rule.class);
            startActivity(i);

        } else if (id == R.id.maintenance) {


        } else if (id == R.id.polling) {
            i=new Intent(Notice_content.this,Polling.class);
            startActivity(i);

        } else if (id == R.id.visitors){
            i=new Intent(Notice_content.this,Visitor.class);
            startActivity(i);

        } else if (id == R.id.facility){
            i=new Intent(Notice_content.this,Facility.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}



