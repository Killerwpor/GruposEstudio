package integrador.gruposestudio;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PerfilActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseUser usuario;
    private ImageView foto;
    private TextView nombre,correo,celular;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        foto=findViewById(R.id.fotoPerfil);
        nombre=findViewById(R.id.nombre);
        correo=findViewById(R.id.email);
        celular=findViewById(R.id.celular);

        //se recupera el usuario actual
        mAuth = FirebaseAuth.getInstance();
        usuario= mAuth.getCurrentUser();



      nombre.setText("Nombre: "+usuario.getDisplayName());
      correo.setText("Correo: "+usuario.getEmail());
      //celular.setText(usuario.getPhoneNumber()); este casi ningun perfil lo tiene configurado.

    //aquí se carga la imagen
        Glide.with(getApplicationContext())
                .load(usuario.getPhotoUrl())
                .into(foto);
    }
}
