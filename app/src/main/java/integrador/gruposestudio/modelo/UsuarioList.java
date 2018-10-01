package integrador.gruposestudio.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsuarioList {
    @SerializedName("members")
    private List<Usuario> usuario;

    public List<Usuario> getUsuarios() {
        return usuario;
    }

    public void setGrupos(List<Usuario> grupos) {
        this.usuario = grupos;
    }

    public UsuarioList(List<Usuario>g){
        this.usuario=g;
    }
}
