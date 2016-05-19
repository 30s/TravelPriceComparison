$(document).ready(
	function() {

		var liDay = null;
		var liHotel = null;
		var liPrice = null;


		var datepicker = null;
		var citySelect = null;
		var citySelect1 = null;

		$("li input").click(
			function() {
				liDay = $("input[name='day']:checked").val();
				liHotel = $("input[name='hotel']:checked").val();
				liPrice = $("input[name='sortPrice']:checked").val();
				if (liDay == null) {liDay = ""}
				if (liHotel == null) {liHotel = ""}
				if (liPrice == null) {liPrice = ""}

				datepicker = $("#datepicker").val();
				citySelect = $("#citySelect").val();
				citySelect1 = $("#citySelect1").val();
				if (datepicker == null) {datepicker = ""}
				if (citySelect == "城市名") {citySelect = ""}
				if (citySelect1 == "城市名") {citySelect1 = ""}

				$.ajax({
					type : "GET",
					url : "http://192.168.31.128:8080/Travel/travelAction"+"?day="+liDay+"&hotel="+liHotel+"&price="+liPrice
								+"&datepicker="+datepicker+"&citySelect="+citySelect+"&citySelect1="+citySelect1,
					dataType : "jsonp",
					success : function(data) {
						$("#mainContent").html("");
						var index = eval(data);
						alert("chenggong+"+index.records.length);
						for(var i=0;i<index.records.length;i++)
							showMainContent(i,index);
					},
					error : function(jqXHR) {

						$("#mainContent").html("");

					}
				});



			});//$("li input").click


		$("#check").click(
			function() {
				liDay = $("input[name='day']:checked").val();
				liHotel = $("input[name='hotel']:checked").val();
				liPrice = $("input[name='sortPrice']:checked").val();
				liDay = ""
				liHotel = ""
				liPrice = ""
				$("input[name='day']:checked").attr("checked",false);
				$("input[name='hotel']:checked").attr("checked",false);
				$("input[name='sortPrice']:checked").attr("checked",false);
				datepicker = $("#datepicker").val();
				citySelect = $("#citySelect").val();
				citySelect1 = $("#citySelect1").val();
				if (datepicker == null) {datepicker = ""}
				if (citySelect == "城市名") {citySelect = ""}
				if (citySelect1 == "城市名") {citySelect1 = ""}

				$.ajax({
					type : "GET",
					url : "http://192.168.31.128:8080/Travel/travelAction"+"?day="+liDay+"&hotel="+liHotel+"&price="+liPrice
					+"&datepicker="+datepicker+"&citySelect="+citySelect+"&citySelect1="+citySelect1,
					dataType : "jsonp",
					success : function(data) {
						alert("成功"+datepicker+citySelect+citySelect1+liDay);
						$("#mainContent").html("");
						var index = eval(data);
						for(var i=0;i<index.records.length;i++)
							showMainContent(i,index);

					},
					error : function(jqXHR) {
						alert(123);
						$("#mainContent").html("");
					}
				});

			});//$("#check").click

	});



function showMainContent(i,index){
	var mainContent = $("#mainContent");
	mainContent.append('<article id="block_topic_post_feature'+i+'" class="block_topic_post_feature" style=" margin-bottom:40px"></article>');
	var article = $("#block_topic_post_feature"+i);
	/*
	 * article里包含三个大div,class值分别为f_pic,content,clearboth
	 */
	article.append('<div id="f_pic'+i+'" class="f_pic"></div>');
	article.append('<div id="content'+i+'" class="content"></div>');
	article.append('<div id="clearboth'+i+'" class="clearboth"></div>');

	var f_pic = $("#f_pic"+i);
	f_pic.append('<a href="news_post.html" class="general_pic_hover scale">'+
		'<img src="'+index.records[i].iMAGE+'" alt="" /></a>');
	var content = $("#content"+i);
	content.append('<p class="title">'+
		'<a href="news_post.html"><font color="red">（跟团游）</font>'+index.records[i].tITLE+'</a>'
		+'</p>');
	content.append('<div id="info'+i+'" class="info"></div>');
	var info = $("#info"+i);
	info.append('<div class="date"><p>'
		+'<b>出发点:</b><font color="red">澳门</font>'//'+index.records[i].sP+'
			//+'<b>目的地:</b><font	color="red">上海</font>'
		+'</p></div>');
	info.append('<div class="r_part"><div class="category"><p>'
		+'<a href="#"><b>出游天数：</b><font color="red">'+index.records[i].tDATA+'</font></a></p></div>'
		+'<a href="#" class="views"><font color="red" size="+2">￥'+index.records[i].pRICE+'</font></a></div>');

	content.append('<div class="category"><p class="text">'+index.records[i].tOUATT+'</p></div></br>')
	content.append('<div class="category" style="position:absolute;margin-top:15px">'
		+'<p class="text">交通工具：'+index.records[i].tRAFFIC+"  "+index.records[i].rETURN+'</p></div>');
	content.append('<div class="category" style="position:absolute;margin-left:350px;margin-top:15px">'
		+'<p class="text">是否直达：直达</p></div><br/>');

	content.append('<div class="category" style="position:absolute;margin-top:30px">'
		+'<p class="text">酒店：'+index.records[i].hOTEL+'</p></div>');
	content.append('<div class="category" style="position:absolute;margin-left:350px;margin-top:30px">'
		+'<p class="text">旅游社团：'+index.records[i].pROXY+'</p></div>');
	content.append('<div class="category" style="position:absolute;margin-left:600px;margin-top:30px">'
		+'<p class="text">数据来源：'+index.records[i].oRIGIN+'</p></div>');

	article.append('<div class="line_2" style="margin:40px 0px 2px;"></div>');

}