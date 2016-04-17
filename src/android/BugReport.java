package io.github.rhildred.cordovabugreport;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Pattern;


public class BugReport extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {

        if (action.equals("report")) {
            String project = data.getString(0);
            final String [] aParts = project.split("/");
            try {

                //Send request not in UI thread
                Thread thread = new Thread(new Runnable(){
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        try {
                            //Your code goes here
                            Process process = Runtime.getRuntime().exec("logcat -d");
                            BufferedReader bufferedReader = new BufferedReader(
                                new InputStreamReader(process.getInputStream()));

                            StringBuilder log = new StringBuilder();
                            String line = "";
                            while ((line = bufferedReader.readLine()) != null) {
                                log.append(line + "\n");
                            }
                            //TextView tv = (TextView) findViewById(R.id.textViewBugReport);
                            //tv.setText(log.toString());
                            URL url;
                            String targetURL = "http://rich-hildred.rhcloud.com/BitBucket";
                            //got this passed in to us
                            String urlParameters =
                                "title=" + URLEncoder.encode("Android Bug Report", "UTF-8") +
                                "&user=" + URLEncoder.encode(aParts[0], "UTF-8") +
                                "&component=" + URLEncoder.encode(aParts[1], "UTF-8") +
                                "&bbAccount=" + URLEncoder.encode("rhildred", "UTF-8") +
                                "&content=" + URLEncoder.encode(log.toString(), "UTF-8");
                            //Create connection
                            url = new URL(targetURL);
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("POST");
                            connection.setRequestProperty("Content-Type",
                                                          "application/x-www-form-urlencoded");

                            connection.setRequestProperty("Content-Length", "" +
                                                          Integer.toString(urlParameters.getBytes().length));
                            connection.setRequestProperty("Content-Language", "en-US");

                            connection.setUseCaches(false);
                            connection.setDoInput(true);
                            connection.setDoOutput(true);

                            DataOutputStream wr = new DataOutputStream(
                                connection.getOutputStream());
                            wr.writeBytes(urlParameters);
                            wr.flush();
                            wr.close();

                            //Get Response
                            InputStream is = connection.getInputStream();
                            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                            String sLine;
                            StringBuffer response = new StringBuffer();
                            while ((sLine = rd.readLine()) != null) {
                                response.append(sLine);
                                response.append('\r');
                            }
                            rd.close();
                            callbackContext.success(response.toString());
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {

                            if (connection != null) {
                                connection.disconnect();
                            }
                        }
                    }
                });
                thread.start();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;

        } else {

            return false;

        }
    }
}
