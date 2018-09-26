package integrador.gruposestudio.adaptadores;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import integrador.gruposestudio.R;
import integrador.gruposestudio.Remote.RetrofitHelper;
import integrador.gruposestudio.modelo.Grupo;
import integrador.gruposestudio.modelo.GrupoList;
import integrador.gruposestudio.modelo.Solicitud;
import integrador.gruposestudio.modelo.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adaptadorSolicitudes extends BaseAdapter {

    List<Solicitud> items;
    Context context;
    LayoutInflater inflater;

    public adaptadorSolicitudes (Context contex, List<Solicitud> item) {
        //Log.d("TAMAÑOA",""+item.size());
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

        final Solicitud item= (Solicitud) getItem(i);


        view=LayoutInflater.from(context).inflate(R.layout.item_solicitudes,null);



         final TextView nombreGrupo=view.findViewById(R.id.nombreGrupo2);
         final TextView nombreUsuario=view.findViewById(R.id.nombreUsuario2);

         final RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);

        service.getAllGrupos().enqueue(new Callback<GrupoList>() {
            @Override
            public void onResponse(Call<GrupoList> call, Response<GrupoList> response) {

               service.getNombreGrupo(item.getGroupId()).enqueue(new Callback<Grupo>() {
                   @Override
                   public void onResponse(Call<Grupo> call, Response<Grupo> response) {
                       nombreGrupo.setText("Grupo: "+response.body().getGroupName()); //Se demora mucho, pensar como reducir el tiempo
                       service.getNombreUsuario(item.getUserUid()).enqueue(new Callback<Usuario>() {
                           @Override
                           public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                               nombreUsuario.setText("Por: "+response.body().getName());
                           }

                           @Override
                           public void onFailure(Call<Usuario> call, Throwable t) {

                           }
                       });
                   }

                   @Override
                   public void onFailure(Call<Grupo> call, Throwable t) {

                   }
               });

            }

            @Override
            public void onFailure(Call<GrupoList> call, Throwable t) {

            }
        });

        return view;
    }

    public void clear() {
        items.clear();
    }


    public String encontrarNombre(List<Grupo> g, int id){
        for(int i=0;i<g.size();i++){
            if(g.get(i).getGroupId()==id)
                return g.get(i).getGroupName();
        }
        return null;
    }
}
