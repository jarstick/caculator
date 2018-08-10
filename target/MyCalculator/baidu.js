$(function() {
	getUserInfo();
})
function search() {
	var AjaxURL = "./test";
	$.ajax({
		type : "post",
		dataType : "json",
		url : AjaxURL,
		data : $('#kwform').serialize(),
		success : function(result) {
			// 如果dataType是text则这样写
			// $('#result').val(result.result);
			// //对json字符串进行解析
			// var obj = eval("("+result+")");
			// alert(obj);
			// 如果是jsonJS可以直接解析
			$('#result').val(result["result"]);
			// 将json反解析为字符串
			// alert("响应体json为：" + JSON.stringify(result));		
		if($('.valu2').val()!=null && $('.valu1').val()!=null){
			if(($('.math').val()=="/") && ($('.valu2').val()==0)){
				$(".tips").text("* "+result["msg"])
				$(".tips").show();
			}else{
				$(".tips").hide()
			}
		}else{
			$(".tips").text("* "+result["msg"])
			$(".tips").show();
		}
		}
	})
}

function getUserInfo() {
	var AjaxURL = "./GetUserInfo";
	$.ajax({
		type : "post",
		dataType : "json",
		url : AjaxURL,
		data : $('#loginForm').serialize(),
		success : function(result) {
			if (result["status"] == 200) {
				// 查询成功，把登录改成昵称
				$('#mask_layer')[0].style.display = "none";
				$('#login_div')[0].style.display = "none";
				$('#userIno')[0].text = result["nickname"];
				$('#userIno').attr("href","javascript:;");
				$('#logOut')[0].style.display = "block";
				setCookie('userIno', result["nickname"], 30);
				return true;
			} else {
				// 查询失败，提示
				$('#msg')[0].innerText = result["msg"];
				$('#logOut')[0].style.display = "none";
				return false;
			}
		},
		error : function() {
			return false;
		}
	})
}
// 保存登录状态
function setCookie(name, value, exdays) {
	var exp = new Date();
	exp.setTime(exp.getTime() + exdays * 24 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires="
			+ exp.toGMTString();
}
// 获取登录状态
function getCookie(name) {
	var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");
	if (arr = document.cookie.match(reg))
		return unescape(arr[2]);
	else
		return null;
}
function clearCookie(name) {
	setCookie(name, "", -1);
}
// 获取登录之后的用户名
function getuserName() {
	var getcookstatus = getCookie('userIno');
	if (getcookstatus != null) {
		$('#userIno')[0].text = getcookstatus;
		$('#logOut')[0].style.display = "block";
	} else {
		$('#userIno')[0].text = '登录'
		$('#logOut')[0].style.display = "none";
	}
}
function closezz(result) {
	$('#mask_layer')[0].style.display = "none";
	$('#login_div')[0].style.display = "none";
	if(result["status"] == 200){
		$('#logOut')[0].style.display = "block";	
	}else{
		$('#logOut')[0].style.display = "none";
	}
}
function showzz() {
	$('.LoginName,.Pwd').val("");
	$("#msg").text("");
	$('#mask_layer')[0].style.display = "block";
	$('#login_div')[0].style.display = "block";
}
function show(isshow, ele) {
	e = window.event || e;
	var s = e.toElement || e.relatedTarget;
	var reg = ele.compareDocumentPosition(s);
}
function login() {
	var AjaxURL = "./login";
	$.ajax({
		type : "post",
		dataType : "json",
		url : AjaxURL,
		data : $('#loginForm').serialize(),
		success : function(result) {
			if (result["status"] == 200) {
				getUserInfo();
				$('#mask_layer')[0].style.display = "none";
				$('#login_div')[0].style.display = "none";
				$('#logOut')[0].style.display = "block";
			} else {
				$('#msg')[0].innerText = result["msg"];
				$('#logOut')[0].style.display = "none";
			}
		},
		error : function() {
			$(function() {
			});
		}
	})
}
function clearNoNum(obj) {
	obj.value = obj.value.replace(/[^\d.]/g,""); //清除"数字"和"."以外的字符
        obj.value = obj.value.replace(/^\./g,""); //验证第一个字符是数字而不是
        obj.value = obj.value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的
        obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
        obj.value = obj.value.replace(/^(\-)*(\d+)\.(\d\d).*$/,'$1$2.$3'); //只能输入两个小数
}

function loginOut() {
	var AjaxURL = "./LoginOut";
	$.ajax({
		type : "get",
		dataType : "json",
		url : AjaxURL,
		data : $('#loginForm').serialize(),
		success : function(result) {
			if (result["status"] == 0) {
				$('#userIno').attr("href","javascript:showzz();");
				$('#userIno')[0].text = "登录";
				alert("注销成功！")
				clearCookie('userIno')
			}
		},
		error : function() {
			return false;
		}
	})
}
function reloadPage() {
	$('.valu1,.valu2,.res').val("");
	$(".tips").hide()
}