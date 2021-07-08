package sigma.telkomgroup.model;

/**
 * Created by Biting on 7/28/2017.
 */

public class ModelGallery {

    private String idGallery;
    private String namaGallery;
    private String caption;

    public ModelGallery() {
    }

    public ModelGallery(String idGallery, String namaGallery) {
        this.idGallery = idGallery;
        this.namaGallery = namaGallery;
    }

    public ModelGallery(String idGallery, String namaGallery, String caption) {
        this.idGallery = idGallery;
        this.caption = caption;
        this.namaGallery = namaGallery;
    }

    public String getIdGallery() {
        return idGallery;
    }

    public void setIdGallery(String idGallery) {
        this.idGallery = idGallery;
    }

    public String getNamaGallery() {
        return namaGallery;
    }

    public void setNamaGallery(String namaGallery) {
        this.namaGallery = namaGallery;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
