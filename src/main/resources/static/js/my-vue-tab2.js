var myVueTab2 = {
    template:'<div><table class="table table-hover ">\n' +
        '<thead>\n' +
        '    <tr class="thead-light">\n' +
        '        <th>时间</th>\n' +
        '        <th>数额</th>\n' +
        '        <th>类型</th>\n' +
        '        <th>受邀人</th>\n' +
        '    </tr>\n' +
        '</thead>\n' +
        '<tbody>\n' +
        '    <tr v-if="pageInfo.pageContent.length <= 0">\n' +
        '        <td class="time text-center" colspan="4">暂无数据</td>\n' +
        '    </tr>\n' +
        '    <tr v-for="item in pageInfo.pageContent">\n' +
        '        <td class="time">{{item.invitedTime|formatDate}}</td>\n' +
        '        <td class="time">+10</td>\n' +
        '        <td class="time">INVITE</td>\n' +
        '        <td class="time">{{item.phoneNumber}}</td>\n' +
        '    </tr>\n' +
        '</tbody>\n' +
        '</table>\n'+
        '<ul class="pagination fs10" v-if="pageInfo.total">\n' +
        '<li class="page-item" :class="{disabled: pageInfo.pageNum===1}"><a class="page-link" href="javascript:;" @click="getPageData(1)">首页</a></li>\n' +
        '<li class="page-item" :class="{disabled: pageInfo.pageNum===1}"><a class="page-link" href="javascript:;" @click="getPageData(pageInfo.pageNum-1)">上一页</a></li>\n' +

        '<li v-for="index in range" class="page-item" :class="{active: index === pageInfo.pageNum}"><a class="page-link" href="javascript:;" @click="getPageData(index)">{{index}}</a></li>\n' +

        '<li class="page-item" :class="{disabled: pageInfo.pageNum===pageInfo.pages}"><a class="page-link" href="javascript:;" @click="getPageData(pageInfo.pageNum+1)">下一页</a></li>\n' +
        '<li class="page-item" :class="{disabled: pageInfo.pageNum===pageInfo.pages}"><a class="page-link" href="javascript:;" @click="getPageData(pageInfo.pages)">尾页</a></li>\n' +
        '</ul></div>',
    data:function(){
        return this.pageInfo;
    },
    props: {
        detailType:{
            default:function () {
                return 1;
            }
        },
        pageInfo:{
            type: Object,
            default:function(){
                return{
                    pageNum:1,//页码(当前页1到pages)
                    pageSize:10,//分页大小
                    total:0,//总条数
                    pages:1// 总页数
                }
            }

        },
        callback: {
            default: function() {
                return function callback() {
                    // todo
                }
            }
        }
    },
    filters:{
        formatDate:function (date) {
            return formatDate(date,"yyyy年MM月dd日");
        }
    },
    methods:{
        range:function (offset, limit) {
            var arr = [];
            for (var i = offset; i<=limit;i++){
                arr.push(i);
            }
            return arr;
        },
        getPageData:function (pageNmuber) {
            this.callback(pageNmuber, this.detailType)
        },
    },
    computed:{
        range(){
            var offset=0,limit=0;
            if(this.pageInfo.pages<7){
                offset = 1;
                limit = this.pageInfo.pages;
            }else {
                if(this.pageInfo.pageNum-3 <= 0){
                    offset = 1;
                    limit = 7;
                }else if(this.pageInfo.pageNum+3 > this.pageInfo.pages){
                    offset = this.pageInfo.pages-6;
                    limit = this.pageInfo.pages;
                }else {
                    offset = this.pageInfo.pageNum-3;
                    limit = this.pageInfo.pageNum+3;
                }
            }
            var arr = [];
            for (var i = offset; i<=limit;i++){
                arr.push(i);
            }
            return arr;
        }
    }
}