/*
el: jquery object
func: callback function name
*/

function initReplyBox(el,func){
	if($("#replyBox")!=null){
		$("#replyBox").remove();
	}
	var html='<div class="log_below2" id="replyBox" style="">\
         	<div class="log_belowa3"><textarea class="log_belowa3"></textarea></div>\
            <div class="log_belowb3">\
            	 <div class="titlec_all">\
   					<a href="" onclick="'+func+'()">回复 </a>\
 				 </div>\
		    </div>\
   	   		</div>';
	el.after(html);
	//alert("");
}