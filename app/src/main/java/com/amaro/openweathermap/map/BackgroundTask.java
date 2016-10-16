package com.amaro.openweathermap.map;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.amaro.openweathermap.R;
import com.amaro.openweathermap.util.OwmVars;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

/**
 * Classe que define um tarefa de background - download dos dados de
 *
 * Created by amaro on 16/10/16.
 */

public class BackgroundTask extends AsyncTask<Void, Void, Boolean> {

    ProgressDialog dialog;
    private Context ctx;
    private String lat;
    private String lng;

    public BackgroundTask(Context ctx, String lat, String lng) {
        this.ctx = ctx;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    protected void onPreExecute() {

        dialog = new ProgressDialog(ctx);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setTitle(ctx.getString(R.string.download_data));
        dialog.setMessage(ctx.getString(R.string.wait_a_little));
        dialog.setIndeterminate(true);
        dialog.show();

    }


    @Override
    protected Boolean doInBackground(Void... params) {


        // try to load XML from HTTP server and parse

        trustEveryone();
        final String USER_AGENT = "Mozilla/5.0";

        try {
            String host = "http://api.openweathermap.org/data/2.5/find?lat="+lat+"&lon="+lng+"&cnt=15&APPID="+ OwmVars.owmAppId;
            URL obj = new URL(host);

            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Send GET request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            if (responseCode == 200) {

                JSONObject result = new JSONObject(response.toString());

                Log.d("OWM", "result : " + result );


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (ProtocolException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean parsingError) {

        if (dialog.isShowing()) {
            dialog.dismiss();
        }


    }

    private void trustEveryone() {
        try {
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager(){
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {}
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }}}, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());
        } catch (Exception e) { // should never happen
            e.printStackTrace();
        }
    }
}
