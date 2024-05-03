package org.example.demo1.Parsing;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import static org.example.demo1.FileCreators.CreateFile.writeInFile;
import static org.example.demo1.Helpers.ClearString.clearString;
import static org.example.demo1.JsonProceeder.JsonWriter.writeMapToJSON;


public class WebPageParsing {
    public static void parseWebPage(String linkToParse, String allLinksPath, String goodLinksPath, String badLinksPath, String parsedDataPath) throws IOException {

        Map<String, Map<String, String>> mainMapWebSiteInfo = new HashMap<>();
        Map<String, String> webPageInfo = new HashMap<>();
        writeInFile(allLinksPath, linkToParse);
        Connection.Response response = Jsoup.connect(linkToParse).followRedirects(false).execute();
        System.out.println(response.statusCode() + " : " + response.url());
        response.statusCode();

        if (response.statusCode() == 200 || response.statusCode() == 302 || response.statusCode() == 301) {
            Document doc = Jsoup.connect(linkToParse)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 Edg/123.0.0.0")
                    .get();
            Elements warningMessage = doc.selectXpath("/html/body/div[3]/div/section/div/div[2]");
            String message = warningMessage.text();

            if (message.isEmpty()) {
                try {

                    Elements phoneNumber = doc.selectXpath("//*[@id=\"js-item-page\"]/div[1]/div[7]/div/div/dl[2]/dd/ul/li[1]/a");
                    String phoneNumberValue = phoneNumber.attr("href");
                    Elements onSiteFrom = doc.selectXpath("//*[@id=\"container\"]/div/section/aside/div[1]/div/dl/dd/span");
                    String onSiteFromValue = onSiteFrom.text();
                    Elements ownerName = doc.selectXpath("//*[@id=\"container\"]/div/section/aside/div[1]/div/dl/dd/a");
                    String ownerNameValue = ownerName.text();
                    Elements goodsPrice = doc.selectXpath("//*[@id=\"js-item-page\"]/div[1]/div[7]/div/div/div[1]/ul/li[1]/span[1]");
                    String goodsPriceValue = goodsPrice.text();
                    Elements goodsTitle = doc.selectXpath("//*[@id=\"container\"]/div/section/header/h1");
                    String goodsTitleValue = goodsTitle.text();
                    Elements goodsCountry = doc.selectXpath("//*[@id=\"js-item-page\"]/div[1]/div[7]/div/div/dl[1]/dd[1]");
                    String goodsCountryValue = goodsCountry.text();
                    Elements announcRegion = doc.selectXpath("//*[@id=\"js-item-page\"]/div[1]/div[7]/div/div/dl[1]/dd[2]");
                    String announcRegionValue = announcRegion.text();
                    Elements announcUpdeted = doc.selectXpath("//*[@id=\"container\"]/div/section/aside/div[1]/div/div[1]");
                    String announcUpdetedValue = announcUpdeted.text();
                    Elements announcGroup = doc.selectXpath("//*[@id=\"m__breadcrumbs\"]/li[2]/a/span");
                    String announcGroupValue = announcGroup.text();
                    Elements announcSubGroup = doc.selectXpath("//*[@id=\"m__breadcrumbs\"]/li[3]/a/span");
                    String announcSubGroupValue = announcSubGroup.text();
                    Elements announcType = doc.selectXpath("//*[@id=\"container\"]/div/section/aside/div[1]/div/div[2]");
                    String announcTypeValue = announcType.text();
                    webPageInfo.put("phoneNumber", clearString(phoneNumberValue, ':', 1));
                    webPageInfo.put("onSiteFrom", clearString(onSiteFromValue, ' ', 10));
                    webPageInfo.put("ownerName", ownerNameValue);
                    webPageInfo.put("goodsPrice", goodsPriceValue);
                    webPageInfo.put("goodsTitle", goodsTitleValue);
                    webPageInfo.put("goodsCountry", goodsCountryValue);
                    webPageInfo.put("announcRegion", clearString(announcRegionValue, ',', 1));
                    webPageInfo.put("announcUpdeted", clearString(announcUpdetedValue, ':', 1));
                    webPageInfo.put("announcGroup", announcGroupValue);
                    webPageInfo.put("announcSubGroup", announcSubGroupValue);
                    webPageInfo.put("announcType", clearString(announcTypeValue, ' ', 1));
                    writeInFile(goodLinksPath, linkToParse);
                    mainMapWebSiteInfo.put(clearString(linkToParse, 'd', 4), webPageInfo);
                } catch (NullPointerException ex) {
                    System.out.println("Announcement is not available 1");
                    ex.printStackTrace();
                } catch (HttpStatusException ex) {
                    System.out.println("Announcement is not available 2");
                    // TODO To put the link is file with bad results
                    writeInFile(badLinksPath, linkToParse);
                    System.out.println(ex.getMessage());
                } catch (IOException ex) {
                    System.out.println("Announcement is not available 3");
                    ex.printStackTrace();
                }
            } else {
                writeInFile(badLinksPath, linkToParse);
                System.out.println("Announcement is not available or deleted");
            }
        } else {
            System.out.println("not possible to get page...");
            // TODO To put the link is file with unavailable results
            writeInFile(badLinksPath, linkToParse);
            System.out.println(response.statusMessage());
        }

        writeMapToJSON(mainMapWebSiteInfo, parsedDataPath);
    }
}
