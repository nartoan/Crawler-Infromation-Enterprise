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
        parserPage.parser();
    }

    public ParserPage() {
        url_page = "";
        urls = new ArrayList<String>();
    }

    public ParserPage(String url_page) {
        this.url_page = url_page;
        urls = new ArrayList<String>();
    }


    public void parser() throws IOException {
        Document document = Jsoup.connect(url_page).get();

        urls.addAll(getURLDetail(document));
        int count = this.getCountOfPage(document);
        for (int i = 2; i <= count; i++) {
            document = Jsoup.connect(url_page + "?p=" + i).get();
            urls.addAll(getURLDetail(document));
        }
        System.out.println(urls.size());

        GetInforEnterprise getInforEnterprise = new GetInforEnterprise();

        for (String url : urls) {
            getInforEnterprise.setUrl_page("https://thongtindoanhnghiep.co/" + url);
            if (getInforEnterprise.filter("xuất nhập khẩu"))
                System.out.println(getInforEnterprise.getEnterprise().getName() + "\n");
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
