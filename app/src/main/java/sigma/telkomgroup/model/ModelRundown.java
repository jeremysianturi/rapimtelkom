package sigma.telkomgroup.model;

/**
 * Created by biting on 07/03/16.
 */
public class ModelRundown {

    private String nomor;
    private String rundown_id;
    private String meeting_id;
    private String rundown_name;
    private String rundown_date;
    private String rundown_time;
    private String rundown_desc;
    private String rundown_speaker;

    public ModelRundown(){

    }

    public ModelRundown(String num, String run_name, String run_time, String run_desc){
        rundown_name = run_name;
        rundown_time = run_time;
        rundown_desc = run_desc;
        nomor = num;
    }

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getRundown_id() {
        return rundown_id;
    }

    public void setRundown_id(String rundown_id) {
        this.rundown_id = rundown_id;
    }

    public String getMeeting_id() {
        return meeting_id;
    }

    public void setMeeting_id(String meeting_id) {
        this.meeting_id = meeting_id;
    }

    public String getRundown_name() {
        return rundown_name;
    }

    public void setRundown_name(String rundown_name) {
        this.rundown_name = rundown_name;
    }

    public String getRundown_date() {
        return rundown_date;
    }

    public void setRundown_date(String rundown_date) {
        this.rundown_date = rundown_date;
    }

    public String getRundown_time() {
        return rundown_time;
    }

    public void setRundown_time(String rundown_time) {
        this.rundown_time = rundown_time;
    }

    public String getRundown_desc() {
        return rundown_desc;
    }

    public void setRundown_desc(String rundown_desc) {
        this.rundown_desc = rundown_desc;
    }

    public String getRundown_speaker() {
        return rundown_speaker;
    }

    public void setRundown_speaker(String rundown_speaker) {
        this.rundown_speaker = rundown_speaker;
    }
}
