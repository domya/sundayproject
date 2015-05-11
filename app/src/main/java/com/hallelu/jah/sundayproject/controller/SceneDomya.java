package com.hallelu.jah.sundayproject.controller;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.hallelu.jah.sundayproject.R;
import com.hallelu.jah.sundayproject.core.Global;
import com.hallelu.jah.sundayproject.core.SceneBase;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by domya on 2015/05/09.
 */
public class SceneDomya extends SceneBase {

    public SceneDomya() {
        layoutId = R.layout.scene_domya;
    }

    public LoginButton loginButton;

    @Override
    public void init(){

        View rootView = getRootView();

        Log.i("SceneDomya", "init");

        //簡易連携ボタン系
        {
            Button facebookButton = (Button) rootView.findViewById(R.id.button_facebook);
            facebookButton.setOnClickListener(new FacebookClickLister());
            Button twitterButton = (Button) rootView.findViewById(R.id.button_twitter);
            twitterButton.setOnClickListener(new TwitterClickLister());
            Button lineButton = (Button) rootView.findViewById(R.id.button_line);
            lineButton.setOnClickListener(new LineClickLister());
        }
        //戻るボタン
        {
            Button backButton = (Button) rootView.findViewById(R.id.button_back);
            backButton.setOnClickListener(new BackClickListener());
        }
        //show hashボタン
        {
            Button showhashButton = (Button) rootView.findViewById(R.id.button_show_hash);
            showhashButton.setOnClickListener(new ShowhashClickListener());
        }
        //FacebookSDK　Loginボタン
        {
            loginButton = (LoginButton) getRootView().findViewById(R.id.button_facebook_login);
            loginButton.setReadPermissions("user_friends");
            // If using in a fragment
//        loginButton.setFragment(this);
            // Other app specific specialization

            //TODO　LoginのCallbackをログインボタンにregister。LoginManagerに登録する場合はそっちにする。
            // Callback registration
//            loginButton.registerCallback(Global.fbManager.getCallbackManager(), new FacebookCallback<LoginResult>() {
            LoginManager.getInstance().registerCallback(Global.fbManager.getCallbackManager(), new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    // App code
                    Log.d("facebook","logincallback success!!");
//                    LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends"));
                }

                @Override
                public void onCancel() {
                    // App code
                    Log.d("facebook","logincallback cancel!!");
                }

                @Override
                public void onError(FacebookException exception) {
                    // App code
                    Log.d("facebook","logincallback error!!");
                }
            });
        }

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

    class ShowhashClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {

            Log.i("SceneDomya", "ShowhashClickListener");
            try {
                PackageInfo info = getActivity().getPackageManager().getPackageInfo(
                        "com.hallelu.jah.sundayproject", PackageManager.GET_SIGNATURES); //Your            package name here
                for (Signature signature : info.signatures) {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
                }
            } catch (PackageManager.NameNotFoundException e) {
                Log.i("SceneDomya", "ShowhashClickListener");
            } catch (NoSuchAlgorithmException e) {
                Log.i("SceneDomya", "ShowhashClickListener");
            }
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
