function containArr(id, arr) {
    if (!arr) return false;
    for (var i = 0; i < arr.length; i++) {
        if (arr[i] === id) {
            return true;
        }
    }
    return false;
}

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
            '                        :parents="getParents"\n' +
            '                        @select-data="$emit(\'select-data\', $event)"\n' +
            // '                        @add-item="$emit(\'add-item\', $event)"\n' +
            '                ></my-treec>\n' +
            '            </ul>\n' +
            '        </li>',
        data: function () {
            if (containArr(this.item.id, this.parents.arr)) {
                return {
                    isOpen: true,
                };
            } else if (!this.parents.arr) {
                if (this.item.parent === 0 || (this.item.style === "catalog" && (this.item.extra === "catalog1" || this.item.extra === "catalog2"))) {
                    return {
                        isOpen: true,
                    };
                }
            }
            return {
                isOpen: false,
            };

        },
        props: {
            item: {
                type: Object,
                default: function () {
                    return {};
                }
            },
            parents: {
                type: Object,
                default: function () {
                    return {
                        arr: []
                    };
                }
            }
        },
        computed: {
            getParents() {
                return this.parents;
            },
            isFolder: function () {
                return this.item.style === "catalog";
            },
            getClass: function () {
                return this.item.myclass + (this.isFolder ? " bold" : "");
            },
            getTitle: function () {
                return this.item.title ? this.item.title : "";
            },
            _lawId: function () {
                let extra = this.item.extra;
                if (extra && extra.trim().length > 0) {
                    let extraJson = JSON.parse(extra);
                    return extraJson.lawId || -1;
                }
                return -1;
            }
        },
        methods: {
            toggle: function () {
                if (this.isFolder) {
                    this.isOpen = !this.isOpen;
                } else if (this.item.style === "law") {
                    window.open('remark.html?lawId=' + this._lawId);
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