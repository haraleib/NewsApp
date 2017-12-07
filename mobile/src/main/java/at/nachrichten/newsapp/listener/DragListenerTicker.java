package at.nachrichten.newsapp.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import at.nachrichten.newsapp.TickerFullArticle;
import at.nachrichten.newsapp.async.TickerarticleHandler;

/**
 * Created by Harald on 07.12.2017.
 */

public class DragListenerTicker extends DragListener {

    public DragListenerTicker(Context context) {
        super(context);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        View rootView = getActivity().findViewById(android.R.id.content);
        Context context = getActivity().getBaseContext();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackgroundColor(Color.WHITE);
                if (v instanceof TextView && v != null) {
                    TextView tv = (TextView) v;
                    final String readTextView = tv.getText().toString();
                    getSp().speak(readTextView);
                }
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                v.setBackgroundColor(Color.WHITE);
                break;
            case DragEvent.ACTION_DROP:

                View view = (View) event.getLocalState(); //ImageView
                ViewGroup owner = (ViewGroup) view.getParent(); //FrameLayout
    //if home/back dann dort hin. wenn in Tickerfullarticle dann nicht mehr machen bei content drop. instance of tickerfullarticle
                if (v instanceof TextView) {
                    String key = ((TextView) v).getText().toString();

                    TickerarticleHandler.setUpCorrectLink(key);
                    new TickerarticleHandler().execute();
                    getSp().onDestroy();

                        Intent intent = new Intent(getContext(), TickerFullArticle.class);
                        getActivity().startActivity(intent);

                    view.setVisibility(View.VISIBLE);
                }
                break;
            case DragEvent.ACTION_DRAG_ENDED:
                v.setBackgroundColor(Color.DKGRAY);
            default:
                break;
        }
        return true;
    }
}
