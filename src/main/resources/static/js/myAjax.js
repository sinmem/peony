/**
 * 简单校验手机号,验证码,密码
 */
function checkPCP() {
    return checkPhone()&&
        checkNullAndBlank($("input[name=validateCode]"), "validateCode", "验证码")&&
        checkNull($("input[name=password]"), "password", "密码")
}

function toHref(href, time) {
    setTimeout(function () {
        window.location.href=href;
    },time*1000)
}