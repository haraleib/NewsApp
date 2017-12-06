package at.nachrichten.newsapp.textToSpeech;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.Locale;

import at.nachrichten.newsapp.Messages;

/**
 * Created by hei on 20.10.2017.
 */

public class Speak {

    private TextToSpeech tts;
    private static boolean introductionDone = false;
    private Context context;

    public TextToSpeech getTts(){
        return tts;
    }

    public Speak(final Context context){
        this.context = context;
        tts = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    Locale language = new Locale(Resources.getSystem().getConfiguration().locale.getLanguage());
                    int result = tts.setLanguage(language);

                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "This Language is not supported");
                    } else {
                        if (!introductionDone) {
                            introduction(context);
                        } else {
                            Log.e("TTS", "Initialized success");
                        }
                    }

                } else {
                    Log.e("TTS", "Initialization Failed!");
                }
            }
        });
    }

    private void introduction(Context context) {
        introductionDone = !introductionDone;
        String text = Messages.textToSpeechInitialized;
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void speak(String text) {
            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        // String text = txtText.getText().toString();
    }

    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}
