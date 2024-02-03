package com.database.society;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class ELA extends AnimatedExpandableListView.AnimatedExpandableListAdapter {
    String u,user_type;
    private static SharedPreferences pref,pref2;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<ComplaintPojo>> _listDataChild;
    View v = null;
    View gview =null;
    TextView gstatus;


    public ELA(Context context, List<String> listDataHeader, HashMap<String, List<ComplaintPojo>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;

    }
    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public int getRealChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }
    @Override
    public View getRealChildView(int groupPosition, final int childPosition,
                                 boolean isLastChild,View convertView, ViewGroup parent) {
        //View row = super.getRealChildView(groupPosition,convertView, parent);

       //final String childText = (String) getChild(groupPosition, childPosition);
        ComplaintPojo item = (ComplaintPojo) getChild(groupPosition, childPosition);
         //View cv = null;
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item2, null);

        final TextView unitno = (TextView)convertView.findViewById(R.id.unitno);

        final TextView complaintby = (TextView)convertView.findViewById(R.id.complaintby);

        final TextView date = (TextView)convertView.findViewById(R.id.date);

        final TextView description = (TextView)convertView.findViewById(R.id.description);

        final TextView severity = (TextView)convertView.findViewById(R.id.severity);
       // final TextView remark = (TextView) convertView
               // .findViewById(R.id.remark);

        final TextView complaintid = (TextView)convertView
                .findViewById(R.id.complaintid);

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageView);

        pref = _context.getSharedPreferences("pref",MODE_PRIVATE);
         u = pref.getString("username","default");
         user_type = pref.getString("user_type","default");
        //Toast.makeText(convertView.getContext(),username,Toast.LENGTH_SHORT).show();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater infalInflater = (LayoutInflater)v.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View mView = infalInflater.inflate(R.layout.dialog_action, null);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());

                final EditText mEmail = (EditText) mView.findViewById(R.id.etAction);
                final EditText mPassword = (EditText) mView.findViewById(R.id.etRemark);
                Button mLogin = (Button) mView.findViewById(R.id.btnLogin);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(user_type.equals("101")) {
                            StringRequest request = new StringRequest(Request.Method.POST, "http://hawarecenturionnerul.com/android/complaint/close_complaint", new Response.Listener<String>() {
                                @Override
                                public void onResponse(String s) {

                                    if (s.equals("success")) {

                                        Toast.makeText(mView.getContext(), "Data inserted", Toast.LENGTH_LONG).show();
                                        dialog.dismiss();

                                    } else {
                                        Toast.makeText(mView.getContext(), "Data not inserted", Toast.LENGTH_LONG).show();

                                    }
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(mView.getContext(), "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> parameters = new HashMap<String, String>();
                                    parameters.put("username", u);
                                    parameters.put("remark", mPassword.getText().toString());
                                    parameters.put("complaint_id", complaintid.getText().toString());

                                    return parameters;
                                }
                            };

                            RequestQueue rQueue = Volley.newRequestQueue(mView.getContext());
                            rQueue.add(request);
                        }
                        else
                            {
                            Toast.makeText(mView.getContext(),"Sorry you are not authorized for this",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

         unitno.setText(item.getUnitno());
         complaintby.setText(item.getComplaintby());
         date.setText(item.getD());
         description.setText(item.getDescription());
         //remark.setText("Remark : " + item.getRemark());
         severity.setText(item.getSeverity());
         complaintid.setText(item.getComplaint_id());

        return convertView;
    }
    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);

            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gview = infalInflater.inflate(R.layout.list_group, null);
            gstatus = (TextView) gview
                    .findViewById(R.id.status);

            JsonObjectRequest obreqnotice = new JsonObjectRequest(Request.Method.GET, "http://hawarecenturionnerul.com/android/complaint/individual_token/276C8E", null,
                    // The third parameter Listener overrides the method onResponse() and passes
                    //JSONObject as a parameter
                    new Response.Listener<JSONObject>() {
                        // Takes the response from the JSON request
                        @Override
                        public void onResponse(JSONObject response) {
                            //  Toast.makeText(getContext(),"hell",Toast.LENGTH_SHORT).show();
                            try {
                                String res = response.getString("status");
                                JSONArray jsonarray = response.getJSONArray("open_complaint");

                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject jsonObject = jsonarray.getJSONObject(i);

                                    String status = jsonObject.getString("mobile_complaint_status");
                                    if(status.equals("Newly Created")){
                                        gview.setBackgroundColor(Color.parseColor("#85C1E9"));
                                        //gstatus.setText("NEW");
                                    }
                                    else
                                        {
                                        gview.setBackgroundColor(Color.parseColor("#ffffff"));
                                    }
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
                            NetworkResponse response = error.networkResponse;
                            if (error instanceof ServerError && response != null) {
                                try {
                                    String res = new String(response.data,
                                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                    // Now you can use any deserializer to make sense of data
                                    JSONObject obj = new JSONObject(res);
                                } catch (UnsupportedEncodingException e1) {
                                    // Couldn't properly decode data to string
                                    e1.printStackTrace();
                                } catch (JSONException e2) {
                                    // returned data is not JSONObject?
                                    e2.printStackTrace();
                                }}
                        }
                    }
            );
            // Adds the JSON object request "obreqnotice" to the request queue
            RequestQueue requestQueue = Volley.newRequestQueue(gview.getContext());
            requestQueue.add(obreqnotice);

        TextView lblListHeader = (TextView) gview
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.NORMAL);
        lblListHeader.setText(headerTitle);
        notifyDataSetChanged();

        return gview;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}