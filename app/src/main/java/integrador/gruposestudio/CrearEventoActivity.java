package integrador.gruposestudio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import integrador.gruposestudio.Remote.RetrofitHelper;
import integrador.gruposestudio.modelo.Evento;
import integrador.gruposestudio.modelo.Grupo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearEventoActivity extends AppCompatActivity {

    private Button botonGuardarEvento;
    private TextView campoTitulo,campoDescripcion,campoFecha;
    private FirebaseUser usuario;
    private FirebaseAuth mAuth;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);

        Intent intent=getIntent();
        id = intent.getIntExtra("Grupo",-1);

        botonGuardarEvento=findViewById(R.id.botonGuardarEvento);
        campoTitulo=findViewById(R.id.campoTitulo);
        campoDescripcion=findViewById(R.id.campoDescripción);
        campoFecha=findViewById(R.id.campoFecha);

        mAuth = FirebaseAuth.getInstance();
        usuario=mAuth.getCurrentUser();



        botonGuardarEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Evento e=new Evento(campoTitulo.getText().toString(),campoDescripcion.getText().toString(),campoFecha.getText().toString(),usuario.getUid(),id);
             RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);
                service.guardarEvento(e).enqueue(new Callback<Evento>() {
                    @Override
                    public void onResponse(Call<Evento> call, Response<Evento> response) {
                        Log.d("ERRORALGUARDAR","GUARDADO");
                        Toast.makeText(getApplicationContext(), "Evento Creado", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Evento> call, Throwable t) {
                        Log.d("ERRORALGUARDAR","ERROR: "+t);

                    }
                });
            }
        });
    }
}
