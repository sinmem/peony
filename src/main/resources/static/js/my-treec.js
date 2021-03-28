var myTreec = Vue.component("my-treec",
    {
        template: '<li :class="" style="cursor:pointer;">\n' +
            '            <div :title="getTitle" \n' +
            '                    :class="getClass"\n' +
            '                    @click="toggle"\n' +
            '                    @dblclick="makeFolder">\n' +
            '                <span v-if="isFolder">[{{ isOpen ? \'-\' : \'+\' }}]</span>\n' +
            '                {{ item.content }}\n' +
            '            </div>\n' +
            '            <ul v-show="isOpen" v-if="isFolder">\n' +
            '                <my-treec \n' +
            '                        class="item"\n' +
            '                        v-for="(child, index) in item.children"\n' +
            '                        :key="index"\n' +
            '                        :item="child"\n' +
            '                        @select-data="$emit(\'select-data\', $event)"\n' +
            // '                        @add-item="$emit(\'add-item\', $event)"\n' +
            '                ></my-treec>\n' +
            '            </ul>\n' +
            '        </li>',
        data: function () {
            return {
                isOpen: true,
            };
        },
        props: {
            item: {
                type: Object,
                default: function () {
                    return {}
                }
            }
        },
        computed: {
            isFolder: function () {
                return this.item.style === "catalog";
            },
            getClass:function () {
                return this.item.myclass+(this.isFolder?" bold":"");
            },
            getTitle:function () {
                return this.item.title?this.item.title:"";
            }
        },
        methods: {
            toggle: function () {
                if (this.isFolder) {
                    this.isOpen = !this.isOpen;
                }else if(this.item.extra==="Legal"){
                    console.log(encodeURI(this.item.title));
                    window.open('remark.html?lawId=' + this.item.id);
                }
                this.$emit("select-data", this.item);
            },
            makeFolder: function () {
                if (!this.isFolder) {
                    this.$emit("make-folder", this.item);
                    this.isOpen = true;
                }
            }
        }
    });