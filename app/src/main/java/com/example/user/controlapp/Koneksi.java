package com.example.user.controlapp;

import android.os.StrictMode;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by USER on 13/08/2016.
 */
public class Koneksi {

    private URL url;
    private HttpURLConnection connection;
    //private OutputStream outputPost;

    public void start() throws IOException {
       HttpURLConnection connection = null;
    }

    public String send(String kata) throws UnsupportedEncodingException {
        String hasil="gagal konek";
        try {
            //Create connection
            String urlParameters = "kata=" + URLEncoder.encode(kata, "UTF-8");
            url = new URL("http://192.168.43.44:8000/api/simpan");
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length", "" +Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches (false);
            connection.setDoInput(true);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream (connection.getOutputStream ());
            wr.writeBytes (urlParameters);
            wr.flush ();
            wr.close ();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            String line;
            StringBuffer response = new StringBuffer();
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            hasil = response.toString();

        } catch (Exception e) {

            e.printStackTrace();
            //return null;

        }
        return hasil;
    }

    public void stop() throws IOException {
        if(connection != null) {
            connection.disconnect();
        }
    }

}
