/*===========其中定义了一些配置文件中可以引用的调用方法============*/
var overall_limit_size =15 ;

var cstart = 0;

var opt_ajax_action_url = typeof($basePath)!='undefined'?($basePath + 'function/operation.action') : 'function/operation.action';

var $finstid=typeof(finstid)!='undefined'?finstid:-1;

/**
 * 存储策略
 */
var storage = window.localStorage;
/**
 * 跟服务器进行ajax通信调用
 * @param fid		需要调用的功能接口
 * @param opt		需要调用的操作接口
 * @param params	出入参数
 * @param synch		是否同步
 * @param success	调用成功
 * @param error		调用失败
 */
function doAjax(fid,opt,params,synch,success,error){
	if(params == undefined || params == null){
		params = {};
	}
	params.fid = fid;
	params.opt = opt;
	doAjaxFromUrl(opt_ajax_action_url, params, synch, success, error);
}

function doAjaxFromUrl(url, params, synch, success, error){
	if(params == undefined || params == null){
		params = {};
	}
	if(typeof(params.magicboy) != 'undefined' && params.magicboy != 'yes'){
		delete params.magicboy;
	}else{
		params.magicboy='yes';
	}
	if(typeof(params.finstid) == 'undefined' || params.finstid == null){
		params.finstid=$finstid;
	}
	//加入默认的一些参数
	if(params.limit == undefined || params.limit == null){params.limit = overall_limit_size;}
	if(params.start == undefined || params.start == null){params.start = 0;}
	
	if(synch == undefined || synch == null){synch = true;}
	try{
		//showLoading('提示','数据加载中...');
	}catch(e){}
	$.ajax({
		type:"post",
		url : url,
		async:synch,
		traditional :true,
		dataType : 'json',
		data : params,
		success:function(data){
			if(success != undefined && success != null){
				success(data);
			}
			//try{hideLoading();}catch(e){}
		},
		error : function(){
			if(error != undefined && error != null){
				error();
			}
			//try{hideLoading();}catch(e){}
		}
	});
}

/**
 * 将当前页面调转到指定页面
 * @param name		指定页面的名称
 * @param url		指定页面的地址
 */
function forward(name,url,_params){
	if(url == undefined || url == null || url == ''){return;}
	url = url.replace('${basePath}',$basePath);	
	//参数拼接
	var form = document.createElement("form");//0830--zhangzh--改用jQuery模拟表单提交
	$(form).attr('action',url);
	$(form).attr('method','post');
	$(form).css('display','none');
	//document.write('<form action="'+url+'" method="post" name="forwardForm" style="display:none">');  
	if(_params != undefined && _params != null){
		for(var key in _params){
			//document.write('<input type="hidden" name="'+key+'" id="'+key+'" value="'+_params[key]+'"/>'); 
			$(form).append('<input type="hidden" name="'+key+'" id="'+key+'" value="'+_params[key]+'"/>');
		}		
	} 
	//document.write("</form>");  
	//document.forwardForm.submit();
	$('body').append($(form));
	$(form).submit();
	if(name != undefined && name != null && name != ''){
		_addPath(name, url, false);
	}
}

function getFormValuesWithoutLimit(domid){
	var _params = getFormValues(domid);
	if(_params != undefined && _params != null){
		delete _params.limit;
		delete _params.start;
	}
	return _params;
}

/**
 * 获取指定id下所有的表单值
 * @param domid	指定的domid
 * 返回该dom下的所有表单项的值
 */
function getFormValues(domid){
	var data = {};
	
	//获取input
	var _form_item = $('#'+domid+' input');
	if(_form_item != undefined && _form_item != null && _form_item.length > 0){
		for(var i = 0; i < _form_item.length; i++){
			data[_form_item[i].id] = _form_item[i].value;
		}
	}
	
	//获取文本域
	var _form_item_textarea = $('#'+domid+' textarea');
	if(_form_item_textarea != undefined && _form_item_textarea != null && _form_item_textarea.length > 0){
		for(var i = 0; i < _form_item_textarea.length; i++){
			data[_form_item_textarea[i].id] = _form_item_textarea[i].value;
		}
	}
	
	//获取select
	var _form_item_select = $('#'+domid+' select');
	if(_form_item_select != undefined && _form_item_select != null && _form_item_select.length > 0){
		for(var i = 0; i < _form_item_select.length; i++){
			var one_select = _form_item_select[i];
			if(one_select.options == undefined || one_select.length == 0){
				data[_form_item_select[i].id] = null;
				data[_form_item_select[i].id+'_text'] = null;
			}else{
				var selectedList = [];
				var selectedListWithText = [];
				for(var k = 0; k < one_select.length; k++){
					if(one_select.options[k].selected){
						selectedList[selectedList.length]=one_select.options[k].value;
						selectedListWithText[selectedListWithText.length]=one_select.options[k].value+'|'+one_select.options[k].text;
					}
				}
				data[_form_item_select[i].id] = selectedList;
				data[_form_item_select[i].id+'_text'] = selectedListWithText;
			}
		}
	}
	
	var start = cstart;					
	if(start == undefined){start = 0;}
	//获取查询表单中所有的输入项	
	if(data == undefined){data = {};}
	data.start = start;	
	data.limit = overall_limit_size;
	cstart = start;
	
	return data;
}

/**
 * 清除某个id下所有表单的数据
 * @param domid
 */
function resetFrom(domid){
	window.location.reload(true);
	throw e;
//	var _form_item = $('#'+domid+' input');
//	if(_form_item != undefined && _form_item != null && _form_item.length > 0){
//		for(var i = 0; i < _form_item.length; i++){
//			_form_item[i].value = '';
//		}
//	}
//	var _select_item = $('#'+domid+' select');
//	for(var i = 0; i < _select_item.length; i++){
//		var _one_select = _select_item[i];		
//		$(_one_select).trigger('chosen:updated');
//		$(_one_select).val('');
//	}
}

/**
 * 清除某个id下所有表单的数据
 * @param domid
 */
function resetForm(domid){
	var _form_item = $('#'+domid+' input');
	if(_form_item != undefined && _form_item != null && _form_item.length > 0){
		for(var i = 0; i < _form_item.length; i++){
			_form_item[i].value = '';
		}
	}
	var _select_item = $('#'+domid+' select');
	for(var i = 0; i < _select_item.length; i++){
		var _one_select = _select_item[i];		
		$(_one_select).val('');
		$(_one_select).trigger('chosen:updated');
	}
}

/**
 * 设置某个form下所有表单项的值为指定的值
 * @param domid
 * @param data
 */
function setFromValues(domid, data){
	var _form_item = $('#'+domid+' input');
	if(_form_item != undefined && _form_item != null && _form_item.length > 0){
		for(var i = 0; i < _form_item.length; i++){
			_form_item[i].value = data[_form_item[i].id]!=undefined?data[_form_item[i].id]:'';
		}
	}
}

/**
 * 弹出提示
 * @param message
 */
function alertError(message){
	new $.zui.Messager(message, {
		type:'danger',
	    icon: 'exclamation-sign',
	    placement: 'top'
	}).show();
}
function alertWarning(message){
	new $.zui.Messager(message, {
		type:'warning',
	    icon: 'warning-sign',
	    placement: 'top'
	}).show();
}
function alertSuccess(message){
	new $.zui.Messager(message, {
		type:'success',
		icon: 'check',
	    placement: 'top'
	}).show();
}
function doDelete(fid,opt,params,success,fail){
	if(params == undefined || params == null){
		params = {};
	}
	params.fid = fid;
	params.opt = opt;
	doDeleteFromUrl(opt_ajax_action_url, params,success,fail);
}
function doDeleteFromUrl(url, params,success,fail){
if($("#deleteModal").length>0){
		
	}
	else{
		$("body").append("<div class='modal fade .deleteModal' id='deleteModal'>"+
				  "<div class='modal-dialog'>"+
				    "<div class='modal-content'>"+
				      "<div class='modal-header'>"+
				        "<button type='button' class='close' data-dismiss='modal'><span aria-hidden='true'>×</span><span class='sr-only'>关闭</span></button>"+
				        "<h4 class='modal-title'>提示</h4>"+
				      "</div>"+
				      "<div class='modal-body'>"+
				        "<p>是否确认删除?</p>"+
				      "</div>"+
				      "<div class='modal-footer'>"+
				        "<button type='button' class='btn btn-success'  data-dismiss='modal'><i class='iconfont icon-quxiao' style='margin-right:3px;font-size:12px;'></i>关闭</button>"+
				       " <button type='button' id='deletebtn'style='color:#fff;' class='btn btn-success'><i class='iconfont icon-queren' style='margin-right:3px;font-size:12px;'></i>确认</button>"+
				     " </div>"+
				    "</div>"+
				  "</div>"+
				"</div>");
	}
	
	$('#deleteModal').modal({
		"moveable":true,
		backdrop:"static"
	});
	$("#deletebtn").unbind('click');
	$("#deletebtn").one("click",function(){
		doAjaxFromUrl(url, params, true, function(data){
			$('#deleteModal').modal("hide");
			if(success != undefined){success(data);}
			else{
				if(data.success){
				    alertSuccess('删除成功!');
					list_query(cstart);
				}else{
					if(data.errorstate){
						if(data.errorstate == 1){//错误等级，1为warning，2为error
							alertWarning(data.emsg);
						}
						if(data.errorstate == 2){
							alertError(data.emsg);
						}
					}
					else{
						alertError(data.emsg);
					}
					list_query(cstart);
				}
			}
			
			 
		}, function(){
			$('#deleteModal').modal("hide");
			alertError('删除失败！');
		});
	});
}
function doConfirm(fid,opt,params,emsg){
	if(params == undefined || params == null){
		params = {};
	}
	params.fid = fid;
	params.opt = opt;
	doConfirmFromUrl(opt_ajax_action_url, params,emsg);
}
function doConfirmFromUrl(url, params,emsg){
if($("#confirmModal").length>0){
	    $("#confirmModal").remove();
	}
		$("body").append("<div class='modal fade .deleteModal' id='confirmModal'>"+
				  "<div class='modal-dialog'>"+
				    "<div class='modal-content'>"+
				      "<div class='modal-header'>"+
				        "<button type='button' class='close' data-dismiss='modal'><span aria-hidden='true'>×</span><span class='sr-only'>关闭</span></button>"+
				        "<h4 class='modal-title'>提示</h4>"+
				      "</div>"+
				      "<div class='modal-body'>"+
				        "<p>"+emsg+"</p>"+
				      "</div>"+
				      "<div class='modal-footer'>"+
				        "<button type='button' class='btn btn-default'  data-dismiss='modal'>关闭</button>"+
				       " <button type='button' id='confirmbtn'style='color:#fff;' class='btn btn-primary'>确认</button>"+
				     " </div>"+
				    "</div>"+
				  "</div>"+
				"</div>");
	
	$('#confirmModal').modal({
		"moveable":true,
		backdrop:"static"
	});
	$("#confirmbtn").unbind('click');
	$("#confirmbtn").one("click",function(){
		doAjaxFromUrl(url, params, true, function(data){
			$('#confirmModal').modal("hide");
			if(data.success){
				    alertSuccess('操作成功!');
					list_query(cstart);
				}else{
					if(data.errorstate){
						if(data.errorstate == 1){//错误等级，1为warning，2为error
							alertWarning(data.emsg);
						}
						if(data.errorstate == 2){
							alertError(data.emsg);
						}
					}
					else{
						alertError(data.emsg);
					}
					list_query(cstart);
				} 
		}, function(){
			$('#confirmModal').modal("hide");
			alertError('未知错误!');
		});
	});
}
