package com.mobin.extractionData;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.monitor.SpiderMonitor;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

import javax.management.JMException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hadoop on 3/8/16.
 */
public class ExtractionDataReduce extends Reducer<Text, Text, NullWritable, Text> {

    protected void reduce(Text key, Iterable<Text> values,
                          final Context context)
            throws IOException, InterruptedException {

        ExtractionDataSpider extractionDataSpider = new ExtractionDataSpider();
        List<String> urls = new ArrayList<String>();

        for (Text url : values) {
            urls.add(url.toString());
        }

       Spider.create(new PageProcessor() {
            private Site site = Site.me().setRetryTimes(3).setTimeOut(1000);
            private StringBuffer message;

            public void process(Page page) {

                int size = new JsonPathSelector("$.data.list.results[*]").selectList(
                        page.getRawText()).size();


                for (int i = 0; i < size; i++) {
                    System.out.println(888);
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
                    // pageMessage.add(message.toString());
                    try {
                        context.write(NullWritable.get(), new Text(message.toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }

            public Site getSite() {
                return site;
            }
        }).thread(5).addUrl(urls.toArray(new String[urls.size()])).run();

        // Spider spider = Spider.create(extractionDataSpider).thread(5).addUrl(urls.toArray(new String[urls.size()]));
        // SpiderMonitor.instance().register(spider);
        // spider.start();

        /*int len = extractionDataSpider.getPageMessage().size();
        System.out.println(9999999);
        for (int i = 0; i < len; i++) {
            System.out.println(extractionDataSpider.getPageMessage().get(i));
            context.write(NullWritable.get(), new Text(extractionDataSpider.getPageMessage().get(i)));
        }*/
    }
}
