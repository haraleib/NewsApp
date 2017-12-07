package at.nachrichten.newsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;

import at.nachrichten.newsapp.async.TickerHandler;
import at.nachrichten.newsapp.listener.DragListenerHome;
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
        new TickerHandler().execute();

        /*Initialize Listeners*/
        DragListenerHome dragListener = new DragListenerHome(Home.this, R.drawable.shape_droptarget, R.drawable.home_screen_border);
        TouchListener touchListener = new TouchListener(Home.this);
        /*Set Listeners*/
        findViewById(R.id.navigationComponent).setOnTouchListener(touchListener);

        findViewById(R.id.Login).setOnDragListener(dragListener);
        findViewById(R.id.News).setOnDragListener(dragListener);
        findViewById(R.id.Register).setOnDragListener(dragListener);
        findViewById(R.id.Ticker).setOnDragListener(dragListener);
    }
}

