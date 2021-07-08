package sigma.telkomgroup.model;

public class ModelBanner {

    private String banner_id;
    private String banner_name;

    public ModelBanner(){

    }

    public ModelBanner(String id, String name) {
        banner_id = id;
        banner_name = name;
    }

    public String getBanner_id() {
        return banner_id;
    }

    public void setBanner_id(String banner_id) {
        this.banner_id = banner_id;
    }

    public String getBanner_name() {
        return banner_name;
    }

    public void setBanner_name(String banner_name) {
        this.banner_name = banner_name;
    }
}
