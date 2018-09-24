package integrador.gruposestudio.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GrupoList {
    @SerializedName("groups") //se trae el objeto grupos el cual es una lista de Grupos
    private List<Grupo> grupos;

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public GrupoList(List<Grupo>g){
        this.grupos=g;
    }
}
