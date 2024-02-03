package com.database.society;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
/**
 * Created by Admin on 6/20/2019.
 */
public class ELAClose extends AnimatedExpandableListView.AnimatedExpandableListAdapter {

    String u;
    private static SharedPreferences pref;
    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<ComplaintPojo>> _listDataChild;

    public ELAClose(Context context, List<String> listDataHeader,
               HashMap<String, List<ComplaintPojo>> listChildData) {
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
    public View getRealChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        //final String childText = (String) getChild(groupPosition, childPosition);
        ComplaintPojo item = (ComplaintPojo) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }

        final TextView unitno = (TextView) convertView
                .findViewById(R.id.unitno);
        final TextView complaintby = (TextView) convertView
                .findViewById(R.id.complaintby);
        final TextView date = (TextView) convertView
                .findViewById(R.id.date);
        final TextView description = (TextView) convertView
                .findViewById(R.id.description);
        final TextView severity = (TextView) convertView
                .findViewById(R.id.severity);
        final TextView remark = (TextView) convertView
                .findViewById(R.id.remark);
        final TextView complaintid = (TextView) convertView
                .findViewById(R.id.complaintid);

        pref = _context.getSharedPreferences("pref",MODE_PRIVATE);
        u = pref.getString("unit_nos","default");
        //user_type = pref.getString("user_type","default");

        unitno.setText("Unit No : " + item.getUnitno());
        complaintby.setText("Complaint By : " + item.getComplaintby());
        date.setText("Logged On : " + item.getD());
        description.setText("Description : " + item.getDescription());
        remark.setText("Remark : " + item.getRemark());
        severity.setText("Severity : " +item.getSeverity());
        complaintid.setText(item.getComplaint_id());
        return convertView;
    }
    @Override
    public int getRealChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
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
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }
        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.NORMAL);
        lblListHeader.setText(headerTitle);
        notifyDataSetChanged();
        return convertView;
    }
    @Override
    public boolean hasStableIds()
    {
        return false;
    }
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }
}