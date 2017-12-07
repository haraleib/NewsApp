package at.nachrichten.newsapp.async;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

public class ContentHandler extends AsyncTask <String, Void, String> {

    public static Document jdoc;
    public static Elements getHeadlineTag() {
        return headlineTag;
    }
    public static List<String> getHeadlineText() {
        return headlineText;
    }
    public static Elements getTimeTag() {
        return timeTag;
    }
    public static List<String> getTimeText() {
        return timeText;
    }
    public static Elements getLinksTag() {
        return linksTag;
    }
    public static List<String> getLinksValue() {
        return linksValue;
    }

    public static Elements headlineTag;
    public static List<String> headlineText;
    public static Elements timeTag;
    public static List<String> timeText;
    public static Elements linksTag;
    public static List<String> linksValue;

    public static boolean executed = false;

    @Override
    protected String doInBackground(String... strings) {
        executed = false;
        try {
            jdoc = Jsoup.connect("http://www.nachrichten.at/nachrichten/ticker/").get();
            headlineTag = jdoc.select("h2");
            headlineText =  headlineTag.eachText();
            timeTag = jdoc.select("span.uticker_time");
            timeText = timeTag.eachText();
            //linksTag = jdoc.select("h2 > a[href]");
            linksTag =   headlineTag.select("a");
            linksValue = linksTag.eachAttr("href");
            // linksValue = linksTag.eachAttr("href");
        } catch (IOException e1) {
            executed = false;
            e1.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        executed = true;
    }


}
