$(function() {
							$("#uploadifyfile_icon3").uploadify( {
								 'method'   : 'Get',
								'swf' : 'web/swfupload/Script/uploadify.swf',
								'queueID' : 'fileQueue_icon',
								'uploader' : 'uploadFiles.do?authcode='+authcode,
								'fileSizeLimit' : 50 * 1024 * 1024,
								'fileTypeExts' : '*.*',
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
									$("#other").show();
									},
								'onUploadSuccess' : function(file, data, response){
									var ret = eval("(" + data + ")");
									filesToO(ret.obj[0]);
									$("#other").hide();
								},
								'onUploadError' : function(file, errorCode, errorMsg, errorString){
									alert("上传失败！请刷新重试");
								},
							});
							
							function filesToO(data){
							var names=$("#names").val();
						    var html = "";
						        html += '<tr class="csO1">'+
									        '<td>'+data.fileName+'</td>'+
									        '<td>'+names+'_'+data.fileName+'</td>'+
									        '<td>上传成功</td>'+
									        '<input type="hidden" name="fileOtherName" value="'+names+'_'+data.fileName+'"/>'+
									        '<input type="hidden" name="fileUrlOther" value="'+data.filePath+'"/>'+
									        '<input type="hidden" name="typeOther" value=other />'+
						        			'<td class="csO2">'+
						        				'<a class="shanchu_2 shanchu_O" title="删除" href="#" ></a></td>'+
						        		 '</tr>';
						        $(".tab_aO").append(html);
						}
							
						});