#HBase行键设计
由于在页面的查询中涉及到分页查询和按不同条件的查询，仅仅一张表是不能完成如此复杂的多条件分页查询的，
所以对表进行了拆分，在HBase中存在有4张表（TRAVEL表，PRICE表，HOTEL表，COSTPER表），表的设计如下：
* TRAVEL表
<img src="https://github.com/hadoop-mobin/TravelProject/blob/master/TRAVEL%E8%A1%A8.png" width="600" height="310"/>
