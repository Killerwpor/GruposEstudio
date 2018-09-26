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
import integrador.gruposestudio.modelo.Chat;

public class adaptadorChat extends BaseAdapter {

    ArrayList<Chat> items;
    Context context;
    LayoutInflater inflater;

    public adaptadorChat (Context contex, ArrayList<Chat> item) {
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

        Chat item= (Chat) getItem(i);

       view=LayoutInflater.from(context).inflate(R.layout.item_chat,null);

       TextView mensaje=view.findViewById(R.id.mensajeItem);
        TextView autor=view.findViewById(R.id.usuarioItem);
        TextView fecha=view.findViewById(R.id.fechaItem);


        mensaje.setText(item.getMensaje());
        autor.setText(item.getAutor());
        fecha.setText(item.getFecha());




       return view;

    }

    public void clear() {
        items.clear();
    }
    public void addAll(ArrayList<Chat> chat) {
        for (int i =0; i < chat.size(); i++) {
            items.add(chat.get(i));
        }
    }

    class Holder {
        //Propiedades
        TextView nombre, mensaje, fecha;

    }
}
