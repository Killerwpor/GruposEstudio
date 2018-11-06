package integrador.gruposestudio;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import integrador.gruposestudio.Remote.RetrofitHelper;
import integrador.gruposestudio.adaptadores.adaptadorEvento;
import integrador.gruposestudio.modelo.Evento;
import integrador.gruposestudio.modelo.EventosList;
import integrador.gruposestudio.modelo.Grupo;
import integrador.gruposestudio.modelo.GrupoList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventosActivity extends AppCompatActivity {

    private int id;
    private ListView lista;
    private Grupo grupoPrincipal;
    private FloatingActionButton botonAgregarEvento;
    private TextView nombreGrupo;
    private RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        nombreGrupo = findViewById(R.id.nombreGrupo);
        botonAgregarEvento = findViewById(R.id.botonAgregarEvento);
        lista=findViewById(R.id.listaEventos);


        Intent intent = getIntent();
        id = intent.getIntExtra("Grupo", -1);


        service.getEventos(Integer.toString(id)).enqueue(new Callback<EventosList>() {
            @Override
            public void onResponse(Call<EventosList> call, Response<EventosList> response) {
                if(response.body()==null)
                    nombreGrupo.setText("No hay eventos programados");
                else
               organizarLista(response.body().getGrupos());
            }

            @Override
            public void onFailure(Call<EventosList> call, Throwable t) {
               // Log.d("PRUEBAS","error: "+t);
            }
        });

        botonAgregarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CrearEventoActivity.class);
                intent.putExtra("Grupo", id);
                startActivity(intent);
                finish();
            }
        });



    }

    void organizarLista(List<Integer> lista){
      final List<Evento> l=new ArrayList<>();
        for(int i=0;i<lista.size();i++){
            service.getEvento(Integer.toString(lista.get(i))).enqueue(new Callback<Evento>() {
                @Override
                public void onResponse(Call<Evento> call, Response<Evento> response) {
                    l.add(response.body());
                    ponerEventos(l);

                }

                @Override
                public void onFailure(Call<Evento> call, Throwable t) {
                    Log.d("PRUEBAS","error "+t);
                }
            });
        }

    }

    void ponerEventos(List<Evento> l){
        adaptadorEvento adaptador=new adaptadorEvento(getApplicationContext(),l);
        lista.setAdapter(adaptador);
    }

}