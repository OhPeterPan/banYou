package com.banutech.collectiontreasure.ui;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.banutech.collectiontreasure.R;
import com.banutech.collectiontreasure.bean.LoginBean;
import com.banutech.collectiontreasure.util.SpUtil;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {
    @BindView(R.id.ivSplash)
    ImageView ivSplash;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void initData() {
        final LoginBean loginBean = (LoginBean) SpUtil.get(SpUtil.ACCOUNT, LoginBean.class);
        ViewHelper.setAlpha(ivSplash, 0);
        ViewPropertyAnimator.animate(ivSplash)
                .alpha(1f)
                .setDuration(2000)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        Intent intent = new Intent();
                        if (loginBean == null) {
                            intent.setClass(SplashActivity.this, LoginActivity.class);

                        } else {
                            intent.setClass(SplashActivity.this, MainActivity.class);
                        }
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                })
                .start();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onInnerListener(View v) {

    }

}
