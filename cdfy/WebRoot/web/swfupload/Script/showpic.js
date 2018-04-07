//显示图片预览DIV
function showpicture(name,basepath) {
	basepath = typeof(basepath) == undefined ? '' : basepath;
	var pathvalue = $('#' + name).val();
	if (pathvalue == "") {
	} else {
		$.fancybox([
					basepath+pathvalue
				], {
					'padding'			: 0,
					'transitionIn'		: 'none',
					'transitionOut'		: 'none',
					'type'              : 'image',
					'changeFade'        : 0
				});
	}
}
// ////////////////////图片显示效果JS结束

