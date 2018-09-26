package integrador.gruposestudio;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import integrador.gruposestudio.Remote.RetrofitHelper;
import integrador.gruposestudio.modelo.Solicitud;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolicitarPermisoActivity extends Activity {

    private Button boton;
    private TextView mensaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitar_permiso);
        boton=findViewById(R.id.botonSolicitarUnirse);
        mensaje=findViewById(R.id.mensaje);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);
                String idUsuario = getIntent().getStringExtra("id");
                int idGrupo = getIntent().getIntExtra("Grupo",-1);
                    service.enviarSolicitud(idUsuario,idGrupo).enqueue(new Callback<Solicitud>() {
                        @Override
                        public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                            if(response.message().equals("BAD REQUEST"))
                                Toast.makeText(getApplicationContext(), "Ya existe una solicitud para este grupo",  Toast.LENGTH_LONG).show();
                            else
                            Toast.makeText(getApplicationContext(), "Se ha enviado la solicitud de admisión al administrador del grupo",  Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Solicitud> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Ups, algo salió mal :(",  Toast.LENGTH_LONG).show();
                        }
                    });


                    finish();


            }
        });

    }
}
