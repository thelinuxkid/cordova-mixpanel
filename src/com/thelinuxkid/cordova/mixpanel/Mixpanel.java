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
    private static String NO = "";

    private static MixpanelAPI mixpanel;
    private static MixpanelAPI.PeopleImpl people;

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
                String tk = args.getString(0);
                Context ctx = cordova.getContext();
                if (mixpanel == null) {
                    mixpanel =  MixpanelAPI.getInstance(ctx, tk);
                    people = mixpanel.getPeople();
                }
                callbackContext.success();
                return true;
            } else if (mixpanel == null) {
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
                mixpanel.alias(alias, original);
                CallbackContext.success();
                return true;
            }
            if (action.equals("identify")) {
                if (args.optString(0, NO) == NO)  {
                    this.error("identify distinct id not provided");
                    return false;
                }
                String id = args.getString(0);
                mixpanel.identify(id);
                CallbackContext.success();
                return true;
            }
            if (action.equals("time_event")) {
                if (args.optString(0, NO) == NO)  {
                    this.error("timeEvent event name not provided");
                    return false;
                }
                String event = args.getString(0);
                mixpanel.timeEvent(event);
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
                mixpanel.track(event, props);
                CallbackContext.success();
                return true;
            }
            if (action.equals("flush")) {
                public void run() {
                    mixpanel.flush();
                    callbackContext.success();
                }
                cordova.getThreadPool().execute(new Runnable() {run});
                return true;
            }
            if (action.equals("super_properties")) {
                JSONObject props = mixpanel.getSuperProperties();
                CallbackContext.success(props);
                return true;
            }
            if (action.equals("distinct_id")) {
                String id = mixpanel.getDistinctId();
                CallbackContext.success(id);
                return true;
            }
            if (action.equals("register")) {
                if (args.optJSONObject(0) == null) {
                    this.error("register properties not provided");
                    return false;
                }
                JSONObject props = args.getJSONObject(0);
                mixpanel.registerSuperProperties(props);
                CallbackContext.success();
                return true;
            }
            if (action.equals("unregister")) {
                if (args.optString(0, NO) == NO)  {
                    this.error("unregister property not provided");
                    return false;
                }
                String prop = args.getString(0);
                mixpanel.unregisterSuperProperty(prop);
                CallbackContext.success();
                return true;
            }
            if (action.equals("register_once")) {
                if (args.optJSONObject(0) == null) {
                    this.error("register once properties not provided");
                    return false;
                }
                JSONObject props = args.getJSONObject(0);
                mixpanel.registerSuperPropertiesOnce(props);
                CallbackContext.success();
                return true;
            }
            if (action.equals("clear")) {
                mixpanel.clearSuperProperties();
                CallbackContext.success();
                return true;
            }
            if (action.equals("reset")) {
                mixpanel.reset();
                CallbackContext.success();
                return true;
            }
            if (action.equals("device_info")) {
                Map<String, String> info = mixpanel.getDeviceInfo();
                CallbackContext.success(info);
                return true;
            }
        } catch (final Exception e) {
            callbackContext.error(e.getMessage());
        }
        return false;
    }

    @Override
    public void onDestroy() {
        mixpanel.flush();
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
        LOG.e("Mixpanel", message);
        callbackContext.error(message);
    }

    public class People extends CordovaPlugin {
        @Override
        public boolean execute(
            String action,
            JSONArray args,
            final CallbackContext callbackContext) throws JSONException {
            try {
                if (mixpanel == null || people == null) {
                    this.error("mixpanel is not initialized");
                }
                // TODO use android.text.TextUtil.isEmpty to check for
                // empty strings and obj.isEmpty() to check for empty
                // JSONObjects.
                if (action.equals("identify")) {
                    if (args.optString(0, NO) == NO)  {
                        this.error("identify distinct id not provided");
                        return false;
                    }
                    String id = args.getString(0);
                    people.identify(id);
                    CallbackContext.success();
                    return true;
                }
                if (action.equals("set")) {
                    if (args.optJSONObject(1) == null) {
                        this.error("set properties not provided");
                        return false;
                    }
                    JSONObject props = args.getJSONObject(0);
                    people.set(props);
                    CallbackContext.success();
                    return true;
                }
                if (action.equals("set_once")) {
                    if (args.optJSONObject(0) == null) {
                        this.error("set once properties not provided");
                        return false;
                    }
                    JSONObject props = args.getJSONObject(0);
                    people.setOnce(props);
                    CallbackContext.success();
                    return true;
                }
                if (action.equals("increment")) {
                    if (args.optString(0, NO) == NO)  {
                        this.error("increment property not provided");
                        return false;
                    }
                    if (args.optDouble(1))  {
                        this.error("increment value not provided");
                        return false;
                    }
                    String prop = args.getString(0);
                    Double value = args.getDouble(1);
                    people.increment(prop, value);
                    CallbackContext.success();
                    return true;
                }
                if (action.equals("append")) {
                    if (args.optString(0, NO) == NO)  {
                        this.error("append property not provided");
                        return false;
                    }
                    if (args.optJSONObject(1) == null) {
                        this.error("append value not provided");
                        return false;
                    }
                    String prop = args.getString(0);
                    JSONObject value = args.getJSONObject(1);
                    people.append(prop, value);
                    CallbackContext.success();
                    return true;
                }
                if (action.equals("union")) {
                    if (args.optString(0, NO) == NO)  {
                        this.error("union property not provided");
                        return false;
                    }
                    if (args.optJSONArray(1) == null) {
                        this.error("union value not provided");
                        return false;
                    }
                    String prop = args.getString(0);
                    JSONObject value = args.getJSONArray(1);
                    people.union(prop, value);
                    CallbackContext.success();
                    return true;
                }
                if (action.equals("unset")) {
                    if (args.optString(0, NO) == NO)  {
                        this.error("unset property not provided");
                        return false;
                    }
                    String prop = args.getString(0);
                    people.unset(prop);
                    CallbackContext.success();
                    return true;
                }
                if (action.equals("track_charge")) {
                    if (args.optDouble(0))  {
                        this.error(
                            "track charge amount not provided"
                            );
                        return false;
                    }
                    if (args.optJSONObject(1) == null) {
                        this.error(
                            "track change properties not provided"
                            );
                        return false;
                    }
                    String amount = args.getDouble(0);
                    JSONObject props = args.getJSONArray(1);
                    people.trackCharge(amount, props);
                    CallbackContext.success();
                    return true;
                }
                if (action.equals("clear_charges")) {
                    people.clearCharges();
                    CallbackContext.success();
                    return true;
                }
                if (action.equals("delete_user")) {
                    people.deleteUser();
                    CallbackContext.success();
                    return true;
                }
                if (action.equals("init_push_handling")) {
                    if (args.optString(0, NO) == NO)  {
                        this.error(
                            "init push handling sender id not provided"
                            );
                        return false;
                    }
                    String id = args.getString(0);
                    people.initPushHandling(id);
                    CallbackContext.success();
                    return true;
                }
                if (action.equals("set_push_reg_id")) {
                    if (args.optString(0, NO) == NO)  {
                        this.error(
                            "set push registration id not provided"
                            );
                        return false;
                    }
                    String id = args.getString(0);
                    people.setPushRegistrationId(id);
                    CallbackContext.success();
                    return true;
                }
                if (action.equals("clear_push_reg_id")) {
                    people.clearPushRegistrationId();
                    CallbackContext.success();
                    return true;
                }
                if (action.equals("distinct_id")) {
                    String id = people.getDistinctId();
                    CallbackContext.success(id);
                    return true;
                }
            } catch (final Exception e) {
                callbackContext.error(e.getMessage());
            }
            return false;
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
            LOG.e("Mixpanel.People", message);
            callbackContext.error(message);
        }

    }
}
