package sigma.telkomgroup.model;

/**
 * Created by biting on 15/04/16.
 */
public class ModelFeedback {
    private String question_id;
    private String question;
    private String tema_name;
    private String start_date;
    private String end_date;

    public ModelFeedback(){

    }

    public ModelFeedback(String id, String quest, String start, String end){
        question_id = id;
        question = quest;
        start_date = start;
        end_date = end;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
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
