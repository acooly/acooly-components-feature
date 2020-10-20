/**
 * acooly verify
 */
var acooly_verify = {


    pattens: {
        "cert15": /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/,
        "cert18": /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[x,X])$/,
        "phone": /^(^0\d{2}-?\d{8}$)|(^0\d{3}-?\d{7}$)|(^0\d2-?\d{8}$)|(^0\d3-?\d{7}$)$/,
        "mobile": /^1[2|3|4|5|6|7|8|9]\d{9}$/,
        "account": /^[a-zA-Z][\w]+$/,
        "member": /^[a-zA-Z0-9]+$/,
        "chs": /^[\u0391-\uFFE5]+$/,
        "csv": /^[\w,\$,\{,\},_,\u4e00-\u9fa5]+(,[\w,\$,\{,\},_,\u4e00-\u9fa5]+)*$/,
        "money": /^-?(([1-9]\d{0,9})|0)(\.\d{1,2})?$/
    },


    regExp: function (value, pattern) {
        var re = new RegExp(pattern);
        var result = re.test(value);
        return re.test(value);
    },

    regVerify: function (value, patternName) {
        return this.pattens[patternName].test(value);
    },

    byteLength: function (value, minlen, maxlen) {
        if (value == null) {
            return false;
        }
        var l = value.length, blen = 0;
        for (i = 0; i < l; i++) {
            if ((value.charCodeAt(i) & 65280) != 0) {
                blen++;
            }
            blen++;
        }
        return !(blen > maxlen || blen < minlen);
    },

    cert: function (value) {
        return this.pattens.cert15.test(value) || this.pattens.cert18.test(value);
    },

    phone: function (value) {
        return this.pattens.phone.test(value);
    },

    mobile: function (value) {
        return this.pattens.mobile.test(value);
    },

    account: function (value) {
        return this.pattens.account.test(value);
    },

    member: function (value) {
        return this.pattens.member.test(value);
    },

    chs: function (value) {
        return this.pattens.chs.test(value);
    },

    csv: function (value) {
        return this.pattens.csv.test(value)
    },
    money: function (value) {
        return this.pattens.money.test(value);
    },

    number: function (value) {
        return /^[0-9]*$/.test(value);
    },

    decimal: function (value) {
        return /^(-?\d+)(\.\d+)?$/.test(value);
    },

    password_security_level: {
        higRegex: "^(?=.{8,})(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*\\W).*$",
        midRegex: "^(?=.{8,})(((?=.*[A-Z])(?=.*[a-z]))|((?=.*[A-Z])(?=.*[0-9]))|((?=.*[a-z])(?=.*[0-9]))).*$",
        lowRegex: "(?=.{8,}).*"
    },

    /**
     * 验证码密码等级
     * @param password 密码
     * @returns {number} 等级（0：不合格,1:低，2:中，3:高）
     */
    password: function (password) {
        if (new RegExp(this.password_security_level.higRegex, "g").test(password)) {
            return 3;
        } else if (new RegExp(this.password_security_level.midRegex, "g").test(password)) {
            return 2;
        } else if (new RegExp(this.password_security_level.lowRegex, "g").test(password)) {
            return 1;
        } else {
            return 0;
        }
    },

    /**
     * 验证JSON格式
     * @param jsonString
     */
    json: function (jsonString) {
        try {
            JSON.parse(str);
            return true;
        } catch (e) {
            return false;
        }
    }


};


(function ($) {
    if (!$.acooly) {
        $.extend({acooly: {}});
    }

    $.extend($.acooly, {
        verify: acooly_verify
    });

})(jQuery);
