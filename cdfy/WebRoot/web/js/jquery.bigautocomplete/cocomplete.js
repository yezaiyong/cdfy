this.currentInputText = null;// 目前获得光标的输入框（解决一个页面多个输入框绑定自动补全功能）
$(function() {
	$("#inputUser").click(function() {
		$.ajax({
			url : 'ajaxfrendList.do',
			dataType : 'json',
			success : function(result) {
				makeContAndShow(result.data);
			}
		});
	});
})

function init() {
	$("body")
			.append(
					"<div id='bigAutocompleteContent' class='bigautocomplete-layout'></div>");
	var offset = $("#inputUser").offset();
	var h = $("#inputUser").outerHeight() - 1;
	$("#bigAutocompleteContent").css("width",
			($("#inputUser").outerWidth() - 2)).css({
		"top" : offset.top + h,
		"left" : offset.left
	});
	// 鼠标悬停时选中当前行
	$("#bigAutocompleteContent").delegate("tr", "mouseover", function() {
		$("#bigAutocompleteContent tr").removeClass("ct");
		$(this).addClass("ct");
	}).delegate("tr", "mouseout", function() {
		$("#bigAutocompleteContent tr").removeClass("ct");
	});
	// 单击选中行后，选中行内容设置到输入框中，并执行callback函数
	$("#bigAutocompleteContent").delegate(
			"tr",
			"click",
			function() {
				// currentInputText.val( $(this).find("div:last").html());
				$("#inputUser").val(
						$("#inputUser").val()
								+ ($(this).find("div:last").html() + ","));
				hideAutocomplete();
			})
}

// 组装下拉框html内容并显示
function makeContAndShow(data_) {
	if ($("body").length > 0 && $("#bigAutocompleteContent").length <= 0) {
		init();// 初始化信息
	}
	var $bigAutocompleteContent = $("#bigAutocompleteContent");

	if (data_ == null || data_.length <= 0) {
		return;
	}
	var cont = "<table><tbody>";
	for ( var i = 0; i < data_.length; i++) {
		cont += "<tr><td><div>" + data_[i].title + "</div></td></tr>"
	}
	cont += "</tbody></table>";
	$bigAutocompleteContent.html(cont);
	$bigAutocompleteContent.show();

}

// 隐藏下拉框
function hideAutocomplete() {
	var $bigAutocompleteContent = $("#bigAutocompleteContent");
	if ($bigAutocompleteContent.css("display") != "none") {
		$bigAutocompleteContent.find("tr").removeClass("ct");
		$bigAutocompleteContent.hide();
	}
}
