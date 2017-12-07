package at.nachrichten.newsapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import at.nachrichten.newsapp.async.TickerarticleHandler;
import at.nachrichten.newsapp.listener.DragListener;
import at.nachrichten.newsapp.listener.DragListenerTicker;
import at.nachrichten.newsapp.listener.TouchListener;

import static android.view.View.VISIBLE;

/**
 * Created by Harald on 07.12.2017.
 */

public class TickerFullArticle extends Activity {

    public TickerFullArticle(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticker);

        DragListener dragListener = new DragListenerTicker(TickerFullArticle.this);
        TouchListener touchListener = new TouchListener(TickerFullArticle.this);
                /*Set Listeners*/
        findViewById(R.id.navigationComponent).setOnTouchListener(touchListener);
        findViewById(R.id.scrollView).setOnDragListener(dragListener); //drag erweitern. nach unten scroll
        findViewById(R.id.Home).setOnDragListener(dragListener);
        findViewById(R.id.textView).setOnDragListener(dragListener);
        findViewById(R.id.textView).setVisibility(VISIBLE);
        CharSequence content = TickerarticleHandler.getContent();
        ((TextView )findViewById(R.id.textView)).setText(content);
    }
}
