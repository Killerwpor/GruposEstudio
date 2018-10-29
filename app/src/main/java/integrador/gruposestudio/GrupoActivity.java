package integrador.gruposestudio;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;
import java.util.StringJoiner;

import integrador.gruposestudio.Remote.RetrofitHelper;
import integrador.gruposestudio.modelo.Grupo;
import integrador.gruposestudio.modelo.GrupoList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GrupoActivity extends AppCompatActivity {
    private FloatingActionButton botonInvitaciones;
    private Button botonChat,botonEventos;
    private String idGrupo;
    private Intent intent;
    private int id;
    private Grupo grupoPrincipal;
    private TextView nombreGrupo,campoInvitaciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
        botonChat=findViewById(R.id.botonChat);
        nombreGrupo=findViewById(R.id.nombreGrupo);
        botonEventos=findViewById(R.id.botonEventos);



        //Aquí se recupera el id del grupo que viene desde el MainActivity
        intent=getIntent();
       id = intent.getIntExtra("Grupo",-1);



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



        botonEventos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),EventosActivity.class);
                intent.putExtra("Grupo",id);
                startActivityForResult(intent,0);
            }
        });

        botonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), ChatActivity.class);
                intent.putExtra("idGrupo",id);
                startActivityForResult(intent, 0);
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
        nombreGrupo.setText(grupoPrincipal.getGroupName());
    }


}
