
//<editor-fold desc="Description">

//</editor-fold>

//<editor-fold desc="一部分">
function showLawTagbox(element) {
    $(element).parent().parent().find(".law-tags-box").show(450);
    $(element).parent().parent().find(".law-tags-bar").hide(500);
}
function showLawTagbar(element) {
    $(element).parent().parent().find(".law-tags-box").hide(485);
    $(element).parent().parent().find(".law-tags-bar").show(500);
}
function showLawCase(element) {
    let lawId = $(element).parent().prevAll("input[name=lawId]").val(),
        index = $(element).parent().prevAll("input[name=index]").val();
    console.log("lawId1: "+$(element).parent().prevAll("input[name=lawId]").val())
    console.log("index: "+index)
    $.get("/web/v1/case/getCase",{"lawId":lawId},function(data){
        let legalCase = data.content;
        console.log(data)
        data = lawVue.lawList[index];
        data["legalCase"] = legalCase;
        lawVue.lawList.splice(index,1,data);
    },"json")
    $(element).parent().find(".case-container").show(500);
    $(element).parent().find(".fa.fa-angle-double-up").show();
    $(element).parent().find(".fa.fa-angle-double-down").hide();
}
function hideLawCase(element) {
    $(element).parent().find(".case-container").hide(500);
    $(element).parent().find(".fa-angle-double-down").show();
    $(element).parent().find(".fa-angle-double-up").hide();
}
//</editor-fold>


//<editor-fold desc="--------以下编辑于vscode-------">
/**
 * 根据数据错误
 * @param data
 */
function showError(data) {
    showLogModal("添加标签失败", data.message)
}
function showSussess(data) {
    showLogModal("添加标签成功",data.message)
}
function cancelTag(el) {
    $(el).parent().parent().find("input").val('')
    $(".tagName-box").hide(500);
}

function addTag(el) {
    $("#DialogModal input[name=name]").addClass("btn btn-outline-primary")
    let name = $(el).parent().parent().find("input").val();
    showDialogModal("标签提交确认","将为选中法条添加此标签: ",name, function(){
        let tempLaw = $(".result-content  .item-checkbox:checked").get();
        let lawIds = [];
        tempLaw.forEach(function(item, index){
            lawIds.push($(item).val().trim());
        })

        let data = {
            "tagName":name,
            "lawIds":lawIds.toString()
        }
        console.log(data)
        // 这里不是分页查询的地址所以不能付给lawVue.actionURL
        $.post("/web/v1/tag/mange/addLawTag",data, function(data){
            cancelTag(el);
            let status = data.code;
            if(status == 0){
                showSussess(data)
            }else {
                showError(data)
            }
            console.log(data)
        },"json")
    });
}

function addCase(el) {
    window.location.href = "/laws/addCase.html?lawId="+$(el).parent().prevAll("input[name=lawId]").val();
    // showCaseModal(el, "添加案例","/web/v1/case/addCase");
}

function showCase(el) {
    window.location.href = "/laws/case.html?lawId="+$(el).parent().prevAll("input[name=lawId]").val();
}

function updCase(el) {
    showCaseModal(el, "修改案例","/web/v1/case/updCase/");
}

function showLawModal(){
    $("#LawModal").modal("show");
    $("#LawModal .modal-title").html("法条");
}

function showLegalModal(fn1,fn2){
    $("#LegalModal").modal("show");
    $("#LegalModal .modal-title").html("法条修改");
    let btnOver = $("#LegalModal .btnOver");
    let btnUpd = $("#LegalModal .btnUpd");
    // 要取消模态框中确认按钮先前绑定的事件
    btnOver.unbind("click")
    btnOver.bind('click', function () {
        let data = getData();
        if(!data){
            return;
        }
        fn1(data);
        $("#LegalModal").modal("hide");
    });
    btnUpd.unbind("click")
    btnUpd.bind('click', function () {
        let data = getData();
        if(!data){
            return;
        }
        fn2(data);
        $("#LegalModal").modal("hide");
    });
}

function getData() {
    let tempLaw = $(".result-content  .item-checkbox:checked").get();
    if(tempLaw.length<=0){
        $("#LegalModal").modal("hide");
        $popup.toast("请先勾选法条", 1);
        setTimeout(function () {
            $("#LegalModal").modal("show");
        },1000)
        return false;
    }
    let lawIds = [];
    tempLaw.forEach(function(item, index){
        lawIds.push($(item).val().trim());
    });
    let data = {
        oldLaws:lawIds.toString(),
        content:$("#LegalModal #LawContent").val().trim(),
        legalId:$("#LegalModal #legalName").val(),
        releaseTime:Number($("#LegalModal #releaseTime").val()),
        no:$("#LegalModal #legalNo").val().trim()
    }
    if(isNaN(data.releaseTime)||data.releaseTime<2000){
        $("#LegalModal").modal("hide");
        $popup.toast("请输入正确发布时间", 1);
        setTimeout(function () {
            $("#LegalModal").modal("show");
        },1000)
        return false;
    }
    if(data.content.length<=0){
        $("#LegalModal").modal("hide");
        $popup.toast("请输入正文内容", 1);
        setTimeout(function () {
            $("#LegalModal").modal("show");
        },1000)
        return false;
    }
    return data;
}
/**
 * @param el 被选中的元素
 * @param title 标题
 * @param action 目标地址
 */
function showCaseModal(el, title, action) {
    $("#CaseModal").modal("show");
    $("#CaseModal .modal-title").html(title);
    let btn = $("#CaseModal .btnSure");
    // 要取消模态框中确认按钮先前绑定的事件
    btn.unbind("click")
    btn.bind('click', function () {
        let legalCase = $("#legalCase").val(),
            caseURL = $("#url").val();
        $("#legalCase").val("");
        $("#url").val("");
        let data = {
            "name": legalCase,
            "url": caseURL,
            "lawId": $(el).parent().prevAll("input[name=lawId]").val()
        };
        // console.log($(el).parent().prev().get())
        // console.log(data)
        showDialogModal("案例提交确认", "案例: ", legalCase+":("+caseURL+")", function () {
            $.post(action, data, function (data) {
                showLogModal("页面提示", title+"成功")
                console.log(data);
            }, "json")
        })
        $("#CaseModal").modal("hide");
    })

}

/**
 *
 * @param title 标题
 * @param label 抬头
 * @param content 内容
 * @param callback 回调函数
 */
function showDialogModal(title, label, content, callback) {
    $("#DialogModal .modal-title span").html(title);
    $("#DialogModal .modal-body label").html(label);
    $("#DialogModal .modal-body span#name").html(content);
    $("#DialogModal").modal("show");
    let btn = $("#DialogModal .btnSure");
    // 要取消模态框中确认按钮先前绑定的事件
    btn.unbind("click")
    btn.bind('click', function () {
        content = $("#DialogModal input[name=name]").val();
        if(callback){
            callback.call(this, content);
        }
        $("#DialogModal").modal("hide");
    })
}

function showLogModal(title, msg) {
    $("#logModal .modal-title").html(title);
    $("#logModal .modal-body").html(msg);
    $("#logModal").modal("show");

}

$(function(){
    let dialog=$("#DialogModal .modal-dialog");
    //修改窗口宽度
    dialog.css("width","500px");
    valign(dialog,0);
    dialog=$("#logModal .modal-dialog");
    //修改窗口宽度
    dialog.css("width","500px");
    valign(dialog,0);
    dialog=$("#CaseModal .modal-dialog");
    dialog.css("width","500px");
    valign(dialog,0);

    dialog=$("#LawModal .modal-dialog");
    dialog.css("width","500px");
    valign(dialog,200);


})
/**
 * 模态框水平居中
 * @param {Object} dialog 那个模态框
 * @param {Number} revised 高度调整修正值(向上为正)
 */
function valign(dialog, revised){
    revised = Number.parseInt(revised);
    //获得浏览器视口高度
    var height=$(window).height();
    //设置窗口居中
    dialog.css("margin-top",(height-200-revised)/2+"px")
}

let checkAllFlage = false;
function chooseAll(){
    if(checkAllFlage){
        checkAllFlage = false;
        $(".result-content  .item-checkbox").prop("checked", false);
        $(".result-content .chooseAll span").removeClass("fa-check-square").addClass("fa-square-o");
    }else{
        checkAllFlage = true;
        $(".result-content  .item-checkbox").prop("checked", true);
        $(".result-content .chooseAll span").removeClass("fa-square-o").addClass("fa-check-square");
    }
    changeCheckboxType();
}
function checkedThis(element,event){
    $(element).parent().find(".item-checkbox").click()
    changeCheckboxType();
}
function changeCheckboxType(){
    $(".result-content  .item-checkbox").parent().parent().find(".itemCheckbox span").removeClass("fa-check-square").addClass("fa-square-o");
    $(".result-content  .item-checkbox:checked").parent().parent().find(".itemCheckbox span").removeClass("fa-square-o").addClass("fa-check-square");
    if($(".result-content  .item-checkbox:checked").get().length <= 0){
        checkAllFlage = false;
        $(".result-content .chooseAll span").removeClass("fa-check-square").addClass("fa-square-o");
    }
}
function addTagInpu(){
    let tagList = $(".result-content  .item-checkbox:checked");
    if(tagList.get().length>0){
        $(".tagName-box").show(500);
        let lawIds = tagList.serialize();
    }else{
        alert("请先选择法条")
    }
}

function getMore() {
    console.log("getMore")
    let data = {
        "conditions":lawVue.conditionList.toString(),
        "pageNum": lawVue.pageNum = lawVue.pageNum >= lawVue.total? lawVue.total: ++lawVue.pageNum,
        "pageSize": lawVue.pageSize,
        "fullName":lawVue.fullName,
        "tagIds":lawVue.tagIds,
        "tag":lawVue.tag,
        "tags": JSON.stringify(lawVue.tags)
    };
    console.log(data)
    showSearch();
    $.get(lawVue.actionURL, data, function (data) {
        console.log("currentType: "+ lawVue.currentType);
        console.log("actionURL: "+ lawVue.actionURL);
        if(lawVue.currentType == 0){
            return;
        }else if(lawVue.currentType === 1){
            data.content.pageContent.forEach(function (item) {
                lawVue.fullNameList.push(item)
            })
        }else if(lawVue.currentType === 2){
            data.content.pageContent.forEach(function (item) {
                lawVue.lawList.push(item)
            })
        }else if(lawVue.currentType === 3){
            data.content.pageContent.forEach(function (item) {
                lawVue.tagList.push(item)
            })
        }else if(lawVue.currentType === 4){
            let arr = data.content;
            arr.forEach(function (item, index) {
                showLaws(item.laws, true);
            })
        }
        // console.log(data.content.pageContent)
        setpageInfo(data.content)
        // console.log(lawVue.lawList)
    },"json")
}
function setpageInfo(data) {
    // console.log("setpageInfo-start")
    // console.log(data)
    $("#footer div#morePage").show()
    lawVue.pageNum = data.pageNum;
    lawVue.pageSize = data.pageSize;
    lawVue.pages = data.pages;
    lawVue.total = data.total;
    console.log(lawVue.pages +" : "+ data.pageNum);
    if(lawVue.pages > 0 && data.pageNum > 0){
        $("#footer #morePage").html("<span class='btn btn-outline-secondary btn-small disabled' disabled>没有更多了</span>")
    }
    if(lawVue.pages == data.pageNum){
        $("#footer #morePage").html("<span class='btn btn-outline-secondary btn-small disabled' disabled>没有更多了</span>")
    }else if(lawVue.pages > data.pageNum){
        $("#footer #morePage").html("<span class='btn btn-primary btn-small' onclick='getMore()'>加载更多</span>")
    }
    // console.log("setpageInfo-end")
    showPageInfo();

}
//</editor-fold>