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

    /**
     * 消息框
     * @param title 标题
     * @param message 消息
     * @param type {info,success,warning,danger,primary}
     */
    messager: function (title, message, type) {
        var args = arguments;
        var ctitle,cmessage,ctype;
        if (args.length == 1) {
            ctitle = '提示';
            cmessage = title;
            ctype = true;
        }else if(args.length == 2){
            ctitle = '提示';
            cmessage = title;
            ctype = message;
        }else{
            ctitle = '提示';
            cmessage = message;
            ctype = type;
        }
        layer.msg(cmessage, {
            icon: (type=='success' ? 1 : 5),
            title: (title == null ? '提示' : title),
            offset: 'rb',
            time: 4000,
            anim: 2,
            area: '260px'
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
        var os = {title:title,anim: -1};
        if(opts){
            os = $.extend(os,opts);
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
    }
};

(function ($) {
    if (!$.acooly) {
        $.extend({acooly: {}});
    }

    $.extend($.acooly, acooly_layui);

})(jQuery);

