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

public class People extends CordovaPlugin {
    private static String NO = "";
    private static MixpanelAPI.People people;

    @Override
    public boolean execute(
        String action,
        JSONArray args,
        final CallbackContext cbCtx) throws JSONException {
        try {
            people = Mixpanel.people;
            if (people == null) {
                this.error(cbCtx, "mixpanel is not initialized");
                return false;
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
        super.onPause(multitasking);
    }

    @Override
    public void onResume(boolean multitasking) {
        // TODO
        super.onResume(multitasking);
    }

    @Override
    public void onReset() {
        // TODO
        super.onReset();
    }

    private void error(CallbackContext cbCtx, String message) {
        LOG.e("Mixpanel.People", message);
        cbCtx.error(message);
    }
}
