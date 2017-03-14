package central.centralepufabc;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import central.centralepufabc.Areas;
import central.centralepufabc.R;

/**
 * Created by Kleverson Nascimento on 12/03/2017.
 */

public class Areas_adapter extends BaseAdapter {
    private ArrayList<Areas> arrayList;
    private Context context;
    private LayoutInflater layout;

    public Areas_adapter(Context context, ArrayList<Areas> arrayList) {
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
        View v=layout.inflate(R.layout.listacronograma, viewGroup, false);
        ImageView img=(ImageView) v.findViewById(R.id.img_area);
        TextView txt=(TextView) v.findViewById(R.id.txt_area);

        img.setImageResource(arrayList.get(i).getImagem());
        txt.setText(arrayList.get(i).getNome());
        return v;
    }
}
