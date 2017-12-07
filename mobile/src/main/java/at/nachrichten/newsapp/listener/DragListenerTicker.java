package at.nachrichten.newsapp.listener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

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

                if (rootView.findViewById(v.getId()) != null) {
                    String textViewName = rootView.getResources().getResourceEntryName(v.getId());
                    int id = getContext().getResources().getIdentifier(textViewName, "id", getContext().getPackageName());
                    if (id != getNO_TEXT_VIEW_ID() && rootView.findViewById(id) instanceof TextView) {
                        TextView tv = (TextView) rootView.findViewById(id);
                        final String readTextView = tv.getText().toString();
                        getSp().speak(readTextView);
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
                    getSp().onDestroy();
                    try {
                        clazz = Class.forName(fullName);
                        //   view.setVisibility(View.VISIBLE);
                        Intent intent = new Intent(getContext(), clazz);
                        getActivity().startActivity(intent);
                    } catch (ClassNotFoundException e) {
                        Log.v("Class not found", e.getMessage());
                        Toast toast = Toast.makeText(getContext(), "Activity Not Found -> Errorin: D ragListener", Toast.LENGTH_SHORT);
                        toast.show();
                        Intent intent = new Intent(getContext(), getContext().getClass());
                        getActivity().startActivity(intent);
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
