package integrador.gruposestudio.modelo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import integrador.gruposestudio.R;

public class adaptadorGrupo extends BaseAdapter{
    List<Grupo> items;
    Context context;
    LayoutInflater inflater;

    public adaptadorGrupo (Context contex, List<Grupo> item) {
        this.context = contex;
        this.items = item;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Grupo item= (Grupo) getItem(i);

        view=LayoutInflater.from(context).inflate(R.layout.item_grupo,null);

        TextView nombreGrupo=view.findViewById(R.id.nombreGrupo);



        nombreGrupo.setText(item.getGroupName());





        return view;

    }

    public void clear() {
        items.clear();
    }
    public void addAll(ArrayList<Grupo> grupo) {
        for (int i =0; i < grupo.size(); i++) {
            items.add(grupo.get(i));
        }
    }

    class Holder {
        //Propiedades
        TextView nombre, mensaje, fecha;

    }
}
