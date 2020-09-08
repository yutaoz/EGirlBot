package Events;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.logging.Level;

public class AniLink {
    public static String anilink(String query) throws IOException {
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setThrowExceptionOnScriptError(false);
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);

        //--------------------------------------------------------------------------------------------------------------

        String animerushUrl = "https://www.animerush.tv/search.php?searchquery=" + URLEncoder.encode(query, "UTF-8");

        HtmlPage page = client.getPage(animerushUrl);
        HtmlElement itemElement = ((HtmlElement) page.getFirstByXPath("//div[@class='success']//h3"));
        HtmlElement notFound = ((HtmlElement) page.getFirstByXPath("//div[@class='amin_box_mid']"));
        String resulter = itemElement == null ? "nothing found" : itemElement.asText();
        String resulter1 = notFound == null ? "something found" : notFound.asText();
        String output = "";
        if (resulter1.contains("No results")) {
            output = "No results at Animerush";
        }
        else {
            output = "AnimeRush: " + animerushUrl;
        }
        //out.write((output + "\n").getBytes());
        //out.flush();

        //--------------------------------------------------------------------------------------------------------------

        String anime9Url = "https://9anime-tv.com//?s=" + URLEncoder.encode(query, "UTF-8");
        HtmlPage page1 = client.getPage(anime9Url);
        HtmlElement notFound1 = ((HtmlElement) page1.getFirstByXPath("//div[@class='container-wrapper']//h5"));
        String resulter2 = notFound1 == null ? "something found" : notFound1.asText();
        String output1 = "";
        if (resulter2.contains("Sorry, but nothing matched your search terms")) {
            output1 = "No results at 9Anime";
        }
        else {
            output1 = "9Anime: " + anime9Url;
        }
        //out.write((output1 + "\n").getBytes());
        //out.flush();

        //--------------------------------------------------------------------------------------------------------------

        String animedaoUrl = "https://animedao.com/search/?key=" + URLEncoder.encode(query, "UTF-8");
        HtmlPage page2 = client.getPage(animedaoUrl);
        HtmlElement notFound2 = ((HtmlElement) page2.getFirstByXPath("//div[@class='ongoingtitle']//h4"));
        String output2 = notFound2 == null ? "No results at Animedao" : "AnimeDao: " + animedaoUrl;

        //out.write((output2 + "\n").getBytes());
        //out.flush();

        //--------------------------------------------------------------------------------------------------------------

        String animeplanetUrl = "https://www.anime-planet.com/anime/all?name=" + URLEncoder.encode(query, "UTF-8");
        HtmlPage page3 = client.getPage(animeplanetUrl);
        HtmlElement notFound3 = ((HtmlElement) page3.getFirstByXPath("//div[@class='error']//p"));
        String resulter3 = notFound3 == null ? "something found" : notFound3.asText();
        String output3 = "";
        if (resulter3.contains("No results")) {
            output3 = "No results at AnimePlanet";
        }
        else {
            output3 = "AnimePlanet: " + animeplanetUrl;
        }

        String finalOutput = output + "\n" + output1 + "\n" + output2 + "\n" + output3;
        return finalOutput;
    }
}
