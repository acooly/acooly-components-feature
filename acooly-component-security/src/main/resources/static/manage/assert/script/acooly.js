/**
 *
 * acooly js for base
 *
 * @author zhangpu
 * @date 2018-12-25
 */
var acooly = {

    /**
     * ajax方式提交表单
     *
     * 可根据参数说明传值控制逻辑
     * 也可以直接传入标准jquery-ajax的参数覆盖封装的逻辑
     *
     * @param opts 参考下面的默认值defOpts说明
     */
    ajax: function (opts) {
        var That = this;
        var defOpts = {
            url: null,                      // 地址
            jsonData: null,                 // json数据，与formId任传其一
            formId: null,                   // form表单ID，与data任传其一
            buttonId: null,                 // 提交按钮id
            buttonDisabledClass: null,      // 提价按钮disableClass
            loadingMessage: '加载中...',     // loading加载效果内容，可以是图片gif
            msgType: "toast",               // 错误提示方式：toast, toptip, alert, msg
            onSuccess: null,
            onFailure: null,
            onError: null
        }
        // if (!opts.formId && !opts.jsonData) {
        //     That._doAjaxErrorMessage("formId和jsonData必输其一", options.msgType);
        //     return;
        // }
        opts.data = opts.jsonData;
        if (!opts.data) {
            opts.data = $('#' + opts.formId).serializeJson();
        }
        var options = $.extend(defOpts, opts);
        var loadingObject = null;
        var ajaxOptions = {
            method: "post",
            cache: false,
            beforeSend: function () {
                loadingObject = That._doAjaxLoading(options);
            },
            complete: function () {
                That._doAjaxLoaded($.extend(options, {loadingObject: loadingObject}));
            },
            success: function (result) {
                if (result.success) {
                    if (options.onSuccess) {
                        options.onSuccess.call(this, result);
                    }
                } else {
                    if (options.onFailure) {
                        options.onFailure.call(this, result);
                    } else {
                        That._doAjaxErrorMessage(result.message, options.msgType);
                    }
                }
            },
            error: function (xhr, m, e) {
                if (options.onError) {
                    options.onError.call(this, m);
                } else {
                    That._doAjaxErrorMessage("通讯或解析异常:" + e, options.msgType)
                }
            }
        };
        $.extend(ajaxOptions, options)
        $.ajax(ajaxOptions);

    },


    /**
     * 处理ajax请求的loading效果
     * @param options
     * @private
     */
    _doAjaxLoading: function (options) {
        if (options.buttonId) {
            var buttonLabel = $("#" + options.buttonId).html();
            if (!buttonLabel) {
                buttonLabel = $("#" + options.buttonId).val();
            }
            $("#" + options.buttonId).attr("loadingLabel", buttonLabel);
            $("#" + options.buttonId).html(options.loadingMessage);
            $.acooly.disable(options.buttonId, options.buttonDisabledClass);
        } else {
            return $.acooly.loading(options.loadingMessage);
        }
    },

    /**
     * 处理ajax请求的loaded效果
     * @param options
     * @private
     */
    _doAjaxLoaded: function (options) {
        if (options.buttonId != null) {
            $.acooly.enable(options.buttonId, options.buttonDisabledClass);
            var buttonLabel = $("#" + options.buttonId).attr("loadingLabel");
            $("#" + options.buttonId).html(buttonLabel);
        } else {
            $.acooly.loaded(options.loadingObject);
        }
    },

    /**
     * ajax错误消息处理
     * @param message 错误消息
     * @param msgType 显示方式
     * @private
     */
    _doAjaxErrorMessage: function (message, msgType) {
        if (!msgType) {
            msgType = 'toptip';
        }
        if (msgType == 'toptip') {
            $.acooly.toptip(message, "error");
        } else if (msgType == 'toast') {
            $.acooly.toast(message, "cancel");
        } else if (msgType == 'alert') {
            $.acooly.alert(message, '错误');
        } else if (msgType == 'msg') {
            $.acooly.msg(message);
        }
    },


    ajaxForm: function (form, onSuccess, onFailure, opts) {

        var formObject = $(form);

    },

    /**
     * ajax+模板渲染
     *
     * @param url           [必选] ajax请求URL
     * @param jsonData      [可选] ajax请求参数json
     * @param container     [必选] 容器
     * @param template      [必选] 模板
     * @param opts          [可选] ajax的标准参数 + acooly.template.render(opts)的参数
     */
    ajaxRender: function (url, jsonData, container, template, opts) {
        this.ajax($.extend({
            url: url,
            jsonData: jsonData,
            onSuccess: function (result) {
                $.acooly.template.render(container, template, result, opts);
            }
        }, opts));
    },


    ajaxPager: function (opts) {

    },


    /**
     * 分页列表（内容递增方式）
     *
     * 列表末尾提供加载更多的按钮，加载后的下一页数据接续在上一页数据后。
     * @param opts参数说明：
     * url 请求数据的URL
     * jsonData 请求参数
     * template 模板ID
     * renderContainer 分页列表数据容器
     * renderController 分页控制容器
     * beforeRender 数据load后，渲染完成前拦截函数
     * afterRender  渲染完成后拦截函数
     */
    pageAppend: function (opts) {

        var def = {
            refresh: false,             // 如果true则刷新，显示第一页。
            entity: null,               // 分页内容的实体名字
            pageSize: 10,               // 默认也大小
            jsonData: {},               // 查询参数
            beforeRender: null,         // 渲染前拦截
            afterRender: null           // 渲染后拦截
        }

        var options = $.extend(def, opts);
        var pageNo = $('#' + options.renderController).attr("pageNo");
        if (options.refresh || !pageNo) {
            pageNo = 1;
        }
        var requestData = $.extend({rows: options.pageSize, page: pageNo}, options.jsonData);

        if (options.entity) {
            options.template = options.entity + "_list_template";
            options.renderController = options.entity + "_list_controller";
            options.renderContainer = options.entity + "_list_container";
        }


        baidu.template.ESCAPE = false;
        $.acooly.portal.ajax(options.url, requestData, function (result) {

            if (options.beforeRender) {
                options.beforeRender.call(this, result);
            }
            if (options.refresh) {
                $.acooly.renderData(options.renderContainer, options.template, result);
            } else {
                $.acooly.renderAppend(options.renderContainer, options.template, result);
            }


            if (result.data.hasNext || result.hasNext) {
                $('#' + options.renderController).show();
            } else {
                $('#' + options.renderController).hide();
            }
            pageNo = parseInt(pageNo) + 1;
            $('#' + options.renderController).attr("pageNo", pageNo);

            if (options.afterRender) {
                options.afterRender.call(this, result);
            }

        });
    },

};

(function ($) {
    if (!$.acooly) {
        $.acooly = {};
    }
    $.extend($.acooly, {acooly: acooly});

    /**
     * 扩展jquery序列化表单为json
     */
    $.fn.serializeJson = function () {
        var serializeObj = {};
        var array = this.serializeArray();
        var str = this.serialize();
        $(array).each(function () {
            if (serializeObj[this.name]) {
                if ($.isArray(serializeObj[this.name])) {
                    serializeObj[this.name].push(this.value);
                } else {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            } else {
                serializeObj[this.name] = this.value;
            }
        });
        return serializeObj;
    };

})(jQuery);


