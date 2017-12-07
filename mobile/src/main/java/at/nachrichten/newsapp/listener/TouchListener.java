package at.nachrichten.newsapp.listener;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import at.nachrichten.newsapp.R;
import at.nachrichten.newsapp.Home;
import at.nachrichten.newsapp.async.Speak;
import at.nachrichten.newsapp.messages.Messages;

/**
 * Created by hei on 20.10.2017.
 * This class gives Motions to an GestureDetector with onTouch() method
 * onTouch method gives Motions to the GestureDetector which handles Gestures
 * on initialization of the TouchListener, a GestureDetector is initialized with the current context and
 * with a extended GestureListener which holds the current TouchListener
 * This is necessary, because the GestureListener needs to access the view -> navigationComponent,
 * and Speak needs the current context.
 * and getDetector().onTouchEvent(motionEvent); does not offers a parameter to pass the view
 */

public class TouchListener implements View.OnTouchListener {

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
        this.gDetector = new GestureDetector(this.context, new GestureListener(getTouchListener()));
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
        Log.i(Messages.LOG_TAG_TouchListener, "onTouch: ");
        return getDetector().onTouchEvent(motionEvent);
    }
}

class GestureListener extends GestureDetector.SimpleOnGestureListener {
    //TODO: gesture handling. swipes, intro etc
    private TouchListener currTouchListener;
    private Speak speak;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;

    GestureListener() {
        super();
    }

    GestureListener(TouchListener touchListener) {
        super();
        this.currTouchListener = touchListener;
        this.speak = new Speak(currTouchListener.getContext());
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2,
                           float velocityX, float velocityY) {
        Log.i(Messages.LOG_TAG_GestureListener, "onFling: " + e1.toString() + e2.toString());
        boolean result = false;
        try {
            /*needs custom drag() method*/
            drag();
            float diffY = e2.getY() - e1.getY();
            float diffX = e2.getX() - e1.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        onSwipeRight();
                    } else {
                        onSwipeLeft();
                    }
                    result = true;
                }
            } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    onSwipeBottom();
                } else {
                    onSwipeTop();
                }
                result = true;
            }
        } catch (Exception exception) {
            currTouchListener.getView().setVisibility(View.VISIBLE);
            Log.i(Messages.LOG_TAG_GestureListener, "exception during onFling: " + e1.toString() + e2.toString());
            exception.printStackTrace();
        }
        return result;

    }

    public void onSwipeTop() {
        Log.i(Messages.LOG_TAG_GestureListener, "onSwipeTop: ");
        currTouchListener.getView().setVisibility(View.VISIBLE);
    }

    public void onSwipeRight() {
        Log.i(Messages.LOG_TAG_GestureListener, "onSwipeRight: ");
        currTouchListener.getView().setVisibility(View.VISIBLE);
    }

    public void onSwipeLeft() {
        Log.i(Messages.LOG_TAG_GestureListener, "onSwipeLeft: ");
        currTouchListener.getView().setVisibility(View.VISIBLE);
    }

    public void onSwipeBottom() {
        Log.i(Messages.LOG_TAG_GestureListener, "onSwipeBottom: ");
        currTouchListener.getView().setVisibility(View.VISIBLE);
    }

    @Override
    public void onLongPress(MotionEvent e) {
        Log.i(Messages.LOG_TAG_GestureListener, "onLongPress: " + e.toString());
        drag();
        super.onLongPress(e);
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(Messages.LOG_TAG_GestureListener, "doubleTap: " + e.toString());
        goToHomeScreen();
        return true;
    }


    public void goToHomeScreen() {
        speak.speak(currTouchListener.getContext().getString(R.string.change_to_home));
        Intent intent = new Intent(currTouchListener.getContext(), getHomeScreenClass());
        currTouchListener.getActivity().startActivity(intent);
    }

    private Class getHomeScreenClass() {
        return Home.class;
    }

    private void drag() {
        Log.i(Messages.LOG_TAG_GestureListener, "drag: ");
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                currTouchListener.getView());
        currTouchListener.getView().startDrag(data, shadowBuilder, currTouchListener.getView(), 0);
        currTouchListener.getView().setVisibility(View.INVISIBLE);
    }
}

