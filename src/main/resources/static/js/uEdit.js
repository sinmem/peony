let $popup = new popup();
function toast(msg, time, fn) {
    $popup.toast(msg, time);
    // debugger
    if (fn)
        setTimeout(function () {
            fn();
        }, time * 1000);
}

let ue;
$(function () {
    ue = UE.getEditor('editor', {
        toolbars: [
            [
                'undo', //撤销
                'redo', //重做
                'bold', //加粗
                'italic', //斜体
                'underline', //下划线
                'fontborder',//字体边框
                'strikethrough',//字体删除线,与下划线互斥
                'subscript',//下标文本，与“superscript”命令互斥
                'superscript',//	上标文本，与“subscript”命令互斥
                'formatmatch',
                'removeformat',
                'forecolor',//	字体颜色
                'backcolor',//背景
                'horizontal', //分隔线
                'inserttitle', //插入标题
                'fontfamily', //字体
                'fontsize', //字号
                'paragraph', //段落格式
                'inserttable', //插入表格
                'emotion', //表情
                'justifyleft', //居左对齐
                'justifyright', //居右对齐
                'justifycenter', //居中对
                'justifyjustify', //两端对齐
                'fullscreen', //全屏
                'edittip ', //编辑提示
                'customstyle', //自定义标题
                'preview', //预览
                'cleardoc', //清空文档
            ]
        ]
    });
});

function deleteEditor() {
    setContent("", false);
    $("#editor").hide();
}

function getContent() {
    return ue.getContent();
}

function setContent(content, isAppendTo) {
    ue.ready(function () {
        ue.setContent(content, isAppendTo);
    })
}

function sureSubmit(url, cb) {
    $(".put-remark").show();
    $(".submit-remark").hide();
    let data = {
        "content": getContent(),
        "lawId": rVue.law.id,
    };
    $.post(url, data, function (data) {
        if (data.code === 0) {
            toast("提交成功,请耐心等待审核! 2秒后跳转至首页", 1, function () {
                if(cb){
                    cb();
                }else {
                    window.location.href = "/";
                }

            })
        }
    }, "json")
}

/**
 * @return {string}
 */
function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]);
    return null;
}