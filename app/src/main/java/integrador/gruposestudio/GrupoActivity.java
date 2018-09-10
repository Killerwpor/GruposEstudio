package integrador.gruposestudio;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GrupoActivity extends AppCompatActivity {
    FloatingActionButton agregarGrupo;
    Button botonChat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
        agregarGrupo=findViewById(R.id.agregarNuevoGrupo);
        botonChat=findViewById(R.id.botonChat);

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
