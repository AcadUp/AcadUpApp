package com.example.acadup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.acadup.Models.ExpandableModel;

import com.example.acadup.R;

import java.util.HashMap;
import java.util.List;

public class CustomExpandableAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<String> _listDataHeader; // header titles
    private HashMap<String, List<ExpandableModel>> _listDataChild;

    public CustomExpandableAdapter(Context context, List<String> listDataHeader, HashMap<String, List<ExpandableModel>> listChildData){
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }
    @Override //Done///
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override ///Done///
    public int getChildrenCount(int i) {
        return this._listDataChild.get(this._listDataHeader.get(i)).size();
    }

    @Override///Done///
    public Object getGroup(int i) {
        return this._listDataHeader.get(i);
    }

    @Override  ///Done///
    public Object getChild(int i, int i1) {
        return this._listDataChild.get(this._listDataHeader.get(i)).get(i1);
    }

    @Override ///Done///
    public long getGroupId(int i) {
        return i;
    }

    @Override ///Done///
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override //Done//
    public boolean hasStableIds() {
        return false;
    }

    @Override //Done//
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle = (String) getGroup(i);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) view.findViewById(R.id.listTitle);
//        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);


        return view;
    }

    @Override ///Done///
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final ExpandableModel childLay = (ExpandableModel) getChild(i,i1);


        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) this._context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
        }

        TextView txtListChild = (TextView) view.findViewById(R.id.expandableListItem);
        ImageView imgListChild=(ImageView) view.findViewById(R.id.expandImg);
        txtListChild.setText(childLay.getText());
        imgListChild.setImageResource(childLay.getImg());
        return view;
    }

    @Override //Done//
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }


}
