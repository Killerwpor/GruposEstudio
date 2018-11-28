package integrador.gruposestudio;

import android.app.Activity;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import integrador.gruposestudio.Remote.RetrofitHelper;
import integrador.gruposestudio.modelo.Solicitud;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AceptarORechazarActivity extends Activity {

    private Button aceptar,rechazar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aceptar_orechazar);
        aceptar=findViewById(R.id.botonAceptar);
        rechazar=findViewById(R.id.botonRechazar);
        final int grupoId=getIntent().getIntExtra("Grupo",-1);
        final String uID= getIntent().getStringExtra("id");
        aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);
                Solicitud s=new Solicitud(uID,grupoId);
                service.aceptarSolicitud(s).enqueue(new Callback<Solicitud>() {
                    @Override
                    public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                        Toast.makeText(getApplicationContext(), "Solicitud aceptada", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Solicitud> call, Throwable t) {

                    }
                });
            }
        });

        rechazar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);
                service.rechazarSolicitud(uID,grupoId).enqueue(new Callback<Solicitud>() {
                    @Override
                    public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                        Toast.makeText(getApplicationContext(), "Solicitud rechazada", Toast.LENGTH_LONG).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Solicitud> call, Throwable t) {

                    }
                });
            }
        });
    }
}
