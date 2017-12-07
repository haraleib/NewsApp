package at.nachrichten.newsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.jsoup.select.Elements;

import java.util.List;

import at.nachrichten.newsapp.async.Newsfetcher;
import at.nachrichten.newsapp.listener.DragListener;
import at.nachrichten.newsapp.listener.DragListenerTicker;
import at.nachrichten.newsapp.listener.TouchListener;

/**
 * Created by Harald on 06.12.2017.
 */

public class Ticker extends Activity {
    /** Called when the activity is first created. */
    private GestureDetector mDetector;
    Elements links=  Newsfetcher.getLinksTag();
    List<String> linkText = Newsfetcher.getLinksValue();
    Elements desc = Newsfetcher.getHeadlineTag();
    List<String> text = Newsfetcher.getHeadlineText();
    Elements time = Newsfetcher.getTimeTag();
    List<String> timetext = Newsfetcher.getTimeText();

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker);

        /*Initialize Listeners*/
        DragListenerTicker dragListener = new DragListenerTicker(Ticker.this);
        TouchListener touchListener = new TouchListener(Ticker.this);
        /*Set Listeners*/
        findViewById(R.id.navigationComponent).setOnTouchListener(touchListener);

        findViewById(R.id.scrollView).setOnDragListener(dragListener);
        findViewById(R.id.Home).setOnDragListener(dragListener);
        findViewById(R.id.textView).setOnDragListener(dragListener);

        createNewsFed();
    }

    private void createNewsFed() {
        TextView tv;
        LinearLayout l = (LinearLayout)findViewById(R.id.content);

        while(text.iterator().hasNext()) {
            tv = new TextView(Ticker.this);
            tv = (TextView) findViewById(R.id.textView);
            if(tv.getParent() != null)
            ((ViewGroup) tv.getParent()).removeView(tv);
            CharSequence cs = text.iterator().next();
            tv.setText(cs);
            l.addView(tv);
            break;
        }
    }
}
