/**
 * 扩展validatebox的验证规则
 * @author zhangpu
 */
$.extend($.fn.validatebox.defaults.rules, {
    /** byte长度验证 */
    byteLength: {
        validator: function (value, param) {
            let minlen = param[0];
            let maxlen = param[1];
            return $.acooly.verify.byteLength(value, minlen, maxlen);
        },
        message: '输入的数据长度超出范围.'
    },
    fixedLength: {
        validator: function (value, param) {
            let fixedLength = param[0];
            return $.acooly.verify.byteLength(value, fixedLength, fixedLength);
        },
        message: '输入的数据长度只能为{0}.'
    },
    number: {
        validator: function (value, param) {
            var min = (param && param[0])?param[0]:0;
            var max = (param && param[1])?param[1]:999999999;
            return $.acooly.verify.number(value) && value >= min && value <= max;
        },
        message: '输入的数字超出范围.'
    },
    integer: {
        validator: function (value, param) {
            var min = (param && param[0])?param[0]:0;
            var max = (param && param[1])?param[1]:999999999;
            return $.acooly.verify.number(value) && value >= min && value <= max;
        },
        message: '输入的数字超出范围.'
    },
    decimal: {
        validator: function (value, param) {
            var min = (param && param[0])?param[0]:0;
            var max = (param && param[1])?param[1]:999999999;
            return $.acooly.verify.decimal(value) && value >= min && value <= max;
        },
        message: '输入的数字超出范围.'
    },
    // 两个表单相等
    equals: {
        validator: function (value, param) {
            return value == $(param[0]).val();
        },
        message: '两次输入不相同.'
    },
    // 两个表单不相等
    notEquals: {
        validator: function (value, param) {
            return value != $(param[0]).val();
        },
        message: '两次输入不能相同.'
    },
    // 验证汉子
    CHS: {
        validator: function (value) {
            return $.acooly.verify.chs(value);
        },
        message: '只能输入汉字'
    },
    chinese: {
        validator: function (value) {
            return $.acooly.verify.chs(value);
        },
        message: '只能输入汉字'
    },
    // 移动手机号码验证
    mobile: {// value值为文本框中的值
        validator: function (value) {
            return $.acooly.verify.mobile(value);
        },
        message: '输入手机号码格式不准确.'
    },
    // 国内邮编验证
    zipcode: {
        validator: function (value) {
            let reg = /^[1-9]\d{5}$/;
            return reg.test(value);
        },
        message: '邮编必须是非0开始的6位数字.'
    },
    // 用户账号验证(只能包括 _ 数字 字母)
    account: {// param的值为[]中值
        validator: function (value, param) {
            if (!$.acooly.verify.account(value)) {
                $.fn.validatebox.defaults.rules.account.message = '用户名只能字母开头，数字、字母、下划线组成.';
                return false;
            } else {
                return true;
            }
        },
        message: '用户名只能字母开头，数字、字母、下划线组成.'
    },
    memberNo: {
        validator: function (value, param) {
            if (!$.acooly.verify.member(value)) {
                return false;
            }
            return true;
        },
        message: '编码只能由字母和数字组成.'
    },
    // 公共正则,param[0]为正则表达式,param[1]为错误提示消息
    commonRegExp: {
        validator: function (value, param) {
            let re = new RegExp(param[0]);
            return $.acooly.verify.regExp(value, re)
        },
        message: '{1}'
    },
    regExp: {
        validator: function (value, param) {
            return $.acooly.verify.regExp(value, param[0])
        },
        message: '{1}'
    },
    // param[0]为bmp,jpg,gif(用逗号隔开),param[1]为错误提示消息
    validImg: {
        validator: function (value, param) {
            let param1 = param[0];// bmp,jpg,gif
            let param1List = param1.split(',');
            let extname = value.substring(value
                .lastIndexOf(".") + 1, value.length);
            extname = extname.toLowerCase();// 处理了大小写
            for (let i = 0; i < param1List.length; i++) {
                if (param1List[i] == extname) {
                    return true;
                }
            }
        },
        message: '{1}'
    },
    simplecsv: {
        validator: function (value, param) {
            return $.acooly.verify.csv(value);
        },
        message: '多个用户和组使用逗号分割'
    },
    money: {
        validator: function (value, param) {
            return $.acooly.verify.money(value);
        },
        message: '金额必须为整数或最多两位小数'
    },
    percent: {
        validator: function (value, param) {
            return $.acooly.verify.decimal(value) && value >= 0 && value <= 100;
        },
        message: '百分数必须在0-100间'
    },
    bankcard: {
        validator: function (value, param) {
            return $.acooly.verify.number(value);
        },
        message: '银行卡号必须为数字组成'
    },
    cert: {
        validator: function (value, param) {
            return $.acooly.verify.cert(value);
        },
        message: '18位有效身份证号码'
    },
    idcard: {
        validator: function (value, param) {
            return $.acooly.verify.cert(value);
        },
        message: '18位有效身份证号码'
    },
    json: {
        validator: function (value) {
            return $.acooly.verify.json(value);
        },
        message: 'JSON格式验证未通过'
    }

});

// ******** 常用 formatter 定义 **************//

/**
 * id formatter
 */
let idFormatter = function (value, row) {
    return row.id;
}

/**
 * 金额元formatter
 */
let moneyFormatter = function (value) {
    return $.acooly.format.money(value, true);
}

/**
 * 金额分格式化为2为小数的元
 */
let centMoneyFormatter = function (value) {
    return $.acooly.format.money(value, false);
}

/**
 * 文件大小格式化
 */
let fileSizeFormatter = function (value) {
    return $.acooly.format.fileSize(value);
}

/**
 * 日期格式化（yyyy-MM-dd）
 */
let dateFormatter = function (datetime) {
    return $.acooly.format.date(datetime, 'yyyy-MM-dd');
}

let dateTimeFormatter = function (datetime) {
    return $.acooly.format.date(datetime, 'yyyy-MM-dd HH:mm:ss');
}

let timeFormatter = function (datetime) {
    return $.acooly.format.date(datetime, 'HH:mm:ss');
}

/**
 * 时长格式化 seconds：秒级时长
 */
let secondFormatter = function (seconds) {
    return $.acooly.format.timespan(seconds);
}
/**
 * 时长格式化 millisecond：毫秒级时长
 */
let millisecondFormatter = function (millisecond) {
    return $.acooly.format.timespan(millisecond, 'ms');
}

let contentFormatter = function (value) {
    return $.acooly.format.content(value);
}

let linkFormatter = function (value) {
    return $.acooly.format.link(value);
}

let jsonFormatter = function (value) {
    return $.acooly.format.json(value);
}

let percentFormatter = function (value) {
    return value + "%"
}

let mappingFormatter = function (value, row, index, data, field) {
    try {
        let mapping = "all" + field.substring(0, 1).toUpperCase() + field.substring(1, field.length) + "s";
        return data["data"][mapping][value];
    } catch (e) {
        return value;
    }
}

let actionFormatter = function (value, row, index, data, field) {
    return formatString($('#' + actionContainer).html(), row.id);
}


// ******** formatter 兼容放方法 **************//
let formatFileSize = function (value) {
    return $.acooly.format.fileSize(value);
}

let formatCurrency = function (value) {
    return $.acooly.format.money(value);
}

let formatDate = function (datetime) {
    return $.acooly.format.date(datetime, 'yyyy-MM-dd');
}

let formatTime = function (seconds) {
    return $.acooly.format.timespan(seconds);
}

let formatContent = function (value, maxSize) {
    return $.acooly.format.content(value, maxSize);
}

let formatLink = function (value, label) {
    return $.acooly.format.link(value, label);
}

let formatPercent = function (value, label) {
    return value + "%"
}


/*
 * formatString功能 使用方法：formatString('字符串{0}字符串{0}字符串{1}','第一个变量','第二个变量');
 * @returns 格式化后的字符串
 */
let formatString = function (str) {
    for (let i = 0; i < arguments.length - 1; i++) {
        eval("let re = /\\{" + i + "\\}/g;");
        str = str.replace(re, arguments[i + 1]);
    }
    return str;
};


let formatIcon = function (value) {
    return "<span style='vertical-align:middle;display:inline-block; width:16px; height:16px;' class='"
        + value + "'></span>"
}

let formatRefrence = function (datagrid, filed, value) {
    try {
        return $("#" + datagrid).datagrid('getData')['data'][filed][value];
    } catch (e) {
        return value;
    }
}

let formatAction = function (actionContainer, value, row) {
    if (row.showCheckboxWithId == '') {
        return '';
    }
    return formatString($('#' + actionContainer).html(), row.id);
}


/**
 *
 * 接收一个以逗号分割的字符串，返回List，list里每一项都是一个字符串
 *
 * @returns list
 */
stringToList = function (value) {
    if (value != undefined && value != '') {
        let values = [];
        let t = value.split(',');
        for (let i = 0; i < t.length; i++) {
            values.push('' + t[i]);
            /* 避免他将ID当成数字 */
        }
        return values;
    } else {
        return [];
    }
};

function mapToOptions(map, forSearch) {
    let mapJson = "[";
    if (forSearch) {
        mapJson += "{id:'',text:'所有'}";
    }
    let i = 0;
    for (let key in map) {
        if (i == 0 && !forSearch) {
            mapJson += "{id:" + key + "," + "text:'" + map[key] + "'}";
        } else {
            mapJson += ",{id:" + key + "," + "text:'" + map[key] + "'}";
        }
        i++;
    }
    mapJson += "]";
    return eval(mapJson);
}

/**
 * 使panel和datagrid在加载时提示
 */
$.fn.panel.defaults.loadingMessage = '加载中....';
$.fn.datagrid.defaults.loadMsg = '加载中....';

/**
 * @requires jQuery,EasyUI panel关闭时回收内存，主要用于layout使用iframe嵌入网页时的内存泄漏问题
 */
$.fn.panel.defaults.onBeforeDestroy = function () {
    let frame = $('iframe', this);
    try {
        if (frame.length > 0) {
            for (let i = 0; i < frame.length; i++) {
                frame[i].contentWindow.document.write('');
                frame[i].contentWindow.close();
            }
            frame.remove();
            if ($.browser.msie) {
                CollectGarbage();
            }
        }
    } catch (e) {
    }
};

/**
 * 扩展datagrid默认的loadFilter,添加错误处理
 */
$.fn.datagrid.defaults.loadFilter = function (data, parent) {
    if (!data.success && data.message) {
        $.messager.alert('提示', data.message);
        return null;
    }
    return data;
}

/**
 * 通用异常处理
 */
let commonErrorFunction = function (XMLHttpRequest, e, x) {
    $.messager.progress('close');
    let message;
    switch (XMLHttpRequest.status) {
        case (500):
            message = "服务器系统内部错误";
            break;
        case (401):
            message = "未认证";
            break;
        case (403):
            message = "无权限执行此操作";
            break;
        case (404):
            message = "请求页面不存在";
            break;
        case (408):
            message = "请求超时";
            break;
        default:
            message = XMLHttpRequest.responseText;
    }
    $.acooly.alert('错误', message);
    // $.messager.alert('错误', message);
};

$.fn.datagrid.defaults.onLoadError = commonErrorFunction;
$.fn.treegrid.defaults.onLoadError = commonErrorFunction;
$.fn.tree.defaults.onLoadError = commonErrorFunction;
$.fn.combogrid.defaults.onLoadError = commonErrorFunction;
$.fn.combobox.defaults.onLoadError = commonErrorFunction;
$.fn.form.defaults.onLoadError = commonErrorFunction;
// $.fn.panel.defaults.onLoadError = commonErrorFunction;
/**
 * 改变jQuery的AJAX默认属性和方法
 */
$.ajaxSetup({
    type: 'POST'
});

/**
 * 防止panel/window/dialog组件超出浏览器边界
 *
 * @param left
 * @param top
 */
let easyuiPanelOnMove = function (left, top) {
    let l = left;
    let t = top;
    if (l < 1) {
        l = 1;
    }
    if (t < 1) {
        t = 1;
    }
    let width = parseInt($(this).parent().css('width')) + 14;
    let height = parseInt($(this).parent().css('height')) + 14;
    let right = l + width;
    let buttom = t + height;
    let browserWidth = $(window).width();
    let browserHeight = $(window).height();
    if (right > browserWidth) {
        l = browserWidth - width;
    }
    if (buttom > browserHeight) {
        t = browserHeight - height;
    }
    $(this).parent().css({
        /* 修正面板位置 */
        left: l,
        top: t
    });
};
$.fn.dialog.defaults.onMove = easyuiPanelOnMove;
$.fn.window.defaults.onMove = easyuiPanelOnMove;
$.fn.panel.defaults.onMove = easyuiPanelOnMove;

/**
 * 将form表单元素的值序列化成对象
 *
 * @returns object
 */
serializeObject = function (form) {
    let o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};

/**
 * 从表单的父容器中序列化表单
 */
serializeObjectFromContainer = function (container) {
    let queryParams = {};
    $("input[name^='search']", container).each(
        function () {
            if (queryParams[this['name']]) {
                queryParams[this['name']] = queryParams[this['name']] + ","
                    + this['value'];
            } else {
                queryParams[this['name']] = this['value'];
            }
        });
    return queryParams;
};

/**
 * 判断对象是否为空对象
 */
isEmptyObject = function (obj) {
    for (let name in obj) {
        return false;
    }
    return true;
};
