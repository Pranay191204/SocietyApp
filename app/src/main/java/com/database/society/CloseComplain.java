package com.database.society;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CloseComplain extends Fragment {
    private View parent_view;
    ExpandableListAdapter listAdapter;
    AnimatedExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<ComplaintPojo>> listDataChild;


    public CloseComplain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_close_complain, container, false);



        expListView = (AnimatedExpandableListView) view.findViewById(R.id.lvExp);



        prepareListData();
        final int[] prevExpandPosition = {-1};
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                if (prevExpandPosition[0] >= 0 && prevExpandPosition[0] != groupPosition) {
                    expListView.collapseGroup(prevExpandPosition[0]);
                }
                prevExpandPosition[0] = groupPosition;
            }
        });

        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // We call collapseGroupWithAnimation(int) and
                // expandGroupWithAnimation(int) to animate group
                // expansion/collapse.
                if (expListView.isGroupExpanded(groupPosition)) {
                    expListView.collapseGroupWithAnimation(groupPosition);
                } else {
                    expListView.expandGroupWithAnimation(groupPosition);
                }
                return true;
            }

        });

        return view;
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<ComplaintPojo>>();

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
                            JSONArray jsonarray = response.getJSONArray("closed_complaint");


                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject jsonObject = jsonarray.getJSONObject(i);

                                String title = jsonObject.getString("complaint_title");

                                listDataHeader.add(jsonObject.getString("complaint_title"));


                                List<ComplaintPojo> content = new ArrayList<>();
                                ComplaintPojo cm = new ComplaintPojo();
                                cm.setComplaint_id(jsonObject.getString("complaint_id"));
                                cm.setUnitno(jsonObject.getString("unit_nos"));
                                cm.setComplaintby(jsonObject.getString("complaint_by"));
                                cm.setDescription(jsonObject.getString("description"));
                                cm.setRemark(jsonObject.getString("remark"));
                                cm.setSeverity(jsonObject.getString("complaint_severity"));
                                cm.setD(jsonObject.getString("logged_on"));
                                content.add(cm);

                               /* content.add("Description :  " + jsonObject.getString("description") + "\n" +
                                        "Complaint Severity :  " + jsonObject.getString("complaint_severity") + "\n" +
                                        "Remark :  " + jsonObject.getString("remark") + "\n" +
                                        "Complaint By :  " + jsonObject.getString("complaint_by") + "\n" +
                                        "Logged On :  " + jsonObject.getString("logged_on") + "\n" +
                                        "Complaint_id :  " + jsonObject.getString("complaint_id"));*/



                                listDataChild.put(listDataHeader.get(i), content);

                            }

                            listAdapter = new ELAClose(getContext(), listDataHeader, listDataChild);
                            expListView.setAdapter(listAdapter);

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
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(obreqnotice);

    }

}
