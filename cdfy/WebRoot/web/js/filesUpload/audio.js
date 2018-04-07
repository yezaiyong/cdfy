	$(function() {
							$("#uploadifyfile_icon").uploadify( {
								 'method'   : 'Get',
								'swf' : 'web/swfupload/Script/uploadify.swf',
								'queueID' : 'fileQueue_icon',
								'uploader' : 'uploadFiles.do?authcode='+authcode,
								'fileSizeLimit' : 50 * 1024 * 1024,
								'fileTypeExts' : '*.wav;*.mp3;*.wma;*.APE;*.ACC',
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
									$("#audio").show();
									},
								'onUploadSuccess' : function(file, data, response){
									var ret = eval("(" + data + ")");
									filesTo(ret.obj[0],file.id);
									$("#audio").hide();
								},
								'onUploadError' : function(file, errorCode, errorMsg, errorString){
									alert("上传失败！请刷新重试");
								},
							});
							
							function filesTo(data,fileId){
								var names=$("#names").val();
						    var html = "";
						        html += '<tr class="cs1">'+
									        '<td>'+data.fileName+'</td>'+
									        '<td>'+names+'_'+data.fileName+'</td>'+
									        '<td>上传成功</td>'+
									        '<input type="hidden" name="fileName" value="'+names+'_'+data.fileName+'"/>'+
									        '<input type="hidden" name="fileUrl" value="'+data.filePath+'"/>'+
									        '<input type="hidden" name="type" value=audio />'+
						        			'<td class="cs2">'+
						        				'<a class="shanchu_2" title="删除" href="#" ></a></td>'+
						        		 '</tr>';
						        $("#tab_a").append(html);
						}
							
						});