var myVuePage = {
    template:'<ul class="pagination">\n' +
        '<li class="page-item" :class="{disabled: pageNum!==1}"><a class="page-link" href="javascript:;" @click="getPageData(1)">首页</a></li>\n' +
        '<li class="page-item" :class="{disabled: pageNum!==1}"><a class="page-link" href="javascript:;" @click="getPageData(pageNum-1)">上一页</a></li>\n' +

        '<li v-for="index in range(offset, limit)" class="page-item" :class="{active: index === pageNum}"><a class="page-link" href="javascript:;" @click="getPageData(index)">{{index}}</a></li>\n' +

        '<li class="page-item" :class="{disabled: pageNum!==pages}"><a class="page-link" href="javascript:;" @click="getPageData(pageNum+1)">下一页</a></li>\n' +
        '<li class="page-item" :class="{disabled: pageNum!==pages}"><a class="page-link" href="javascript:;" @click="getPageData(pages)">尾页</a></li>\n' +
        '                    </ul>',
    data:function(){
        if(this.pageInfo.pages<7){
            this.pageInfo.offset = 1;
            this.pageInfo.limit = this.pageInfo.pages;
        }else {
            var offset=1,limit=7;
            if(this.pageInfo.pageNum-3 < 0){
                this.pageInfo.offset = 1;
                this.pageInfo.limit = 7;
            }else if(this.pageInfo.pageNum+3 > this.pageInfo.pages){
                this.pageInfo.offset = this.pageInfo.pages-6;
                this.pageInfo.limit = this.pageInfo.pages;
            }else {
                this.pageInfo.offset = this.pageInfo.pageNum-3;
                this.pageInfo.limit = this.pageInfo.pageNum+3;
            }
        }
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
        }
    },
    computed:{
        // 分页大小 获取的时候显示父级传入的，修改的时候修改自身的。子组件不能修改父元素的值
        pagesize:{
            get:function(){
                return this.pagerData.page.pageSize;
            },
            set:function(value){
                this.pagerData.page.pageSize = value;
                this.pagerData.page.pageNum = 1;
                this.callback(1)
            }
        },
        pageCurrent:{
            get:function(){
                return this.pagerData.page.pageNum;
            },
            set:function(value){
                this.pagerData.page.pageNum = value;
            }
        }
    }
}