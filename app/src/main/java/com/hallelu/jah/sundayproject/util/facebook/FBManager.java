package com.hallelu.jah.sundayproject.util.facebook;

import android.app.Activity;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;

/**
 * Created by domya on 2015/05/11.
 */
public class FBManager {

    private Activity mainActivity;
    private CallbackManager callbackManager;

    public FBManager(Activity mainActivity) {
        this.setMainActivity(mainActivity);
        FacebookSdk.sdkInitialize(mainActivity.getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    public Activity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(Activity mainActivity) {
        this.mainActivity = mainActivity;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public void setCallbackManager(CallbackManager callbackManager) {
        this.callbackManager = callbackManager;
    }
}
