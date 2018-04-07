$().ready(function(){
	var sp = 1;
	$('#content').infinitescroll({
		navSelector  	: "#more",
		nextSelector 	: "#more a",
		itemSelector 	: ".classitem",
		loading:{
			img: "images/loading.gif",
			msgText: '数据加载中...',
			finishedMsg: '到底了',
			finished: function(){
				sp++;
				if(sp>=10){ //到第10页结束事件
					$("#more").remove();
					$("#infscr-loading").hide();
					//$("#page").show();
					$(window).unbind('.infscr');
				}
			}
		},errorCallback:function(){ 
			//$("#page").show();
			ZENG.msgbox.show('数据已经加载完毕', 4, 3000);
		}
	},function(newElements){
		var $newElems = $(newElements);
		//$('.infinite_scroll').masonry('appended', $newElems, false);
		$newElems.fadeIn();
		//item_callback();
		return;
	});
	
	$("#pubButton").click(function(){
		if($("#pubContent").val() == '') {
			ZENG.msgbox.show('请输入发布内容', 3, 3000);
			$("#pubContent").focus();
			return;
		}
		var photoIds = [];
		$("input[name='img']").each(function(){
			photoIds.push($(this).val());
		});
		$('#uploadifyfile').uploadify('stop');
		var $this = $(this);
		$.ajax({
		  url: "addWeibo.do",
		  type: "POST",
		  data: {
			  priv:$("#priType").val(),
			  content:$("#pubContent").val(),
			  photoIds:photoIds
		  },
		  traditional: true,
		  beforeSend: function(xhr){
		  	$this.attr("disabled", true);
		  },
		  success: function(data){
		  	$("#content").prepend(data);
		  	$("#pubContent").val('');
		  	$("#attachfileQueue").empty();
		  	$("#fileQueue").empty();
		  	$this.attr("disabled", false);
		  	ZENG.msgbox.show('发表成功', 4, 2000);
		  }
		  /*,
		  dataType: "json"
		  */
		});
	});
	
	$("#pinglunBtn").live('click', function(){
		if($("#pinglunContent").val() == '') {
			ZENG.msgbox.show('请输入内容', 3, 3000);
			$("#pinglunContent").focus();
			return;
		}
		var $this = $(this);
		$.ajax({
		  url: "addMessage.do",
		  type: "POST",
		  data: {sid:$this.attr("data-id"),content:$("#pinglunContent").val()},
		  beforeSend: function(xhr){
		  	$this.attr("disabled", true);
		  },
		  success: function(data){
		  	$("#pinglunContent").val('');
		  	$this.attr("disabled", false);
		  	$("#pinglun_"+$this.attr("data-id")).remove();
		  	$("#attachfileQueue").empty();
		  	ZENG.msgbox.show('评论成功', 4, 2000);
		  	$("#item_"+$this.attr("data-id")).append(data);
		  }
		  /*,
		  dataType: "json"
		  */
		});
	});
	
	$("#zhuanboBtn").live('click', function(){
		if($("#zhuanboContent").val() == '') {
			ZENG.msgbox.show('请输入内容', 3, 3000);
			$("#zhuanboContent").focus();
			return;
		}
		var $this = $(this);
		$.ajax({
		  url: "addRelay.do",
		  type: "POST",
		  data: {sid:$this.attr("data-id"),content:$("#zhuanboContent").val()},
		  beforeSend: function(xhr){
		  	$this.attr("disabled", true);
		  },
		  success: function(data){
		  	$("#zhuanboContent").val('');
		  	$this.attr("disabled", false);
		  	ZENG.msgbox.show('转播成功', 4, 2000);
		  	$("#zhuanbo_"+$this.attr("data-id")).remove();
		  	$("#item_"+$this.attr("data-id")).append(data);
		  }
		  /*,
		  dataType: "json"
		  */
		});
	});
	
	/**
	 * 评论
	 */
	$(".m_headpl .icon1").live('click', function(){
		var $this = $(this);
		var $item = $("#item_"+$this.attr("data-id"));
		$(".mydivssbig[id!='pinglun_"+$this.attr("data-id")+"']").remove();
		if($("#pinglun_"+$this.attr("data-id")).size()==0) {
			$.ajax({
			  url: "message.do",
			  type: "POST",
			  data: {sid:$this.attr("data-id"),t:1},
			  success: function(data){
				  $(data).appendTo($item);
				  $this.attr("disabled", false);
			  },
			  beforeSend: function(xhr){
				  $this.attr("disabled", true);
			  }
			});
		} else {
			$("#pinglun_"+$this.attr("data-id")).remove();
		}
	});
	
	/**
	 * 转播
	 */
	$(".m_headpl .icon2").live('click', function(){
		var $this = $(this);
		var $item = $("#item_"+$this.attr("data-id"));
		$(".mydivssbig[id!='zhuanbo_"+$this.attr("data-id")+"']").remove();
		if($("#zhuanbo_"+$this.attr("data-id")).size()==0) {
			$.ajax({
			  url: "message.do",
			  type: "POST",
			  data: {sid:$this.attr("data-id"),t:2},
			  success: function(data){
				//$("#item_"+$this.attr("data-id")+" .mydivssbig").html(data);
				$(data).appendTo($item);
			  	$this.attr("disabled", false);
			  },
			  beforeSend: function(xhr){
				  $this.attr("disabled", true);
			  }
			});
		} else {
			$("#zhuanbo_"+$this.attr("data-id")).remove();
		}
	});
	
	/**
	 * 赞
	 */
	$(".m_headpl .icon3").live('click', function(){
		var $this = $(this);
		$.ajax({
		  url: "praise.do",
		  type: "POST",
		  data: {sid:$this.attr("data-id")},
		  success: function(data){
			$this.val(data.state == '1' ? '赞 ('+data.total+')' : '已赞 ('+data.total+')');
		  	$this.attr("disabled", false);
		  },
		  beforeSend: function(xhr){
			  $this.attr("disabled", true);
		  }
		});
	});
	
	/**
	 * 收藏
	 */
	$(".m_headpl .icon4").live('click', function(){
		var $this = $(this);
		$.ajax({
		  url: "togglefav.do",
		  type: "POST",
		  data: {sid:$this.attr("data-id")},
		  success: function(data){
		  	$this.val(data.state == '1' ? '收藏' : '取消收藏');
		  	$this.attr("disabled", false);
		  },
		  beforeSend: function(xhr){
			  $this.attr("disabled", true);
		  }
		});
	});
});