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

    public static MixpanelAPI mixpanel;
    public static MixpanelAPI.People people;

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
                    this.error(cbCtx, "alias original id not provided");
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
                people.identify(id);
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
                    // It's OK for an event not to have properties.
                    props = new JSONObject();
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
        LOG.e("Mixpanel", message);
        cbCtx.error(message);
    }
}
