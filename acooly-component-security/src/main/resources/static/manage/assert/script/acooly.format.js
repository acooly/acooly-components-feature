(function ($) {
    var formatClass = {

        /**
         * 格式化:文件大小
         *
         * @Param value
         *            [必选]字节大小
         * @Return 根据大小自动判断单位的人类试图大小。
         */
        fileSize: function (value) {
            if (value < 1024) {
                return value + 'byte';
            } else if (value >= 1024 && value < 1024 * 1024) {
                return Math.round(value / 1024 * 100) / 100 + 'K';
            } else if (value >= 1024 * 1024 && value < 1024 * 1024 * 1024) {
                return Math.round(value / 1024 / 1024 * 100) / 100 + 'M';
            } else {
                return Math.round(value / 1024 / 1024 / 1024 * 100) / 100 + 'G';
            }
        },

        /**
         * 格式化:金额元显示
         *
         * @Param num
         *            [必填]金额
         * @Param yuan
         *            [可选]是否元:true(默认),false
         * @Param symbol
         *            [可选]货币符号，默认无，可选:￥,$等
         * @Return 货币金额格式化显示，保留2位小数
         */
        money: function (num, yuan, symbol) {
            if (isNaN(num))
                num = "0";
            num = num.toString().replace(/\$|\,/g, '');
            if (!yuan)
                num = num / 100;
            if (!symbol)
                symbol = "";
            sign = (num == (num = Math.abs(num)));
            num = Math.floor(num * 100 + 0.50000000001);
            cents = num % 100;
            num = Math.floor(num / 100).toString();
            if (cents < 10)
                cents = "0" + cents;
            for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
                num = num.substring(0, num.length - (4 * i + 3)) + ','
                    + num.substring(num.length - (4 * i + 3));
            currency = (((sign) ? '' : '-') + num + '.' + cents);
            return symbol + currency;
        },

        fenToWan: function (fen, symbol) {
            var num = fen / 10000;
            return this.money(num, false, symbol);
        },

        /**
         * 格式化日期时间
         *
         * @Param d
         *            日期对象或日期时间字符串（支持GMT）
         * @Param format
         *            格式patten(默认：yyyy-MM-dd HH:mm:ss)
         * @Return 根据format指定格式的字符串
         */
        date: function (d, format) {
            if (!format) {
                format = 'yyyy-MM-dd HH:mm:ss'
            }
            if (typeof (d) == 'string') {
                d = new Date(d);
            }
            return this._dateFormat(d, format);
        },

        /**
         * 时长格式化
         *
         * 根据秒数，格式化为对应的human格式。如：1天12:12:01
         *
         * @Param span
         *            时长
         * @Param unit
         *            时长单位，支持: s:秒(默认)，ms:毫秒
         * @Return 时长human格式
         */
        timespan: function (span, unit) {
            if (!unit || unit == 's') {
                // 秒
                span = span * 1000;
            }
            var sm = Math.floor(span % 1000); // 计算毫秒
            var second = Math.floor(span / 1000 % 60); // 计算秒
            var minite = Math.floor((span / 1000 / 60) % 60); // 计算分
            var hour = Math.floor((span / 1000 / 3600) % 24); // 计算小时
            var hourspan = (hour < 10 ? "0" + hour : hour) + ":"
                + (minite < 10 ? "0" + minite : minite) + ":"
                + (second < 10 ? "0" + second : second);
            var day = Math.floor((span / 1000 / 3600 / 24) % 356); // 计算天
            day = (day > 0 ? day + '天' : '');
            sm = (sm > 0 ? '.' + sm : '');
            return day + hourspan + sm;
        },

        /**
         * 显示过去了多长时间
         * 默认：1分钟前，10分钟前，1小时前 2小时前 6小时前 1天前 2天前 5天前 10天前 1个月前 2个月前 6个月前 1年前
         * @param datetime
         */
        timePast: function (datetime, mapping) {
            if(!datetime){
                return "";
            }
            if (!mapping) {
                mapping = [
                    {seconds: 60, label: "1分钟前"},
                    {seconds: 60 * 10, label: "10分钟前"},
                    {seconds: 60 * 60, label: "1小时前"},
                    {seconds: 60 * 60 * 2, label: "2小时前"},
                    {seconds: 60 * 60 * 6, label: "6小时前"},
                    {seconds: 60 * 60 * 24, label: "1天前"},
                    {seconds: 60 * 60 * 24 * 2, label: "2天前"},
                    {seconds: 60 * 60 * 24 * 3, label: "5天前"},
                    {seconds: 60 * 60 * 24 * 10, label: "10天前"},
                    {seconds: 60 * 60 * 24 * 30, label: "1个月前"},
                    {seconds: 60 * 60 * 24 * 30 * 2, label: "两个前"},
                    {seconds: 60 * 60 * 24 * 30 * 6, label: "半年前"},
                    {seconds: 60 * 60 * 24 * 30 * 12, label: "1年前"},
                    {seconds: 60 * 60 * 24 * 30 * 120000, label: "1年以上"}
                ]
            }
            datetime = datetime.replace(/-/g, "/")
            var startTime = Date.parse(datetime);
            var now = new Date();
            var past = (now.getTime() - startTime) / 1000;

            var label = null;
            var isOk = false;
            $(mapping).each(function (index,item) {
                if (!isOk && past < parseInt(item.seconds)) {
                    isOk = true;
                    label = item.label;
                }
            });
            return label;

        },

        /**
         * 格式化html文本数据
         *
         * 格式化为只显示一部分，点击后面的更多弹出层显示剩余信息
         *
         */
        content: function (html, maxSize) {
            if (!html || html == '')
                return '';
            if (!maxSize)
                maxSize = 20;
            if (html.length > maxSize) {
                temp = html.substring(0, maxSize);
                html = '<a href="javascript:;" title="' + html
                    + '" onclick="$(this).children().toggle()">' + temp
                    + '<span>...</span><span style="display:none;">' + html
                    + '</span></a>';
            }
            return html;
        },

        /**
         * 链接格式化
         */
        link: function (url, label) {
            if (!url || url == '')
                return '';
            if (!label)
                label = '查看';
            var html = '<a href="' + url + '" target="_blank">' + label
                + '</a>';
            return html;
        },

        /**
         * 简单js模板
         * formatString功能 使用方法：formatString('字符串{0}字符串{0}字符串{1}','第一个变量','第二个变量');
         */
        template: function (str) {
            for (var i = 0; i < arguments.length - 1; i++) {
                eval("var re = /\\{" + i + "\\}/g;");
                str = str.replace(re, arguments[i + 1]);
            }
            return str;
        },

        json: function (json, options) {
            var reg = null,
                keyStyle = 'color:#92278f;',
                valStyle = 'color:#3ab54a;',
                formatted = '',
                pad = 0,
                PADDING = '    '; // one can also use '\t' or a different number of spaces


            // optional settings
            options = options || {};
            // remove newline where '{' or '[' follows ':'
            options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
            // use a space after a colon
            options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;

            // begin formatting...
            if (typeof json !== 'string') {
                // make sure we start with the JSON as a string
                json = JSON.stringify(json);
            } else {
                // is already a string, so parse and re-stringify in order to remove extra whitespace
                json = JSON.parse(json);
                json = JSON.stringify(json);
            }

            // add newline before and after curly braces
            reg = /([\{\}])/g;
            json = json.replace(reg, '\r\n$1\r\n');

            // add newline before and after square brackets
            reg = /([\[\]])/g;
            json = json.replace(reg, '\r\n$1\r\n');

            // add newline after comma
            reg = /(\,)/g;
            json = json.replace(reg, '$1\r\n');

            // remove multiple newlines
            reg = /(\r\n\r\n)/g;
            json = json.replace(reg, '\r\n');

            // remove newlines before commas
            reg = /\r\n\,/g;
            json = json.replace(reg, ',');

            // optional formatting...
            if (!options.newlineAfterColonIfBeforeBraceOrBracket) {
                reg = /\:\r\n\{/g;
                json = json.replace(reg, ':{');
                reg = /\:\r\n\[/g;
                json = json.replace(reg, ':[');
            }
            if (options.spaceAfterColon) {
                reg = /\:/g;
                json = json.replace(reg, ': ');
            }

            $.each(json.split('\r\n'), function (index, node) {
                var i = 0,
                    indent = 0,
                    padding = '';

                if (node.match(/\{$/) || node.match(/\[$/)) {
                    indent = 1;
                } else if (node.match(/\}/) || node.match(/\]/)) {
                    if (pad !== 0) {
                        pad -= 1;
                    }
                } else {
                    if (node && node != '') {
                        var pair = node.split(':')
                        node = '<span style="' + keyStyle + '">' + pair[0] + '</span>:<span style="' + valStyle + '">' + pair[1] + '</span>';
                    }
                    indent = 0;
                }

                for (i = 0; i < pad; i++) {
                    padding += PADDING;
                }

                formatted += padding + node + '\r\n';
                pad += indent;
            });

            return formatted;
        },

        /**
         * 安全mask(建议服务器端处理)
         */
        mask: function (value) {

        },

        // *** 私有帮助方法

        /**
         * 格式化日期对象
         */
        _dateFormat: function (dateObject, format) {
            var o = {
                "M+": dateObject.getMonth() + 1, // month
                "d+": dateObject.getDate(), // day
                "H+": dateObject.getHours(), // hour
                "m+": dateObject.getMinutes(), // minute
                "s+": dateObject.getSeconds(), // second
                "q+": Math.floor((dateObject.getMonth() + 3) / 3), // quarter
                "S": dateObject.getMilliseconds()
                // millisecond
            }
            if (/(y+)/.test(format))
                format = format.replace(RegExp.$1,
                    (dateObject.getFullYear() + "")
                        .substr(4 - RegExp.$1.length));
            for (var k in o)
                if (new RegExp("(" + k + ")").test(format))
                    format = format.replace(RegExp.$1,
                        RegExp.$1.length == 1 ? o[k] : ("00" + o[k])
                            .substr(("" + o[k]).length));
            return format;
        }

    };

    $.extend($.acooly, {
        format: formatClass
    })
})(jQuery);
