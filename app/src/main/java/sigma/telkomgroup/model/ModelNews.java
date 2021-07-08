package sigma.telkomgroup.model;

/**
 * Created by biting on 07/03/16.
 */
public class ModelNews {
    private String news_id;
    private String user_id;
    private String user_news_id;
    private String news_title;
    private String news_content;
    private String news_date;
    private String news_status;
    private String news_count;

    public ModelNews(){

    }

    public ModelNews(String id, String user_news, String title, String content, String dates, String status){
        news_id = id;
        user_news_id = user_news;
        news_title = title;
        news_content = content;
        news_date = dates;
        news_status = status;
    }

    public ModelNews(String idus, String jml){
        user_id = idus;
        news_count = jml;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_news_id() {
        return user_news_id;
    }

    public void setUser_news_id(String user_news_id) {
        this.user_news_id = user_news_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public String getNews_date() {
        return news_date;
    }

    public void setNews_date(String news_date) {
        this.news_date = news_date;
    }

    public String getNews_status() {
        return news_status;
    }

    public void setNews_status(String news_status) {
        this.news_status = news_status;
    }
}
