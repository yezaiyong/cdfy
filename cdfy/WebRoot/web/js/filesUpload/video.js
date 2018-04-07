$(function() {
							$("#uploadifyfile_icon1").uploadify( {
								 'method'   : 'Get',
								'swf' : 'web/swfupload/Script/uploadify.swf',
								'queueID' : 'fileQueue_icon',
								'uploader' : 'uploadFiles.do?authcode='+authcode,
								'fileSizeLimit' : 5000 * 1024 * 1024,
								'fileTypeExts' : '*.mp4;*.flv;*.swf',
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
									$("#video").show();
									},
								'onUploadSuccess' : function(file, data, response){
									var ret = eval("(" + data + ")");
									filesToV(ret.obj[0]);
									$("#video").hide();
								},
								'onUploadError' : function(file, errorCode, errorMsg, errorString){
									alert("上传失败！请刷新重试");
								},
							});
							
							function filesToV(data){
								var names=$("#names").val();
						    var html = "";
						        html += '<tr class="csV1">'+
									        '<td>'+data.fileName+'</td>'+
									        '<td>'+names+'_'+data.fileName+'</td>'+
									        '<td>上传成功</td>'+
									        '<input type="hidden" name="fileVideoName" value="'+names+'_'+data.fileName+'"/>'+
									        '<input type="hidden" name="fileUrlVideo" value="'+data.filePath+'"/>'+
									        '<input type="hidden" name="typeVideo" value=video />'+
						        			'<td class="csV2">'+
						        				'<a class="shanchu_2 shanchu_V" title="删除" href="#" ></a></td>'+
						        		 '</tr>';
						        $(".tab_aV").append(html);
						}
							
						});