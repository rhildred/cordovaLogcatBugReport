package io.github.rhildred.cordovabugreport;

import org.apache.cordova.*;
import org.json.JSONArray;
import org.json.JSONException;

public class BugReport extends CordovaPlugin {

    @Override
    public boolean execute(String action, JSONArray data, CallbackContext callbackContext) throws JSONException {

        if (action.equals("report")) {

            String reportername = data.getString(0);
            String message = "Hello, " + reportername;
            callbackContext.success(message);

            return true;

        } else {
            
            return false;

        }
    }
}
