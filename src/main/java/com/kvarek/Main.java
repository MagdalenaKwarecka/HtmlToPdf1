package com.kvarek;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import com.itextpdf.html2pdf.*;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;




public class Main {
    public static void main(String[] args) throws IOException {
        try {
            Document doc = Jsoup.connect
                    ("https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/package-summary.html").get();

            Element classSummary = doc.select("[class=blockList]").get(2);
            Elements classSummaryElements = classSummary.select("a[href]");

            List<String> classNames = new ArrayList<>();

            for (Element link : classSummaryElements) {
                classNames.add(String.valueOf(link.text()));
                classNames.remove("named");
                classNames.remove("Throwable.getStackTrace()");
            }

            int i;
            for (i=0; i<classNames.size(); i++) {
                String url = "https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/"
                        + classNames.get(i) + ".html";
                Document classLink = Jsoup.connect(url).get();
                Element description = classLink.getElementsByClass("contentContainer").first();
                String fileName ="C:/Users/kvare/IdeaProjects/PDF/ClassSummaryElements/"+classNames.get(i)+".pdf";
                HtmlConverter.convertToPdf(String.valueOf(description), new FileOutputStream(fileName));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



