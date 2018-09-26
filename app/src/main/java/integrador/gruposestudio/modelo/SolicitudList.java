package integrador.gruposestudio.modelo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SolicitudList {
    @SerializedName("group_requests") //se trae el objeto group_requests el cual es una lista de Solicitudes
    private List<Solicitud> solicitudes;


    public List<Solicitud> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicituds(List<Solicitud> solicituds) {
        this.solicitudes = solicituds;
    }
}
