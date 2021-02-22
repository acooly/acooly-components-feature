// 货币：两位小数
Inputmask.extendAliases({
    'money': {
        prefix: "",
        rightAlign: false,
        groupSeparator:'',
        alias: "currency"
    },
    'integer': {
        alias: "numeric",
        digits: 0,
        rightAlign: false,
        radixPoint: ""
    },
    'percent': {
        alias: "percentage",
        rightAlign:false,
        suffix: ''
    },
    'bankCard' : {
        mask:"9999 9999 9999 (9{1,3})|(9{4} 9{0,3})|(9{4} 9{4} 9{0,3})",
        autoUnmask:true
    },
    'bankcard' : {
        alias:"bankCard",
    },
    'mobile' : {
        mask:"99999999999",
        autoUnmask:true
    },
    'cert': {
        mask:"99999999999999999*",
        autoUnmask: true
    },
    'idcard': {
        alias:"cert"
    },
    'account':{
        mask:"a*{0,31}",
        autoUnmask: false,
        definitions: {
            "a": {
                validator: "[A-Za-z]"
            }
        }
    },
    url: {
        regex: "https{0,1}://.*",
        autoUnmask: false
    }
});
