package integrador.gruposestudio.Remote;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import integrador.gruposestudio.modelo.Grupo;
import integrador.gruposestudio.modelo.GrupoList;
import integrador.gruposestudio.modelo.Solicitud;
import integrador.gruposestudio.modelo.SolicitudList;
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
import retrofit2.http.Path;

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

    public interface GetDataService { //aquí se crean todas las peticiones
        @GET("/groups")
        Call<GrupoList> getAllGrupos();

        @GET("/group_requests/{group_id}")
        Call<SolicitudList> getSolicitudesGrupo(@Path ("group_id") int idGrupo);

        @GET("group_name/{group_id}")
        Call<Grupo> getNombreGrupo(@Path ("group_id") int idGrupo);

        @Headers("Content-type: application/json")
        @POST("/register_group")
        Call<Grupo> guardarGrupo(@Body Grupo g);

    @Headers("Content-type: application/json")
    @POST("/register_user")
    Call<Usuario> guardarUsuario(@Body Usuario u);

    @Headers("Content-type: application/json")
     @POST("/user_requests/{user_uid}/{group_id}")
        Call<Solicitud> enviarSolicitud(@Path("user_uid") String id, @Path("group_id") int idGrupo);

        @Headers("Content-type: application/json")
        @POST("/group_users")
        Call<Solicitud> aceptarSolicitud(@Body  Solicitud s);




    }

}
