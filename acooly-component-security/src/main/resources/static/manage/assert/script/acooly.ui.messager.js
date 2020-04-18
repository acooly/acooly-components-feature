/**
 * acooly 对 layui的扩展和封装使用
 * @type {{loading: acooly_layui.loading, loaded: acooly_layui.loaded, msg: acooly_layui.msg}}
 */
var acooly_layui = {

    loadingId: '',
    /**
     * 打开加载load效果
     * @param msg
     * @returns {string} loadingId
     */
    loading: function (msg) {
        if (!msg)
            msg = '加载中…';
        this.loadingId = layer.msg(msg, {icon: 16, shade: 0.01});
        return this.loadingId;
    },

    /**
     * 关闭加载load效果
     * @param id
     */
    loaded: function (id) {
        if (id) {
            layer.close(id);
            return;
        }
        if (this.loadingId) {
            layer.close(this.loadingId);
            return;
        }
        layer.closeAll();
    },

    msg_data: {
        "default": {title: '信息', defaultContent: '', icon: 'ion ion-ios-information'},
        "success": {title: '成功', defaultContent: '处理成功', icon: 'ion ion-ios-checkmark'},
        "info": {title: '提示', defaultContent: '', icon: 'ion ion-ios-information'},
        "warning": {title: '警告', defaultContent: '', icon: 'ion ion-android-alert'},
        "primary": {title: '信息', defaultContent: '', icon: 'ion ion-ios-information'},
        "danger": {title: '失败', defaultContent: '处理失败', icon: 'ion ion-ios-close'}
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
        var html = "<div role=\"alert\" class=\"ac-notification right\" style=\"top: 16px; z-index: 12150;display: none \">\n" +
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
        if (!icon) {
            icon = 0;
        }
        layer.msg(message, {icon: icon, closeBtn: 1});
    },

    msgrb: function (message, success, title) {
        layer.msg(message, {
            icon: (success ? 1 : 5),
            title: (title == null ? '提示' : title),
            offset: 'rb',
            time: 4000,
            anim: 2,
            area: '260px'
        });
    },

    alert: function (title, message, opts) {
        var os = {title: title, anim: -1};
        if (opts) {
            os = $.extend(os, opts);
        }
        layer.alert(message, os);
    },

    tips: function (content, objectId, position) {
        if (!position) {
            position = 2;
        }
        // if (objectId.indexOf('#') != 0) {
        //     objectId = '#' + objectId;
        // }
        layer.tips(content, objectId, {
            tips: position,
            style: ['background-color:#81af43; color:#fff', '#81af43'],
            maxWidth: 200,
            closeBtn: [1, true] //显示关闭按钮
        });
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

    $.extend($.acooly, acooly_layui);

})(jQuery);

