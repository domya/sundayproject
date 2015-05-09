package com.hallelu.jah.sundayproject.core;

import android.app.Activity;

/**
 * Created by domya on 2015/05/09.
 */
public class Global {

    public static SceneManager sceneManager;

    public static void init(Activity activity, int viewAnimatorId) {
        sceneManager = new SceneManager();
        sceneManager.setup(activity, viewAnimatorId);
    }
}
