package integrador.gruposestudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import integrador.gruposestudio.modelo.Grupo;
import integrador.gruposestudio.Remote.RetrofitHelper;
import integrador.gruposestudio.modelo.GrupoList;
import integrador.gruposestudio.modelo.Solicitud;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearGrupoActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseUser usuario;
    private EditText nombreGrupo;
    private Button guardar;
    private Date fecha;
    private GrupoList grupos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);
        nombreGrupo=findViewById(R.id.campoNombreGrupo);
        guardar=findViewById(R.id.botonCrear);
        mAuth = FirebaseAuth.getInstance();
        usuario= mAuth.getCurrentUser();
        myRef = database.getReference();
        UserInfo informacionUsuario = usuario;



        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fecha=new Date();
                guardarNuevoGrupo();
            }
        });


    }

    private void guardarNuevoGrupo() {
        DateFormat Formato = new SimpleDateFormat("dd/mm/yyyy");
        String fechaActual=Formato.format(fecha).toString();
       Grupo grupo=new Grupo(nombreGrupo.getText().toString(),fechaActual,usuario.getUid());
        RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);
        service.guardarGrupo(grupo).enqueue(new Callback<Grupo>() {
            @Override
            public void onResponse(Call<Grupo> call, Response<Grupo> response) {
                Toast.makeText(getApplicationContext(), "Grupo creado",  Toast.LENGTH_LONG).show();
                finish();
            }

            @Override
            public void onFailure(Call<Grupo> call, Throwable t) {

            }
        });

        //Se traen todos los grupos
        service.getAllGrupos().enqueue(new Callback<GrupoList>() {
            @Override
            public void onResponse(Call<GrupoList> call, Response<GrupoList> response) {
                grupos=response.body();
                meterAlAdministrador(grupos.getGrupos());
            }

            @Override
            public void onFailure(Call<GrupoList> call, Throwable t) {

            }
        });



    }

    //Este metodo se hace para que cuando cree un grupo él mismo se agregue como miembro automaticamente
    public void meterAlAdministrador(List<Grupo> g){
        final RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);
final List<Grupo> gAux=g;
        for(int i=0;i<g.size();i++){
            if(g.get(i).getAdminUid().equals(usuario.getUid())){
                final int k=i;
                service.enviarSolicitud(usuario.getUid(),g.get(i).getGroupId()).enqueue(new Callback<Solicitud>() { //Aquí se envía la solicitud
                    @Override
                    public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {
                        Solicitud solicitud=new Solicitud(usuario.getUid(),gAux.get(k).getGroupId());
                        service.aceptarSolicitud(solicitud).enqueue(new Callback<Solicitud>() { //Aquí se acepta la solicitud
                            @Override
                            public void onResponse(Call<Solicitud> call, Response<Solicitud> response) {

                            }

                            @Override
                            public void onFailure(Call<Solicitud> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Solicitud> call, Throwable t) {

                    }
                });
            }
        }
    }
}
