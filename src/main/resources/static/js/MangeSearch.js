let lawVue;
$(function () {
    lawVue = new Vue({
        el: "#result-list",
        data: {
            //查找条件寄存
            conditionList: [],
            fullName:0,
            tag:0,
            // 查找类型寄存
            currentType:0,
            // ...
            page: {},
            // 返回数据列表寄存
            tagList: [],
            fullNameList: [],
            lawList: [],
            // ...
            thisTag:"",
            thisTagId:0,
            // 分页信息
            // 分页查询地址
            actionURL:"",
            // 页码
            pageNum: 1,
            // 页面大小
            pageSize: 20,
            // 总页数
            pages: 0,
            // 总条数
            total: 0,
        },
        filters: {
            countFilter: function (count) {
                if(count>0){
                    return count+"条";
                }else {
                    return "暂无数据"
                }
            },
            nameFilter: function (name, maxSize) {
                if(name.length > maxSize){
                    name = name.substring(0,maxSize-1)+"...";
                }
                return name;
            },
            getfullName: function (lawList) {
                if(lawList.length >0){
                    return lawList[0]["fullName"];
                }
            },
            subContent: function (content, maxSize) {
                maxSize == undefined ? 10 : maxSize;
                if(content == undefined){
                    return "错误数据";
                }
                if(content.length > maxSize){
                    content = content.substring(0, maxSize)+"...";
                }
                return content;
            },
            formatCase: function (count) {
                if(count > 0)
                    return "已有案例";
                else
                    return "暂无案例"
            },
            formatRemark: function (count) {
                if(count > 0)
                    return "已有备注";
                else
                    return "暂无备注"
            }
        },
        methods: {
        }
    });
})

let oldValue = "",// 旧条件
    oldType = 0,// 旧条件类型
    conditions = [],// 条件列表
    oldcondition = "",
    returnable = false,
    clickable = false,
    byTypeFlage = false;//导航栏搜索按钮识别
    ;

function sFullName(input, incon) {
    let curretnValue = $(input).val().trim();
    if(!checkInput(curretnValue, 1))
        return;
    conditions = curretnValue;
    spinner(incon);
    showSearch(curretnValue);
    oldValue = curretnValue;
    // 60秒后删除旧值
    setTimeout(function () {
        oldValue = "";
    },60000);
    // 该条件已被查询, 不能继续搜索
    byTypeFlage = true;
    setTimeout(function () {
        byTypeFlage = false;
    },60000)
    oldType = 1;
    getFullName(incon);
}
function getFullName(incon) {
    lawVue.actionURL = "/web/v1/law/searchFullName";
    lawVue.currentType = 1;
    lawVue.conditionList = conditions;
    $.get(lawVue.actionURL,
        {"conditions":conditions.toString()},
        function (data) {
            check(incon);
            if(data.code == 0){
                showPageInfo(data.content);
                showFullName(data.content.pageContent);
                setpageInfo(data.content);
            }
            // console.log(data);
        },
        "json"
    ).fail(function (data) {
        check(incon);
    });
}
function sTags(input, incon){
    let curretnValue = $(input).val().trim();
    if(!checkInput(curretnValue, 3))
        return;
    conditions = curretnValue;
    spinner(incon);
    showSearch(curretnValue);
    oldValue = curretnValue;
    // 60秒后删除旧值
    setTimeout(function () {
        oldValue = "";
    },60000);
    // 该条件已被查询, 不能继续搜索
    byTypeFlage = true;
    setTimeout(function () {
        byTypeFlage = false;
    },60000)
    oldType = 3;
    getTags(incon);
}
function getTags(incon) {
    lawVue.actionURL = "/web/v1/tag/searchTags";
    lawVue.currentType = 3;
    lawVue.conditionList = conditions;
    $.get(lawVue.actionURL,
        {"conditions":conditions.toString()},
        function (data) {
            check(incon);
            if(data.code == 0){
                showPageInfo(data.content);
                showTags(data.content.pageContent);
                setpageInfo(data.content);
            }
            // console.log(data);
        },
        "json"
    ).fail(function (data) {
        check(incon);
    });
}
/**
 * 检查输入
 * @param curretnValue 当前输入的值
 * @param type 输入值类型(三种搜索类型)
 * @returns {boolean}
 */
function checkInput(curretnValue, type) {
    if(curretnValue == ""){
        alert("为空");
        return false;
    }else if(curretnValue == oldValue && oldType == type){
        alert("频繁重复提交! 请1分钟后重试")
        return false;
    }else{
        // 60秒后删除旧值
        setTimeout(function () {
            oldValue = "";
        },60000);
        lawVue.conditionList = conditions;
        oldcondition = conditions;
        // 有新条件检测通过, 搜索栏可以点击搜索
        byTypeFlage = false;
        return true;
    }
}

function showLawsByFullName(el) {
    let fullName = $(el).find("input[name=fullNameId]").val();
    console.log(fullName)
    lawVue.actionURL = "/web/v1/law/getLawsByFullName";
    lawVue.currentType = 2;
    lawVue.fullName = fullName;
    $.get(lawVue.actionURL,
        {"fullName":fullName},
        function (data) {
            console.log(data)
            showLaws(data.content.pageContent);
            setpageInfo(data.content);
            if(data.content.pageContent.length > 0){
                returnable = true;
                clickable = true;
                showORdisToolBar(".tool-bar span","")
            }else{
                returnable = true;
                clickable = false;
                showORdisToolBar(".tool-bar span.fa-arrow-left",".tool-bar span.fa-other")
            }
        },
        "json"
    )
}
function showLawsByTag(el) {
    let tag = $(el).find("input[name=tagId]").val();
    console.log(tag)
    lawVue.thisTag = $(el).find("input[name=tagName]").val();
    lawVue.thisTagId = $(el).find("input[name=tagId]").val();
    lawVue.actionURL = "/web/v1/law/getLawsByTag";
    lawVue.currentType = 2;
    lawVue.tag = tag;
    $.get(lawVue.actionURL,
        {"tag":tag},
        function (data) {
            console.log(data);
            showLaws(data.content.pageContent);
            setpageInfo(data.content);
            if(data.content.pageContent.length > 0){
                returnable = true;
                clickable = true;
                showORdisToolBar(".tool-bar span","")
            }else{
                returnable = true;
                clickable = false;
                showORdisToolBar(".tool-bar span.fa-arrow-left",".tool-bar span.fa-other")
            }
        },
        "json"
    )
}

function getMore() {
    console.log("getMore")
    let data = {
        "conditions":lawVue.conditionList.toString(),
        "pageNum": lawVue.pageNum = lawVue.pageNum >= lawVue.total? lawVue.total: ++lawVue.pageNum,
        "pageSize": lawVue.pageSize,
        "fullName":lawVue.fullName,
        "tag":lawVue.tag,
    };
    console.log(data)
    showSearch();
    showBtnSpinner();
    $.get(lawVue.actionURL, data, function (data) {
        console.log("currentType: "+ lawVue.currentType);
        console.log("actionURL: "+ lawVue.actionURL);
        console.log(data);
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
        setpageInfo(data.content)
    },"json")
}
// function setpageInfo(data) {
//     $("#footer div#morePage").show();
//     lawVue.pageNum = data.pageNum;
//     lawVue.pageSize = data.pageSize;
//     lawVue.pages = data.pages;
//     lawVue.total = data.total;
//     if(lawVue.pages == data.pageNum || lawVue.total == 0){
//         $("#footer #morePage").hide();
//     }
// }
function setpageInfo(data) {
    $("#footer div#morePage").show();
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
        $("#footer #morePage").html("<span class='btn btn-primary btn-small' onclick='getMore()'><span id='btn-spinner' class='fa fa-spinner fa-pulse'></span>加载更多</span>")
    }
    showPageInfo(data);
}


/*-------------------一堆修改操作-------------------*/
function updThisTag() {
    let arr = [];
    getSelected().forEach(function (item) {
        arr.push($(item).val())
    })
    if(arr.length == 0){
        parent.showLogModal("页面提示","选项不能为空")
        return;
    }
    let data = {
        "tagName":lawVue.thisTag,
        "oldTagId":lawVue.thisTagId,
        "lawIds":arr.toString(),
    }
    parent.showtagModal("修改标签-"+data["tagName"], "/web/v1/tag/mange/renameLawTag", data)
}
function delThisTag() {
    let arr = [];
    getSelected().forEach(function (item) {
        arr.push($(item).val())
    })
    if(arr.length == 0){
        parent.showLogModal("页面提示","操作成功3S后刷新页面")
        setTimeout(function () {
            parent.window.location.href = "/admin/MangeHome.html";
        },3000)
        return;
    }
    let data = {
        "oldTagId":lawVue.thisTagId,
        "lawIds":arr.toString(),
    }
    console.log(data);
    parent.showDialogModal("标签删除确认", "是否删除选中法条的标签","",function () {
        $.post("/web/v1/tag/mange/removeLawTag",data, function (data) {
            if(data.code == 0){
                parent.showLogModal("页面提示","操作成功3S后刷新页面")
                setTimeout(function () {
                    parent.window.location.href = "/admin/MangeHome.html";
                },3000)
            }else{
                parent.showLogModal("页面提示","操作失败: "+ data.message)
            }
        }, "json")
    })
}
function delThisCase() {
    let arr = [];
    getSelected().forEach(function (item) {
        arr.push($(item).val())
    })
    if(arr.length == 0){
        parent.showLogModal("页面提示","选项不能为空")
        return;
    }
    let data = {
        "lawIds":arr.toString(),
    }
    parent.showDialogModal("案例删除确认", "是否删除选中法条的案例","",function () {
        $.post("/web/v1/case/delCase",data, function (data) {
            if(data.code == 0){
                parent.showLogModal("页面提示","操作成功3S后刷新页面")
                setTimeout(function () {
                    parent.window.location.href = "/admin/MangeHome.html";
                },3000)
            }else{
                parent.showLogModal("页面提示","操作失败: "+ data.message)
            }
        }, "json")
    })
}
function delThisRemark() {
    let arr = [];
    getSelected().forEach(function (item) {
        arr.push($(item).val())
    })
    if(arr.length == 0){
        parent.showLogModal("页面提示","选项不能为空")
        return;
    }
    let data = {
        "lawIds":arr.toString(),
    }
    parent.showDialogModal("备注删除确认", "是否删除选中法条的备注","",function () {
        $.post("/web/v1/remark/delRemark",data, function (data) {
            if(data.code == 0){
                parent.showLogModal("页面提示","操作成功3S后刷新页面")
                setTimeout(function () {
                    parent.window.location.href = "/admin/MangeHome.html";
                },3000)
            }else{
                parent.showLogModal("页面提示","操作失败: "+ data.message)
            }
        }, "json")
    })
}
function adoptRemark() {
    let arr = getSelected();
    if(arr.length>1){
        parent.showLogModal("页面提示","该操作无法批量进行");
        return;
    }else if(arr.length === 0){
        parent.showLogModal("页面提示","请选择备注");
        return;
    }
    let data = {
        "id":$(arr[0]).val(),
        "submissionType": $(arr[0]).parent().find("input[name=submissionType]").val(),

    };
    console.log(data);
    parent.showDialogModal("备注通过确认", "点击确认通过备注","",function () {
        $.post("/web/v1/remark/adoptRemark",data, function (data) {
            if(data.code === 0){
                parent.showLogModal("页面提示","操作成功3S后刷新页面");
                setTimeout(function () {
                    parent.window.location.href = "/admin/MangeHome.html";
                },3000)
            }else{
                parent.showLogModal("页面提示","操作失败: "+ data.message)
            }
        }, "json")
    })
}
function rejectRemark() {
    let arr = getSelected();
    if(arr.length>1){
        parent.showLogModal("页面提示","该操作无法批量进行")
        return;
    }else if(arr.length == 0){
        parent.showLogModal("页面提示","选项不能为空")
        return;
    }
    let data = {
        "tempLegalRemarkId":$(arr[0]).val(),
    }
    console.log(data);
    parent.showDialogModal("备注驳回确认", "是否删除选中法条的标签","",function () {
        $.post("/web/v1/remark/rejectRemark",data, function (data) {
            if(data.code == 0){
                parent.showLogModal("页面提示","操作成功3S后刷新页面")
                setTimeout(function () {
                    parent.window.location.href = "/admin/MangeHome.html";
                },3000)
            }else{
                parent.showLogModal("页面提示","操作失败: "+ data.message)
            }
        }, "json")
    })
}

function getSelected() {
    return $("input:checked").get();
}
/*--------------------切换显示--------------------*/
function showORdisToolBar(el, del) {
    $(el).removeClass("disabled");
    $(del).addClass("disabled");
}

function showBtnSpinner() {
    $("#btn-spinner").show();
}
function hideBtnSpinner() {
    $("#btn-spinner").hide();
}
function check(el) {
    $(el).removeClass("fa-spinner fa-pulse").addClass("fa-check")
}
function spinner(el) {
    $(el).removeClass("fa-check").addClass("fa-spinner fa-pulse")
}
function showSearch(data) {
    $(".message-box").show();
    $("#searching").show();
    $("#pageInfo").hide();
    hideBtnSpinner();
}
function showPageInfo(data){
    $("#searching").hide();
    $("#pageInfo").show();
    hideBtnSpinner();
    let total = 0,
        showTotal = 0;
    total = data.total;
    showTotal = data.pageNum < data.pages ? data.pageSize * data.pageNum : total;
    $("#total").html(total);
    $("#showTotal").html(showTotal);
}

function showTags(data) {
    $("#law-list").addClass("d-none").hide();
    $("#legal-tag-list").show();
    lawVue.tagList = data;
}
function turnToTag() {
    $(".item-checkbox").prop("checked",false);
    changeCheckboxType();
    if(returnable){getTags();}else {return}
    returnable = false;
    clickable = false;
    showORdisToolBar("",".tool-bar span")
}
function showFullName(data){
    $("#law-list").addClass("d-none").hide();
    $("#legal-name-list").show();
    lawVue.fullNameList = data;
}
function turnToFullName(){
    $(".item-checkbox").prop("checked",false);
    changeCheckboxType();
    if(returnable){getFullName();}else {return}
    returnable = false;
    clickable = false;
    showORdisToolBar("",".tool-bar span")
}
function showLaws(data) {
    $("#law-list").removeClass("d-none").show();
    $("#legal-name-list").hide();
    $("#legal-tag-list").hide();
    lawVue.lawList = data;
}
function showLawTagbox(element) {
    $(element).parent().parent().find(".law-tags-box").show();
    $(element).parent().parent().find(".law-tags-bar").hide();
}
function showLawTagbar(element) {
    $(element).parent().parent().find(".law-tags-box").hide();
    $(element).parent().parent().find(".law-tags-bar").show();
}
/*---------------复选框---------------*/
let isCheckAll = false;
function chooseAll(){
    if(isCheckAll){
        isCheckAll = false;
        $(".result-content .item-checkbox").prop("checked", false);
        $(".result-content .chooseAll span").removeClass("fa-check-square").addClass("fa-square-o");
    }else{
        isCheckAll = true;
        $(".result-content .item-checkbox").prop("checked", true);
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
        isCheckAll = false;
        $(".result-content .chooseAll span").removeClass("fa-check-square").addClass("fa-square-o");
    }
}