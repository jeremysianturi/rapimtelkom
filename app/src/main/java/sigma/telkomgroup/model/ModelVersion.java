package sigma.telkomgroup.model;

/**
 * Created by biting on 14/07/16.
 */
public class ModelVersion {

    private String version_id;
    private String version_number;
    private String version_date;
    private String version_url;
    private String version_note;

    public ModelVersion(){
        
    }

    public ModelVersion(String id, String number, String date, String url, String note){
        version_id = id;
        version_number = number;
        version_date = date;
        version_url = url;
        version_note = note;
    }

    public String getVersion_id() {
        return version_id;
    }

    public void setVersion_id(String version_id) {
        this.version_id = version_id;
    }

    public String getVersion_number() {
        return version_number;
    }

    public void setVersion_number(String version_number) {
        this.version_number = version_number;
    }

    public String getVersion_date() {
        return version_date;
    }

    public void setVersion_date(String version_date) {
        this.version_date = version_date;
    }

    public String getVersion_url() {
        return version_url;
    }

    public void setVersion_url(String version_url) {
        this.version_url = version_url;
    }

    public String getVersion_note() {
        return version_note;
    }

    public void setVersion_note(String version_note) {
        this.version_note = version_note;
    }
}
