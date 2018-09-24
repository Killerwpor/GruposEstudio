package integrador.gruposestudio;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.StringJoiner;

import integrador.gruposestudio.Remote.RetrofitHelper;

public class GrupoActivity extends AppCompatActivity {
    private FloatingActionButton agregarGrupo;
    private Button botonChat;
    private String idGrupo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
        agregarGrupo=findViewById(R.id.agregarNuevoGrupo);
        botonChat=findViewById(R.id.botonChat);

        //Aquí se recupera el id del grupo que viene desde el MainActivity
        Intent intent=getIntent();
        int id = intent.getIntExtra("Grupo",-1);
        Log.d("RECUPERAR","recuperado "+id);

        //Se recuperan los datos del grupo con Retrofit
        RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);




        agregarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(),CrearGrupoActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        botonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(), ChatActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }
}
