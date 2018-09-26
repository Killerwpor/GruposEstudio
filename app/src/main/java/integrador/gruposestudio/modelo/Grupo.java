

        package integrador.gruposestudio.modelo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Grupo {

    @SerializedName("group_id")
    @Expose
    private Integer groupId;
    @SerializedName("group_name")
    @Expose
    private String groupName;
    @SerializedName("creation_date")
    @Expose
    private String creationDate;
    @SerializedName("admin_uid")
    @Expose
    private String adminUid;
    @SerializedName("members")
    @Expose
    private List<Usuario> members;
    @SerializedName("events")
    @Expose
    private List<Object> events = null;


    public Grupo(String n, String f, String id){
        this.groupName=n;
        this.creationDate=f;
        this.adminUid=id;
      //  this.members=m;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getAdminUid() {
        return adminUid;
    }

    public void setAdminUid(String adminUid) {
        this.adminUid = adminUid;
    }

    public List<Usuario> getMembers() {
        return members;
    }

    public void setMembers(List<Usuario> members) {
        this.members = members;
    }

    public List<Object> getEvents() {
        return events;
    }

    public void setEvents(List<Object> events) {
        this.events = events;
    }

}
