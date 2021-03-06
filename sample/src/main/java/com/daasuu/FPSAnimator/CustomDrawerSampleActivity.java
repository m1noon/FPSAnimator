package com.daasuu.FPSAnimator;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.daasuu.FPSAnimator.util.UIUtil;
import com.daasuu.library.DisplayObject;
import com.daasuu.library.FPSTextureView;
import com.daasuu.library.drawer.CircleDrawer;
import com.daasuu.library.drawer.CustomDrawer;
import com.daasuu.library.easing.Ease;
import com.daasuu.library.util.Util;

public class CustomDrawerSampleActivity extends AppCompatActivity {

    private FPSTextureView mFPSTextureView;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, CustomDrawerSampleActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_drawer_sample);
        mFPSTextureView = (FPSTextureView) findViewById(R.id.animation_texture_view);

        final Paint paint1 = new Paint();
        paint1.setColor(ContextCompat.getColor(getApplicationContext(), R.color.circle1));
        final Paint paint2 = new Paint();
        paint2.setColor(ContextCompat.getColor(getApplicationContext(), R.color.circle2));
        final Paint paint3 = new Paint();
        paint3.setColor(ContextCompat.getColor(getApplicationContext(), R.color.circle3));

        CustomDrawer customDrawer = new CustomDrawer(new CustomDrawer.CustomDraw() {
            @Override
            public void draw(Canvas canvas, float x, float y, int alpha) {

                paint1.setAlpha(alpha);
                paint2.setAlpha(alpha);
                paint3.setAlpha(alpha);

                canvas.drawCircle(x, y, 100, paint3);
                canvas.drawCircle(x, y, 67, paint2);
                canvas.drawCircle(x, y, 35, paint1);
            }

            @Override
            public float getWidth() {
                return 100;
            }

            @Override
            public float getHeight() {
                return 100;
            }
        });

        DisplayObject displayObject = new DisplayObject();
        displayObject.with(customDrawer)
                .tween()
                .tweenLoop(true)
                .transform(400, 400)
                .to(800, 600, 400, 0, 4f, 4f, 0, Ease.SINE_IN_OUT)
                .waitTime(300)
                .transform(400, 400, Util.convertAlphaFloatToInt(1f), 1f, 1f, 0)
                .waitTime(300)
                .end();

        DisplayObject displayObject2 = new DisplayObject();
        displayObject2.with(customDrawer)
                .parabolic()
                .transform(UIUtil.getWindowWidth(this) / 2, UIUtil.getWindowHeight(this) / 2)
                .leftSide(100)
                .end();

        DisplayObject displayObject3 = new DisplayObject();
        displayObject3.with(customDrawer)
                .parabolic()
                .transform(UIUtil.getWindowWidth(this) / 2, UIUtil.getWindowHeight(this) / 2)
                .accelerationX(-8)
                .leftSide(100)
                .end();


        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        CircleDrawer circleDrawer = new CircleDrawer(paint, 100);
        DisplayObject displayObject4 = new DisplayObject();
        displayObject4.with(circleDrawer)
                .tween()
                .tweenLoop(true)
                .to(3000, 300, 500)
                .scale(1000, 3, 4)
                .scale(1000, 1, 1)
                .to(3000, 0, 0)
                .end();


        mFPSTextureView
                .addChild(displayObject2)
                .addChild(displayObject3)
                .addChild(displayObject4)
                .addChild(displayObject);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mFPSTextureView.tickStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mFPSTextureView.tickStop();
    }
}
