// 货币：两位小数
Inputmask.extendAliases({
    'money': {
        prefix: "",
        rightAlign: false,
        alias: "currency"
    },
    'integer': {
        alias: "numeric",
        digits: 0,
        rightAlign: false,
        radixPoint: ""
    },
    'bankCard' : {
        mask:"9999 9999 9999 (9{1,3})|(9{4} 9{0,3})|(9{4} 9{4} 9{0,3})",
        autoUnmask:true
    },
    'mobile' : {
        mask:"99999999999",
        autoUnmask:true
    },
    'account': {
        regex: $.acooly.verify.pattens.account
    },
    'cert': {
        regex: $.acooly.verify.pattens.cert18
    }
});
