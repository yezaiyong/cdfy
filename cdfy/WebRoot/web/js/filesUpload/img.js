$(function() {
							$("#uploadifyfile_icon2").uploadify( {
								 'method'   : 'Get',
								'swf' : 'web/swfupload/Script/uploadify.swf',
								'queueID' : 'fileQueue_icon',
								'uploader' : 'uploadFiles.do?authcode='+authcode,
								'fileSizeLimit' : 50 * 1024 * 1024,
								'fileTypeExts' : '*.jpg;*.png;*.gif',
								'fileTypeDesc' : '请选择文件',
								'uploadLimit' : 20,
								'height' : 38,
								'width' : 100,
								'auto' : true,
								'multi' : true,
								'removeCompleted' : true,
								'buttonImage' : 'web/images/xuanze.png',
								'overrideEvents' : [ 'onUploadSuccess' ],
								'onUploadStart' : function(file){
									$("#img").show();
									},
								'onUploadSuccess' : function(file, data, response){
									var ret = eval("(" + data + ")");
									filesToI(ret.obj[0]);
									$("#img").hide();
								},
								'onUploadError' : function(file, errorCode, errorMsg, errorString){
									alert("上传失败！请刷新重试");
								},
							});
							
							function filesToI(data){
							var names=$("#names").val();
						    var html = "";
						        html += '<tr class="csI1">'+
									        '<td>'+data.fileName+'</td>'+
									        '<td>'+names+'_'+data.fileName+'</td>'+
									        '<td>上传成功</td>'+
									        '<input type="hidden" name="fileImgName" value="'+names+'_'+data.fileName+'"/>'+
									        '<input type="hidden" name="fileUrlImg" value="'+data.filePath+'"/>'+
									        '<input type="hidden" name="typeImg" value=img />'+
						        			'<td class="csI2">'+
						        				'<a class="shanchu_2 shanchu_I" title="删除" href="#" ></a></td>'+
						        		 '</tr>';
						        $(".tab_aI").append(html);
						}
							
						});