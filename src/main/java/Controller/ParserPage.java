package Controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DucToan on 15/03/2017.
 * <p>
 * Parser page to get url about detail Enterprise and get all of Page
 */
public class ParserPage {
    private String url_page = "";
    private List<String> urls;

    public static void main(String[] args) throws IOException {
        ParserPage parserPage = new ParserPage("https://thongtindoanhnghiep.co/tp-ho-chi-minh/huyen-binh-chanh/xa-binh-hung");
        // ParserPage parserPage = new ParserPage("https://thongtindoanhnghiep.co/tp-ho-chi-minh/huyen-binh-chanh/xa-binh-duong");
        // ParserPage parserPage = new ParserPage("https://thongtindoanhnghiep.co/ha-noi/huyen-thach-that/xa-can-kiem");
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


    public void parser(final String keyWord) throws IOException {
        Document document = Jsoup.connect(url_page).get();

        urls.addAll(getURLDetail(document));
        int count = this.getCountOfPage(document);
        for (int i = 2; i <= count; i++) {
            document = Jsoup.connect(url_page + "?p=" + i).get();
            urls.addAll(getURLDetail(document));
        }
        System.out.println(urls.size());

        final GetInforEnterprise getInforEnterprise = new GetInforEnterprise();

        Thread thread = new Thread() {
            public void run() {
                for (String url : urls.subList(0, (int) (urls.size()/4))) {
                    getInforEnterprise.setUrl_page(Data.URL_WEB + url);
                    try {
                        if (getInforEnterprise.filter(keyWord))
                            System.out.println(getInforEnterprise.getEnterprise().getTaxCode() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();


        Thread thread2 = new Thread() {
            public void run() {
                for (String url : urls.subList((int) (urls.size()/4) + 1, (int) urls.size()/2)) {
                    getInforEnterprise.setUrl_page(Data.URL_WEB + url);
                    try {
                        if (getInforEnterprise.filter(keyWord))
                            System.out.println(getInforEnterprise.getEnterprise().getTaxCode() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread2.start();

        Thread thread3 = new Thread() {
            public void run() {
                for (String url : urls.subList((int) (urls.size()/2) + 1, (int) (3*urls.size()/4))) {
                    getInforEnterprise.setUrl_page(Data.URL_WEB + url);
                    try {
                        if (getInforEnterprise.filter(keyWord))
                            System.out.println(getInforEnterprise.getEnterprise().getTaxCode() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread3.start();

        Thread thread4 = new Thread() {
            public void run() {
                for (String url : urls.subList(((int) (urls.size()*3/4)), urls.size()-1)) {
                    getInforEnterprise.setUrl_page(Data.URL_WEB + url);
                    try {
                        if (getInforEnterprise.filter(keyWord))
                            System.out.println(getInforEnterprise.getEnterprise().getTaxCode() + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread4.start();

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
