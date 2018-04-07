/**
 * jquery 评论插件
 */
(function($){
	 // 插件的定义    
	  $.fn.comments = function(options) {
		  var opts = $.extend({}, $.fn.comments.defaults, options);  
		    init();
	       this.each(function() {
	           var $this = $(this);
	        	$(this).html(createDate($(this).attr("number"),opts.boxImage,opts.barImage,opts.animate ,opts.showText ,opts.height,opts.width));
	        	$.fn.progressBar.css($this);
	        });
	       
	  };
	  
	  $.fn.progressBar.css = function(thiss) {    
		  css(thiss);
	    };
		  
		  
	  function createDate(number,boxImage,barImage,animate ,showText ,height,width){
		  var newnum = number.substring(0,number.length-1);
		  //console.info("开始前"+number);
			//console.info("截取后："+newnum);
		  if(newnum == '' || newnum == null || newnum == '0' || newnum == 'undefined'){
			  newnum =0;
		  }else if (newnum >= 100){
			  newnum =1;
		  }else{
			  newnum = parseFloat(newnum/100);
			  if(newnum >= 1){
				  newnum = 1 ;
			  }
		  }
		 // console.info("处理"+newnum);
		  var sss = 1 - newnum;
		  var pos = sss*width * -1
		  var html = '';
		  var html2 = '';
		  html = '<img  src="' + boxImage + '" alt="0%" style="width: ' + width + 'px; height: ' + height + 'px; background-position: ' +pos+ 'px 50%;'+
			 'background-image: url(' + barImage + '); padding: 0; margin: 0;" class="percentImage" /><span  class="percentText">'+number+'</span>';
		  
		  html2 = '<div class="ibx-uc-utool-content">'+
				     ' <span class="ibx-uc-utool-rank">'+
				         '<div id="ibx-uc-ur-rate" class="rank-bg" style="width:0%;"></div>'+
				        '<div id="ibx-uc-ur-rate-num" class="rank-num">0%</div>'+
				   '</span>'+
				'</div>';
		     return html2;
	  }
	  
	  function init(){
		  $("head").append('<link href="web/js/jquery.progressBar/jquery.progressBar.css" rel="stylesheet" type="text/css" />');
	  }
	  
	  
	  function css(thiss){
		  setTimeout(function(){
			     thiss.children(".ibx-uc-utool-content").children(".ibx-uc-utool-rank").children("#ibx-uc-ur-rate").css("width",thiss.attr("number"));	        
				 thiss.children(".ibx-uc-utool-content").children(".ibx-uc-utool-rank").children("#ibx-uc-ur-rate-num").html(thiss.attr("number"));	        
		   	
		  },1000);
	  }
	  
	// 插件的defaults    
	  $.fn.comments.defaults = {    
	    width: '50%',
	    	: 'web/js/jsprogressbarhandler/images/bramus/percentImage.png',			
		barImage	: 'web/js/jsprogressbarhandler/images/bramus/percentImage_back1.png',	
		animate		: true,									
		width: 120,
		showText	: true,		
		height		: 12
	  }; 
	// 闭包结束 
})(jQuery);	