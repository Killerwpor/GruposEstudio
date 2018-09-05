package integrador.gruposestudio;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GrupoActivity extends AppCompatActivity {
    FloatingActionButton agregarGrupo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo);
        agregarGrupo=findViewById(R.id.agregarNuevoGrupo);

        agregarGrupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (getApplicationContext(),CrearGrupoActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }
}
