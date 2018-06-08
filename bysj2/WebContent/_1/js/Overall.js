
/**
 * 判断数据是否满足正则表达式
 * @param message
 * @param regularExp
 * @returns {Boolean}
 */
function regularMatch(message, regularExp){
	if(regularExp == null || regularExp == '' || typeof(regularExp) == 'undefined'){return true;}
	if(message == null || typeof(message) ==  'undefined'){return false;}
	if(typeof(message) == 'object'){message=JSON.stringify(message);}
	var result = message.match(regularExp);
	if(result == null){return false;}	
	return true;
}

function isNotEmptyArray(data){
	if(data != undefined && data!= null && data.length != undefined && data.length != null && data.length > 0){return true;}
	return false;	
}

function initLoading(){
    $("body").append("<!-- loading -->" +
            "<div class='modal fade' id='loading' tabindex='-1' role='dialog' aria-labelledby='myModalLabel' data-backdrop='static'>" +
            "<div id='loadingText' class='loading'></div>" +
            "<span class='glyphicon glyphicon-refresh' aria-hidden='true'></span>" +
            "</div>" +
            "</div>"
    );
}
/**
 * 扩展Date函数，转换成指定格式的时间字符串
 * (new Date()).format("yyyy-MM-dd hh:mm:ss.S") ==> "2006-07-02 08:09:04.423"
 * (new Date()).format("yyyy-M-d h:m:s.S")      ==> "2006-7-2 8:9:4.18"
 */
Date.prototype.format = function (fmt) { 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}
