package central.centralepufabc;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kleverson Nascimento on 15/03/2017.
 */

public class Dia_importante_adapter extends BaseAdapter {
    private ArrayList<Dia_importante> arrayList;
    private Context context;
    private LayoutInflater layout;

    public Dia_importante_adapter(Context context, ArrayList<Dia_importante> arrayList) {
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
        if (i==0){
            layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layout.inflate(R.layout.first_lista, viewGroup, false);
            TextView txt = (TextView) v.findViewById(R.id.txt_first);
            txt.setText(arrayList.get(i).getData());
            return v;
        }
        else {
            layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layout.inflate(R.layout.calendario_branco, viewGroup, false);
            TextView txt = (TextView) v.findViewById(R.id.txt_dia_c);
            TextView txt2 = (TextView) v.findViewById(R.id.txt_mes_c);
            TextView txt3 = (TextView) v.findViewById(R.id.nome_data_c);
            TextView txt4 = (TextView) v.findViewById(R.id.txt_separa);

            txt.setText(arrayList.get(i).getDia());
            txt2.setText(arrayList.get(i).getMes());
            txt3.setText(arrayList.get(i).getData());
            if (arrayList.get(i).getNotificar().equals("notificar") || arrayList.get(i).getNotificar().equals("notificado")) {
                CardView c = (CardView) v.findViewById(R.id.card_c);
                c.setCardBackgroundColor(Color.rgb(13, 89, 59));
                txt.setTextColor(Color.WHITE);
                txt2.setTextColor(Color.WHITE);
                txt3.setTextColor(Color.WHITE);
                txt4.setBackgroundColor(Color.WHITE);

            }
            return v;
        }
    }
}
