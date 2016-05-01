#HBase行键设计及查询策略
##行键设计
由于在页面的查询中涉及到分页查询和按不同条件的查询，仅仅一张表是不能完成如此复杂的多条件分页查询的，
所以对表中的字段进行了冗余作为索引表以便实现分页查询，在HBase中存在有4张表（TRAVEL表，PRICE表，HOTEL表，COSTPER表），
其中PRICE表，HOTEL表，COSTPER表是属于索引表，表的设计如下：
* TRAVEL表<br/> 
  <img src="https://github.com/hadoop-mobin/TravelProject/blob/master/TRAVEL%E8%A1%A8.png" width="600" height="310"/> 
 
  >TRAVEL表中存储了旅游信息的所有信息，是旅游信息中的总表。

* COSTER表<br/>
  <img src="https://github.com/hadoop-mobin/TravelProject/blob/master/COSTPER%E8%A1%A8.png" width="600" height="310"/>
>COSTPER表（性价比表）：该表包含3个字段，ROWKEY, RECORD, ST

* PRICE表<br/>
  <img src="https://github.com/hadoop-mobin/TravelProject/blob/master/PRICE%E8%A1%A8.png" width="600" height="310"/>
>PRICE表中主要有4个字段：PRICEROWKEY,RECORD,ROWKEY,ST,其中ROWKEY对应TRAVEL表中的ROWKEY,RECORD为序列递增值，主要用于分页查询时记录偏移量的定位

* HOTLE表<br/>
  <img src="https://github.com/hadoop-mobin/TravelProject/blob/master/PRICE%E8%A1%A8.png" width="600" height="310"/>
>HOTEL表有5个字段：HOTELROWKEY,RECORD,ROWKEY,HOTELLEVEL,ST
ROWKEY对应的是TRAVEL表中的ROWKEY.

TRAVEL表作为主表其行键格式如下：
*    出发点-目的地-性价比值（性价比值是通过算法得到的唯一值）
    
行键设计的好坏直接影响到数据的查询效率，由于HBase中数据的查询主要通过ROWKEY来查询或全表扫描查询，所以行键的设计就显得及为重要.

__数据热点问题：__

   按默认的方式创建表时，只有一个region,这时start key end key都没有边界，当我们不断往表中插入数据时，总会往最大的start key所在的region写数据，之前因为数据超过region阀值而被分裂出来的region不会再被写数据，在写比较频繁的情况下，数据增长过快，split的次数也会增多，由于split是比较费时费资源的，所以必须对这种情况进行优化。
   
__解决办法：__

为了防止数据热点的出现，必须让数据在插入时就分布在不同的region中，我们通过预分区可以解决该问题，通过按一定规则来划分多个Region（按按出发点来设置Rgeion的startKey,如”澳门”,”深圳”）能使数据分布在不同的Region中且相同出发点的数据都落在同一个Region中，这给查询带了及带的便利，使得我们通过出发点，目的地，时间来查询时并不需要跨Region去取数据。这样，数据既能分布在不同Region中，以使得相同出发点的数据在同一Region中。

由于我们在按条件查询时先通过HOTEL表或PRICE表或COSTPER表来获得ROWKEY，再通过ROWKEY在TRAVEL表中取得相应的数据，所以HTOEL，PRICE，COSTPER表的行键设计也需要一定的技巧性，所以这三张表的行键的设计如下：
   
*    出发点-目的地-XXX
 
__这样的格式的行键，能使这三张表的数据和TRAVEL中的数据在同一Region中，再得到ROWKRY后并不需要跨Region就能取得旅游数据的详细信息。__

#查询策略
默认查询显示出的旅游信息是按性价比排序的；

1.    当用户通过出发时间，出发点，目的地来查询时，首先对查询字段进行拼接后作为在查询主键去COSTPER表来获得该页中相应的数据的ROWKEY值，再根据ROWKEY在TRAVEL表中查询出旅游数据的详细信息。

2.    当用户需要按价格的高低来查询时，首先根据出发时间，出发地，目的地对查询字段进行拼接后作为在查询主键去PRICE表中获得该页中相应的数据的ROWKEY值，再通过ROWKEY在TRVEL表中取得旅游数据的详细信息；

3.    同样的当用户需要按酒店的星级数来排序时，首先通过出发时间，出发地，目的地和酒店星级数对查询字段进行拼接后作为在查询主键去HOTEL表中获得该页中相应的数据的ROWKEY值，再通过ROWKEY在TRAVEL表中取得旅游数据的详细信息。

##分页查询
由于在插入数据后，表中并不存在连续数字的列，所以当对查询结果进行分页时就不太好操作，解决办法就是在上面拆分的表三张表中添加一个字段，让其有序递增（如下图（图5）的RECORD字段）形能够有效分页的分页表，恰好Phoenix就提供了一个Sequence的序列值，这样当用户需要按条件分页查询时就根据条件先在这三张分页表表中查询得到该页的所有数据的主键，再根据主键在TRAVEL中查询出具体的旅游信息。


  <img src="https://github.com/hadoop-mobin/TravelProject/blob/master/TRAVEL%E8%A1%A8.png" width="600" height="310"/>  

##二级索引
Phoenix为HBase提供了二级索引的功能，通过为常用的查询字段建立索引可以极大的提高查询效率，所以在这里对表中ST,HOTELLEVEL字段建立的二级索引。

