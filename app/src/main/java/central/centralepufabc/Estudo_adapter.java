package central.centralepufabc;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kleverson Nascimento on 14/03/2017.
 */

public class Estudo_adapter extends BaseAdapter {
    private ArrayList<Estudo> arrayList;
    private Context context;
    private LayoutInflater layout;

    public Estudo_adapter(Context context, ArrayList<Estudo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        layout=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v=layout.inflate(R.layout.item_estudo, viewGroup, false);
        TextView txt_t=(TextView) v.findViewById(R.id.txt_titulo);
        TextView txt_d=(TextView) v.findViewById(R.id.txt_desc);

        txt_t.setText(arrayList.get(i).getNome());
        txt_d.setText(arrayList.get(i).getDesc());
        return v;
    }
}
