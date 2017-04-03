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
 * Created by Kleverson Nascimento on 02/04/2017.
 */

public class Monitoria_adapter extends BaseAdapter {
    private ArrayList<Monitoria_obj> arrayList;
    private Context context;
    private LayoutInflater layout;

    public Monitoria_adapter(Context context, ArrayList<Monitoria_obj> arrayList) {
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
        layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layout.inflate(R.layout.monitoria, viewGroup, false);
        TextView txt = (TextView) v.findViewById(R.id.txt_nome_monitoria);
        txt.setText(arrayList.get(i).getNome_materia());
        txt=(TextView) v.findViewById(R.id.txt_detalhes_monitoria);
        txt.setText(arrayList.get(i).getDetalhes_monitoria());
        return v;
    }
}
