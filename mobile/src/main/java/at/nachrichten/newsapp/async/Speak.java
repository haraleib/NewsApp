package at.nachrichten.newsapp.async;

import at.nachrichten.newsapp.messages.Messages;

import android.content.Context;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

import at.nachrichten.newsapp.R;

/**
 * Created by hei on 20.10.2017.
 * This class converts text to speech.
 * Every class that uses Speak, needs to initialize an Object from this class at first.
 * Usage: Initialise a Speak object. call speak method which calls a new thread who is responsible for
 * get the text to audio output
 */

public class Speak implements Runnable {

    private TextToSpeech tts;
    private Context context;
    private String text;
    private static boolean introductionDone = false;

    public Speak(final Context context) {
        this.context = context;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale language = new Locale(Resources.getSystem().getConfiguration().locale.getLanguage());
                    int result = tts.setLanguage(language);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e(Messages.LOG_TAG_SPEAK, "This Language is not supported");
                    } else {
                        if (!introductionDone) {
                            introductionDone = !introductionDone;
                            text = context.getString(R.string.app_first_use);
                            speak(text);
                        } else {
                            Log.i(Messages.LOG_TAG_SPEAK, "Initialized success");
                        }
                    }

                } else {
                    Log.e(Messages.LOG_TAG_SPEAK, "Initialization Failed!");
                }
            }
        });
    }

    @Override
    public void run() {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void speak(String text) {
        this.text = text;
        run();
    }


    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
