package com.database.society;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.DataSetObserver;
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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RaiseComplain extends AppCompatActivity implements AdapterView.OnItemSelectedListener,NavigationView.OnNavigationItemSelectedListener {

    Calendar c = Calendar.getInstance();
    int date = c.get(Calendar.DAY_OF_MONTH);
    String[] complain_severity = {"Urgent", "Routine"};
    ArrayAdapter aa;
    // String url="http://hawarecenturionnerul.com/android/complaint/raise_complaint";
    EditText title, dates, description;
    Spinner spinner, sp;
    Button button;
    String URL = "";
    String url = "";
    String username = "";
    String unit_nos = "";
    List<DataObject> spinnerData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_raise_complain);

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

        title = (EditText) findViewById(R.id.title);
        dates = (EditText) findViewById(R.id.date);
        description = (EditText) findViewById(R.id.brief);
        spinner = (Spinner) findViewById(R.id.spinner1);
        sp = (Spinner) findViewById(R.id.spinner2);

        String date_n = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
        dates.setText(date_n);

        spinner.setOnItemSelectedListener(this);
        Bundle extra = getIntent().getExtras();
        username = extra.getString("username");
        URL = "http://hawarecenturionnerul.com/android/complaint/raise_complaint";
        Toast.makeText(getApplicationContext(), username, Toast.LENGTH_SHORT).show();

        //Creating the ArrayAdapter instance having the country list
        aa = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, complain_severity);
        aa.setDropDownViewResource(R.layout.spinner_text2);
        //Setting the ArrayAdapter data on the Spinner
        spinner.setAdapter(aa);

        sp.setOnItemSelectedListener(this);
        Bundle extras = getIntent().getExtras();
        unit_nos = extras.getString("unit_nos");
        url = "http://hawarecenturionnerul.com/android/unit_detail/individual_token/276C8E";
        Toast.makeText(getApplicationContext(), unit_nos, Toast.LENGTH_SHORT).show();
        /*StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                if (null != spinnerData) {
                    spinner = (Spinner) findViewById(R.id.spinner2);
                    assert spinner != null;
                    spinner.setVisibility(View.VISIBLE);
                    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(RaiseComplain.this, spinnerData);
                    spinner.setAdapter(spinnerAdapter);
                }
            }
        });
                new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError)
            {
                Toast.makeText(RaiseComplain.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
            }
        };

        RequestQueue rQueue = Volley.newRequestQueue(RaiseComplain.this);
        rQueue.add(request);
*/
        button=(Button)findViewById(R.id.button);


      button.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {

              final String t = title.getText().toString();
              final String d = dates.getText().toString();
              final String b = description.getText().toString();
              final String s = spinner.getSelectedItem().toString();

              StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                  @Override
                  public void onResponse(String s) {

                      if (s.equals("success")) {

                          Toast.makeText(RaiseComplain.this, "Data inserted", Toast.LENGTH_LONG).show();

                      } else {
                          Toast.makeText(RaiseComplain.this, "Data not inserted", Toast.LENGTH_LONG).show();

                      }
                  }
              }, new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError volleyError) {
                      Toast.makeText(RaiseComplain.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                  }
              }) {
                  @Override
                  protected Map<String, String> getParams() throws AuthFailureError {
                      Map<String, String> parameters = new HashMap<String, String>();
                      parameters.put("title", title.getText().toString());
                      parameters.put("description", description.getText().toString());
                      parameters.put("severity", spinner.getSelectedItem().toString());
                      parameters.put("username",username);
                     // parameters.put("unit_nos",unit_nos);
                      return parameters;
                  }
              };

              RequestQueue rQueue = Volley.newRequestQueue(RaiseComplain.this);
              rQueue.add(request);
          }
      });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(getApplicationContext(),complain_severity[i],Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                            Intent i =new Intent(RaiseComplain.this,Login.class);
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
            i=new Intent(RaiseComplain.this,HomePage.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.notice) {
            i=new Intent(RaiseComplain.this,MainActivity.class);
            startActivity(i);

        } else if (id == R.id.complain) {

        } else if (id == R.id.submenu1) {
            i=new Intent(RaiseComplain.this,RaiseComplain.class);
            startActivity(i);
        }else if (id == R.id.submenu2) {
            i=new Intent(RaiseComplain.this,ViewComplain.class);
            startActivity(i);
        }

        else if (id == R.id.rule) {
            i=new Intent(RaiseComplain.this,Rule.class);
            startActivity(i);

        } else if (id == R.id.maintenance) {


        } else if (id == R.id.polling) {
            i=new Intent(RaiseComplain.this,Polling.class);
            startActivity(i);

        } else if (id == R.id.visitors){
            i=new Intent(RaiseComplain.this,Visitor.class);
            startActivity(i);

        } else if (id == R.id.facility){
            i=new Intent(RaiseComplain.this,Facility.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}




