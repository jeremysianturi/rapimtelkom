package sigma.telkomgroup.model;

/**
 * Created by biting on 07/03/16.
 */
public class ModelQuesioner {
    private String question_id;
    private String meeting_id;
    private String question_1;
    private String question_2;
    private String question_3;
    private String question_4;
    private String question_5;
    private String question_6;
    private String question_7;
    private String question_8;
    private String question_9;
    private String question_10;
    private String question_date;
    private String question_status;

    public ModelQuesioner(String qid, String mid, String q1, String q2, String q3, String q4,
                          String q5, String q6, String q7, String q8, String q9, String q10, String dt, String stat) {
        question_id = qid;
        meeting_id = mid;
        question_1 = q1;
        question_2 = q2;
        question_3 = q3;
        question_4 = q4;
        question_5 = q5;
        question_6 = q6;
        question_7 = q7;
        question_8 = q8;
        question_9 = q9;
        question_10 = q10;
        question_date = dt;
        question_status = stat;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getQuestion_1() {
        return question_1;
    }

    public void setQuestion_1(String question_1) {
        this.question_1 = question_1;
    }

    public String getQuestion_2() {
        return question_2;
    }

    public void setQuestion_2(String question_2) {
        this.question_2 = question_2;
    }

    public String getQuestion_3() {
        return question_3;
    }

    public void setQuestion_3(String question_3) {
        this.question_3 = question_3;
    }

    public String getQuestion_4() {
        return question_4;
    }

    public void setQuestion_4(String question_4) {
        this.question_4 = question_4;
    }

    public String getQuestion_5() {
        return question_5;
    }

    public void setQuestion_5(String question_5) {
        this.question_5 = question_5;
    }

    public String getQuestion_6() {
        return question_6;
    }

    public void setQuestion_6(String question_6) {
        this.question_6 = question_6;
    }

    public String getQuestion_7() {
        return question_7;
    }

    public void setQuestion_7(String question_7) {
        this.question_7 = question_7;
    }

    public String getQuestion_8() {
        return question_8;
    }

    public void setQuestion_8(String question_8) {
        this.question_8 = question_8;
    }

    public String getQuestion_9() {
        return question_9;
    }

    public void setQuestion_9(String question_9) {
        this.question_9 = question_9;
    }

    public String getQuestion_10() {
        return question_10;
    }

    public void setQuestion_10(String question_10) {
        this.question_10 = question_10;
    }

    public String getQuestion_date() {
        return question_date;
    }

    public void setQuestion_date(String question_date) {
        this.question_date = question_date;
    }

    public String getQuestion_status() {
        return question_status;
    }

    public void setQuestion_status(String question_status) {
        this.question_status = question_status;
    }
}
