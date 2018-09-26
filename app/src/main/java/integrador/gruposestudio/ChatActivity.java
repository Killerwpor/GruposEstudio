package integrador.gruposestudio;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import integrador.gruposestudio.modelo.Chat;
import integrador.gruposestudio.adaptadores.adaptadorChat;

public class ChatActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private FirebaseUser usuario;
    private EditText mensaje;
    private Button enviar;
    private Chat chat;
    private TextView mensajei, mensajef;
    private ValueEventListener mPostListener;
    private ArrayList<Chat> chatArray;
    private ListView lv;
    private adaptadorChat adaptador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        enviar = findViewById(R.id.botonEnviar);
        mensaje=findViewById(R.id.campoMensaje);
        lv = findViewById(R.id.listaChat);
        mAuth = FirebaseAuth.getInstance();
        usuario = mAuth.getCurrentUser();
        chatArray=new ArrayList<Chat>();

enviar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        enviarMensaje();
    }
});
    }
    protected void onStart() {
        super.onStart();
        myRef = database.getReference();
        UserInfo informacionUsuario = usuario;
        String id = informacionUsuario.getUid();
        Log.d("IDDEUSUARIO", "ID: " + id);
        myRef.child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                    Chat chatAux = dataSnapshot.getValue(Chat.class);
                //og.d("CHAT", "Mensaje: " + chatAux.getMensaje());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("TAG", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        });
    }



    private void enviarMensaje() {

        myRef = database.getReference();
        UserInfo informacionUsuario = usuario;
        String id = informacionUsuario.getUid();
        String mensajeChat=mensaje.getText().toString();
        chat=new Chat(mensajeChat,informacionUsuario.getDisplayName()+":","Fecha");
        chatArray.add(chat);
        adaptador = new adaptadorChat(getApplicationContext(), chatArray);
        //Log.d("LISTA", "elementos: "+chatArray.get(0).getAutor());
       myRef.child(id).setValue(chat);
        lv.setAdapter(adaptador);
    }


}
