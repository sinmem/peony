$(function() {
	$("input[type='password'][data-eye]").each(function(i) {
		let $this = $(this);

		$this.wrap($("<div/>", {
			style: 'position:relative'
		}));
		$this.css({
			paddingRight: 60
		});
		$this.after($("<div/>", {
			html: 'Show',
			class: 'btn btn-primary btn-sm',
			id: 'passeye-toggle-'+i,
			style: 'position:absolute;right:10px;top:50%;transform:translate(0,-50%);padding: 2px 7px;font-size:12px;cursor:pointer;'
		}));
		$this.after($("<input/>", {
			type: 'hidden',
			id: 'passeye-' + i
		}));
		$this.on("keyup paste", function() {
			$("#passeye-"+i).val($(this).val());
		});
		$("#passeye-toggle-"+i).on("click", function() {
			if($this.hasClass("show")) {
				$this.attr('type', 'password');
				$this.removeClass("show");
				$(this).removeClass("btn-outline-primary");
				$(this).addClass("btn-primary");
			}else{
				$this.attr('type', 'text');
				$this.val($("#passeye-"+i).val());
				$this.addClass("show");
				$(this).removeClass("btn-primary");
				$(this).addClass("btn-outline-primary");
			}
		});
	});
});



function getVerifyCode(el, smsType) {
	if(!checkPhone()) return;
	var time = 60;
	$(el).html("60(s)").removeClass("btn-primary").addClass("my-disabled").prop("title","验证码已发送如需重新发送请60S后重试");
	countdown(el, time);
	if($(el).hasClass("my-disabled")){
		sendSms(smsType);
	}
}

function sendSms(smsType) {
	smsType = smsType?smsType:4;
	var data = {
		"phoneNumber":$("input[name=phone]").val().trim(),
		"type": smsType,
	};
	var Popup = new popup();
	$.get("/user/auth/getVerifyCode",data,
		function (data) {
		console.log(data);
			if(data.code === 0){
				Popup.toast("发送成功, 请及时使用",1);
			}else {
				Popup.toast("发送失败\n"+data.message,4);
			}
		},"json")
		.fail(function (e) {
		Popup.toast("登录失败",3);
	});
}

function countdown(el, time) {
	if (time>0){
		time--;
		$(el).html(time+"(s)");
		setTimeout(function () {
			countdown(el, time)
		}, 1000)
	}else{
		$(el).html("获取验证码").removeClass("my-disabled").addClass("btn-primary").prop("title","");
		return;
	}
}

// 验证手机号
function isPhoneNo(phone) {
	var pattern = /^1[345789]\d{9}$/;
	return pattern.test(phone);
}

function isChecked(el) {
	return $(el).is(":checked");
}

function checkPhone() {
	var phone = $(".form-group input[name=phone]").val().trim();
	if(!isPhoneNo(phone)){
		setTip($(".phoneTip"), "X请填写正确手机号X");
		return false;
	}else {
		removeTip($(".phoneTip"));
		return true;
	}
}

function checkNull(el, textLabel, title){
	var $tip = $("."+textLabel+"Tip");
	if( $(el).val() === ""){
		setTip($tip,title+"不能为空");
		return false;
	}else {
		removeTip($tip);
		return true;
	}
}

function checkNullAndBlank(el, textLabel, title){
	var $tip = $("."+textLabel+"Tip");
	if($(el).val().trim() === ""){
		setTip($tip,title+"不能为空");
		return false;
	}else {
		removeTip($tip);
		return true;
	}
}

function checkAgree(el, title) {
	var $tip = $(".agreeTip");
	console.log("isChecked($tip)", isChecked($tip));
	if(isChecked(el)){
		removeTip($tip);
		return true;
	}else {
		setTip($tip,"请勾选"+title);
		return false;
	}
}
function setTip(el, msg) {
	$(el).html(msg);
}
function removeTip(el) {
	$(el).html("").hide();
}