package com.hallelu.jah.sundayproject.controller;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
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

        Button facebookButton = (Button)rootView.findViewById(R.id.button_facebook);
        facebookButton.setOnClickListener(new FacebookClickLister());
        Button twitterButton = (Button)rootView.findViewById(R.id.button_twitter);
        twitterButton.setOnClickListener(new TwitterClickLister());
        Button lineButton = (Button)rootView.findViewById(R.id.button_line);
        lineButton.setOnClickListener(new LineClickLister());

        Button backButton = (Button)rootView.findViewById(R.id.button_back);
        backButton.setOnClickListener(new BackClickListener());
    }

    class  FacebookClickLister implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if(isShareAppInstall("com.facebook.katana")){
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setPackage("com.facebook.katana");
                intent.setType("text/plain");
                intent.putExtra( Intent.EXTRA_TEXT, "http://www.yahoo.co.jp/");
                getActivity().startActivity(intent);
            }else{
                shareAppDl("com.facebook.katana");
            }
        }
    }
    class  TwitterClickLister implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String str = "domyaaa";
            String url = "http://twitter.com/share?text=" + str;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            getActivity().startActivity(intent);
        }
    }
    class  LineClickLister implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (!isShareAppInstall("jp.naver.line.android")){
                try {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=jp.naver.line.android"))); // Google Play の Facebook アプリインストールページヘ
                } catch (android.content.ActivityNotFoundException anfe) {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=jp.naver.line.android")));
                }
                return;
            }

            String lineComment = "domya!!";
            try {
                String lineString = "line://msg/text/" + lineComment;
                Intent intent = Intent.parseUri(lineString, Intent.URI_INTENT_SCHEME);
                getActivity().startActivity(intent);
            } catch (Exception e) {
                //LINE投稿失敗
                Log.e("domya","line error001");
            }

        }
    }

    class BackClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Global.sceneManager.pop();
        }
    }


    // アプリがインストールされているかチェック
    private Boolean isShareAppInstall(String packageName){
        try {
            PackageManager pm = getActivity().getPackageManager();
            pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    // アプリが無かったのでGooglePalyに飛ばす
    private void shareAppDl(String packageName){
        Uri uri = Uri.parse("market://details?id="+packageName);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        getActivity().startActivity(intent);
    }
}
