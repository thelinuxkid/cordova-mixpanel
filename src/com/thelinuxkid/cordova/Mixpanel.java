package com.thelinuxkid.cordova.mixpanel;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.LOG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

public class Mixpanel extends CordovaPlugin {
    private static String TAG = "Mixpanel";
    private static String NO = "";

    private MixpanelAPI mixpanel;

    @Override
    public boolean execute(
        String action,
        JSONArray args,
        final CallbackContext callbackContext) throws JSONException {
        try {
            // TODO use android.text.TextUtil.isEmpty to check for
            // empty strings and obj.isEmpty() to check for empty
            // JSONObjects.
            if (action.equals("init")) {
                if (args.optString(0, NO) == NO)  {
                    this.error("init token not provided");
                    return false;
                }
                String token = args.getString(0);
                Context ctx = cordova.getContext();
                if (this.mixpanel == null) {
                    this.mixpanel =  MixpanelAPI.getInstance(ctx, token);
                }
                callbackContext.success();
                return true;
            } else if (this.mixpanel == null) {
                this.error("mixpanel is not initialized");
                return false;
            }
            if (action.equals("alias")) {
                if (args.optString(0, NO) == NO)  {
                    this.error("alias id not provided");
                    return false;
                }
                if (args.optString(0, NO) == NO)  {
                    this.error("alias original id provided");
                    return false;
                }
                String alias = args.getString(0);
                String original = args.getString(1);
                this.mixpanel.alias(alias, original);
                CallbackContext.success();
                return true;
            }
            if (action.equals("identify")) {
                if (args.optString(0, NO) == NO)  {
                    this.error("identify distinct id not provided");
                    return false;
                }
                String id = args.getString(0);
                this.mixpanel.identify(id);
                CallbackContext.success();
                return true;
            }
            if (action.equals("timeEvent")) {
                if (args.optString(0, NO) == NO)  {
                    this.error("timeEvent event name not provided");
                    return false;
                }
                String event = args.getString(0);
                this.mixpanel.timeEvent(event);
                CallbackContext.success();
                return true;
            }
            if (action.equals("track")) {
                if (args.optString(0, NO) == NO)  {
                    this.error("track event name not provided");
                    return false;
                }
                if (args.optJSONObject(1) == null) {
                    this.error("track properties not provided");
                    return false;
                }
                String event = args.getString(0);
                JSONObject props = args.getJSONObject(1);
                this.mixpanel.track(event, props);
                CallbackContext.success();
                return true;
            }
            if (action.equals("flush")) {
                public void run() {
                    this.mixpanel.flush();
                    callbackContext.success();
                }
                cordova.getThreadPool().execute(new Runnable() {run});
                return true;
            }
            if (action.equals("super_properties")) {
                JSONObject props = this.mixpanel.getSuperProperties();
                CallbackContext.success(props);
                return true;
            }
            if (action.equals("distinct_id")) {
                String id = this.mixpanel.getDistinctId();
                CallbackContext.success(id);
                return true;
            }
            if (action.equals("register")) {
                if (args.optJSONObject(0) == null) {
                    this.error("register properties not provided");
                    return false;
                }
                JSONObject props = args.getJSONObject(0);
                this.mixpanel.registerSuperProperties(props);
                CallbackContext.success();
                return true;
            }
            if (action.equals("unregister")) {
                if (args.optString(0, NO) == NO)  {
                    this.error("unregister property not provided");
                    return false;
                }
                String prop = args.getString(0);
                this.mixpanel.unregisterSuperProperty(prop);
                CallbackContext.success();
                return true;
            }
            if (action.equals("register_once")) {
                if (args.optJSONObject(0) == null) {
                    this.error("register once properties not provided");
                    return false;
                }
                JSONObject props = args.getJSONObject(0);
                this.mixpanel.registerSuperPropertiesOnce(props);
                CallbackContext.success();
                return true;
            }
            if (action.equals("clear")) {
                this.mixpanel.clearSuperProperties();
                CallbackContext.success();
                return true;
            }
        } catch (final Exception e) {
            callbackContext.error(e.getMessage());
        }
        return false;
    }

    @Override
    public void onDestroy() {
        this.mixpanel.flush();
        super.onDestroy();
    }

    @Override
    public void onPause(boolean multitasking) {
        // TODO
    }

    @Override
    public void onResume(boolean multitasking) {
        // TODO
    }

    @Override
    public void onReset() {
        // TODO
    }

    private void error(String message) {
        LOG.e(TAG, message);
        callbackContext.error(message);
    }
}
