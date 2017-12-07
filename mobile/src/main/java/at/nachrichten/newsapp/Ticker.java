package at.nachrichten.newsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;

import org.jsoup.select.Elements;

import java.util.List;

import at.nachrichten.newsapp.async.Newsfetcher;
import at.nachrichten.newsapp.listener.DragListener;
import at.nachrichten.newsapp.listener.TouchListener;

/**
 * Created by Harald on 06.12.2017.
 */

public class Ticker extends Activity {
    /** Called when the activity is first created. */
    private GestureDetector mDetector;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

      Elements links=  Newsfetcher.getLinksTag();
      List<String> linkText = Newsfetcher.getLinksValue();
      Elements desc = Newsfetcher.getHeadlineTag();
      Elements time = Newsfetcher.getTimeTag();
      List<String> text = Newsfetcher.getHeadlineText();
      List<String> timetext = Newsfetcher.getTimeText();

        /*Initialize Listeners*/
        DragListener dragListener = new DragListener(Ticker.this, R.drawable.shape_droptarget, R.drawable.shape);
        TouchListener touchListener = new TouchListener(Ticker.this);
        /*Set Listeners*/
        findViewById(R.id.navigationComponent).setOnTouchListener(touchListener);

       // findViewById(R.id.Login).setOnDragListener(dragListener);
        findViewById(R.id.News).setOnDragListener(dragListener);
        findViewById(R.id.Register).setOnDragListener(dragListener);
        findViewById(R.id.Ticker).setOnDragListener(dragListener);
    }
}
