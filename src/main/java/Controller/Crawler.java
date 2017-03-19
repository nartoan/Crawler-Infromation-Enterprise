package Controller;

import Model.Data;
import Model.Enterprise;
import Model.ExportToExcel;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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
        final HashMap<String, CopyOnWriteArrayList<Enterprise>> list =
                new HashMap<String, CopyOnWriteArrayList<Enterprise>>();
        final ArrayList<String> districts = this.getAllChildOfPlace("/tinh-thanh-pho/" + province, province + "/");

        for (final String district : districts) {
            final Thread tempThread = new Thread() {
                public void run() {
                    list.put(district, getInforOfDistrict(province, district, keyWord));
//            System.out.println(district);
                }
            };
            tempThread.start();
            try {
                tempThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        ExportToExcel.ExportToFileExcel(list, "/" + province + ".xlsx");

    }

    public CopyOnWriteArrayList<Enterprise> getInforOfDistrict(final String province, final String district, final String keyWord) {
        final CopyOnWriteArrayList<Enterprise> enterprises = new CopyOnWriteArrayList<Enterprise>();
        ArrayList<String> villages = getAllChildOfPlace(province + "/" + district, province + "/" + district + "/");
        for (final String village : villages) {
            Thread temp = new Thread() {
                @Override
                public void run() {
                    enterprises.addAll(new ParserPage(Data.URL_WEB + "/" + province + "/" + district + "/" + village).parser(keyWord));
//                    System.out.println(Data.URL_WEB + "/" + province + "/" + district + "/" + village);
                }
            };
            temp.start();
            try {
                temp.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return enterprises;
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
