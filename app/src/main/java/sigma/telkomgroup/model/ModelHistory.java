package sigma.telkomgroup.model;

public class ModelHistory {
    String date;
    String question;
    String direktorat;

    public ModelHistory(String date, String question, String direktorat) {
        this.date = date;
        this.question = question;
        this.direktorat = direktorat;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getDirektorat() {
        return direktorat;
    }

    public void setDirektorat(String direktorat) {
        this.direktorat = direktorat;
    }
}
