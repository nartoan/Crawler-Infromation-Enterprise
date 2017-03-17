package Controller;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by DucToan on 13/03/2017.
 * <p>
 * Get information of Enterprise
 */

public class GetInforEnterprise {
    String url_page = "";

    Enterprise enterprise;

    public static void main(String[] args) throws IOException {
        GetInforEnterprise getInforEnterprise = new GetInforEnterprise("https://thongtindoanhnghiep.co/0302183649-cty-tnhh-san-xuat-thuong-mai-huy-hoang");
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

    public void parserHtml() throws IOException {
        if (url_page != null && !url_page.equals("")) {
            Document document = Jsoup.connect(url_page).get();


            //Get Time update
            Element element = document.getElementsByClass("col-lg-8 col-md-7").get(0);
            enterprise.setTimeUpdate(element.select("div div p:last-child").text());
            System.out.println(enterprise.getTimeUpdate());

            //Get tax code
            element = document.getElementsByClass(
                    "table table-striped table-bordered table-responsive table-details").get(0);
            enterprise.setTaxCode(element.select("table strong").text());
            System.out.println(enterprise.getTaxCode());

            //get name
            enterprise.setName(element.select("table tr:nth-child(2) h3").text());
            System.out.println(enterprise.getName());

            //get type of tax
            enterprise.setTypeOfTax(element.select("table tr:last-child ul").text());
            System.out.println(enterprise.getTypeOfTax());
        }
    }

    public boolean filter(String keyword) throws IOException {
        if (url_page != null && !url_page.equals("")) {

            Document document = Jsoup.connect(url_page).get();
            Element elementTime = document.getElementsByClass("col-lg-8 col-md-7").get(0);
            Element elementTable =document.getElementsByClass(
                    "table table-striped table-bordered table-responsive table-details").get(0);
           // System.out.println(elementTable);
//            System.out.println(elementTable.select("table tr:last-child ul").text());

            if (elementTable.select("table tr:last-child ul").text().contains(keyword)) {
                //Get Time update
//                enterprise.setTimeUpdate(elementTime.select("div div p:last-child").text());
//                System.out.println(enterprise.getTimeUpdate());

                //Get tax code
                enterprise.setTaxCode(elementTable.select("table strong").text());
                System.out.println(enterprise.getTaxCode());

//                //get name
//                enterprise.setName(elementTable.select("table tr:nth-child(2) h3").text());
//                System.out.println(enterprise.getName());
//
//                //get type of tax
//                enterprise.setTypeOfTax(elementTable.select("table tr:last-child ul").text());
//                System.out.println(enterprise.getTypeOfTax());

                return true;
            }
        }
        return false;
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
