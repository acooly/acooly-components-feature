/**
 * acooly verify
 */
var acooly_verify = {

    regExp: function (value, pattern) {
        var re = new RegExp(pattern);
        var result = re.test(value);
        return re.test(value);
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
        //(15位)
        isIDCard1 = /^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/;
        //(18位)
        isIDCard2 = /^[1-9]\d{5}[1-9]\d{3}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])((\d{4})|\d{3}[A-Z])$/;
        return isIDCard1.test(value) || isIDCard2.test(value);
    },

    phone: function (value) {
        return this.regExp(value, "(^0\\d{2}-?\\d{8}$)|(^0\\d{3}-?\\d{7}$)|(^0\\d2-?\\d{8}$)|(^0\\d3-?\\d{7}$)");
    },

    mobile: function (value) {
        var reg = /^1[2|3|4|5|6|7|8|9]\d{9}$/;
        return reg.test(value);
    },

    account: function (value) {
        return !/^[a-zA-Z][\w]+$/.test(value);
    },

    chs: function (value) {
        return /^[\u0391-\uFFE5]+$/.test(value);
    },

    csv: function (value) {
        return /^[\w,\$,\{,\},_,\u4e00-\u9fa5]+(,[\w,\$,\{,\},_,\u4e00-\u9fa5]+)*$/.test(value)
    },
    money: function (value) {
        return /^-?(([1-9]\d{0,9})|0)(\.\d{1,2})?$/.test(value);
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
