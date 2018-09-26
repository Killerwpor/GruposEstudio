package integrador.gruposestudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import integrador.gruposestudio.Remote.RetrofitHelper;
import integrador.gruposestudio.adaptadores.adaptadorSolicitudes;
import integrador.gruposestudio.modelo.Grupo;
import integrador.gruposestudio.modelo.GrupoList;
import integrador.gruposestudio.modelo.Solicitud;
import integrador.gruposestudio.modelo.SolicitudList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitudesActivity extends AppCompatActivity {

    private ListView lista;
    public List<Solicitud> listaSolicitudes = new ArrayList<>();
    private List<Grupo> listaGrupos = new ArrayList<>();
    private FirebaseUser usuario;
    private FirebaseAuth mAuth;
    private adaptadorSolicitudes adaptador;
    private List<String> listaAux=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);
        lista = findViewById(R.id.listaSolicitudes);
        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();
        listaSolicitudes = new ArrayList<>();

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Solicitud s= (Solicitud) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getApplicationContext(), AceptarORechazarActivity.class);
                intent.putExtra("Grupo", s.getGroupId());
                intent.putExtra("id", s.getUserUid());
                startActivityForResult(intent, 0);

            }
        });


        final RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);
        service.getAllGrupos().enqueue(new Callback<GrupoList>() {
            @Override
            public void onResponse(Call<GrupoList> call, Response<GrupoList> response) {
                listaGrupos = traerGruposDelUsuario(response.body().getGrupos()); //se envían todos los grupos al metodo traerGruposDe();
                llenarListaDeSolicitudes(listaGrupos);

            }

            @Override
            public void onFailure(Call<GrupoList> call, Throwable t) {

            }
        });


    }


    @Override
    public void onBackPressed() { //si presiona atras se destruye la activity
        finish();
    }

    public List<Grupo> traerGruposDelUsuario(List<Grupo> lista) { //se recuperan todos los grupos del que el usuario es admin
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getAdminUid().equals(usuario.getUid()))
                listaGrupos.add(lista.get(i));
        }

        return listaGrupos;
    }

    public void llenarListaDeSolicitudes(final List<Grupo> lista) {

        List<Solicitud> list = new ArrayList<>();
        final RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);

        for (int i = 0; i < lista.size(); i++) {

            service.getSolicitudesGrupo(lista.get(i).getGroupId()).enqueue(new Callback<SolicitudList>() {

                @Override
                public void onResponse(Call<SolicitudList> call, Response<SolicitudList> response) {

                    //Aquí se guardan todas las solicitudes del usuario en la listaSolicitudes
                    if(!response.message().equals("NOT FOUND")) { //Si no encuentra solicitudes no entra aquí para que no haya errores con nulos
                        listaSolicitudes.addAll(response.body().getSolicitudes());
                        llenarListView();
                    }

                }

                @Override
                public void onFailure(Call<SolicitudList> call, Throwable t) {

                }


            });


        }

    }

    public void llenarListView() {
        adaptador = new adaptadorSolicitudes(getApplicationContext(), listaSolicitudes);
        lista.setAdapter(adaptador);


    }









}
