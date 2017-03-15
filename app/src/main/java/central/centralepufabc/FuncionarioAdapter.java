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

public class FuncionarioAdapter extends BaseAdapter{
    private ArrayList<Funcionário> arrayList;
    private Context context;
    private LayoutInflater layout;

    public FuncionarioAdapter(Context context, ArrayList<Funcionário> arrayList) {
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
        View v = layout.inflate(R.layout.funcionarios, viewGroup, false);
        ImageView img = (ImageView) v.findViewById(R.id.img_func);
        TextView txt = (TextView) v.findViewById(R.id.nome_func);
        TextView txt2 = (TextView) v.findViewById(R.id.func_func);


        img.setImageResource(arrayList.get(i).getImagem_func());
        txt.setText(arrayList.get(i).getNome_func());
        txt2.setText(arrayList.get(i).getCargo_func());
        return v;
    }
}
