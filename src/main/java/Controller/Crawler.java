package Controller;

import Model.Data;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
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
    }

    public void getInforOfProvince(final String province, final String keyWord) throws IOException {
        final ArrayList<String> districts = this.getAllChildOfPlace("/tinh-thanh-pho/" + province, province + "/");

        for (final String district : districts) {

            new Thread() {
                public void run() {
                   getInforOfDistrict(province, district, keyWord);
//            System.out.println(district);
                }
            }.start();
        }

      //  ExportToExcel.ExportToFileExcel(list, "/" + province + ".xlsx");

    }

    public void getInforOfDistrict(final String province, final String district, final String keyWord) {
        ArrayList<String> villages = getAllChildOfPlace(province + "/" + district, province + "/" + district + "/");
        for (final String village : villages) {
            new Thread() {
                @Override
                public void run() {
                    new ParserPage(Data.URL_WEB + "/" + province + "/" + district + "/" + village)
                            .parser(keyWord, province, district, village);
//                    System.out.println(Data.URL_WEB + "/" + province + "/" + district + "/" + village);
                }
            }.start();
        }
    }


    public final ArrayList<String> getAllChildOfPlace(String place, String key) {
        ArrayList<String> childOfPlaces = new ArrayList<String>();
        String URL = Data.URL_WEB + ((place == null || place.equals("")) ? "" : ("/" + place));

        Document document = ConnectURL.connect(URL);
        if (document != null) {
            Elements links = document.getElementsByTag("a");
            for (Element link : links) {
                if (link.attr("href").contains(key)) {
                    String childOfPlace = link.attr("href").replace("/" + key, "");
                    if (!childOfPlaces.contains(childOfPlace)) {
                        childOfPlaces.add(childOfPlace);
                    }
                }
            }
        }
        return childOfPlaces;
    }
}
