package sigma.telkomgroup.model;

/**
 * Created by biting on 14/04/16.
 */
public class ModelPolling {

    private String id_polling;
    private String question;
    private String tema_name;
    private String start_date;
    private String end_date;

    public ModelPolling(){

    }

    public ModelPolling(String id, String quest, String start, String end) {
        id_polling = id;
        question = quest;
        start_date = start;
        end_date = end;
    }

    public String getId_polling() {
        return id_polling;
    }

    public void setId_polling(String id_polling) {
        this.id_polling = id_polling;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getTema_name() {
        return tema_name;
    }

    public void setTema_name(String tema_name) {
        this.tema_name = tema_name;
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
}
