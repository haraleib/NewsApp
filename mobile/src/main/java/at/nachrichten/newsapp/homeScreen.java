package at.nachrichten.newsapp;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Locale;

import at.nachrichten.newsapp.listener.DragListener;
import at.nachrichten.newsapp.listener.TouchListener;
import language.LocaleHelper;


public class homeScreen extends Activity {
    /** Called when the activity is first created. */
    
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        /*Initialize Listeners*/
        DragListener dragListener = new DragListener(homeScreen.this, R.drawable.shape_droptarget, R.drawable.shape);
        TouchListener touchListener = new TouchListener(homeScreen.this);
        /*Set Listeners*/
        findViewById(R.id.navigationComponent).setOnTouchListener(touchListener);

        findViewById(R.id.Login).setOnDragListener(dragListener);
        findViewById(R.id.News).setOnDragListener(dragListener);
        findViewById(R.id.Register).setOnDragListener(dragListener);
        findViewById(R.id.Empty).setOnDragListener(dragListener);
    }
}

