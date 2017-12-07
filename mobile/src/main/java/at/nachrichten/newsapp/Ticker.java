package at.nachrichten.newsapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import at.nachrichten.newsapp.async.TickerHandler;
import at.nachrichten.newsapp.listener.DragListenerTicker;
import at.nachrichten.newsapp.listener.TouchListener;
import at.nachrichten.newsapp.messages.Messages;

import static android.view.View.VISIBLE;

/**
 * Created by Harald on 06.12.2017.
 */

public class Ticker extends Activity {
    /**
     * Called when the activity is first created.
     */
    private GestureDetector mDetector;
    private LinearLayout contentLayout;
    private List<TextView> newsFeed;
    private DragListenerTicker dragListener;
    private TouchListener touchListener;
    private HashMap<String, String> buildContentToLink;
    public Ticker() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker);

                /*Initialize Listeners*/
        dragListener = new DragListenerTicker(Ticker.this);
        touchListener = new TouchListener(Ticker.this);

        if (TickerHandler.isExecuted()) {
            createNewsFeed();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    appendTextViewsToLayout();
                }
            });
        }
        /*Set Listeners*/
        findViewById(R.id.navigationComponent).setOnTouchListener(touchListener);

        findViewById(R.id.scrollView).setOnDragListener(dragListener); //drag erweitern. nach unten scroll
        findViewById(R.id.Home).setOnDragListener(dragListener);
    }

    private void appendTextViewsToLayout() {
        contentLayout = (LinearLayout) findViewById(R.id.content);
        Iterator<TextView> iter = newsFeed.iterator();
        while (iter.hasNext()) {
            contentLayout.addView(iter.next());
        }
    }

    private void createNewsFeed() {

        Iterator<String> iterText = TickerHandler.getHeadlineText().iterator();
        Iterator<String> iterTime = TickerHandler.getTimeText().iterator();
        Iterator<String> iterLink = TickerHandler.getLinksValue().iterator();
        newsFeed = new ArrayList<TextView>();
        buildContentToLink = new HashMap<String,String>();
        while (iterText.hasNext() && iterTime.hasNext() && iterLink.hasNext()) {
            TextView addTextView = createNextTextView();
            addTextView.setText(createTextViewText(iterText, iterTime));
            buildContentToLink.put(addTextView.getText().toString(), iterLink.next());
            newsFeed.add(addTextView);
        }
        TickerHandler.setContentToLink(buildContentToLink);
    }

    private TextView createNextTextView() {
        TextView nextView = new TextView(this);
        TextView layout = (TextView) findViewById(R.id.textView);
        nextView.setLayoutParams(layout.getLayoutParams());
        nextView.setBackgroundResource(R.drawable.ticker_newsfeed_border);
        nextView.setTextSize(20);
        nextView.setVisibility(VISIBLE);
        nextView.setOnDragListener(dragListener);
        if (nextView.getParent() != null) {
            ((ViewGroup) nextView.getParent()).removeView(nextView);
        }
        return nextView;
    }

    private CharSequence createTextViewText(Iterator<String> iterText, Iterator<String> iterTime) {
        CharSequence text;
        StringBuilder sb = new StringBuilder();
        sb.append(Messages.TICKER_TIME + iterTime.next() + '\n');
        sb.append(iterText.next());
        text = sb.toString();
        return text;
    }
}