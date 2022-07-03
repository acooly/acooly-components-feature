/**
 * acooly messager扩展和封装使用
 */
var acooly_msg = {

    loadingId: '',
    /**
     * 打开加载load效果
     * @param msg
     * @returns {string} loadingId
     */
    loading: function (msg) {
        // if (!msg)
        //     msg = '加载中…';
        // this.loadingId = layer.msg(msg, {icon: 16, shade: 0.01});
        // return this.loadingId;
    },

    /**
     * 关闭加载load效果
     * @param id
     */
    loaded: function (id) {
        // if (id) {
        //     layer.close(id);
        //     return;
        // }
        // if (this.loadingId) {
        //     layer.close(this.loadingId);
        //     return;
        // }
        // layer.closeAll();
    },

    msg_data: {
        "default": {title: '信息', defaultContent: '', icon: 'fa fa-info-circle'},
        "success": {title: '成功', defaultContent: '处理成功', icon: 'fa fa-check-circle'},
        "info": {title: '提示', defaultContent: '', icon: 'fa fa-info-circle'},
        "warning": {title: '警告', defaultContent: '', icon: 'fa fa-exclamation-circle'},
        "primary": {title: '信息', defaultContent: '', icon: 'fa fa-info-circle'},
        "danger": {title: '失败', defaultContent: '处理失败', icon: 'fa fa-times-circle'}
    },

    /**
     * 消息框
     * @param title 标题
     * @param message 消息
     * @param type {info,success,warning,danger,primary}
     */
    messager: function (title, message, type) {
        var args = arguments;
        var ctype;
        if (args.length == 1) {
            ctype = 'primary';
            data = this.msg_data[ctype];
            data.message = title;
        } else if (args.length == 2) {
            ctype = message;
            data = this.msg_data[ctype];
            data.message = title;
        } else {
            ctype = !type ? 'primary' : type;
            data = this.msg_data[ctype];
            data.title = title ? title : data.title;
            data.message = message ? message : data.defaultContent;
        }
        var html = "<div role=\"alert\" class=\"ac-notification right\" style=\"top: 57px;right: 55px; z-index: 12150;display: none \">\n" +
            // "    <ion-icon class=\"ac-notification__icon text-" + ctype + "\" name=\"" + data.icon + "\"></ion-icon>\n" +
            "    <i class=\"" + data.icon + " ac-notification__icon text-" + ctype + "\"></i>\n" +
            "    <div class=\"ac-notification__group is-with-icon\">\n" +
            "        <h2 class=\"ac-notification__title\">" + data.title + "</h2>\n" +
            "        <div class=\"ac-notification__content\"><p>" + data.message + "</p></div>\n" +
            "        <i onclick='$(this).parent().parent().remove();' class=\"ac-notification__closeBtn ion ion-android-close\"></i>\n" +
            // "        <ion-icon onclick='$(this).parent().parent().remove();' class=\"ac-notification__closeBtn\" name=\"close-outline\"></ion-icon>\n" +
            "    </div>\n" +
            "</div>";
        var manager = $(html).appendTo("body")
        $(manager).show().delay(3000).fadeOut(function () {
            $(manager).remove();
        });
    },


    msg: function (message, icon) {
        $.messager.alert('消息', message);
    },

    msgrb: function (message, success, title) {
        $.messager.show({
            title: (title == null ? '提示' : title),
            msg: message,
            timeout: 4000,
            showType: 'slide'
        });
    },

    alert: function (title, message, opts) {
        $.messager.alert(title, message, opts);
    },

    tips: function (content, objectId, position) {

    },

    toast: function (title, message, type, position) {
        ctype = !type ? 'default' : type;
        data = this.msg_data[ctype];
        data.title = title ? title : data.title;
        data.message = message ? message : data.message;
        $(document).Toasts('create', {
            class: 'bg-' + ctype,
            icon: data.icon + " fa-lg",
            autohide: true,
            delay: 3000,
            title: data.title,
            body: data.message
        })
    }
};

(function ($) {
    if (!$.acooly) {
        $.extend({acooly: {}});
    }

    $.extend($.acooly, acooly_msg);

})(jQuery);

