/*$(document).ready(function(){
	var sp = 1;
	$('#traineeList').infinitescroll({
		navSelector  	: "#more",
		nextSelector 	: "#more a",
		itemSelector 	: ".classstudent_nr1",
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
});
*/