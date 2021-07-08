package sigma.telkomgroup.model;

/**
 * Created by biting on 17/04/16.
 */
public class ModelVenue {

    private String venue_id;
    private String venue_title;
    private String venue_image;
    private String venue_caption;

    public ModelVenue(String venue_id, String venue_title, String venue_image, String venue_caption) {
        this.venue_id = venue_id;
        this.venue_title = venue_title;
        this.venue_image = venue_image;
        this.venue_caption = venue_caption;
    }

    public String getVenue_id() {
        return venue_id;
    }

    public void setVenue_id(String venue_id) {
        this.venue_id = venue_id;
    }

    public String getVenue_title() {
        return venue_title;
    }

    public void setVenue_title(String venue_title) {
        this.venue_title = venue_title;
    }

    public String getVenue_image() {
        return venue_image;
    }

    public void setVenue_image(String venue_image) {
        this.venue_image = venue_image;
    }

    public String getVenue_caption() {
        return venue_caption;
    }

    public void setVenue_caption(String venue_caption) {
        this.venue_caption = venue_caption;
    }
}
