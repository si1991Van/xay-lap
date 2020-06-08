package com.viettel.construction.appbase;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import java.util.HashMap;
import java.util.List;

/***
 * KePham
 * @param <Parrent>
 * @param <Child>
 */
public abstract class AdapterExpandableListBase<Parrent, Child>
        extends BaseExpandableListAdapter {
    protected List<ExpandableListModel<Parrent, Child>> listData;
    protected AdapterFragmentListBase.IItemRecyclerviewClick<Child> itemRecyclerviewClick;
    protected Context context;

    public AdapterExpandableListBase(List<ExpandableListModel<Parrent, Child>> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    public void setListData(List<ExpandableListModel<Parrent, Child>> listData) {
        this.listData = listData;
    }

    public List<ExpandableListModel<Parrent, Child>> getListData() {
        return listData;
    }

    @Override
    public int getGroupCount() {
        return listData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return listData.get(groupPosition).getChildren().size();
    }

    @Override
    public Parrent getGroup(int groupPosition) {
        return listData.get(groupPosition).getParrent();
    }

    @Override
    public Child getChild(int groupPosition, int childPosition) {
        return listData.get(groupPosition).getChildren().get(childPosition);
    }

    @Override
    public long getGroupId(int i) {
        return 0;
    }

    @Override
    public long getChildId(int i, int i1) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    public abstract View createGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup);
    public abstract View createChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup);

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        return createGroupView(groupPosition, b, view, viewGroup);
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        return createChildView(groupPosition,childPosition,b,view,viewGroup);
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
