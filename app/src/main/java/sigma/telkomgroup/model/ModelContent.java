package sigma.telkomgroup.model;

/**
 * Created by biting on 29/03/16.
 */
public class ModelContent {
    private String content_id;
    private String meeting_id;
    private String content_title;
    private String content_path;
    private String content_date;

    public ModelContent(){

    }

    public ModelContent(String title, String path){
        content_title = title;
        content_path = path;
    }

    public String getContent_id() {
        return content_id;
    }

    public void setContent_id(String content_id) {
        this.content_id = content_id;
    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getContent_title() {
        return content_title;
    }

    public void setContent_title(String content_title) {
        this.content_title = content_title;
    }

    public String getContent_path() {
        return content_path;
    }

    public void setContent_path(String content_path) {
        this.content_path = content_path;
    }

    public String getContent_date() {
        return content_date;
    }

    public void setContent_date(String content_date) {
        this.content_date = content_date;
    }
}
