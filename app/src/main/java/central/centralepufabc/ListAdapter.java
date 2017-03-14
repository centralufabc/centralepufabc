package central.centralepufabc;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Kleverson Nascimento on 12/03/2017.
 */

public class ListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> listData;
    private HashMap<String, List<String>> listHashMap;

    public ListAdapter(Context context, List<String> listData, HashMap<String, List<String>> listHashMap) {
        this.context = context;
        this.listData = listData;
        this.listHashMap = listHashMap;
    }


    @Override
    public int getGroupCount() {
        return listData.size();
    }

    @Override
    public int getChildrenCount(int i) {
        return listHashMap.get(listData.get(i)).size();
    }

    @Override
    public Object getGroup(int i) {
        return listData.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return listHashMap.get(listData.get(i)).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
        String headerTitle=(String) getGroup(i);
        if(view==null)
        {
            LayoutInflater inflater=(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.layout_lista,null);
        }
        TextView lbllistheader=(TextView) view.findViewById(R.id.design_lista);
        lbllistheader.setTypeface(null, Typeface.BOLD);
        lbllistheader.setText(headerTitle);
        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        final String childText=(String)getChild(i,i1);
        if(view==null){
            LayoutInflater inflater=(LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(R.layout.layout_item_lista,null);
        }
        TextView txtListChild=(TextView) view.findViewById(R.id.design_item_lista);
        txtListChild.setText(childText);
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }
}
