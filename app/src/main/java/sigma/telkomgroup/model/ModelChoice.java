package sigma.telkomgroup.model;

/**
 * Created by biting on 18/04/16.
 */
public class ModelChoice {
    private String choice_id;
    private String choice_list;

    public ModelChoice(){

    }

    public ModelChoice(String id, String list) {
        choice_id = id;
        choice_list = list;
    }

    public String getChoice_id() {
        return choice_id;
    }

    public void setChoice_id(String choice_id) {
        this.choice_id = choice_id;
    }

    public String getChoice_list() {
        return choice_list;
    }

    public void setChoice_list(String choice_list) {
        this.choice_list = choice_list;
    }
}
