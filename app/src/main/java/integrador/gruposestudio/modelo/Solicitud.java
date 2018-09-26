

        package integrador.gruposestudio.modelo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Solicitud {




    @SerializedName("request_id")
    @Expose
    private Integer requestId;
    @SerializedName("user_uid")
    @Expose
    private String userUid;
    @SerializedName("group_id")
    @Expose
    private Integer groupId;


    public Solicitud(String id, int idG){
this.userUid=id;
this.groupId=idG;

    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getUserUid() {
        return userUid;
    }

    public void setUserUid(String userUid) {
        this.userUid = userUid;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

}
