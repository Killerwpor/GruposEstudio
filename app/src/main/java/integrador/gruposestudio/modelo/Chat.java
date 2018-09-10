package integrador.gruposestudio.modelo;

public class Chat {
    private String mensaje;
    private String autor;
    private String fecha;

    public Chat(){

    }

    public Chat(String m, String a, String f){
this.mensaje=m;
this.autor=a;
this.fecha=f;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
