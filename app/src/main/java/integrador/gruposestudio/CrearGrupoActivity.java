package integrador.gruposestudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import integrador.gruposestudio.modelo.Grupo;
import integrador.gruposestudio.Remote.RetrofitHelper;
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
                Log.d("METODOPOST", "POSITIVO"+response.message());
            }

            @Override
            public void onFailure(Call<Grupo> call, Throwable t) {
                Log.d("METODOPOST","Error "+t);
            }
        });


    }
}
