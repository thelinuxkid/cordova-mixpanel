package org.apache.cordova.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Mixpanel extends CordovaPlugin {
    @Override
    public boolean execute(
        String action,
        JSONArray args,
        final CallbackContext callbackContext) throws JSONException {
        if (action.equals("mixpanel")) {
            String message = args.getString(0);
            this.echo(message, callbackContext);
            return true;
        }
        return false;
    }

    private void mixpanel(
        String message,
        CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
                public void run() {
                    if (context.aborted) {
                        return;
                    }
                    if (message != null && message.length() > 0) {
                        callbackContext.success(message);
                    } else {
                        callbackContext.error(
                            "Expected one non-empty string argument."
                            );
                    }
                }
            });
    }
}
