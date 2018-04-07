// 创建一个闭包  
(function($){
	 // 插件的定义    
	  $.fn.progressBar = function(options) {
		  var opts = $.extend({}, $.fn.progressBar.defaults, options);  
	        return this.each(function() {
	        	$(this).html(createDate($(this).attr("number"),opts.boxImage,opts.barImage,opts.animate ,opts.showText ,opts.height,opts.width));
	        });
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
				     ' <a target="_blank" href="http://www.baidu.com/ur/scorelist" class="ibx-uc-utool-rank">'+
				         '<div id="ibx-uc-ur-rate" class="rank-bg" style="width: 63.59%;"></div>'+
				        '<div id="ibx-uc-ur-rate-num" class="rank-num">1081/1700</div>'+
				   '</a>'+
				'</div>';
		     return html;
	  }
	  
	  
	  
	  
	// 插件的defaults    
	  $.fn.progressBar.defaults = {    
	    number: '50%',
	    boxImage	: 'web/js/jsprogressbarhandler/images/bramus/percentImage.png',			
		barImage	: 'web/js/jsprogressbarhandler/images/bramus/percentImage_back1.png',	
		animate		: true,									
		width: 120,
		showText	: true,		
		height		: 12
	  }; 
	// 闭包结束 
})(jQuery);	