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
};

(function ($) {
    if (!$.acooly) {
        $.extend({acooly: {}});
    }

    $.extend($.acooly, acooly_layui);

})(jQuery);

