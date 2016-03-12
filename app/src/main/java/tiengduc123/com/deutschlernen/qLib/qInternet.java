package tiengduc123.com.deutschlernen.qLib;

import android.content.Context;
import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by qadmin on 11.12.15.
 */
public class qInternet {
    public String ads;

    public String docNoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();

        // many of these calls can throw exceptions, so i've just
        // wrapped them all in one try/catch statement.
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public String Setting(){
        String _settingString  = docNoiDung_Tu_URL("http://tiengduc123.com/app/setting.php");
        try {
            JSONArray mangJson = new JSONArray(_settingString);
            JSONObject _setting = mangJson.getJSONObject(0);
            ads =  _setting.getString("ads");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    /*public String DownloadVideo(String f_url, String VideoID) {
        int count;
        try {
            URL url = new URL(f_url);
            URLConnection conection = url.openConnection();
            conection.connect();
            // getting file length
            int lenghtOfFile = conection.getContentLength();

            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            // Output stream to write file
            OutputStream output = new FileOutputStream("/sdcard/" + VideoID + ".mp4");

            byte data[] = new byte[1024];

            long total = 0;

            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                // After this onProgressUpdate will be called
                //publishProgress(""+(int)((total*100)/lenghtOfFile));

                // writing data to file
                output.write(data, 0, count);
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }*/





    public void shareForFriend(Context context) {
        String message = "Deutsch Lernen Mit Video.<br /> https://play.google.com/store/apps/details?id=com.kabam.marvelbattle";
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("text/plain");
        share.putExtra(Intent.EXTRA_TEXT, message);

        context.startActivity(Intent.createChooser(share, "Share to your Friend"));
    }
}
