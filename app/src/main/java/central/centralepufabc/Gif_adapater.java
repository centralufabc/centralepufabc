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
 * Created by Kleverson Nascimento on 03/04/2017.
 */

public class Gif_adapater extends BaseAdapter {
    private ArrayList<Gif> arrayList;
    private Context context;
    private LayoutInflater layout;

    public Gif_adapater(Context context, ArrayList<Gif> arrayList) {
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
        if(i==0){
            layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layout.inflate(R.layout.first_lista, viewGroup, false);
            TextView txt = (TextView) v.findViewById(R.id.txt_first);
            txt.setText(arrayList.get(i).getNome_gif());
            return v;
        }else {
            layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layout.inflate(R.layout.style_gif, viewGroup, false);
            TextView txt2 = (TextView) v.findViewById(R.id.txt_nome_gif);

            txt2.setText(arrayList.get(i).getNome_gif());
            txt2.setTag(arrayList.get(i).getTag_gif());
            return v;
        }
    }
}
