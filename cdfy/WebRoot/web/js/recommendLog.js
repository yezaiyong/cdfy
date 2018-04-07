$(".recommendLog").live('click', function(){
	var $this = $(this);
	var option = {"streamId":$this.attr("streamId")};
	$.ajax( {
		type : "post",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		url : "recommendLog.do",
		data : option,
		dataType : "json",
		success : function(data) {
			if(data.recommendState==1){
				$this.html("已推荐");
			}else{
				$this.html("推荐");
			}
//			console.log(data);
		},
		error : function(msg) {
			console.log(msg);
		}
	});
});