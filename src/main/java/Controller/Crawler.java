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
    public Crawler() {

    }

    public static void main(String[] args) throws IOException {
        Crawler crawler = new Crawler();
        crawler.getInforOfProvince(Data.PROVINCE[0], "Xuất nhập khẩu");

//        for (String temp : Data.PROVINCE) {
//            System.out.println("\"" + temp + "\",");
//        }
////      ArrayList<String> districts = new ArrayList<String>();
//        ArrayList<String> villages = new ArrayList<String>();
//
//        for (String province : provinces) {
//            ArrayList<String> districtsTemp = crawler.getAllChildOfPlace("/tinh-thanh-pho/" + province, province + "/");
//            districts.addAll(districtsTemp);
//
//            for(String district : districtsTemp) {
//                villages.addAll(crawler.getAllChildOfPlace(province + "/" + district, province + "/" + district + "/"));
//            }
//        }
//         crawler.getAllChildOfPlace( "/tinh-thanh-pho/ha-noi", "ha-noi/");
//         crawler.getAllChildOfPlace("ha-noi/huyen-ba-vi", "ha-noi/huyen-ba-vi/");
    }

    public void getInforOfProvince(final String province, final String keyWord) throws IOException {
        final ArrayList<String> districts = this.getAllChildOfPlace("/tinh-thanh-pho/" + province, province + "/");

        for (final String district : districts) {
            new Thread() {
                public void run() {
                    getInforOfDistrict(province, district, keyWord);
                }
            }.start();
        }
    }

    public void getInforOfDistrict(final String province, final String district, final String keyWord) {
        try {
            ArrayList<String> villages = getAllChildOfPlace(province + "/" + district, province + "/" + district + "/");
            for (final String village : villages) {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            new ParserPage(Data.URL_WEB + "/" + province + "/" + district + "/" + village).parser(keyWord);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public final ArrayList<String> getAllChildOfPlace(String place, String key) throws IOException {
        ArrayList<String> childOfPlaces = new ArrayList<String>();
        String URL = Data.URL_WEB + ((place == null || place.equals("")) ? "" : ("/" + place));
//        System.out.println(URL);
        Document document = Jsoup.connect(URL).get();

        Elements links = document.getElementsByTag("a");
        for (Element link : links) {
            if (link.attr("href").contains(key)) {
                String childOfPlace = link.attr("href").replace("/" + key, "");
                if (!childOfPlaces.contains(childOfPlace)) {
                    childOfPlaces.add(childOfPlace);
                }
            }
        }

//        for (String temp : childOfPlaces) {
//            System.out.println(temp + "\n");
//        }

        return childOfPlaces;
    }
}
