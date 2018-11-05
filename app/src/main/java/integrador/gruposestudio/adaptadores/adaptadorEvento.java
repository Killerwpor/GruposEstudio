package integrador.gruposestudio.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import integrador.gruposestudio.R;
import integrador.gruposestudio.modelo.Evento;
import integrador.gruposestudio.modelo.Grupo;

public class adaptadorEvento extends BaseAdapter {
    private List<Evento> items;
    private Context context;
    private LayoutInflater inflater;

    public adaptadorEvento(Context c, List<Evento> item){
        this.context=c;
        this.items=item;
        this.inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        Evento item = (Evento) getItem(i);

        view = LayoutInflater.from(context).inflate(R.layout.item_evento, null);

        TextView nombreEvento = view.findViewById(R.id.labelEvento);


        nombreEvento.setText(item.getTitle());
    return view;
    }
}
