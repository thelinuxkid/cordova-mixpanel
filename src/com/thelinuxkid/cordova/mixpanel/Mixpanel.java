package com.thelinuxkid.cordova.mixpanel;


import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.LOG;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;

import com.mixpanel.android.mpmetrics.MixpanelAPI;

public class Mixpanel extends CordovaPlugin {
    private static String NO = "";

    private static MixpanelAPI mixpanel;
    private static MixpanelAPI.People people;

    @Override
    public boolean execute(
        String action,
        JSONArray args,
        final CallbackContext cbCtx) throws JSONException {
        try {
            if (action.equals("init")) {
                String tk = args.optString(0, NO);
                if (TextUtils.isEmpty(tk))  {
                    this.error(cbCtx, "init token not provided");
                    return false;
                }
                Context ctx = cordova.getActivity();
                if (mixpanel == null) {
                    mixpanel =  MixpanelAPI.getInstance(ctx, tk);
                    people = mixpanel.getPeople();
                }
                cbCtx.success();
                return true;
            } else if (mixpanel == null) {
                this.error(cbCtx, "mixpanel is not initialized");
                return false;
            }
            if (action.equals("alias")) {
                String alias = args.optString(0, NO);
                if (TextUtils.isEmpty(alias))  {
                    this.error(cbCtx, "alias id not provided");
                    return false;
                }
                String original = args.optString(1, NO);
                if (TextUtils.isEmpty(original))  {
                    this.error(cbCtx, "alias original id provided");
                    return false;
                }
                mixpanel.alias(alias, original);
                cbCtx.success();
                return true;
            }
            if (action.equals("identify")) {
                String id = args.optString(0, NO);
                if (TextUtils.isEmpty(id))  {
                    this.error(
                        cbCtx,
                        "identify distinct id not provided");
                    return false;
                }
                mixpanel.identify(id);
                cbCtx.success();
                return true;
            }
            if (action.equals("time_event")) {
                String event = args.optString(0, NO);
                if (TextUtils.isEmpty(event))  {
                    this.error(
                        cbCtx,
                        "timeEvent event name not provided");
                    return false;
                }
                mixpanel.timeEvent(event);
                cbCtx.success();
                return true;
            }
            if (action.equals("track")) {
                String event = args.optString(0, NO);
                if (TextUtils.isEmpty(event))  {
                    this.error(
                        cbCtx,
                        "track event name not provided");
                    return false;
                }
                JSONObject props = args.optJSONObject(1) ;
                if (props == null) {
                    this.error(
                        cbCtx,
                        "track properties not provided");
                    return false;
                }
                mixpanel.track(event, props);
                cbCtx.success();
                return true;
            }
            if (action.equals("flush")) {
                Runnable runnable = new Runnable() {
                        @Override
                        public void run() {
                            mixpanel.flush();
                            cbCtx.success();
                        }
                    };
                cordova.getThreadPool().execute(runnable);
                return true;
            }
            if (action.equals("super_properties")) {
                JSONObject props = mixpanel.getSuperProperties();
                cbCtx.success(props);
                return true;
            }
            if (action.equals("distinct_id")) {
                String id = mixpanel.getDistinctId();
                cbCtx.success(id);
                return true;
            }
            if (action.equals("register")) {
                JSONObject props = args.optJSONObject(0);
                if (props == null) {
                    this.error(
                        cbCtx,
                        "register properties not provided");
                    return false;
                }
                mixpanel.registerSuperProperties(props);
                cbCtx.success();
                return true;
            }
            if (action.equals("unregister")) {
                String prop = args.optString(0, NO);
                if (TextUtils.isEmpty(prop))  {
                    this.error(
                        cbCtx,
                        "unregister property not provided");
                    return false;
                }
                mixpanel.unregisterSuperProperty(prop);
                cbCtx.success();
                return true;
            }
            if (action.equals("register_once")) {
                JSONObject props = args.optJSONObject(0);
                if (props == null) {
                    this.error(
                        cbCtx,
                        "register once properties not provided");
                    return false;
                }
                mixpanel.registerSuperPropertiesOnce(props);
                cbCtx.success();
                return true;
            }
            if (action.equals("clear")) {
                mixpanel.clearSuperProperties();
                cbCtx.success();
                return true;
            }
            if (action.equals("reset")) {
                mixpanel.reset();
                cbCtx.success();
                return true;
            }
        } catch (final Exception e) {
            cbCtx.error(e.getMessage());
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

    private void error(CallbackContext cbCtx, String message) {
        LOG.e("Mixpanel", message);
        cbCtx.error(message);
    }

    public class People extends CordovaPlugin {
        @Override
        public boolean execute(
            String action,
            JSONArray args,
            final CallbackContext cbCtx) throws JSONException {
            try {
                if (mixpanel == null || people == null) {
                    this.error(cbCtx, "mixpanel is not initialized");
                }
                if (action.equals("identify")) {
                    String id = args.optString(0, NO);
                    if (TextUtils.isEmpty(id))  {
                        this.error(
                            cbCtx,
                            "identify distinct id not provided");
                        return false;
                    }
                    people.identify(id);
                    cbCtx.success();
                    return true;
                }
                if (action.equals("set")) {
                    JSONObject props = args.optJSONObject(0);
                    if (props == null) {
                        this.error(
                            cbCtx,
                            "set properties not provided");
                        return false;
                    }
                    people.set(props);
                    cbCtx.success();
                    return true;
                }
                if (action.equals("set_once")) {
                    JSONObject props = args.optJSONObject(0);
                    if (props == null) {
                        this.error(
                            cbCtx,
                            "set once properties not provided");
                        return false;
                    }
                    people.setOnce(props);
                    cbCtx.success();
                    return true;
                }
                if (action.equals("increment")) {
                    String prop = args.optString(0, NO);
                    if (TextUtils.isEmpty(prop))  {
                        this.error(
                            cbCtx,
                            "increment property not provided");
                        return false;
                    }
                    Double value = args.optDouble(1);
                    if (Double.isNaN(value))  {
                        this.error(
                            cbCtx,
                            "increment value not provided");
                        return false;
                    }
                    people.increment(prop, value);
                    cbCtx.success();
                    return true;
                }
                if (action.equals("append")) {
                    String prop = args.optString(0, NO);
                    if (TextUtils.isEmpty(prop))  {
                        this.error(
                            cbCtx,
                            "append property not provided");
                        return false;
                    }
                    JSONObject value = args.optJSONObject(1);
                    if (value == null) {
                        this.error(
                            cbCtx,
                            "append value not provided");
                        return false;
                    }
                    people.append(prop, value);
                    cbCtx.success();
                    return true;
                }
                if (action.equals("union")) {
                    String prop = args.optString(0, NO);
                    if (TextUtils.isEmpty(prop))  {
                        this.error(
                            cbCtx,
                            "union property not provided");
                        return false;
                    }
                    JSONArray values = args.optJSONArray(1);
                    if (values == null) {
                        this.error(
                            cbCtx,
                            "union value not provided");
                        return false;
                    }
                    people.union(prop, values);
                    cbCtx.success();
                    return true;
                }
                if (action.equals("unset")) {
                    String prop = args.optString(0, NO);
                    if (TextUtils.isEmpty(prop))  {
                        this.error(
                            cbCtx,
                            "unset property not provided");
                        return false;
                    }
                    people.unset(prop);
                    cbCtx.success();
                    return true;
                }
                if (action.equals("track_charge")) {
                    Double amount = args.optDouble(0);
                    if (Double.isNaN(amount))  {
                        this.error(
                            cbCtx,
                            "track charge amount not provided");
                        return false;
                    }
                    JSONObject props = args.optJSONObject(1);
                    if (props == null) {
                        this.error(
                            cbCtx,
                            "track change properties not provided");
                        return false;
                    }
                    people.trackCharge(amount, props);
                    cbCtx.success();
                    return true;
                }
                if (action.equals("clear_charges")) {
                    people.clearCharges();
                    cbCtx.success();
                    return true;
                }
                if (action.equals("delete_user")) {
                    people.deleteUser();
                    cbCtx.success();
                    return true;
                }
                if (action.equals("init_push_handling")) {
                    String id = args.optString(0, NO);
                    if (TextUtils.isEmpty(id))  {
                        this.error(
                            cbCtx,
                            "init push handling sender id not provided");
                        return false;
                    }
                    people.initPushHandling(id);
                    cbCtx.success();
                    return true;
                }
                if (action.equals("set_push_reg_id")) {
                    String id = args.optString(0, NO);
                    if (TextUtils.isEmpty(id))  {
                        this.error(
                            cbCtx,
                            "set push registration id not provided");
                        return false;
                    }
                    people.setPushRegistrationId(id);
                    cbCtx.success();
                    return true;
                }
                if (action.equals("clear_push_reg_id")) {
                    people.clearPushRegistrationId();
                    cbCtx.success();
                    return true;
                }
                if (action.equals("distinct_id")) {
                    String id = people.getDistinctId();
                    cbCtx.success(id);
                    return true;
                }
            } catch (final Exception e) {
                cbCtx.error(e.getMessage());
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

        private void error(CallbackContext cbCtx, String message) {
            LOG.e("Mixpanel.People", message);
            cbCtx.error(message);
        }

    }
}
