package com.silver.app.kalanama.utility;

import android.os.AsyncTask;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 08/19/2015.
 */
public class ServiceTask extends AsyncTask<NameValuePair, Void, String> {
    public static String serverAddress = "http://91.98.120.196/KalaNamaServer";
    //public static String serviceAddress=serverAddress;
    public static String serviceAddress = serverAddress + "/";
    public Object Data;

    public interface ServiceCallBack {
        void getResult(String result, Object data);
    }

    public ServiceTask(ServiceCallBack callBack) {
        this.callBack = callBack;
    }

    private ServiceCallBack callBack;

    @Override
    protected String doInBackground(NameValuePair... input) {

        return setMessageRead(input);
    }

    // onPostExecute displays the results of the AsyncTask.
    @Override
    protected void onPostExecute(String result) {
        if (result == "IOException") {

        }
        callBack.getResult(result, this.Data);
    }

    private static String convertStreamToUTF8String(InputStream stream) throws IOException {
        String result = "";
        StringBuilder sb = new StringBuilder();
        try {
            InputStreamReader reader = new InputStreamReader(stream, "UTF-8");
            char[] buffer = new char[4096];
            int readedChars = 0;
            while (readedChars != -1) {
                readedChars = reader.read(buffer);
                if (readedChars > 0)
                    sb.append(buffer, 0, readedChars);
            }
            result = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    private String load(String address, List<NameValuePair> params) {
        String result = "";
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(30000);
            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStreamWriter w = new OutputStreamWriter(conn.getOutputStream());
            w.write(getQuery(params));
            w.flush();
            InputStream istream = conn.getInputStream();
            result = convertStreamToUTF8String(istream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
        }

        return result.toString();
    }

    private String setMessageRead(NameValuePair... input) {


        List<NameValuePair> params = new ArrayList<NameValuePair>(input.length - 1);

        for (int i = 1; i < input.length; i++)
            params.add(input[i]);


        return load(serviceAddress + input[0].getValue(), params);
    }//
}
