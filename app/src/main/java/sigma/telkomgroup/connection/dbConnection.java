package sigma.telkomgroup.connection;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import sigma.telkomgroup.model.ModelUser;

/**
 * Created by biting on 07/03/16.
 */
public class dbConnection {
    public static final String serverUrl = "http://telkomgroup.digitalevent.id/"; //production
//    public static final String serverUrl = "http://rapimcorsec.digitalevent.id/"; //production
    //public static final String serverUrl = "http://180.250.242.69:8022/"; // development
    private final static String TAG = "ServerRequest";

    //GET data
    public static final String urlNews = "api_data/getNewNews";
    public static final String urlUpdateNews = "api_data/updateNews";
    public static final String urlCountNews = "api_data/countNews";
    public static final String urlRundown = "api_data/getRundown";
    public static final String urlContent = "api_data/getContent";
    public static final String urlFeedback = "api_data/getQuistioner";
    public static final String urlPolling = "api_data/getPolling";
    public static final String urlChoice = "api_data/getOption";
    public static final String urlMetting = "api_data/getMeeting";
    public static final String urlVenue = "api_data/getVenue";
    public static final String urlQuestionHistory = "api_data/getQuestion";
    public static final String urlVersion = "api_data/getVersion";
    public static final String urlDownload = "download/";
    public static final String urlShoutbox = "api_data/getShoutbox";
    public static final String urlCheckShoutbox = "api_data/checkShoutbox";

    //POST data
    public static final String urlLogin = "api_login/auth/";
    public static final String urlSubmitQuisioner = "api_data/submitQuistioner";
    public static final String urlSubmitPolling = "api_data/submitPolling";
    public static final String urlSubmitShoutbox = "api_data/submitShoutbox";
    public static final String urlSubmitSelfie = "api_data/submitSelfie";

    public dbConnection() {
        super();
    }

    /**
     * Mengirimkan GET request
     */
    public String sendGetRequest(String reqUrl) {
        HttpClient httpClient;
        HttpGet httpGet = new HttpGet(serverUrl + reqUrl);
        InputStream is = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 60 * 1000);
            HttpConnectionParams.setSoTimeout(params, 60 * 1000);
            httpClient = new DefaultHttpClient(params);
            Log.d(TAG, "executing...");
            HttpResponse httpResponse = httpClient.execute(httpGet);
            StatusLine status = httpResponse.getStatusLine();
            if (status.getStatusCode() == HttpStatus.SC_OK && httpResponse != null) {
                /** mengambil response string dari server */
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
                is.close();
            }
        } catch (Exception e) {
            Log.d(TAG, "error");
        }

        return stringBuilder.toString();
    }

    /**
     * Mengirimkan POST request
     */

    public String sendPostRequestLogin(ModelUser username, String url1) {
        int replyCode = 99;
        HttpClient httpClient;
        InputStream is = null;
        HttpPost post = new HttpPost(this.serverUrl + url1);
        StringBuilder stringBuilder = new StringBuilder();
        /** menambahkan parameter ke dalam request */
        List<NameValuePair> value = new ArrayList<NameValuePair>();
        value.add(new BasicNameValuePair(ConstantUtil.LOGIN.TAG_USER_NAME, username.getUsername()));
        value.add(new BasicNameValuePair(ConstantUtil.LOGIN.TAG_PASS, username.getPassword()));
        value.add(new BasicNameValuePair(ConstantUtil.LOGIN.TAG_DEVICE_ID, username.getIdDevice()));
        value.add(new BasicNameValuePair(ConstantUtil.LOGIN.TAG_VERSION, FirebaseInstanceId.getInstance().getToken()));

        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 60 * 1000);
            HttpConnectionParams.setSoTimeout(params, 60 * 1000);
            httpClient = new DefaultHttpClient(params);
            post.setEntity(new UrlEncodedFormEntity(value));
            HttpResponse httpResponse = httpClient.execute(post);
            StatusLine status = httpResponse.getStatusLine();
            if (status.getStatusCode() == HttpStatus.SC_OK && httpResponse != null) {

                replyCode = status.getStatusCode();

                /** mengambil response string dari server */
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line + "\n");

                    Log.d(TAG, stringBuilder.toString());
                }
                is.close();
            } else {
                throw new IOException();
            }

        } catch (ConnectTimeoutException e) {
            Log.e("CONN TIMEOUT", e.toString());
        } catch (SocketTimeoutException e) {
            Log.e("SOCK TIMEOUT", e.toString());
        } catch (IOException e) {
            stringBuilder.append("0");
        }
        return stringBuilder.toString();
    }
}