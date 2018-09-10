package integrador.gruposestudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CrearGrupoActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseUser usuario;
    private EditText nombreGrupo;
    private Button guardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);
        nombreGrupo=findViewById(R.id.nombreGrupo);
        guardar=findViewById(R.id.botonCrear);
        mAuth = FirebaseAuth.getInstance();
        usuario= mAuth.getCurrentUser();
        myRef = database.getReference();


        guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guardarNuevoGrupo();
            }
        });


    }

    private void guardarNuevoGrupo() {
        myRef = database.getReference();
        UserInfo informacionUsuario = usuario;
        String id = informacionUsuario.getUid();
        String nombre = nombreGrupo.getText().toString();
        //grupo = new Grupo(nombre, "1", null, null);
        //myRef.child("Grupos").child(id).child(nombre).setValue(grupo);
      //  myRef.child("Grupos").child(nombre).setValue(grupo);
    }
}
