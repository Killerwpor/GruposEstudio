package integrador.gruposestudio;

import android.content.Intent;
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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
    private int idGrupo;
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
       Intent intent=getIntent();
       idGrupo = intent.getIntExtra("idGrupo",-1);

enviar.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        enviarMensaje();
    }
});
    }
    protected void onStart() {
        super.onStart();
       myRef=database.getReference();
        DatabaseReference myRef2 = database.getReference().child(Integer.toString(idGrupo));
        UserInfo informacionUsuario = usuario;
        String id = informacionUsuario.getUid();
        myRef2.addValueEventListener(new ValueEventListener() { //Si hay cambios en la base de datos se entra aquí
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatArray.clear(); //se vacia la lista para que cada vez que haya un cambio no se dupliquen los datos
                for(DataSnapshot item : dataSnapshot.getChildren()) { //se obtienen los hijos de la referencia
                    Chat data = item.getValue(Chat.class);
                    chatArray.add(data);
                }
                adaptador = new adaptadorChat(getApplicationContext(), chatArray);
                adaptador.notifyDataSetChanged();
                lv.setAdapter(adaptador);

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
        Calendar calendario = new GregorianCalendar();
        int horas,minutos,milesimas;
        horas=calendario.get(Calendar.HOUR_OF_DAY);
        minutos=calendario.get(Calendar.MINUTE);
        milesimas=calendario.get(Calendar.MILLISECOND);
        String fecha=Integer.toString(horas)+":"+Integer.toString(minutos);
        myRef = database.getReference();
        UserInfo informacionUsuario = usuario;
        String id = informacionUsuario.getUid();
        String mensajeChat=mensaje.getText().toString();
        chat=new Chat(mensajeChat,informacionUsuario.getDisplayName()+":",fecha);
       myRef.child(Integer.toString(idGrupo)).child(fecha+":"+milesimas).setValue(chat);

    }


}
