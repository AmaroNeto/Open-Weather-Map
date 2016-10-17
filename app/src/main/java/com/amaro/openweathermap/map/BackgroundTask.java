package com.amaro.openweathermap.map;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.amaro.openweathermap.R;
import com.amaro.openweathermap.city.City;
import com.amaro.openweathermap.city.CityListActivity;
import com.amaro.openweathermap.repository.CityController;
import com.amaro.openweathermap.util.OwmVars;
import com.amaro.openweathermap.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
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
    private CityController cityController;

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

        cityController = CityController.getInstance();
        cityController.cleanCities();

    }


    @Override
    protected Boolean doInBackground(Void... params) {




        // try to load XML from HTTP server and parse

        trustEveryone();
        final String USER_AGENT = "Mozilla/5.0";

        try {
            //String host= "http://api.openweathermap.org/data/2.5/find?lat=9.199226759834747&lon=38.94587405025959&cnt=15&APPID=dde59122af8801206943afb72b61cd89";
            String host = "http://api.openweathermap.org/data/2.5/find?lat="+lat+"&lon="+lng+"&cnt=15&units=metric&APPID="+ OwmVars.owmAppId;
           //String host = URLEncoder.encode("http://api.openweathermap.org/data/2.5/find?lat="+lat+"&lon="+lng+"&cnt=15&APPID="+ OwmVars.owmAppId+"/","UTF-8");
            URL obj = new URL(host);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", USER_AGENT);
            //con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            // Send GET request
            /*con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.flush();
            wr.close();*/

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

                JSONArray list = result.getJSONArray("list");

                for(int i = 0; i < list.length(); i ++){

                    JSONObject city = list.getJSONObject(i);

                    //Nome do Local
                    String id = city.getString("id");
                    String name = city.getString("name");

                    //Dados de Temperatura
                    JSONObject temperature = city.getJSONObject("main");

                    //Temperatura - min/media/max
                    Double temp = temperature.getDouble("temp");
                    Double temp_min = temperature.getDouble("temp_min");
                    Double temp_max = temperature.getDouble("temp_max");

                    //Weather
                    JSONArray weather = city.getJSONArray("weather");
                    String main = weather.getJSONObject(0).getString("main");
                    String description = weather.getJSONObject(0).getString("description");
                    String icon = weather.getJSONObject(0).getString("icon");

                    //Criação dos Objetos
                    City cityObj = new City();
                    cityObj.setId(id);
                    cityObj.setName(name);
                    cityObj.setLat(lat);
                    cityObj.setLng(lng);
                    cityObj.setTemp(temp);
                    cityObj.setMax_temp(temp_max);
                    cityObj.setMin_temp(temp_min);
                    cityObj.setDescription(description);
                    cityObj.setTitle_description(main);
                    cityObj.setIcon(Util.iconStringToIconInt(icon));

                    cityController.addCity(cityObj);
                    //Log.d("OWM", "city : " + city );
                    //Log.d("OWM", "temp : " + temp +" cel: "+Util.FahrenheitToCelsius(temp));
                }


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

        //Vai para a tela de lista de cidades
        if(parsingError){
            Intent it = new Intent(ctx, CityListActivity.class);
            ctx.startActivity(it);
        }else{
            Toast.makeText(ctx,ctx.getString(R.string.error_download_data),Toast.LENGTH_LONG).show();
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
