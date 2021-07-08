package sigma.telkomgroup.model;

/**
 * Created by biting on 18/04/16.
 */
public class ModelGetFeedback {
    private String user_id;
    private String date_detail;
    private String quis_id;
    private String value_content;
    private String feedback_content;

    public ModelGetFeedback(){

    }

    public ModelGetFeedback(String idu, String date, String idq, String value, String feedback){
        user_id = idu;
        date_detail = date;
        quis_id = idq;
        value_content = value;
        feedback_content = feedback;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getDate_detail() {
        return date_detail;
    }

    public void setDate_detail(String date_detail) {
        this.date_detail = date_detail;
    }

    public String getQuis_id() {
        return quis_id;
    }

    public void setQuis_id(String quis_id) {
        this.quis_id = quis_id;
    }

    public String getValue_content() {
        return value_content;
    }

    public void setValue_content(String value_content) {
        this.value_content = value_content;
    }

    public String getFeedback_content() {
        return feedback_content;
    }

    public void setFeedback_content(String feedback_content) {
        this.feedback_content = feedback_content;
    }
}
