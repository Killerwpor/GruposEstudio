package integrador.gruposestudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import integrador.gruposestudio.Remote.RetrofitHelper;
import integrador.gruposestudio.adaptadores.adaptadorGrupo;
import integrador.gruposestudio.modelo.Grupo;
import integrador.gruposestudio.modelo.GrupoList;
import integrador.gruposestudio.modelo.Usuario;
import integrador.gruposestudio.modelo.UsuarioList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MisGruposActivity extends AppCompatActivity {

    private ListView lista;
    private FirebaseUser usuario;
    private TextView misGruposlabel;
    private FirebaseAuth mAuth;
    private List<Grupo> listaFinal=new ArrayList<>();
    private  RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_grupos);

        lista = findViewById(R.id.listaMisGrupos);
        misGruposlabel=findViewById(R.id.labelMisGrupos);

        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();

        traerTodosLosGrupos();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Grupo g = (Grupo) adapterView.getItemAtPosition(i);
                RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);

                Intent intent = new Intent(getApplicationContext(), GrupoActivity.class);
                intent.putExtra("Grupo", g.getGroupId());
                startActivityForResult(intent, 0);

            }


        });





    }

    void traerTodosLosGrupos() {
        service.getAllGrupos().enqueue(new Callback<GrupoList>() {
            @Override
            public void onResponse(Call<GrupoList> call, Response<GrupoList> response) {
                List<Grupo> grupos= recuperarGruposDelUsuario(response.body());

            }

            @Override
            public void onFailure(Call<GrupoList> call, Throwable t) {

            }
        });



    }

    List<Grupo> recuperarGruposDelUsuario(final GrupoList l) {

         List<Grupo> listaGrupos = l.getGrupos();
          List<Grupo> listaGruposFinal = new ArrayList<>();
        for (int i = 0; i < listaGrupos.size(); i++) {
            final int aux=i;
            service.getMiembros(listaGrupos.get(i).getGroupId()).enqueue(new Callback<UsuarioList>() {
                @Override
                public void onResponse(Call<UsuarioList> call, Response<UsuarioList> response) {
                 if(response.body()==null)
                     misGruposlabel.setText("No esta en ningun grupo");
                 else
                     comprobarSiPerteneceAlGrupo(response.body().getUsuarios(),aux,l.getGrupos());

                }

                @Override
                public void onFailure(Call<UsuarioList> call, Throwable t) {
                }
            });


        }
       // Log.d("PRUEBA","tamaño: "+listaFinal.size());
        return listaGruposFinal;
    }

    public void comprobarSiPerteneceAlGrupo(List<Usuario> usuarios, int k, List<Grupo> l){

        for(int i=0;i<usuarios.size();i++){
            if(usuarios.get(i).getEmail().equals(usuario.getEmail()))

                listaFinal.add(l.get(k));
        }

       ponerGrupos();
    }

    void ponerGrupos(){
        adaptadorGrupo adaptador=new adaptadorGrupo(getApplicationContext(),listaFinal);
       lista.setAdapter(adaptador);
    }


}
