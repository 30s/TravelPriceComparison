package com.mobin.extractionData;

import java.util.ArrayList;
import java.util.List;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

public class ExtractionDataSpider implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setTimeOut(1000);
    private static StringBuffer message;
    private static List<String> pageMessage = new ArrayList<String>();

    public void process(Page page) {
        int size = new JsonPathSelector("$.data.list.results[*]").selectList(
                page.getRawText()).size();


        for (int i = 0; i < size; i++) {
            message = new StringBuffer();
            message.append(new JsonPathSelector("$.data.list.results[" + i + "].url")
                    .select(page.getRawText()) + "\t")

                    .append(new JsonPathSelector("$.data.qdata.rawdep")
                            .selectList(page.getRawText()) + "\t")

                    .append(new JsonPathSelector("$.data.qdata.realQuery")
                            .selectList(page.getRawText()) + "\t")

                    .append(new JsonPathSelector("$.data.list.results[" + i + "].sights[*]")
                            .selectList(page.getRawText()) + "\t")

                    .append(new JsonPathSelector("$.data.list.results[" + i + "].allDate")
                            .select(page.getRawText()) + "\t")

                    .append(new JsonPathSelector("$.data.list.results[" + i
                            + "].details.hotel").select(page.getRawText()) + "\t")

                    .append(new JsonPathSelector("$.data.list.results[" + i + "].details.tripTime")
                            .select(page.getRawText()) + "\t")


                    .append(new JsonPathSelector("$.data.list.results[" + i + "].totalPrice")
                            .select(page.getRawText()) + "\t")

                    .append(new JsonPathSelector("$.data.list.results[" + i + "].details.traffic")
                            .select(page.getRawText()) + "\t")

                    .append(new JsonPathSelector("$.data.list.results[" + i + "].twoLeveltype")
                            .select(page.getRawText()) + "\t")

                    .append(new JsonPathSelector("$.data.list.results[" + i + "].extensionImg")
                            .select(page.getRawText()) + "\t")

                    .append(new JsonPathSelector("$.data.list.results[" + i + "].summary.supplier.name")
                            .select(page.getRawText()) + "\t");
            pageMessage.add(message.toString());
        }

    }

    public Site getSite() {
        return site;
    }

    public static StringBuffer getMessage() {
        return message;
    }

    public static void setMessage(StringBuffer message) {
        ExtractionDataSpider.message = message;
    }

    public static List<String> getPageMessage() {
        return pageMessage;
    }

    public static void setPageMessage(List<String> pageMessage) {
        ExtractionDataSpider.pageMessage = pageMessage;
    }

}
