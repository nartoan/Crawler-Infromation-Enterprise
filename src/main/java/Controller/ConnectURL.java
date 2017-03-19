package Controller;

import Model.Data;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by toan on 3/18/2017.
 */
public class ConnectURL {
    public static Document connect(String URL) {
        try {
           // System.out.println(URL);
            Connection connection = Jsoup.connect(URL)
                    .userAgent(Data.USER_ARGENT);

            Connection.Response response = connection.execute();
            if (response.statusCode() == 200) {
                return connection.get();
            }
        } catch (SocketTimeoutException ste) {
//            ste.printStackTrace();
            System.out.println("Time out : " + URL);
        } catch (IOException e) {
//            e.printStackTrace();
            System.out.println("500 : " + URL);
        }
        return null;
    }
}
