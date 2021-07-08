package sigma.telkomgroup.model;

/**
 * Created by biting on 22/07/16.
 */
public class ModelShoutbox {

    private String id_user;
    private String status;
    private String note;

    public ModelShoutbox(){
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
