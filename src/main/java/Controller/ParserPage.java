package Controller;

import Model.Data;
import Model.Enterprise;
import Model.ExportToExcel;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by DucToan on 15/03/2017.
 * <p>
 * Parser page to get url about detail Enterprise and get all of Page
 */
public class ParserPage {
    private String url_page = "";
    private List<String> urls;

    public static void main(String[] args) {
        ParserPage parserPage = new ParserPage("https://thongtindoanhnghiep.co/tp-ho-chi-minh/huyen-binh-chanh/xa-binh-hung");
        parserPage.parser("Xuất nhập khẩu");
    }

    public ParserPage() {
        url_page = "";
        urls = new ArrayList<String>();
    }

    public ParserPage(String url_page) {
        this.url_page = url_page;
        urls = new ArrayList<String>();
    }


    public  CopyOnWriteArrayList<Enterprise> parser(final String keyWord) {
        Document document = ConnectURL.connect(url_page);
        if (document != null) {
            final CopyOnWriteArrayList<Enterprise> enterprises = new CopyOnWriteArrayList<Enterprise>();
            urls.addAll(getURLDetail(document));
            int count = this.getCountOfPage(document);
            for (int i = 2; i <= count; i++) {
                document = ConnectURL.connect(url_page + "?p=" + i);
                if (document != null)
                    urls.addAll(getURLDetail(document));
            }

            final GetInforEnterprise getInforEnterprise = new GetInforEnterprise();
            if (urls.size() > 500) {
                Thread thread = new Thread() {
                    public void run() {
                        for (String url : urls.subList(0, urls.size() >> 2)) {
                            getInforEnterprise.setUrl_page(Data.URL_WEB + "/" + url);
                            enterprises.add(getInforEnterprise.filter(keyWord));
                        }
                    }
                };
                thread.start();

                Thread thread2 = new Thread() {
                    public void run() {
                        for (String url : urls.subList((urls.size() >> 2) + 1, urls.size() >> 1)) {
                            getInforEnterprise.setUrl_page(Data.URL_WEB + "/" + url);
                            enterprises.add(getInforEnterprise.filter(keyWord));
                        }
                    }
                };
                thread2.start();

                Thread thread3 = new Thread() {
                    public void run() {
                        for (String url : urls.subList((urls.size() >> 1) + 1, 3 * urls.size() >> 2)) {
                            getInforEnterprise.setUrl_page(Data.URL_WEB + "/" + url);
                            enterprises.add(getInforEnterprise.filter(keyWord));
                        }
                    }
                };
                thread3.start();

                Thread thread4 = new Thread() {
                    public void run() {
                        for (String url : urls.subList(urls.size() * 3 >> 2, urls.size() - 1)) {
                            getInforEnterprise.setUrl_page(Data.URL_WEB + "/" + url);
                            enterprises.add(getInforEnterprise.filter(keyWord));
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
                    getInforEnterprise.setUrl_page(Data.URL_WEB + "/" + url);
                    enterprises.add(getInforEnterprise.filter(keyWord));
                }
            }
            return enterprises;
        }

        return null;
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
