package at.nachrichten.newsapp.listener;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.text.method.Touch;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import at.nachrichten.newsapp.textToSpeech.TextToSpeechExtended;

/**
 * Created by hei on 20.10.2017.
 */

public class TouchListener extends GestureDetector.SimpleOnGestureListener implements View.OnTouchListener {

    private static final String DEBUG_TAG = "Gestures";
    private Context context;
    private TextToSpeechExtended tts;
    private GestureDetector gDetector;
    private View view;

    public TouchListener(){
        super();
    }

    public TouchListener(Context context) {
        this(context, null);
    }

    public TouchListener(Context context, GestureDetector gDetector) {
        this.context = context;
        this.gDetector = gDetector;

        if(gDetector == null)
            gDetector = new GestureDetector(context, this);

        if(tts == null) {
            this.tts = new TextToSpeechExtended(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {

                }
            });
        }
    }

    public GestureDetector getDetector()
    {
        return gDetector;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        super.onLongPress(e);
    }

    @Override
    public boolean onDown(MotionEvent event) {
        Log.d(DEBUG_TAG,"onDown: " + event.toString());
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return super.onSingleTapConfirmed(e);
    }

    @Override
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
