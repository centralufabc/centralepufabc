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

public class Aluno_adapter extends BaseAdapter {
    private ArrayList<Aluno> arrayList;
    private Context context;
    private LayoutInflater layout;

    public Aluno_adapter(Context context, ArrayList<Aluno> arrayList) {
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
            txt.setText(arrayList.get(i).getFrase());
            return v;
        }else {
            if (i % 2!= 0) {
                layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = layout.inflate(R.layout.ex_alunos, viewGroup, false);
                ImageView img = (ImageView) v.findViewById(R.id.img_aluno);
                TextView txt = (TextView) v.findViewById(R.id.frase_aluno);
                TextView txt2 = (TextView) v.findViewById(R.id.txt_curso);


                img.setImageResource(arrayList.get(i).getImagem());
                txt.setText(arrayList.get(i).getNome());
                txt2.setText(arrayList.get(i).getFrase());
                return v;
            } else {
                layout = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = layout.inflate(R.layout.ex_alunos2, viewGroup, false);
                ImageView img = (ImageView) v.findViewById(R.id.img_aluno2);
                TextView txt = (TextView) v.findViewById(R.id.frase_aluno2);
                TextView txt2 = (TextView) v.findViewById(R.id.txt_curso2);


                img.setImageResource(arrayList.get(i).getImagem());
                txt.setText(arrayList.get(i).getNome());
                txt2.setText(arrayList.get(i).getFrase());
                return v;
            }
        }
    }
}
