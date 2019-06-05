/**
 * 扩展validatebox的验证规则
 */
$.extend($.fn.validatebox.defaults.rules, {
    // changelog: by zhangpu on 2013-5-28
    // byteLength validator for chinese-string
    /** byte长度验证 */
    byteLength: {
        validator: function (value, param) {
            var minlen = param[0];
            var maxlen = param[1];
            return $.acooly.verify.byteLength(value, minlen, maxlen);
        },
        message: '输入的数据长度超出范围.'
    },
    number: {
        validator: function (value, param) {
            var min = param[0] || 0;
            var max = param[1] || 999999999;
            return $.acooly.verify.number(value) && value >= min && value <= max;
        },
        message: '输入的数字超出范围.'
    },
    decimal: {
        validator: function (value, param) {
            var min = param[0] || 0;
            var max = param[1] || 999999999;
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
    // 验证汉子
    CHS: {
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
            var reg = /^[1-9]\d{5}$/;
            return reg.test(value);
        },
        message: '邮编必须是非0开始的6位数字.'
    },
    // 用户账号验证(只能包括 _ 数字 字母)
    account: {// param的值为[]中值
        validator: function (value, param) {
            if (value.length < param[0]
                || value.length > param[1]) {
                $.fn.validatebox.defaults.rules.account.message = '用户名长度必须在'
                    + param[0] + '至' + param[1] + '范围';
                return false;
            } else {
                if ($.acooly.verify.account(value)) {
                    $.fn.validatebox.defaults.rules.account.message = '用户名只能数字、字母、下划线组成.';
                    return false;
                } else {
                    return true;
                }
            }
        },
        message: '用户名只能字母开头，数字、字母、下划线组成.'
    },
    // 公共正则,param[0]为正则表达式,param[1]为错误提示消息
    commonRegExp: {
        validator: function (value, param) {
            var re = new RegExp(param[0]);
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
            var param1 = param[0];// bmp,jpg,gif
            var param1List = param1.split(',');
            var extname = value.substring(value
                .lastIndexOf(".") + 1, value.length);
            extname = extname.toLowerCase();// 处理了大小写
            for (var i = 0; i < param1List.length; i++) {
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
    cert: {
        validator: function (value, param) {
            return $.acooly.verify.cert(value);
        },
        message: '18位有效身份证号码'
    }

});

// ******** 常用 formatter 定义 **************//

/**
 * id formatter
 */
var idFormatter = function (value, row) {
    return row.id;
}

/**
 * 金额元formatter
 */
var moneyFormatter = function (value) {
    return $.acooly.format.money(value, true);
}

/**
 * 金额分格式化为2为小数的元
 */
var centMoneyFormatter = function (value) {
    return $.acooly.format.money(value, false);
}

/**
 * 文件大小格式化
 */
var fileSizeFormatter = function (value) {
    return $.acooly.format.fileSize(value);
}

/**
 * 日期格式化（yyyy-MM-dd）
 */
var dateFormatter = function (datetime) {
    return $.acooly.format.date(datetime, 'yyyy-MM-dd');
}

var dateTimeFormatter = function (datetime) {
    return $.acooly.format.date(datetime, 'yyyy-MM-dd HH:mm:ss');
}

var timeFormatter = function (datetime) {
    return $.acooly.format.date(datetime, 'HH:mm:ss');
}

/**
 * 时长格式化 seconds：秒级时长
 */
var secondFormatter = function (seconds) {
    return $.acooly.format.timespan(seconds);
}
/**
 * 时长格式化 millisecond：毫秒级时长
 */
var millisecondFormatter = function (millisecond) {
    return $.acooly.format.timespan(millisecond, 'ms');
}

var contentFormatter = function (value) {
    return $.acooly.format.content(value);
}

var linkFormatter = function (value) {
    return $.acooly.format.link(value);
}

var jsonFormatter = function (value) {
    return $.acooly.format.json(value);
}

var mappingFormatter = function (value, row, index, data, field) {
    try {
        var mapping = "all" + field.substring(0, 1).toUpperCase() + field.substring(1, field.length) + "s";
        return data["data"][mapping][value];
    } catch (e) {
        return value;
    }
}


// ******** formatter 兼容放方法 **************//
var formatFileSize = function (value) {
    return $.acooly.format.fileSize(value);
}

var formatCurrency = function (value) {
    return $.acooly.format.money(value);
}

var formatDate = function (datetime) {
    return $.acooly.format.date(datetime, 'yyyy-MM-dd');
}

var formatTime = function (seconds) {
    return $.acooly.format.timespan(seconds);
}

var formatContent = function (value, maxSize) {
    return $.acooly.format.content(value, maxSize);
}

var formatLink = function (value, label) {
    return $.acooly.format.link(value, label);
}


/*
 * formatString功能 使用方法：formatString('字符串{0}字符串{0}字符串{1}','第一个变量','第二个变量');
 * @returns 格式化后的字符串
 */
var formatString = function (str) {
    for (var i = 0; i < arguments.length - 1; i++) {
        eval("var re = /\\{" + i + "\\}/g;");
        str = str.replace(re, arguments[i + 1]);
    }
    return str;
};


var formatIcon = function (value) {
    return "<span style='vertical-align:middle;display:inline-block; width:16px; height:16px;' class='"
        + value + "'></span>"
}

var formatRefrence = function (datagrid, filed, value) {
    try {
        return $("#" + datagrid).datagrid('getData')['data'][filed][value];
    } catch (e) {
        return value;
    }
}

var formatAction = function (actionContainer, value, row) {
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
        var values = [];
        var t = value.split(',');
        for (var i = 0; i < t.length; i++) {
            values.push('' + t[i]);
            /* 避免他将ID当成数字 */
        }
        return values;
    } else {
        return [];
    }
};

function mapToOptions(map, forSearch) {
    var mapJson = "[";
    if (forSearch) {
        mapJson += "{id:'',text:'所有'}";
    }
    var i = 0;
    for (var key in map) {
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
    var frame = $('iframe', this);
    try {
        if (frame.length > 0) {
            for (var i = 0; i < frame.length; i++) {
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
var commonErrorFunction = function (XMLHttpRequest, e, x) {
    $.messager.progress('close');
    var message;
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
var easyuiPanelOnMove = function (left, top) {
    var l = left;
    var t = top;
    if (l < 1) {
        l = 1;
    }
    if (t < 1) {
        t = 1;
    }
    var width = parseInt($(this).parent().css('width')) + 14;
    var height = parseInt($(this).parent().css('height')) + 14;
    var right = l + width;
    var buttom = t + height;
    var browserWidth = $(window).width();
    var browserHeight = $(window).height();
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
    var o = {};
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
    var queryParams = {};
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
    for (var name in obj) {
        return false;
    }
    return true;
};
