package sigma.telkomgroup.model;

/**
 * Created by biting on 07/03/16.
 */
public class ModelMeeting {
    private String meeting_id;
    private String agenda_name;
    private String tema_name;
    private String agenda_date;
    private String start_date;
    private String end_date;
    private String meeting_location;
    private String meeting_image;

    public ModelMeeting(){

    }

    public ModelMeeting(String id, String agenda, String tema, String agDate,String start, String end, String loca){
        meeting_id = id;
        agenda_name = agenda;
        tema_name = tema;
        agenda_date = agDate;
        start_date = start;
        end_date = end;
        meeting_location = loca;
    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getAgenda_name() {
        return agenda_name;
    }

    public void setAgenda_name(String agenda_name) {
        this.agenda_name = agenda_name;
    }

    public String getTema_name() {
        return tema_name;
    }

    public void setTema_name(String tema_name) {
        this.tema_name = tema_name;
    }

    public String getAgenda_date() {
        return agenda_date;
    }

    public void setAgenda_date(String agenda_date) {
        this.agenda_date = agenda_date;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getMeeting_location() {
        return meeting_location;
    }

    public void setMeeting_location(String meeting_location) {
        this.meeting_location = meeting_location;
    }

    public String getMeeting_image() {
        return meeting_image;
    }

    public void setMeeting_image(String meeting_image) {
        this.meeting_image = meeting_image;
    }
}
