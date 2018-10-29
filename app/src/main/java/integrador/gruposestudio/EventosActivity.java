package integrador.gruposestudio;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import integrador.gruposestudio.Remote.RetrofitHelper;
import integrador.gruposestudio.modelo.Grupo;
import integrador.gruposestudio.modelo.GrupoList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventosActivity extends AppCompatActivity {

    private int id;
    private Grupo grupoPrincipal;
    private FloatingActionButton botonAgregarEvento;
    private TextView nombreGrupo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventos);
        nombreGrupo=findViewById(R.id.nombreGrupo);
        botonAgregarEvento=findViewById(R.id.botonAgregarEvento);


       Intent intent=getIntent();
       id = intent.getIntExtra("Grupo",-1);

       botonAgregarEvento.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(getApplicationContext(),CrearEventoActivity.class);
               intent.putExtra("Grupo",id);
               startActivity(intent);
           }
       });



        //Se recuperan los datos del grupo con Retrofit
        RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);
        service.getAllGrupos().enqueue(new Callback<GrupoList>() {
            @Override
            public void onResponse(Call<GrupoList> call, Response<GrupoList> response) {

                encontrarGrupo(response.body());
                organizarUI();


            }

            @Override
            public void onFailure(Call<GrupoList> call, Throwable t) {

            }
        });
    }

    public void encontrarGrupo(GrupoList g){
        List<Grupo> grupos=g.getGrupos();
        for(int i=0;i<grupos.size();i++){

            if(grupos.get(i).getGroupId()==id){

                grupoPrincipal=grupos.get(i);

            }
        }

    }

    public void organizarUI(){
        if(grupoPrincipal!=null)
            nombreGrupo.setText("Eventos del grupo: "+grupoPrincipal.getGroupName());
    }

}
