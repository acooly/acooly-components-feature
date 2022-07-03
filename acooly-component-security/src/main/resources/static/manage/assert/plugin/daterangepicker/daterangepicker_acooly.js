let acooly_daterangepicker_class = {
    /**
     * 初始化acooly-daterangepicker
     *
     * 通过在button按钮上添加".acooly-daterangepicker"的class触发日期选择端控件
     *
     *  html：
     *  <button type="button" class="btn btn-sm btn-default float-right acooly-daterangepicker" style="width: 275px;"
     *      data-start-name="search_GTE_taskPlanEnd" data-end-name="search_LTE_taskPlanEnd" data-start-value="" data-end-value="">
     *      <i class="fa fa-calendar"></i> <span>选择日期段 </span> <i class="fa fa-caret-down"></i>
     *  </button>
     *
     *  核心参数：
     *  data-start-name: 开始日期表单名
     *  data-start-value：开始日期默认值
     *  data-end-name: 结束日期表单名
     *  data-end-value：结束日期默认值
     *
     */
    init: function () {
        var that = this;
        // 初始化组件
        $('.acooly-daterangepicker').daterangepicker({
                ranges: {
                    '今天': [moment(), moment()],
                    '昨天': [moment().subtract(1, 'days'), moment().subtract(1, 'days')],
                    '7天内': [moment().subtract(6, 'days'), moment()],
                    '30天内': [moment().subtract(29, 'days'), moment()],
                    '本月': [moment().startOf('month'), moment().endOf('month')],
                    '上个月': [moment().subtract(1, 'month').startOf('month'), moment().subtract(1, 'month').endOf('month')]
                },
                locale: {
                    "format": "YYYY-MM-DD",
                    "separator": " - ",
                    "applyLabel": "确定",
                    "cancelLabel": "清除",
                    "fromLabel": "从",
                    "toLabel": "到",
                    "customRangeLabel": "自定义",
                    "weekLabel": "周",
                    "daysOfWeek": ["日", "一", "二", "三", "四", "五", "六"],
                    "monthNames": ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"],
                    "firstDay": 1
                },
                "startDate": moment().subtract(6, 'days'),
                "endDate": moment(),
                "alwaysShowCalendars": true,
                "showDropdowns": true,
                "showISOWeekNumbers": true,
                "linkedCalendars": false,
                "minDate": "2010-01-01",
                "maxDate": "2100-01-01"
            },
            function (start, end, label) {
            }
        );
        // 注册提交事件：设置label显示，自动生成开始日期和结束日期表单和设置值
        $('.acooly-daterangepicker').on('apply.daterangepicker', function (ev, picker) {
            that._on_apply(picker.element, picker.startDate.format('YYYY-MM-DD'), picker.endDate.format('YYYY-MM-DD'), picker.chosenLabel);
        });
        // 注册取消时间：清理已选择的值
        $('.acooly-daterangepicker').on('cancel.daterangepicker', function (ev, picker) {
            that._on_cancel(ev, picker);
        });
        // 根据设置的data-*值，初始化控件的表单和label显示。
        $(".acooly-daterangepicker").each(function () {
            let startVal = $(this).data("start-value");
            let endVal = $(this).data("end-value");
            if (startVal) {
                $(this).data('daterangepicker').setStartDate(startVal);
            }
            if (endVal) {
                $(this).data('daterangepicker').setEndDate(endVal);
            }
            if (startVal || endVal) {
                that._on_daterangepicker_apply($(this), startVal, endVal, "自定义");
            }
        });
    },

    /**
     * 按钮方式日期段选择初始化
     * @param el daterangepicker
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param label 标签
     * @private
     */
    _on_apply: function (el, startDate, endDate, label) {
        let filedStart = $(el).data("start-name");
        let filedEnd = $(el).data("end-name");
        let existStart = $(el).find("input[name='" + filedStart + "']");
        if (!existStart || existStart.length == 0) {
            // 不存在，则动态创建
            $(el).append("<input type=\"hidden\" name=\"" + filedStart + "\" value=\"" + startDate + "\">");
        } else {
            existStart.val(startDate);
        }
        let existEnd = $(el).find("input[name='" + filedEnd + "']");
        if (!existEnd || existEnd.length == 0) {
            // 不存在，则动态创建
            $(el).append("<input type=\"hidden\" name=\"" + filedEnd + "\" value=\"" + endDate + "\">");
        } else {
            existEnd.val(endDate);
        }
        let show = "";
        if (!startDate && !endDate) {
            show = "选择日期段";
        } else {
            if (!startDate) {
                startDate = "?";
            }
            if (!endDate) {
                endDate = "?";
            }
            show = label + ": " + startDate + ' - ' + endDate;
        }
        $(el).find('span').html(show);
    },

    _on_cancel: function (ev, picker) {
        let el = picker.element;
        let filedStart = $(el).data("start-name");
        let filedEnd = $(el).data("end-name");
        let existStart = $(el).find("input[name='" + filedStart + "']");
        let existEnd = $(el).find("input[name='" + filedEnd + "']");
        if (existStart && existStart.length > 0) {
            existStart.val("");
        }
        if (existEnd && existEnd.length > 0) {
            existEnd.val("");
        }
        $(el).find('span').html("选择日期段");
    }
};

/***
 * JQuery静态类前缀处理
 */
(function ($) {
    if (!$.acooly) {
        $.acooly = {};
    }
    if (!$.acooly.daterangepicker) {
        $.extend($.acooly, {daterangepicker: acooly_daterangepicker_class});
    }
})(jQuery);
