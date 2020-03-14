/**
 * 格式化日期
 * @param {Object} dateStr 要格式化的日期字符串
 * @param {Object} fmt 格式(例:yyyy-MM-dd hh:mm:ss 另S:毫秒,q:季度)
 */
function formatDate(dateStr, fmt) {
	if(dateStr == null || dateStr == undefined){
		return "-";
	}
	let date = new Date(dateStr);
	let o = {
		"M+": date.getMonth() + 1, //月份   
		"d+": date.getDate(), //日   
		"h+": date.getHours(), //小时   
		"m+": date.getMinutes(), //分   
		"s+": date.getSeconds(), //秒   
		"q+": Math.floor((date.getMonth() + 3) / 3), //季度   
		"S": date.getMilliseconds() //毫秒
	};
	if(/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
	for(var k in o)
		if(new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
}

/**
 * 格式化字节
 * @param {number} fileSize 字节大小(B)
 */
function formateSize(fileSize) {
	if(fileSize == null || fileSize == undefined){
		return "-";
	}
	let fmt = "";
	if(fileSize < 1024) {
		fmt = formatNumber(fileSize, 0) + 'B'
		return fmt
	} else if(fileSize < 1024*1024) {
		fmt = formatNumber(fileSize / parseFloat(1024), 0) + 'KB'
		return fmt
	} else if(fileSize < 1048576*1024){
		fmt = formatNumber(fileSize / parseFloat(1048576), 0) + 'MB'
		return fmt
	}else{
		fmt = formatNumber(fileSize / parseFloat(1073741824), 3) + 'GB'
		return fmt
	}
}

/**
 * 格式化数字
 * @param number：要格式化的数字
 * @param decimals：保留几位小数
 * @param dec_point：小数点符号
 * @param thousands_sep：千分位符号
 */
function formatNumber(number, decimals, dec_point, thousands_sep) {
	// 筛掉number中非数字部分
	number = (number + '').replace(/[^0-9+-Ee.]/g, '');
	// 声明并初始化变量,number(number)和decimal(int)声明为正数,
	// dec_point,thousands_sep未定义就默认为.和,
	// toFixedFix(n, prec)为保留小数点用(number, decimals)
	let n = !isFinite(+number) ? 0 : +number,
		prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
		sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
		dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
		s = '',
		toFixedFix = function(n, prec) {
			var k = Math.pow(10, prec);
			return '' + Math.ceil(n * k - 0.4) / k;
		};
	// 将保留小数等后的数字分割成整数部分和小数部分
	s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
	let re = /(-?\d+)(\d{3})/;
	// 匹配整数部分,可以有负号
	while(re.test(s[0])) {
		s[0] = s[0].replace(re, "$1" + sep + "$2");
	}
	//格式化小数部分,如果没小数部分就是俩0,如果就以为就在前面加0
	if((s[1] || '').length < prec) {
		s[1] = s[1] || '';
		// 用来加0的...
		s[1] += new Array(prec - s[1].length + 1).join('0');
	}
	return s.join(dec);
}

/**
 * 格式化头像
 * @param {Object} logourl 头像md5
 */
function logoWarrper(logourl){
	if(logourl == undefined || logourl == null || logourl == "") {
		return "../img/inco.png"
	} else {
		let logoDataURL = localStorage.getItem("logoDataURL");
		if(logoDataURL == undefined || logoDataURL == null || logoDataURL == "") {
			getlogo(logourl, function(url) {
				$("#LogoImage").attr("src", url);
			});
		} else {
			return logoDataURL;
		}
	}
}

/**
 * 从服务器获取头像二进制文件
 * @param {Object} logourl 头像md5码
 * @param {Object} fn 回调,参数为头像url
 */
function getlogo(logourl, fn) {
	$.get("http://www.YIDisk.com/fileTransfer/getUserLogo", {
		"fileMd5Code": logourl
	}, function(data) {
		data = JSON.parse(data)
		let flieArray = data.file
		let fileArrayBuffer = new Int8Array(flieArray.content).buffer
		let file = new File([fileArrayBuffer], flieArray.name, {
			type: "image/jpeg"
		})
		var reader = new FileReader();
		reader.readAsDataURL(file);
		reader.onloadend = function(e) {
			localStorage.setItem("logoDataURL", e.target.result);
			fn(e.target.result);
		};
	})
}