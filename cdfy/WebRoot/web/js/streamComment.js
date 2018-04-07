var pageCount;//总页数
var pageNo = 1;//当前页数
var pageSize = 10; //没有显示数
var num = 5; //分页显示数量（只能是奇数）
$(function() {
	ajaxcomment();
	var $comments = $("#comments");
	var $replay = $(".act-reply");
	var $canel = $(".canel");
	var $okbtn = $(".okbtn");
	
	var $ping = $("#ping");
	var $repl = $(".repl");
	var $q2 = $(".q2");
	var $inputpager = $("input[name='pagervalue']");

	// 取消回复框
	$repl.live('click', function(event) {
		var $this = $(this);
		$(document).one("click", function() {// 对document绑定一个影藏Div方法
					$this.fadeOut('slow', function() {
						$(this).html('');
					});
				});
		event.stopPropagation();// 阻止事件向上冒泡

		});
	// 评论
	$replay
			.live(
					'click',
					function() {
						$this = $(this);
						var user1 = $this.attr("user-data");
						var user2 = $this.attr("dj-data");
						var type = $this.attr("type");
						var html = '<div class="qz-poster-bd">'
						html += '<div class="qz-poster-editor-cont">'
						html += '<div class="qz-inputer bor2" data-version="20130625"'
						html += '	style="padding-right: 0px;">'
						html += '		<div class="q1" idprefix="$14">'
						html += '	<div   x:id="content_content" ';

						if ($this.attr("type") == "0") {
							html += 'style="width:590px;overflow-y:auto;"'
						} else {
							html += 'style="width:540px;overflow-y:auto;"'
						}
						html += ' x:idprefix="content"'
						html += '		class="q2 textinput textarea c_tx2"'
						html += '		contenteditable="true" accesskey="q" style=""'
						html += '		 x:idprefix="substitutor" x:id="substitutor_content"  idprefix="$14_content">'
						//html += '		<button name="@{uin: '
						//		+ $this.attr("tousers") + ',nick: '
						//		+ $this.attr("tousers") + ',auto:1}"'
						//html += '			onclick="return false;" tabindex="-1"'
						//html += '			contenteditable="false" class="c_tx3 mention">回复 ' + $this
						//		.attr("tousers") + ' : '
					//	html += '		</button>'
							html +='<button  class="c_tx3 mention" tabindex="-1"  contenteditable="false" onclick="return false;" name="@{uin:'+ $this.attr("tousers") + ',nick:'+ $this.attr("tousers") + ',auto:1}">回复   '+ $this.attr("tousers") + ' </button>'
						//html += '	<span></span>'
						html += '	</div>'
						html += '	</div>'
						html += '	</div>'
						html += '	</div>'
						html += '</div>'
						html += '		<div class="op">'
						html += '	<a tocomments="' + $this.attr("tocomments") + '"';
						if ($this.attr("type") == "0") {
							html += 'style="margin-left:532px;"'
						} else {
							html += 'style="margin-left:482px;"'
						}
						html += 'tousers="' + $this.attr("tousers") + '" class="okbtn" href="javascript:void(0)"'
						html += 'class="btn-post gb_bt evt_click"'
						html += '	data-hottag="MOODPOSTER.POST" data-clicklog="post"><i'
						html += '	class="icon icon-loading"></i><span class="txt">发表</span>'
						html += '	</a>'
						html += '	</div>';
						$(".comments-listttt > ul > li").each(
								function(index) {
									var $this = $(this);
									var $t = $this.find(".comments-item-bd")
											.find(".comments-content").find(
													".comments-op").find(
													".repl");
									$t.html('');
								});
						if (type == "0") {
							$this.parent(".comments-op").find(".repl").append(
									html).fadeIn("slow");
						} else {
							$this.parent(".comments-op").find(".repl").append(
									html).fadeIn("slow");
						}
						$this.parent(".comments-op").find(".repl").focus();
					});
	// 取消评论
	$canel.live('click', function() {
		$this = $(this);
		$this.parent(".op").parent(".repl").fadeOut('slow', function() {
			$(this).html('');
		});
	});
	// 提交评论
	$okbtn.live('click', function() {
		$this = $(this);
		var touser = $this.attr("tousers");
		var commentsId = $this.attr("tocomments");
		var content = $this.parent(".op").parent(".repl").children(".qz-poster-bd")
				.children(".qz-poster-editor-cont").children(".qz-inputer").children(".q1")
				.children(".q2").html();
		var $temp = $('<div></div>');
		var $st = $temp.html(content);
		var $temp1 = $st.children("img").remove();
		content = $temp.html();
		if (content == null || content == '' || content == 'null') {
			ZENG.msgbox.show('内容不能为空！！', 3, 3000); 
			return false;
		}
		if (content.length > 600) {
			ZENG.msgbox.show('字数不能超过500！！', 3, 3000);
			return false;
		}
		var map = {};
		map.touser = touser;
		map.content = content;
		map.commentsId = commentsId;
		map.streamId = comments.ids;
		$.ajax( {
			type : 'post',
			data : map,
			url : comments.commentReplyajax,
			dataType : 'html',
			success : function(data) {
				if (data == "1") {
					ZENG.msgbox.show("回复成功！", 4, 3000);
					ajaxcomment();
				} else {
					ZENG.msgbox.show("服务器异常！", 3, 3000);
				}
			}
		});
	});

	// 评论课件
	$ping.live('click',function() {
		var $textareas = $("#textareas");
		var content = $textareas.val();
		var streamId = comments.ids;
		if (content == null || content == '' || content == 'null') {
			return false;
		}
		if (content.length > 600) {
			ZENG.msgbox.show('字数不能超过500！！', 3, 3000);
			return false;
		}
		var map = {};
		map.streamId = streamId;
		map.content = content;
		$.ajax( {
			type : 'post',
			data : map,
			url : comments.commentReplyajax,
			dataType : 'html',
			success : function(data) {
				if (data == "1") {
					$textareas.val('');
					ZENG.msgbox.show("评论成功！", 4, 3000);
					ajaxcomment();
				} else {
					ZENG.msgbox.show("服务器异常！", 3, 3000);
				}
			}
		});
	});
	
	// 验证页码
	$inputpager.live('keyup', function() {
		var regu = '^[0-9]*[1-9][0-9]*$';
		var $this = $(this);
		var re = new RegExp(regu);
		if ($this.val().search(re) != -1) {
			if ($this.val() > pageCount) {
				$this.val(1);
				return false;
			}
			return true;
		} else {
			$this.val(1);
			return false;
		}
	});

});
/**
 * 初始化容器
 * 
 * @return
 */
function init() {
	$(".comments-listttt ul.data").html('评论加载中请稍等...').show();
}
/**
 * 生成容器
 * 
 * @param html
 * @return
 */
function createDate(html) {
	$(".comments-listttt ul.data").fadeOut("slow", function() {
		$(".comments-listttt ul.data").html(html).fadeIn("slow");
	});
}

/**
 * 封装数据
 * 
 * @param data
 * @return
 */
function createHtml(data) {
	var html = '';
	$(data.list)
			.each(
					function(i, per) {
						html += '<li';
						if (per.LEVEL == "1") {
							html += '  style="margin-left:0px" ';
						} else {
							html += '  style="margin-left:40px" ';
						}
						html += ' class="comments-item bor3" >';
						html += ' <div class="comments-item-bd">'
						html += '<div class="ui-avatar">'
						html += ' <a href="personalWebsite/mainPage.do?personalUserId='+per.USERID+'" target="_blank">'
						html += ' <img class="q_namecard" src="' + per.PHOTO + '">'
						html += ' 	</a>'
						html += ' </div>'
						html += ' <div class="comments-content">'
						html += '&nbsp;';
						if (per.LEVEL == "1") {
							html += '<a class="c_tx q_namecard" target="_blank" href="">' + per.NAME + '</a>&nbsp;';
						} else {
							html += '<a class="c_tx q_namecard" target="_blank" href="javascript:;">' + per.NAME + '</a>&nbsp;';
							html += '回复';
							html += '<a class="c_tx q_namecard" target="_blank" href="javascript:;">' + per.NAME + '</a>&nbsp;';
						}
						html += ':' + per.CONTENT + ''
						html += '<div class="comments-op">'
						html += '	<span class=" ui-mr10 state">' + per.CREATE_DATE + '</span>';
						html += '<a style="display: inline-block !important;" class="act-reply" ';
						if (per.LEVEL == "1") {
							html += 'type="0"';
						} else {
							html += 'type="1"';
						}
						html += 'tocomments ="' + per.COMMENT_ID
								+ '" tousers="' + per.NAME
								+ '"   title="回复  " href="javascript:;"><b'
						html += '	class="hide-clip">回复</b>'
						html += '	</a>'
						html += '	<div style="display:none;" class="repl"></div>'
						html += '	</div>'
						html += '</div>'
						html += '</div>'
						html += '</li>';
					});
	if (data.total > 0) {
		// 计算总页数
		pageCount = parseInt((data.total) % pageSize) > 0 ? parseInt(parseInt((data.total)
				/ pageSize) + 1)
				: parseInt((data.total) / pageSize);
		// 初始化分页按钮
		$("#pager").pager( {
			pagenumber : pageNo,
			pagecount : pageCount,
			pageSize: num,
			buttonClickCallback : function(pageclickednumber) {
				pageNo = pageclickednumber;
				goto_page(pageclickednumber, pageSize);
			}
		});
		$(".comments-listttt ul.pager li").fadeIn("slow");
	} else {
		html = '<li><a>暂无评论内容！！</a></li>';
		$(".comments-listttt ul.pager li").hide();
	}
	createDate(html);
}


/**
 * 加载数据
 * 
 * @return
 */
function ajaxcomment() {
	init();
	$.ajax( {
		type : 'post',
		data : {
			"courseId" : comments.ids,
			"type": comments.type,
			"pageStart" : pageNo
		},
		url : comments.commentListajax,
		dataType : 'json',
		async : false,
		beforeSend : function(XMLHttpRequest) {
			// init();
	},
	success : function(data) {
		createHtml(data);
	}
	});

}
/**
 * 点击分页按钮 刷新数据 及分页按钮
 * 
 * @param page
 * @param s
 * @return
 */
function goto_page(page, s) {
	pageNo = page;
	ajaxcomment();
}
/**
 * 分页查询
 * 
 * @param pager
 * @return
 */
function goPager(pager) {
	var $value = $(pager).parent(".bg2").find("span.to").find(
			"input[name='pagervalue']");
	if ($value.val() == '') {
		return false;
	}
	pageNo = $value.val();
	ajaxcomment();
}