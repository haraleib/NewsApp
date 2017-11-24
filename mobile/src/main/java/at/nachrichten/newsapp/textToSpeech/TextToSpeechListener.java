package at.nachrichten.newsapp.textToSpeech;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.Locale;

/**
 * Created by hei on 20.10.2017.
 */

public class TextToSpeechListener implements TextToSpeech.OnInitListener {

    private Context context;
    private TextToSpeech tts;
    private EditText editText;

    public TextToSpeechListener(Context context){
        tts = new TextToSpeech(context, this);
        this.context = context;
    }

    public TextToSpeechListener(Context context, EditText editText){
        this.tts = new TextToSpeech(context, this);
        this.context = context;
        this.editText = editText;
    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {

            int result = tts.setLanguage(Locale.US);

            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            } else {
                speakOut();
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }

    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    public void speakOut() {

        String text = editText.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void speakOut(EditText textFromField) {

        String text = textFromField.getText().toString();

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    public void speakOut(String text) {

        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
}
