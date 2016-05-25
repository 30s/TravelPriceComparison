package com.mobin.downURL;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

public class DownURLSpider implements PageProcessor {

    private Site site = Site.me();
    private String end;
    private String start;
    private static List<String> allPageURL = new ArrayList<String>();
    private static List<String> legalUrl = new ArrayList<String>();

    public DownURLSpider() {
    }

    public DownURLSpider(String start) {
        this.start = start;
    }

    public Site getSite() {

        return site;
    }

    public void process(Page page) {
        //一个出发点对应一个目的地
        if (page.getUrl().regex("http://dujia.qunar.com/pqkd/list_.*").match()) {
             /*1.出发点正确，目的地错误--------title为null
				 *2.出发点错误，目的地正确或错误----------title不匹配 
				 * */
            String title = page.getHtml().xpath("//title").toString();

            if (title != null && title.contains(start)) {
                end = getMatcher(".*kd/list_([\u4e00-\u9fa5]+)", page.getUrl().toString());
                String url = "http://dujia.qunar.com/golfz/routeList/adaptors/pcTop?&isTouch=0&t=all&f=%E8%B7%9F%E5%9B%A2%E6%B8%B8&o=pop-desc&lm=0%2C20&fhLimit=0%2C20&q=" + end + "&d=" + start + "&s=all&qs_ts=1447834475346&ti=3&tm=l01_all_search_origin&sourcepage=list&qssrc=eyJ0cyI6IjE0NDc4MzQ0NzUzNDYiLCJzcmMiOiJhbGwuZW52YSIsImFjdCI6InNlYXJjaCJ9&m=l%2Clm&displayStatus=pc&lines6To10=0&_=1447834476708";
                page.addTargetRequest(url);//将上面的URL添加到队列中来获取总页数
            }
        } else {//说明是获取总页数的URL

            String recordCount = new JsonPathSelector("$.data.list.numFound")
                    .select(page.getRawText());//总记录数
            //如果总记录数小于20，就只有一页
            int pageCount = (((Integer.valueOf(recordCount)) / 20) < 0) ? 1 : (Integer.valueOf(recordCount)) / 20;
            int len = pageCount >= 6 ? 6 : pageCount;
            int n = 0;
            for (int i = 0; i < len; i++) {
                String currentURL = page.getUrl().toString();//当前的用于获取总页数URL，也是获取其他数据的URL
                String[] recordURL = currentURL.split("lm=0");
                String recordURL_ = recordURL[0] + "lm=" + n + recordURL[1] + "," + start;
                allPageURL.add(recordURL_);//一个出发点-目的地对应的所有页数的数据的URL
                n = n + 20;
            }
        }
    }

    public static String getMatcher(String regex, String source) {
        String result = "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(source);
        while (matcher.find()) {
            result = matcher.group(1);
        }
        return result;
    }

    public static List<String> getAllPageURL() {
        return allPageURL;
    }

    public static void setAllPageURL(List<String> allPageURL) {
        DownURLSpider.allPageURL = allPageURL;
    }

    public static List<String> getLegalUrl() {
        return legalUrl;
    }

    public static void setLegalUrl(List<String> legalUrl) {
        DownURLSpider.legalUrl = legalUrl;
    }


}
