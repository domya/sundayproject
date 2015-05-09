package com.hallelu.jah.sundayproject.controller;

import android.view.View;
import android.widget.Button;

import com.hallelu.jah.sundayproject.R;
import com.hallelu.jah.sundayproject.core.Global;
import com.hallelu.jah.sundayproject.core.SceneBase;

/**
 * Created by domya on 2015/05/09.
 */
public class SceneDomya extends SceneBase {

    public SceneDomya() {
        layoutId = R.layout.scene_domya;
    }

    @Override
    public void init(){

        View rootView = getRootView();

        Button backButton = (Button)rootView.findViewById(R.id.button_back);
        backButton.setOnClickListener(new backClickListener());
    }

    class backClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Global.sceneManager.pop();
        }
    }

}
