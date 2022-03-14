
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
//import JSoup Lib
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

public class SoupTestCats {

    public static void main(String[] args) throws IOException {
        
        Document doc = Jsoup.connect("https://www.thinkingoutsidethecage.org/meet-animals/adopt/cats/").get();
        Document humane = Jsoup.connect("https://humaneanimalrescue.org/adopt/?_type=cat").get();

        String allInfo;
        String[] names = new String[100];
        String[] breeds = new String[100];
        String[] ages = new String[100];
        String[] URL = new String[100];
        String[] sex = new String[100];
        int i = 0;

        //Get all divs contain a class named animal-item-content.
        Elements divs = doc.select("div[class=animal-item-content]");

        //print all cat names from inner div.
        for (Element div : divs) {

            //Extract name of cat and print.
            names[i] = div.getElementsByClass("plain").text();

            //Extract breed & age (All Info).
            allInfo = div.getElementsByTag("p").text();

            //Extract breed of cat and print.
            breeds[i] = allInfo.substring(allInfo.indexOf("|") + 2);

            //Extract age of cat and print.
            ages[i] = allInfo.substring(0, allInfo.indexOf("|"));

            //Extract URL to cats page
            URL[i] = div.getElementsByAttribute("href").toString();
            URL[i] = URL[i].substring(URL[i].indexOf("https:"), URL[i].indexOf("/\""));

            //increment.
            i++;
        }
        //Get all divs contain a class named animal-item-content.
        Elements divs2 = humane.select("div[class=wpgb-card-body]");
        //print all cat names from inner div.
        for (Element div : divs2) {

            //Extract name of cat and print.
            names[i] = div.getElementsByAttribute("href").toString();
            names[i] = names[i].substring(names[i].indexOf(">")+1, names[i].indexOf("</"));

            //Extract breed of cat and print.
            breeds[i] = div.getElementsByClass("wpgb-block-1").text();
            
            //extract sex of cat male or female
            sex[i] = div.getElementsByClass("wpgb-block-3").text();
            sex[i] = sex[i].substring(0, sex[i].length() - 1);

            //Extract age of cat and print.
            ages[i] = div.getElementsByClass("wpgb-block-4").text();

            //Extract URL to cats page
            URL[i] = div.getElementsByAttribute("href").toString();
            URL[i] = URL[i].substring(URL[i].indexOf("https:"), URL[i].indexOf("/\""));

            //increment.
            i++;
        }
        

        //New count for number of printed items.
        int counter = 1;
        //Print cats

        try {
            PrintWriter pw = new PrintWriter(new File("C:\\Users\\Jonathan\\Documents\\School\\La Roche\\Spring 2022\\HCI\\cats.csv"));
            StringBuilder sb = new StringBuilder();

            sb.append("ID");
            sb.append(",");
            sb.append("Name");
            sb.append(",");
            sb.append("Breed");
            sb.append(",");
            sb.append("Age");
            sb.append(",");
            sb.append("Sex");
            sb.append(",");
            sb.append("URL");
            sb.append("\n");
            for (int c = 0; c < names.length; c++) {

                //If name is blank exit the loop ( Otherwise it prints null values.
                if (names[c] == null) {
                    break;
                } //Only if the cat is what we are looking for, print it and increment
                else {
                    System.out.println("Cat " + (counter) + " | " + "Name: " + names[c] + " | " + "Breed: " + breeds[c] + " | " + "Age: " + ages[c] + " | " + "Sex: " + sex[c] + "\n" + URL[c] + "\n");
                    sb.append(counter);
                    sb.append(",");
                    sb.append(names[c]);
                    sb.append(",");
                    sb.append(breeds[c]);
                    sb.append(",");
                    sb.append(ages[c]);
                    sb.append(",");
                    sb.append(sex[c]);
                    sb.append(",");
                    sb.append(URL[c]);
                    sb.append("\n");
                    counter++;
                }
            }
            pw.write(sb.toString());
            pw.close();
            System.out.println("Done!");
        } catch (Exception e) {
            System.out.println("File location not found.");
        }

    }

}