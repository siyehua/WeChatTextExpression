package com.siyehua.wechattextexpression;/**
 * Created by huangxk on 2016/10/28.
 */

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * @method
 * @pram
 * @return
 */
public class ScaleVieoView extends VideoView {
    public ScaleVieoView(Context context) {
        super(context);
    }

    public ScaleVieoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScaleVieoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ScaleVieoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

@Override
protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //Log.i("@@@@", "onMeasure");
        int width = getDefaultSize(getWidth(), widthMeasureSpec);
        int height = getDefaultSize(getHeight(), heightMeasureSpec);
        /**//*if (mVideoWidth > 0 && mVideoHeight > 0) {
    if ( mVideoWidth * height  > width * mVideoHeight ) {
        //Log.i("@@@", "image too tall, correcting");
        height = width * mVideoHeight / mVideoWidth;
     } else if ( mVideoWidth * height  < width * mVideoHeight ) {
         //Log.i("@@@", "image too wide, correcting");
         width = height * mVideoWidth / mVideoHeight;
     } else {
         //Log.i("@@@", "aspect ratio is correct: " +
                 //width+"/"+height+"="+
                 //mVideoWidth+"/"+mVideoHeight);
     }
 }*/
       //Log.i("@@@@@@@@@@", "setting size: " + width + 'x' + height);
       setMeasuredDimension(width,height);
   }

}
