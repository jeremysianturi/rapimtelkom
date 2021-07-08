package sigma.telkomgroup.parser;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 * Created by biting on 18/04/16.
 */
public class PostJSON extends AsyncTask<Void, Void, String> {

    //ProgressDialog pd = new ProgressDialog(null);
    private String jsonData="";
    private String urlService="";
    private String form=null;
    //	private Context context;
    ProgressDialog pd ;
    public PostJSON(String jsonData, String urlService){
        this.jsonData=jsonData;
        this.urlService = urlService;
        Log.v("URL CALLED", this.urlService);

//		 pd = new ProgressDialog(context);
//		 pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//         pd.setMessage("Uploading photo, please wait.");
//         pd.setMax(100);
//         pd.setCancelable(true);
    }

    public PostJSON(String jsonData, String urlService, String form){
        this.jsonData=jsonData;
        this.urlService = urlService;
        this.form = form;

    }

    protected String getASCIIContentFromEntity(HttpEntity entity)
            throws IllegalStateException, IOException {
        InputStream in = entity.getContent();
        StringBuffer out = new StringBuffer();
        int n = 1;
        while (n > 0) {
            byte[] b = new byte[4096];
            n = in.read(b);
            if (n > 0)
                out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    protected String getUrlByProjectStatus(){
        StringBuilder sb=null;

        sb=new StringBuilder(urlService);
        sb.append("/"+jsonData);


        return sb.toString();
    }

    @Override
    protected void onPreExecute() {

//		   pd.setMessage("Sending ...");
//		   pd.setCancelable(false);
//		   pd.setIndeterminate(true);
//		   pd.show();
    };


    @Override
    protected String doInBackground(Void... params) {

        try {

            HttpPost httpPostRequest = new HttpPost(urlService);
            HttpParams httpParameters = new BasicHttpParams();
            DefaultHttpClient httpclient = new DefaultHttpClient(httpParameters);
            StringEntity se;
            se = new StringEntity(jsonData.toString());

            if (jsonData.toString().length() > 4000) {
                int chunkCount = jsonData.toString().length() / 4000;     // integer division
                for (int i = 0; i <= chunkCount; i++) {
                    int max = 4000 * (i + 1);
                    if (max >= jsonData.toString().length()) {
                        Log.i("JSON DATA " + i + " of " + chunkCount + ":", jsonData.toString().substring(4000 * i));
                    } else {
                        Log.i("JSON DATA " + i + " of " + chunkCount + ":", jsonData.toString().substring(4000 * i, max));
                    }
                }
            } else
            {
                Log.i("JSON DATA", jsonData.toString());

            }

            //Log.i("JSON DATA", jsonData.toString());
            // Set HTTP parameters
            httpPostRequest.setEntity(se);
            httpPostRequest.setHeader("Accept", "application/json");
            httpPostRequest.setHeader("Content-type", "application/json");
            //httpPostRequest.setHeader("Accept-Encoding", "gzip"); // only set this parameter if you would like to use gzip compression


            // Set the timeout in milliseconds until a connection is established.
            // The default value is zero, that means the timeout is not used.
            int timeoutConnection = 30000;
            HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
            // Set the default socket timeout (SO_TIMEOUT)
            // in milliseconds which is the timeout for waiting for data.
            int timeoutSocket = 30000;
            HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);


            Log.i("SENDING", "SEND to [" +httpPostRequest + "ms]");
            long t = System.currentTimeMillis();
            HttpResponse response = (HttpResponse) httpclient.execute(httpPostRequest);
            Log.i("RESPONSE", "HTTPResponse received in [" + (System.currentTimeMillis()-t) + "ms]");

            // Get hold of the response entity (-> the data):
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                // Read the content stream
                InputStream instream = entity.getContent();
                Header contentEncoding = response.getFirstHeader("Content-Encoding");
                if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                    instream = new GZIPInputStream(instream);
                }

                // convert content stream to a String
                String resultString= convertStreamToString(instream);
                instream.close();
                resultString = resultString.substring(0,resultString.length()-1); // remove wrapping "[" and "]"

                Log.i("RESULT", resultString);
                // Transform the String into a JSONObject
                //JSONObject jsonObjRecv = new JSONObject(resultString);
                // Raw DEBUG output of our received JSON object:
                //Log.i("RESPONSE","<JSONObject>\n"+jsonObjRecv.toString()+"\n</JSONObject>");

                return resultString;
            }

        }
        catch (Exception e)
        {
            // More about HTTP exception handling in another tutorial.
            // For now we just print the stack trace.
            e.printStackTrace();
        }
        return null;

    }

    @Override
    protected void onPostExecute(String results) {
        //pd.dismiss();

        if (results != null && form != null) {
            //Toast.make
        }

    }

    private String convertStreamToString(InputStream is) {
		/*
		 * To convert the InputStream to String we use the BufferedReader.readLine()
		 * method. We iterate until the BufferedReader return null which means
		 * there's no more data to read. Each line will appended to a StringBuilder
		 * and returned as String.
		 *
		 * (c) public domain: http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
		 */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
