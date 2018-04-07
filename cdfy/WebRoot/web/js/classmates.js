$(document).ready(function(){
	var sp = 1;
	$('#classmatesContent').infinitescroll({
		navSelector  	: "#moreclassmates",
		nextSelector 	: "#moreclassmates a",
		itemSelector 	: ".classmates_big",
		loading:{
			img: "images/loading.gif",
			msgText: '数据加载中...',
			finishedMsg: '到底了',
			finished: function(){
				sp++;
				if(sp>=10){ //到第10页结束事件
					$("#moreclassmates").remove();
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
	
	
});