package integrador.gruposestudio.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Evento {
    @SerializedName("event_id")
    @Expose
    private int event_id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("publisher_uid")
    @Expose
    private String publisher_uid;
    @SerializedName("group_id")
    @Expose
    private int group_id;
   /*
    @SerializedName("assistants")
    @Expose
    private UsuarioList assistants;
*/
    public Evento(String t, String d, String dat, String p, int g){
this.title=t;
this.description=d;
this.date=dat;
this.publisher_uid=p;
this.group_id=g;
    }


    public int getEvent_id() {
        return event_id;
    }

    public void setEvent_id(int event_id) {
        this.event_id = event_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPublisher_uid() {
        return publisher_uid;
    }

    public void setPublisher_uid(String publisher_uid) {
        this.publisher_uid = publisher_uid;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }
/*
    public UsuarioList getAssistants() {
        return assistants;
    }

    public void setAssistants(UsuarioList assistants) {
        this.assistants = assistants;
    }
    */
}
