package app.com.saily;

import android.os.AsyncTask;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by saily on 15-06-2018.
 */

public class Router extends AsyncTask<JSONObject,Void,JSONObject> {

    int status;
    String data = "";
    String dataParsed = "";



    @Override
    protected JSONObject doInBackground(JSONObject... HttpRequestJSON) {


        JSONObject HttpResponseJSON = null;
        String methodName = "POST";

        //server to be connected
        final String baseurl = "https://external.dev.pheramor.com/";






        try
        {
            //connection
            URL url = new URL(baseurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);

            connection.setRequestMethod(methodName);


            connection.setRequestProperty("Content-Type", "application/json");

            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            JSONObject parameters;


            parameters = HttpRequestJSON[0];


            OutputStreamWriter post = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            BufferedWriter writer = new BufferedWriter(post);
            writer.write(parameters.toString());
            writer.flush();
            writer.close();
            post.close();
            connection.connect();
            status = connection.getResponseCode();
            if(status < HttpURLConnection.HTTP_BAD_REQUEST) {
                InputStream inputStream = connection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                dataParsed = bufferedReader.readLine();
                if(dataParsed != null) {
                    HttpResponseJSON = new JSONObject(dataParsed);

                    return HttpResponseJSON;
                }
            }
            else
            {
                InputStream inputStream = connection.getErrorStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                dataParsed = bufferedReader.readLine();
                HttpResponseJSON = new JSONObject(dataParsed);
                return HttpResponseJSON;

            }

        }catch(MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }

        return null;

    }
    @Override
    protected void onPostExecute(JSONObject aVoid) {
        super.onPostExecute(aVoid);



    }
}
