package com.database.society;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

class CustomGridViewActivity extends BaseAdapter {

    private Context mContext;

    private final String[] gridViewString;
    private final int[] gridViewImageId;

    public CustomGridViewActivity(Context context, String[] gridViewString, int[] gridViewImageId) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;

    }

    @Override
    public int getCount() {
        return gridViewString.length;
    }

    @Override
    public Object getItem(int i) {
        return 0;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {

            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.gridview_layout, null);
            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
            final TextView notification = (TextView) gridViewAndroid.findViewById(R.id.notification);
            textViewAndroid.setText(gridViewString[i]);
            imageViewAndroid.setImageResource(gridViewImageId[i]);
            //notification.setText(String.valueOf(notificationtext[i]));
            String gridtext = gridViewString[i];
            if(gridtext.equals("Complaint")){

                JsonObjectRequest obreqnotice = new JsonObjectRequest(Request.Method.GET, "http://hawarecenturionnerul.com/android/complaint/unread/open",null,
                        // The third parameter Listener overrides the method onResponse() and passes
                        //JSONObject as a parameter
                        new Response.Listener<JSONObject>() {

                            // Takes the response from the JSON request
                            @Override
                            public void onResponse(JSONObject response) {


                                try {
                                    int res = response.getInt("data_count");
                                    notification.setText(String.valueOf(res));


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
                            public void onErrorResponse(VolleyError error)
                            {
                                Log.e("Volley", "Error");
                            }
                        }
                );

                // Adds the JSON object request "obreqnotice" to the request queue
                RequestQueue requestQueue = Volley.newRequestQueue(gridViewAndroid.getContext());
                requestQueue.add(obreqnotice);

            }else{
                notification.setText(String.valueOf(""));
            }
        }
        else
            {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}