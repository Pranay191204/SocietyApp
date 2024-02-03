package com.database.society;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.List;

public class Notice extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    TextView textView;
    String Url = "";
    // String URL="http://hawarecenturionnerul.com/android/notice/content/notice_id";
    String noticeData = "";
    RequestQueue requestQueue;
    String username;
    RecyclerView rv;
    private adapter recyclerAdapter;
    private List<Movie> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        requestQueue = Volley.newRequestQueue(this);
        //textView=(TextView)findViewById(R.id.jsonDataNotice);

        rv = (RecyclerView) findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setHasFixedSize(true);
        recyclerAdapter = new adapter(this, list);
        //rv.setAdapter(recyclerAdapter);


        Bundle extra = getIntent().getExtras();
        username = extra.getString("username");
        Url = "http://hawarecenturionnerul.com/android/notice/individual_token/" + username;
        JsonObjectRequest obreqnotice = new JsonObjectRequest(Request.Method.GET, Url, null,
                // The third parameter Listener overrides the method onResponse() and passes
                //JSONObject as a parameter
                new Response.Listener<JSONObject>() {

                    // Takes the response from the JSON request
                    @Override
                    public void onResponse(JSONObject response) {


                        try {
                            String res = response.getString("status");
                            JSONArray jsonarray = response.getJSONArray("noticeData");


                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonObject = jsonarray.getJSONObject(i);
                                // String status = jsonObject.getString("status");
                                String date = jsonObject.getString("date");
                                String valid_till = jsonObject.getString("valid_till");
                                String notice_status = jsonObject.getString("notice_status");
                                String title = jsonObject.getString("title");
                                //  String notice_id = jsonObject.getString("notice_id");

                                // Adds strings from object to the "data" string

                                // noticeData += "Date : " + date + "\n" + valid_till + "\n" + notice_status + "\n" +  title + "\n" +  "\n";
                                // Adds the data string to the TextView "results"
                                // textView.setText(noticeData);
                                //Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                            }

                        }
                        // Try and catch are included to handle any errors due to JSON
                        catch (JSONException e) {
                            // If an error occurs, this prints the error to the log
                            e.printStackTrace();
                        }
                    }
                },
                // The final parameter overrides the method onErrorResponse() and passes VolleyError
                //as a parameter
                new Response.ErrorListener() {
                    @Override
                    // Handles errors that occur due to Volley
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error");
                    }
                }
        );

        // Adds the JSON object request "obreqnotice" to the request queue
        requestQueue.add(obreqnotice);
        //rv.setAdapter(adapter);


    }

    public class adapter extends RecyclerView.Adapter<adapter.ViewHolder> {

        private Context context;
        private List<Movie> list;

        public adapter(Context context, List<Movie> list) {
            this.context = context;
            this.list = list;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.single_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(adapter.ViewHolder holder, int i) {
            final Movie movie = list.get(i);

            holder.textTitle.setText(movie.getTitle());
            holder.textNotice_id.setText(String.valueOf(movie.getNotice_id()));
            holder.textValid_till.setText(movie.getValid_till());
            holder.textNotice_status.setText(movie.getNotice_status());
            holder.textDate.setText(movie.getDate());
        }


        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public TextView textTitle, textNotice_id, textValid_till, textNotice_status, textDate;

            public ViewHolder(View itemView) {
                super(itemView);

                textTitle = itemView.findViewById(R.id.main_title);
                textDate = itemView.findViewById(R.id.main_date);
                textValid_till = itemView.findViewById(R.id.main_valid_till);
                textNotice_status = itemView.findViewById(R.id.main_notice_status);
                textNotice_id = itemView.findViewById(R.id.main_notice_id);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        // Intent i = new Intent(view.getContext(),Notice_content.class);
                        // i.putExtra("notice_id",textNotice_id.getText().toString());
                        //view.getContext().startActivity(i);

                    }
                });
            }
        }
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
                            Intent i = new Intent(Notice.this, Login.class);
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
            i = new Intent(Notice.this, HomePage.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.notice) {
            i = new Intent(Notice.this, MainActivity.class);
            startActivity(i);
        } else if (id == R.id.complain) {

        } else if (id == R.id.submenu1) {
            Intent intent = new Intent(Notice.this, RaiseComplain.class);
            intent.putExtra("username", username);
            // intent.putExtra("unit_nos",unit_no);
            startActivity(intent);
        } else if (id == R.id.submenu2) {
            i = new Intent(Notice.this, ViewComplain.class);
            startActivity(i);
        } else if (id == R.id.rule) {
            i = new Intent(Notice.this, Rule.class);
            startActivity(i);

        } else if (id == R.id.maintenance) {

        } else if (id == R.id.polling) {
            i = new Intent(Notice.this, Polling.class);
            startActivity(i);

        } else if (id == R.id.visitors) {
            i = new Intent(Notice.this, Visitor.class);
            startActivity(i);

        } else if (id == R.id.facility) {
            i = new Intent(Notice.this, Facility.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}





