package at.nachrichten.newsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;

import at.nachrichten.newsapp.async.Newsfetcher;
import at.nachrichten.newsapp.listener.DragListener;
import at.nachrichten.newsapp.listener.TouchListener;


public class Home extends Activity {
    /**
     * Called when the activity is first created.
     */
    private GestureDetector mDetector;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*Load content for ticker in own thread*/
        new Newsfetcher().execute();

        /*Initialize Listeners*/
        DragListener dragListener = new DragListener(Home.this, R.drawable.shape_droptarget, R.drawable.shape);
        TouchListener touchListener = new TouchListener(Home.this);
        /*Set Listeners*/
        findViewById(R.id.navigationComponent).setOnTouchListener(touchListener);

        findViewById(R.id.Login).setOnDragListener(dragListener);
        findViewById(R.id.News).setOnDragListener(dragListener);
        findViewById(R.id.Register).setOnDragListener(dragListener);
        findViewById(R.id.Ticker).setOnDragListener(dragListener);
    }
}

