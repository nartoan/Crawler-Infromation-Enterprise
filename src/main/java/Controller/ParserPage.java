package Controller;

import Model.Data;
import Model.Enterprise;
import Model.ExportToExcel;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by DucToan on 15/03/2017.
 * <p>
 * Parser page to get url about detail Enterprise and get all of Page
 */
public class ParserPage {
    private CopyOnWriteArrayList<Enterprise> enterprises = new CopyOnWriteArrayList<Enterprise>();
    private String url_page = "";
    private List<String> urls;

    public static void main(String[] args) {
        ParserPage parserPage = new ParserPage("https://thongtindoanhnghiep.co/ha-nam/thanh-pho-phu-ly/khu-cong-nghiep-chau-son");
        parserPage.parser("Xuất nhập khẩu", "tp-hcm", "huyen-binh-chanh", "binh-hung");
    }

    public ParserPage() {
        url_page = "";
        urls = new ArrayList<String>();
    }

    public ParserPage(String url_page) {
        this.url_page = url_page;
        urls = new ArrayList<String>();
    }


    public void parser(final String keyWord, String province, String district, String village) {
        Document document = ConnectURL.connect(url_page);
        if (document != null) {
            urls.addAll(getURLDetail(document));
            int count = this.getCountOfPage(document);
            for (int i = 2; i <= count; i++) {
                document = ConnectURL.connect(url_page + "?p=" + i);
                if (document != null)
                    urls.addAll(getURLDetail(document));
            }

            if (urls.size() > 3) {
                Thread thread = new Thread() {
                    public void run() {
                        for (String url : urls.subList(0, urls.size() >> 2)) {
                            GetInforEnterprise getInforEnterprise = new GetInforEnterprise();
                            getInforEnterprise.setUrl_page(Data.URL_WEB + "/" + url);
                            Enterprise a = getInforEnterprise.filter(keyWord);
                            if (a != null) {
                                enterprises.add(a);
                            }
                        }
                    }
                };
                thread.start();

                Thread thread2 = new Thread() {
                    public void run() {
                        for (String url : urls.subList((urls.size() >> 2) + 1, urls.size() >> 1)) {
                            GetInforEnterprise getInforEnterprise = new GetInforEnterprise();
                            getInforEnterprise.setUrl_page(Data.URL_WEB + "/" + url);
                            Enterprise a = getInforEnterprise.filter(keyWord);
                            if (a != null) {
                                enterprises.add(a);
                            }
                        }
                    }
                };
                thread2.start();

                Thread thread3 = new Thread() {
                    public void run() {
                        for (String url : urls.subList((urls.size() >> 1) + 1, 3 * urls.size() >> 2)) {
                            GetInforEnterprise getInforEnterprise = new GetInforEnterprise();
                            getInforEnterprise.setUrl_page(Data.URL_WEB + "/" + url);
                            Enterprise a = getInforEnterprise.filter(keyWord);
                            if (a != null) {
                                enterprises.add(a);
                            }
                        }
                    }
                };
                thread3.start();

                Thread thread4 = new Thread() {
                    public void run() {
                        for (String url : urls.subList(urls.size() * 3 >> 2, urls.size() - 1)) {
                            GetInforEnterprise getInforEnterprise = new GetInforEnterprise();
                            getInforEnterprise.setUrl_page(Data.URL_WEB + "/" + url);
                            Enterprise a = getInforEnterprise.filter(keyWord);
                            if (a != null) {
                                enterprises.add(a);
                            }
                        }
                    }
                };
                thread4.start();

                try {
                    thread.join();
                    thread2.join();
                    thread3.join();
                    thread4.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                for (String url : urls) {
                    GetInforEnterprise getInforEnterprise = new GetInforEnterprise();
                    getInforEnterprise.setUrl_page(Data.URL_WEB + "/" + url);
                    Enterprise a = getInforEnterprise.filter(keyWord);
                    if (a != null) {
                        enterprises.add(a);
                    }
                }
            }
            //Write to excel
            System.out.println(enterprises);
            ExportToExcel.ExportToFileExcel(enterprises, province, district , village);
        }
    }

    private List<String> getURLDetail(Document document) {
        List<String> urls = new ArrayList<String>();
        Elements elements = document.getElementsByClass("news-v3 bg-color-white");
        for (Element element : elements) {
            urls.add(element.select("div a:first-child").attr("href").replace("/", ""));
        }

        return urls;
    }

    private int getCountOfPage(Document document) {
        Elements elements = document.getElementsByClass("news-v3 bg-color-white");
        Elements elementTemp = elements.get(elements.size() - 1).nextElementSibling().select("li:last-child a");
        for (Element element : elementTemp) {
            return Integer.parseInt(elementTemp.attr("href").replaceAll(".*=", ""));
        }

        return 0;
    }

    public final void addElement(Enterprise enterprise) {

    }

    public String getUrl_page() {
        return url_page;
    }

    public void setUrl_page(String url_page) {
        this.url_page = url_page;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }
}
