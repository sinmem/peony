var myTree = Vue.component("my-tree",
    {
        template: '<li :class="">\n' +
            '            <div :title="getTitle" \n' +
            '                    :class="getClass"\n' +
            '                    @click="toggle"\n' +
            '                    @dblclick="makeFolder">\n' +
            '                <span v-if="isFolder">[{{ isOpen ? \'-\' : \'+\' }}]</span>\n' +
            '                {{ item.content }}\n' +
            '            </div>\n' +
            '            <ul v-show="isOpen" v-if="isFolder">\n' +
            '                <my-tree \n' +
            '                        class="item"\n' +
            '                        v-for="(child, index) in item.children"\n' +
            '                        :key="index"\n' +
            '                        :item="child"\n' +
            // '                        @make-folder="$emit(\'make-folder\', $event)"\n' +
            // '                        @add-item="$emit(\'add-item\', $event)"\n' +
            '                ></my-tree>\n' +
            '            </ul>\n' +
            '        </li>',
        data: function () {
            return {
                isOpen: false,
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
                return this.item.children && this.item.children.length;
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
                }
            },
            makeFolder: function () {
                if (!this.isFolder) {
                    this.$emit("make-folder", this.item);
                    this.isOpen = true;
                }
            }
        }
    });