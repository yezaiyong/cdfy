$(".recommentAlbum").live('click', function(){
	var $this = $(this);
	var option = {"albumId":$this.attr("albumId")};
	$.ajax( {
		type : "post",
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		url : "recommentAlbum.do",
		data : option,
		dataType : "json",
		success : function(data) {
			console.log(data);
			if(data.recommetState==1){
				$this.text("已推荐");
			}else{
				$this.text("推荐");
			}
		},
		error : function(msg) {
			console.log(msg);
		}
	});
});