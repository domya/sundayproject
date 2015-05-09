package com.hallelu.jah.sundayproject.core;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;

import com.hallelu.jah.sundayproject.R;

import java.util.ArrayList;

/**
 * Created by domya on 2015/05/09.
 */
public class SceneManager {

    //ちょっとコミットするだけ
    private Activity activity;
    private ArrayList<SceneBase> sceneList;
    private int viewAnimatorId;//大元のLayout。この上にSceneをのせていく。

    public void setup(Activity activity, int id) {
        this.activity = activity;

        sceneList = new ArrayList<SceneBase>();
        this.viewAnimatorId = id;
    }

    public int getSceneCount() {
        return sceneList.size();
    }

    //sceneManagerの再初期化。全てのSceneをfinishして、引数のsceneで初期化する
    public void reset(SceneBase scene) {
        FrameLayout viewFlipper = (FrameLayout)activity.findViewById(viewAnimatorId);

        viewFlipper.removeAllViews();

        View view = (View)activity.getLayoutInflater().inflate(scene.layoutId, null);

        initScene(scene, view);
        viewFlipper.addView(view);

        for( int i = 0; i < sceneList.size();i++) {
            SceneBase oldscene = sceneList.get(sceneList.size() - (i + 1) );
            oldscene.onSceneFinish();
        }

        sceneList = new ArrayList<SceneBase>();
        sceneList.add(scene);
    }

    //今のSceneと引数のSceneを取り替える
    public void change(SceneBase scene) {
        FrameLayout viewFlipper = (FrameLayout)activity.findViewById(viewAnimatorId);
        View view = (View)activity.getLayoutInflater().inflate(scene.layoutId, null);
        initScene(scene, view);

        int pos = sceneList.size() - 1;
        viewFlipper.addView(view);

        SceneBase oldscene = sceneList.get(pos);
        oldscene.onSceneFinish();
        goneSceneView(pos);//visibilityをGONEにする

        View oldView = oldscene.getRootView();

        viewFlipper.removeView(oldView);

        sceneList.set(sceneList.size()-1,scene);
    }

    //今のSceneの上にもう一個Sceneをのっける
    public void push(SceneBase scene) {
        FrameLayout viewFlipper = (FrameLayout)activity.findViewById(viewAnimatorId);
        View view = (View)activity.getLayoutInflater().inflate(scene.layoutId, null);
        initScene(scene, view);
        viewFlipper.addView(view);
        goneSceneView(sceneList.size() - 1);

        sceneList.add(scene);
    }

    //今のSceneを一枚どける
    public void pop() {
        int pos = sceneList.size() - 1;
        FrameLayout viewFlipper = (FrameLayout)activity.findViewById(R.id.main_layout);

        SceneBase targetPopScene = sceneList.get(pos);
        targetPopScene.onSceneFinish();
        viewFlipper.removeView(targetPopScene.getRootView());

        sceneList.remove(pos);

        SceneBase scene = sceneList.get(sceneList.size()-1);
        scene.getRootView().setVisibility(View.VISIBLE);
        scene.onResume();
    }



    // Activityがresumeした時に呼ぶ
    public void onResumeActivity(){
        SceneBase scene = sceneList.get(sceneList.size()-1);
        scene.onResumeActivity();
    }
    // ActivityがPauseした時に呼ぶ
    public void onPauseActivity() {
        SceneBase scene = sceneList.get(sceneList.size()-1);
        scene.onPauseActivity();
    }
    // ActivityがDestoryした時に呼ぶ
    public void onDestoryActivity() {
        SceneBase scene = sceneList.get(sceneList.size()-1);
        scene.onDestoryActivity();
    }


    private void goneSceneView(int no) {
        if(no < 0) return; //シーン番号が0以下ならreturn//////////////////////////
        View oldView = sceneList.get(no).getRootView();
        oldView.setVisibility(View.GONE);
    }




    private void initScene(SceneBase sceneBase, View rootView) {
        sceneBase.setActivity(activity);
        sceneBase.setRootView(rootView);
        sceneBase.setSceneManager(this);
        sceneBase.init();
    }




}
