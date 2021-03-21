function User(option) {
    if(!(this instanceof User)){
        return new User(option);
    }

    //这里或者抛出一个错误的信息也可以
    if(!(this instanceof User)){
        throw new Error('User instantiation error');
    }


    this.username = '';
    this.phoneNumber = '';
    this.roles = [];
    // this.id = '';
    // this.accountStatus = '';
    this.init(option)
}

User.prototype  = {
    constructor: User,
    init: function (option) {
        this.username = option.username || '';
        this.phoneNumber = option.phoneNumber || '';
        this.roles = toRoles(option.roles || []);
        // this.id = option.id || '';
        // this.accountStatus = option.accountStatus || '';
        return this;   //链式操作的核心，就可以在
    },

};

let toRoles = function (roles) {
    let result = [];
    for (let item in roles) {
        if(roles[item]&&roles[item].role){
            result.push(roles[item].role)
        }
    }
    return result;

}
