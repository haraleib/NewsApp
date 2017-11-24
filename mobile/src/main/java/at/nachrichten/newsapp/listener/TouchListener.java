package at.nachrichten.newsapp.listener;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.text.method.Touch;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by hei on 20.10.2017.
 */

public class TouchListener implements View.OnTouchListener {

    private Context context;
    private TextToSpeech tts;

    public TouchListener(Context context, TextToSpeech tts) {
        this.context = context;
        this.tts = tts;
    }

    private GestureDetector gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {

            return super.onDoubleTap(e);
        }
        @Override
        public boolean onSingleTapConfirmed(MotionEvent event) {
            return false;
        }
    });


    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                    view);
            view.startDrag(data, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        } else {
            return false;
        }
    }
}
