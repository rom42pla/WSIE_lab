package com.wsie.lab.maven.lab;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class SimpleCrawler_Prova_Segnala implements Crawler {
    public static void main(String args[]) throws IOException {

        Fetcher fetcher = new SimpleFetcher();
        StorageWithFiles storage = new SimpleStorageWithFiles();
        URLManager urlManager = new SimpleURLManager();

        // PAGINE DELLE SEGNALAZIONI
        //String url = "http://www.romatoday.it/social/segnalazioni/";

        // PAGINA DI UNA SEGNALAZIONE
        String url = "http://www.romatoday.it/social/segnalazioni/cumuli-di-immondizia-gettati-in-strada-da-incivili.html";
        
        Document doc = Jsoup.connect(url).get();

        Element body = doc.body();

        


        // GET CONTENT of REPORT
        Element article = doc.body();
        String title = article.select(".entry-title").text();
        String description = article.select(".segnalazione_inside_txt").select("p").text();
        String date = null;
        String location = null;
        String HTML = doc.toString();
        

        //System.out.printf("\nHTML: %s\n", HTML);
        System.out.printf("\nTitle: %s\n", title);
        System.out.printf("\nContent: %s\n", description);


        // Provo ad ottenere la data
        date = article.select(".datestamp").text();

        System.out.printf("\nDate: %s\n", date) ;

        // ... e il posto
        location =  article.select(".map-address").text();
        String[] location_to_Arr = location.split("·");

        System.out.printf("\nStreet address: %s\nCity: %s", location_to_Arr[0],location_to_Arr[1]) ;

        // ANALYZING THE PAGES OF REPORTS

        // GET ELEMENTS BY SELECT
        Elements articles = body.select("article");


        //Elements h3 = articles.select(".card-heading"); // Gets the elements whose class is "card-heading"
        //Elements h3 = articles.select(".card-heading > a"); // '>' indicates that i only want the 'a' tags in the child (not grandchild/dren)
        //Elements h3 = articles.select(".card-heading > a[href*=via]"); // gets only the href containg the word "via"

        
        // GET ELEMENTS BY CLASS
        /*
        for (Element child : body.getElementsByClass("card-heading")){ // Ottengo tutti i titoli delle Card per ciascuna segnalazione
            System.out.println(child.text());
            System.out.println();
        }
        */

        // GET ELEMENTS BY TAG
        /*
          Elements desiredElements = body.getElementsByTag("article");
        
        for (Element article : desiredElements) {
            Element h3 = article.getElementsByClass("card-heading").get(0); // We grab the first element in block__heading class (in this case the title)
            Element location = article.getElementsByClass("location-label").get(0);
            Element author = article.getElementsByClass("author-name").get(0);

            Elements aTag_author = author.getElementsByTag("a");
            String Link_author = aTag_author.attr("href"); //get the relative path to the author page inside http://www.romatoday.it

            //System.out.println(aTag_author.attr("href"));
            //System.out.println(author.getElementsByTag("a").attr("href"));
            //System.out.println(h3); stampa l'outer e l'inner HTML 
            //System.out.printf("Title: %s\nLocation: %s\nAuthor: %s\n\n", h3.text(),location.text(),author.text());   // Stampa direttamente il testo inserito come titolo e come luogo della segnalazione
                                                                                            // Più precisamente, text() accumula tutto il testo dell'elemento h3 e di tutti i suoi figli
        }
        */
      


        
    }
}