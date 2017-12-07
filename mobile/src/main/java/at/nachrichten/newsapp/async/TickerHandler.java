package at.nachrichten.newsapp.async;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Harald on 07.12.2017.
 */

public class TickerHandler extends ContentHandler {
    private static Document jdoc;

    private static List<String> headlineText;
    private static List<String> timeText;
    private static List<String> linksValue;
    private static boolean executed = false;
    private static HashMap<String, String> contentToLink;

    public static HashMap<String, String> getContentToLink() {
        return contentToLink;
    }
    public static void setContentToLink(HashMap<String, String> map) {
        contentToLink = map;
    }
    public static List<String> getHeadlineText() {
        return headlineText;
    }
    public static List<String> getTimeText() {
        return timeText;
    }
    public static List<String> getLinksValue() {
        return linksValue;
    }

    public static boolean isExecuted() {
        return executed;
    }

    @Override
    protected String doInBackground(String... strings) {
        executed = false;
        try {
            jdoc = Jsoup.connect("http://www.nachrichten.at/nachrichten/ticker/").get();
            Elements headlineTag = jdoc.select("h2");
            headlineText = headlineTag.eachText();
            Elements timeTag = jdoc.select("span.uticker_time");
            timeText = timeTag.eachText();
            //linksTag = jdoc.select("h2 > a[href]");
            Elements linksTag = headlineTag.select("a");
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
