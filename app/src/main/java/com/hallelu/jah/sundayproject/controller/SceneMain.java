package com.hallelu.jah.sundayproject.controller;

import android.view.View;
import android.widget.Button;

import com.hallelu.jah.sundayproject.R;
import com.hallelu.jah.sundayproject.core.Global;
import com.hallelu.jah.sundayproject.core.SceneBase;

/**
 * Created by domya on 2015/05/09.
 */
public class SceneMain extends SceneBase {


    public SceneMain(){
        layoutId = R.layout.scene_main;
    }

    @Override
    public void init(){

        View rootView = getRootView();

        Button domyabutton = (Button)rootView.findViewById(R.id.main_domya_button);
        domyabutton.setOnClickListener(new goDomyaClickListener());
    }

    class goDomyaClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Global.sceneManager.push(new SceneDomya());
        }
    }
}
