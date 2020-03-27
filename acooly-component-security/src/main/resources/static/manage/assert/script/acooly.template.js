/**
 * acooly封装：javascript template for render
 * @type {{}}
 */
var acooly_template = {

    engine: "baidu",

    _doRender: function (template, data, engineOpts) {
        if (this.engine == 'baidu') {
            return this._doBaiduRender(template, data, engineOpts);
        }
        return null;
    },

    _doBaiduRender: function (template, data, engineOpts) {
        baidu.template.ESCAPE = false;
        baidu.template.LEFT_DELIMITER = '<%';
        baidu.template.RIGHT_DELIMITER = '%>';
        if (engineOpts) {
            $.each(engineOpts, function (k, v) {
                var cmd = "baidu.template." + k + "=" + v;
                eval(cmd);
            });
        }
        return baidu.template(template, data);
    },

    /**
     * 渲染
     * @param template
     * @param data
     * @param opts
     * @param engineOpts
     */
    render: function (template, data, opts, engineOpts) {
        var defOpts = {
            engine: null,
            append: false,
            beforeRender: null,
            afterRender: null
        }
        var options = $.extend(defOpts, opts);

        if (options.beforeRender) {
            options.beforeRender.call(this, data);
        }
        if (options.engine) {
            this.engine = options.engine;
        }
        var result = this._doRender(template, data, engineOpts);

        if (options.afterRender) {
            options.afterRender.call(this, data, result);
        }
        return result;
    },

    /**
     * 渲染到
     * @param container  渲染后的装载容器（id）
     * @param template   模板（id）
     * @param data       数据（json）
     * @param options    其他参数
     * @param engineOpts 具体模板引擎的参数设置
     */
    renderTo: function (container, template, data, opts, engineOpts) {
        var defOpts = {
            engine: null,
            append: false,
            beforeRender: null,
            afterRender: null
        }
        var options = $.extend(defOpts, opts);
        var result = this.render(template, data, opts, engineOpts)
        if (options.append) {
            $('#' + container).append(result);
        } else {
            $('#' + container).html(result);
        }
    }

};

(function ($) {
    if (!$.acooly) {
        $.extend({acooly: {}});
    }

    $.extend($.acooly, {
        template: acooly_template
    });

})(jQuery);