package at.nachrichten.newsapp.listener;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.speech.tts.TextToSpeech;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import at.nachrichten.newsapp.async.Speak;

/**
 * Created by hei on 20.10.2017.
 */

public class DragListenerHome extends DragListener {

    private Activity activity;
    private Context context;
    private Drawable enterShape;
    private Drawable normalShape;
    private Speak sp;
    private final int NO_TEXT_VIEW_ID = 0;

    public DragListenerHome(Context context, @DrawableRes int RDrawableResource, @DrawableRes int RDrawableResourceNormalShape) {
        this.activity = (Activity) context;
        this.context = context;
        this.enterShape = context.getDrawable(RDrawableResource);
        this.normalShape = context.getDrawable(RDrawableResourceNormalShape);
        this.sp = new Speak(context);
    }

    public DragListenerHome(Context context, @DrawableRes int RDrawableResource, @DrawableRes int RDrawableResourceNormalShape, TextToSpeech tts) {
        this.activity = (Activity) context;
        this.context = context;
        this.enterShape = context.getDrawable(RDrawableResource);
        this.normalShape = context.getDrawable(RDrawableResourceNormalShape);
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        View rootView = activity.findViewById(android.R.id.content);
        Context context = activity.getBaseContext();
        switch (event.getAction()) {
            case DragEvent.ACTION_DRAG_STARTED:
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                v.setBackground(enterShape);

                if (rootView.findViewById(v.getId()) != null) {
                    String textViewName = rootView.getResources().getResourceEntryName(v.getId());
                    int id = this.context.getResources().getIdentifier(textViewName, "id", this.context.getPackageName());
                    if (id != NO_TEXT_VIEW_ID && rootView.findViewById(id) instanceof TextView) {
                        TextView tv = (TextView) rootView.findViewById(id);
                        final String readTextView = tv.getText().toString();
                        sp.speak(readTextView);
                    }

                    break;
                }
            case DragEvent.ACTION_DRAG_EXITED:
                v.setBackgroundColor(Color.DKGRAY);
                break;
            case DragEvent.ACTION_DROP:
                String clazzName = " ";
                String packageName = " ";
                String fullName = " ";
                Class clazz = null;

                View view = (View) event.getLocalState(); //ImageView
                ViewGroup owner = (ViewGroup) view.getParent(); //FrameLayout

                if (v instanceof TextView) {
                    clazzName = rootView.getResources().getResourceEntryName(v.getId());
                    packageName = context.getApplicationContext().getPackageName();
                    fullName = packageName + "." + clazzName;
                    sp.onDestroy();
                    try {
                        clazz = Class.forName(fullName);
                        //   view.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(this.context, clazz);
                        activity.startActivity(intent);
                    } catch (ClassNotFoundException e) {
                        Log.v("Class not found", e.getMessage());
                        Toast toast = Toast.makeText(this.context, "Activity Not Found -> Errorin: D ragListener", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(this.context, this.context.getClass());
                        activity.startActivity(intent);
                    }
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
