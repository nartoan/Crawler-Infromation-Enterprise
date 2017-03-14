package Controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by DucToan on 13/03/2017.
 */
public class Crawler {
    private String url_web = "https://thongtindoanhnghiep.co";

    public Crawler() {

    }

    public static void main(String[] args) throws IOException {
        Crawler crawler = new Crawler();
        ArrayList<String> provinces = crawler.getAllChildOfPlace("", "tinh-thanh-pho/");
        ArrayList<String> districts = new ArrayList<String>();
        ArrayList<String> villages = new ArrayList<String>();

        for (String province : provinces) {
            ArrayList<String> districtsTemp = crawler.getAllChildOfPlace("/tinh-thanh-pho/" + province, province + "/");
            districts.addAll(districtsTemp);

            for(String district : districtsTemp) {
                villages.addAll(crawler.getAllChildOfPlace(province + "/" + district, province + "/" + district + "/"));
            }
        }
       // crawler.getAllChildOfPlace( "/tinh-thanh-pho/ha-noi", "ha-noi/");
       // crawler.getAllChildOfPlace("ha-noi/huyen-ba-vi", "ha-noi/huyen-ba-vi/");
    }

    private ArrayList<String> getAllChildOfPlace(String place, String keyWord) throws IOException {
        ArrayList<String> childOfPlaces = new ArrayList<String>();
        String URL = url_web + ((place == null || place.equals("")) ? "" : ("/" + place));
        System.out.println(URL);
        Document document = Jsoup.connect(URL).get();

        Elements links = document.getElementsByTag("a");
        for (Element link : links) {
            if (link.attr("href").contains(keyWord)) {
                String childOfPlace = link.attr("href").replace("/" + keyWord, "");
                if (!childOfPlaces.contains(childOfPlace)) {
                    childOfPlaces.add(childOfPlace);
                }
            }
        }

        for (String temp : childOfPlaces) {
            System.out.println(temp + "\n");
        }

        return childOfPlaces;
    }
}
