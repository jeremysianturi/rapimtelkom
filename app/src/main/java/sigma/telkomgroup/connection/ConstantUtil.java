package sigma.telkomgroup.connection;

/**
 * Created by biting on 29/03/16.
 */
public interface ConstantUtil {

    public interface URL {
       // public String SERVER = "http://180.250.242.69:8017/";
       public String SERVER = "http://telkomgroup.digitalevent.id/";
//       public String SERVER = "http://rapimcorsec.digitalevent.id/";

        public static final String GET_GALLERY = SERVER + "api_data/gallery";
        public static final String GET_BANNER = SERVER + "api_data/getBanner";

    }

    public interface BANNER {
        public String TAG_TITLE = "banner";
        public String TAG_ID = "banner_id";
        public String TAG_NAME = "banner_names";
    }

    public interface MEETING{

        public String TAG_TITLE = "meeting";
        public String TAG_ID = "id";
        public String TAG_TEMA_NAME = "tema_name";
        public String TAG_AGENDA_NAME = "agenda_name";
        public String TAG_AGENDA_DATE = "agenda_date";
        public String TAG_START_DATE = "start_date";
        public String TAG_END_DATE = "end_date";
        public String TAG_LOCATION = "location";
        public String TAG_IMAGE = "image";
    }

    public interface RUNDOWN{

        public String TAG_TITLE = "rundown";
        public String TAG_ID = "id";
        public String TAG_MEET_ID = "meeting_id";
        public String TAG_NAME = "name";
        public String TAG_DATE = "rundown_date";
        public String TAG_TIME = "rundown_time";
        public String TAG_DESC = "description";
    }

    public interface CONTENT{

        public String TAG_TITLE = "content";
        public String TAG_ID = "id";
        public String TAG_MEET_ID = "meeting_id";
        public String TAG_NAME = "name";
        public String TAG_PATH = "file";
    }

    public interface VENUE {

        public String TAG_TITLE = "venue";
        public String TAG_ID = "venue_id";
        public String TAG_VENUE_TITLE = "venue_title";
        public String TAG_IMAGE = "venue_image";
        public String TAG_VENUE_CAPTION = "venue_caption";

    }

    public interface LOGIN{
        public String TAG_DEVICE_ID = "device_id";
        public String TAG_ID = "id";
        public String TAG_USER_NAME = "username";
        public String TAG_PASS = "password";
        public String TAG_NAME = "name";
        public String TAG_CFU = "cfu_fu";
        public String TAG_POSITION = "position";
        public String TAG_STATUS = "status";
        public String TAG_VERSION = "version_apk";
        public String TAG_QRCODE = "qrcode";
    }

    public interface NEWS{
        public String TAG_TITLE = "news";
        public String TAG_TITLE_COUNT = "count_news";
        public String TAG_ID = "news_id";
        public String TAG_USER_NEWS_ID = "id_user_news";
        public String TAG_TITLE_CONTENT = "news_title";
        public String TAG_CONTENT = "news_content";
        public String TAG_DATE = "news_date";
        public String TAG_STATUS = "status";
        //count
        public String TAG_USER_ID = "user_id";
        public String TAG_COUNT = "jml";
    }

    public interface QUISIONER {
        public String TAG_TITLE = "quistioner";
        public String TAG_QUIS_ID = "quistioner_id";
        public String TAG_TEMA_NAME = "tema_name";
        public String TAG_QUESTION = "question";
        public String TAG_START_DATE = "start_date";
        public String TAG_END_DATE = "end_date";
    }

    public interface POLLING {
        public String TAG_TITLE = "polling";
        public String TAG_POLL_ID = "id_polling";
        public String TAG_QUESTION = "question";
        public String TAG_TEMA_NAME = "tema_name";
        public String TAG_START_DATE = "start_date";
        public String TAG_END_DATE = "end_date";
    }

    public interface CHOICE {
        public String TAG_TITLE = "option";
        public String TAG_ID = "id";
        public String TAG_OPTION_NAME = "option_name";
    }

    public interface SUBMIT_FEEDBACK {
        public String TAG_TITLE = "quistioner";
        public String TAG_TITLE_1 = "quis1";
        public String TAG_TITLE_2 = "quis2";
        public String TAG_TITLE_3 = "quis3";
        public String TAG_TITLE_4 = "quis4";
        public String TAG_VALUE = "value";
        public String TAG_ADVICE = "feedback";
        public String TAG_ID_QUIS = "id_quistioner";
        public String TAG_DATE = "date";
        public String TAG_ID_USER = "user_id";
    }

    public interface SUBMIT_POLLING {
        public String TAG_TITLE = "polling";
        public String TAG_TITLE_1 = "pol1";
        public String TAG_TITLE_2 = "pol2";
        public String TAG_TITLE_3 = "pol3";
        public String TAG_TITLE_4 = "pol4";
        public String TAG_USER_ID = "user_id";
        public String TAG_POLL_ID = "id_polling";
        public String TAG_DATE = "date";
        public String TAG_VALUE_1 = "value1";
        public String TAG_VALUE_2 = "value2";
    }

    public interface CHECK_VERSION {
        public String TAG_TITLE = "version";
        public String TAG_ID = "version_id";
        public String TAG_NUMBER = "version_number";
        public String TAG_DATE = "version_date";
        public String TAG_URL = "version_url";
        public String TAG_NOTE = "version_note";
    }

    public interface CHECK_SHOUTBOX {
        public String TAG_TITLE = "shoutbox";
        public String TAG_USER_ID = "user_id";
        public String TAG_STATUS = "status";
        public String TAG_NOTE = "note";
    }

    public interface SHOUTBOX {
        public String TAG_TITLE = "shoutbox";
        public String TAG_TEMA_ID = "tema_id";
        public String TAG_SHOUT_OPTION_ID = "shout_option_id";
        public String TAG_SONG = "song_title";
    }

    public interface SUBMIT_SHOUT{
        public String TAG_TITLE = "shoutbox";
        public String TAG_TEMA_ID = "tema_id";
        public String TAG_USER_ID = "user_id";
        public String TAG_VALUE = "shout_value";
        public String TAG_CAPTION = "shout_kesan";
    }

    public interface SUBMIT_SELFIE{
        public String TAG_TITLE = "selfie";
        public String TAG_USER_ID = "user_id";
        public String TAG_TEMA_ID = "tema_id";
        public String TAG_CAPTION = "selfie_caption";
        public String TAG_IMAGE = "selfie_image";
    }

    public interface GALLERY {
        public String TAG_TITLE = "gallery";
        public String TAG_ID = "selfie_id";
        public String TAG_NAME = "photo_name";
//        public String TAG_CAPTION = "caption";
//        public String TAG_ID_USER = "id_user";
    }
}
