package at.nachrichten.newsapp.listener;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.text.method.Touch;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import at.nachrichten.newsapp.R;
import at.nachrichten.newsapp.homeScreen;
import at.nachrichten.newsapp.textToSpeech.Speak;

/**
 * Created by hei on 20.10.2017.
 */

public class TouchListener implements View.OnTouchListener {

    private static final String DEBUG_TAG = "Gestures";
    private Context context;
    private GestureDetector gDetector;
    private View view;

    public TouchListener() {
        super();
    }

    public TouchListener(Context context) {
        this(context, null);
    }

    public TouchListener(Context context, GestureDetector gDetector) {
        this.context = context;
        Activity activity = (Activity) context;
        this.view = activity.findViewById(R.id.navigationComponent);
        this.gDetector = new GestureDetector(this.context, new MyGestureListener(getTouchListener()));
    }

    public View getView() {
        return view;
    }

    public Context getContext() {
        return context;
    }

    public Activity getActivity() {
        return (Activity) context;
    }

    public GestureDetector getgDetector() {
        return gDetector;
    }

    public TouchListener getTouchListener() {
        return this;
    }

    public GestureDetector getDetector() {
        return this.gDetector;
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return getDetector().onTouchEvent(motionEvent);
    }
}

class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
    private static final String DEBUG_TAG = "Gestures";
    private TouchListener tl;
    private Speak sp;

    MyGestureListener() {
        super();
    }

    MyGestureListener(TouchListener touchListener) {
        super();
        this.tl = touchListener;
        this.sp = new Speak(tl.getContext());
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {
        Log.d(DEBUG_TAG, "onFling: " + event1.toString() + event2.toString());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.d(DEBUG_TAG, "onLongPress: " + e.toString());
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                tl.getView());
        tl.getView().startDrag(data, shadowBuilder, tl.getView(), 0);
        tl.getView().setVisibility(View.INVISIBLE);
        super.onLongPress(e);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(DEBUG_TAG, "doubleTap: " + e.toString());
        //  tts.onDestroy();
        sp.speak("Double Tap");
        sp.onDestroy();
        Intent intent = new Intent(tl.getContext(), getHomeScreenClass());
        tl.getActivity().startActivity(intent);
        return true;
    }



    public Class getHomeScreenClass() {
        return homeScreen.class;
    }
}

