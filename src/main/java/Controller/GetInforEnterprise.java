package Controller;

import Model.Enterprise;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by DucToan on 13/03/2017.
 * <p>
 * Get information of Enterprise
 */

public class GetInforEnterprise {
    private String url_page = "";
    private Enterprise enterprise;

    public static void main(String[] args) throws IOException {
        GetInforEnterprise getInforEnterprise = new GetInforEnterprise("https://thongtindoanhnghiep.co/0105943474-cong-ty-co-phan-thuong-mai-dich-vu-tong-hop-trinh-nguyen");
        //getInforEnterprise.parserHtml();
        getInforEnterprise.filter("Xuất nhập khẩu");
    }

    public GetInforEnterprise() {
        this.url_page = "";
        enterprise = new Enterprise();
    }

    public GetInforEnterprise(String url_page) {
        this.url_page = url_page;
        enterprise = new Enterprise();
    }

    public Enterprise filter(String keyword) {
        if (url_page != null && !url_page.equals("")) {

            Document document = ConnectURL.connect(url_page);
            if (document != null) {
                Element elementTime = document.getElementsByClass("col-lg-8 col-md-7").get(0);
                Element elementTable = document.getElementsByClass(
                        "table table-striped table-bordered table-responsive table-details").get(0);

                if (elementTable.select("table tr:last-child ul").text().contains(keyword)) {
                    //Get Time update
                    enterprise.setTimeUpdate(elementTime.select("div div p:last-child").text());

                    //Get tax code
                    enterprise.setTaxCode(elementTable.select("table strong").text());

                    //get name
                    enterprise.setName(elementTable.select("table tr:nth-child(2) h3").text());

                    //get type of tax
                    enterprise.setTypeOfTax(elementTable.select("table tr:last-child ul").text());

                    //Print
//                    System.out.println(url_page);
//                    System.out.println(enterprise.toString());

                    return enterprise;
                }
            }
        }
        return null;
    }

    public String getUrl_page() {
        return url_page;
    }

    public void setUrl_page(String url_page) {
        this.url_page = url_page;
    }

    public Enterprise getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(Enterprise enterprise) {
        this.enterprise = enterprise;
    }
}
