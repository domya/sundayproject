package com.hallelu.jah.sundayproject.core;

import android.app.Activity;
import android.view.View;

/**
 * Created by domya on 2015/05/09.
 */
public class SceneBase {

    private Activity activity;
    private View rootView;
    private SceneManager sceneManager;
    public int layoutId = -1;

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void init(){

    }


    // シーンが終了した時に呼ばれる
    public void onSceneFinish(){

    }

    // 他のシーンが稼働してて、戻ってきた時に呼ばれる
    public void onResume(){

    }

    // Activityがresumeした時に呼ぶ
    public void onResumeActivity() {

    }
    // ActivityがPauseした時に呼ぶ
    public void onPauseActivity() {

    }
    // ActivityがDestoryした時に呼ぶ
    public void onDestoryActivity() {

    }
}
