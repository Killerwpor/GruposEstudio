package integrador.gruposestudio.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EventosList {
    @SerializedName("group_events")
    private List<Integer> grupos;

    public List<Integer> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Integer> grupos) {
        this.grupos = grupos;
    }
}
