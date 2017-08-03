# TravelPriceComparison（旅游比价决策系统）

本系统首先通过webmagic网络爬虫框架并利用MapReduce的任务调度分发机制来达到分布式爬虫，并且通过Quartz任务调度框架定时爬取来自不同旅游网站的网页，然后对网页内容进行抽取和分析，提取其中的关键数据经过比价算法计算后存储到HBase中，Phoenix作为查询引擎为用户提供查询服务，用户输入出发地、出发日期、旅游目的地等条件后， 系统能按照一定的逻辑进行线路的展示和比价，方便用户做出决策，还提供是按价格范围，价格高低，出游天数，酒店等级，交通方式来显示旅游信息。


# Architecture

<img src="https://github.com/MOBIN-F/TravelPriceComparison/blob/master/%E9%A1%B9%E7%9B%AE%E6%9E%B6%E6%9E%84%E5%9B%BE.png" width="700" height="550"/>
