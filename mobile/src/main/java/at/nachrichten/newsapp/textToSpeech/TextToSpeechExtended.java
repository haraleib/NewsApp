package at.nachrichten.newsapp.textToSpeech;

import android.app.Activity;
import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.EditText;

import org.w3c.dom.Text;

import java.util.Locale;

import at.nachrichten.newsapp.Messages;

/**
 * Created by hei on 20.10.2017.
 */

public class TextToSpeechExtended extends TextToSpeech {

    public TextToSpeechExtended (Context context, OnInitListener onInit ){
        super(context, onInit);
    }

    public void firstUseOfApp() {
        // String text = txtText.getText().toString();
        String text = Messages.textToSpeecInitialized;
        this.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void speakOut(String text) {

        // String text = txtText.getText().toString();
        this.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public void onDestroy() {
        // Don't forget to shutdown tts!
        if (this != null) {
            this.stop();
            this.shutdown();
        }
    }
}
