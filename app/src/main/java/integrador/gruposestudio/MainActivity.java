package integrador.gruposestudio;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v7.widget.SearchView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import integrador.gruposestudio.modelo.Grupo;
import integrador.gruposestudio.modelo.GrupoList;
import integrador.gruposestudio.Remote.RetrofitHelper;
import integrador.gruposestudio.modelo.Usuario;
import integrador.gruposestudio.adaptadores.adaptadorGrupo;
import integrador.gruposestudio.modelo.UsuarioList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseUser usuario;
    private TextView nombreUsuario, correoUsuario;
    private ImageView foto;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef;
    private ListView lv;
    private GrupoList gruposTotales;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        nombreUsuario = headerView.findViewById(R.id.nombreUsuario);
        correoUsuario = headerView.findViewById(R.id.correoUsuario);
        foto = headerView.findViewById(R.id.fotoPerfil);
        lv = findViewById(R.id.listaGrupo);

        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();

        myRef = database.getReference();






        //comprueba si hay alguna sesion activa y si la hay actualiza la interfaz grafica de acuerdo al usuario
        usuario = mAuth.getCurrentUser();
        if (usuario != null)
            actualizarIU();


        // Obtener el ID unico de usuario
        UserInfo informacionUsuario = usuario;
        String id = informacionUsuario.getUid();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CrearGrupoActivity.class);
                startActivityForResult(intent, 0);
                finish();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        /*Aquí empieza la lógica con Retrofit para traer los grupos*/
        RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);
        Call<GrupoList> call = service.getAllGrupos();
        call.enqueue(new Callback<GrupoList>() {
            @Override
            public void onResponse(Call<GrupoList> call, Response<GrupoList> response) {
                Log.d("RESPUESTA ","respuesta: "+response.message());
                    gruposTotales=response.body();
                    if(gruposTotales!=null)
                    ponerGrupos(gruposTotales);
            }

            @Override
            public void onFailure(Call<GrupoList> call, Throwable t) {
                Log.d("RESPUESTA ","error: "+t);
            }
        });
        /*Aquí termina la lógica de Retrofit*/

        //Se comprueba si el miembro pertenece a un grupo, si no pertenece da la opción de unirse.
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               final Grupo g = (Grupo) adapterView.getItemAtPosition(i);
                RetrofitHelper.GetDataService service = RetrofitHelper.getRetrofitInstance().create(RetrofitHelper.GetDataService.class);
                service.getMiembros(g.getGroupId()).enqueue(new Callback<UsuarioList>() {
                    @Override
                    public void onResponse(Call<UsuarioList> call, Response<UsuarioList> response) {
                        Boolean pertenece=comprobarSiPerteneceAlGrupo(response.body().getUsuarios());
                        if(pertenece){
                            Intent intent = new Intent(getApplicationContext(), GrupoActivity.class);
                            intent.putExtra("Grupo", g.getGroupId());
                            startActivityForResult(intent, 0);
                        }
                        else{
                            Intent intent = new Intent(getApplicationContext(), SolicitarPermisoActivity.class);
                            intent.putExtra("Grupo", g.getGroupId());
                            intent.putExtra("id", usuario.getUid());
                            startActivityForResult(intent, 0);
                        }
                    }

                    @Override
                    public void onFailure(Call<UsuarioList> call, Throwable t) {

                    }
                });

                //Log.d("ENTROMAIN","TAMAÑO "+g.getMembers().size());




            }


        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        //Aquí empieza la lógica de la busqueda
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                List <Grupo> lista=busquedaGrupo(s);
                ponerGrupos(lista);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d("BUSQUEDA", "mensaje2 " + s);
                return false;
            }
        });
        //Aquí termina la lógica de la busqueda
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_camera) {
            Intent intent = new Intent(getApplicationContext(), PerfilActivity.class);
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(getApplicationContext(), SolicitudesActivity.class);
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(getApplicationContext(), EventosActivity.class);
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_view) {
            Intent intent = new Intent(getApplicationContext(), GrupoActivity.class);
            startActivityForResult(intent, 0);
        } else if (id == R.id.nav_manage) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivityForResult(intent, 0);
            finish();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //actualiza la IU de acuerdo a su nombre, correo e imagen de perfil.
    public void actualizarIU() {
        String nombre, correo;
        Uri fotoU = usuario.getPhotoUrl();

        nombre = usuario.getDisplayName();
        correo = usuario.getEmail();
        nombreUsuario.setText(nombre);
        correoUsuario.setText(correo);

        Glide.with(getApplicationContext())
                .load(fotoU)
                .into(foto);
    }


    //Filtrado de grupos y luego se ponen en el ListView
    public void ponerGrupos(GrupoList grupos) {
        adaptadorGrupo adaptador = new adaptadorGrupo(getApplicationContext(), grupos.getGrupos());
        lv.setAdapter(adaptador);
    }

    //este solo recibe una lista
    public void ponerGrupos(List <Grupo> grupos) {
        adaptadorGrupo adaptador = new adaptadorGrupo(getApplicationContext(), grupos);
        lv.setAdapter(adaptador);
    }

    public List busquedaGrupo(String s) {
        List <Grupo> lista=new ArrayList();
        for(int i=0;i<gruposTotales.getGrupos().size();i++){
            if(gruposTotales.getGrupos().get(i).getGroupName().equals(s)){
                lista.add(gruposTotales.getGrupos().get(i));
            }
        }
return lista;
    }



    public Boolean comprobarSiPerteneceAlGrupo(List<Usuario> usuarios){
        for(int i=0;i<usuarios.size();i++){
            if(usuarios.get(i).getEmail().equals(usuario.getEmail()))
                return true;
        }
        return false;
    }


    }






