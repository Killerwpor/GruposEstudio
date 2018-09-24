package integrador.gruposestudio.Remote;

import java.util.List;
import java.util.Map;

import integrador.gruposestudio.modelo.Grupo;
import integrador.gruposestudio.modelo.GrupoList;
import integrador.gruposestudio.modelo.Usuario;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public class RetrofitHelper {
    private static Retrofit retrofit;
    private static final String BASE_URL = "https://backend-grupos-estudio.herokuapp.com";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
/*
    public interface GetDataService {
        @GET("/groups")
        Call<List<Grupo>> getAllPhotos(); //a
    }
*/
    public interface GetDataService { //aquí se crean todas las peticiones
        @GET("/groups")
        Call<GrupoList> getAllGrupos();

        @Headers("Content-type: application/json")
        @POST("/register_group")
        Call<Grupo> guardarGrupo(@Body Grupo g);

    @Headers("Content-type: application/json")
    @POST("/register_user")
    Call<Usuario> guardarUsuario(@Body Usuario u);




    }

}
