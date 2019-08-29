package com.qm.test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class jsoupNovel {
    private static String pageURL = "";                                         // 第一章
    private static String indexURL = "";                                        // 首章
    private static String eleN1 = "";                                           // name大标签
    private static String eleN2 = "";                                           // name小标签
    private static String eleC1 = "";                                           // content大标签
    private static String eleC2 = "";                                           // content小标签
    private static String fileURL = "C:/Users/Administrator/Desktop/test/2.txt";// 存储URL
    @Test
    public void jNovel() throws IOException {
        String pcAll = "";
        do{
            Element element = getElement();                                         // 获取标签
            String pageName = getPageNameOrContent(element, eleN1, eleN2);         // 获取名称
            String pageContent = getPageNameOrContent(element, eleC1, eleC2);      // 获取内容
            pageURL = getNextPageURL(element);                                      // 获取下一URL
            pcAll += (pageName+pageContent);
        }while (pageURL!=null);
        writeTXT(pcAll);
    }

    private void writeTXT(String pcAll) throws IOException {
        FileOutputStream file = new FileOutputStream(fileURL);
        file.write(pcAll.getBytes());
        file.flush();
    }

    private String getNextPageURL(Element element) {
        String pageURL = null;
        Elements elements = element.select("a[href]");
        for(Element select : elements){
            if (select.toString().indexOf("下一章")!=-1){
                pageURL = select.attr("abs:href");
                break;
            }
        }
        if (!indexURL.equals(pageURL)){
            return pageURL;
        }
        return null;
    }

    private String getPageNameOrContent(Element element, String s, String s1) {
        Elements elements = element.select(s);
        Elements select = elements.select(s1);
        return select.get(0).text();
    }

    private Element getElement() throws IOException {
       return Jsoup.connect(pageURL).userAgent("Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)").get();
    }
}
