package at.nachrichten.newsapp.async;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

/**
 * Created by Harald on 07.12.2017.
 */

public class TickerarticleHandler extends TickerHandler {

    private Document jdoc;
    private boolean fetched;
    private static String link = "http://www.nachrichten.at";

    public static String getContent() {
        return content;
    }

    private static String content;
    public TickerarticleHandler(){

    }
    //TODO: correct search statements and exception handling if nothing found
    @Override
    protected String doInBackground(String... strings) {
        fetched = false;
            try {
                jdoc = Jsoup.connect(link).get();
                Elements contentTag = jdoc.select("div").attr("class", "artikeldetail");
                Elements artikelHead = contentTag.select("h2").attr("class", "textsize artikeldetailhead_title");
                Elements leadText = contentTag.select("h3").attr("class", "leadtext textsize");
                Elements mainContentTag = contentTag.select("p");
                List<String> mainContentText = mainContentTag.eachText();
                List<String> contentText = contentTag.eachText();
                content = contentText.get(0);
                fetched = !fetched;
            } catch (IOException e1) {
                fetched = false;
                e1.printStackTrace();
            }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        fetched = true;
    }

    public static void setUpCorrectLink(String content){
         link += TickerHandler.getContentToLink().get(content);
    }
}
