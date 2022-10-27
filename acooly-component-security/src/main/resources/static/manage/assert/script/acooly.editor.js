let acooly_editor = {
    /**
     * KindEditor富文本框编辑器初始化
     */
    kindEditor: function (opts) {
        if (!KindEditor) {
            console.log("KindEditor为加载，请配置加载插件。");
            return;
        }
        let uploadUrl = opts.uploadUrl;
        if (!uploadUrl) {
            let token = $("meta[name='X-CSRF-TOKEN']").attr("content");// 从meta中获取token
            uploadUrl = '/ofile/kindEditor.htm?_csrf=' + token;
        }
        opts.uploadUrl = uploadUrl;
        let options = {
            // themeType : 'simple',
            // //指定主题风格，可设置”default”、”simple”，指定simple时需要引入simple.css;
            // 默认值: “default”
            minHeight: opts.minHeight + 'px',
            resizeType: 1, // 2或1或0，2时可以拖动改变宽度和高度，1时只能改变高度，0时不能拖动;默认值:2
            allowFileManager: true, // true时显示浏览远程服务器按钮 ;默认值: false
            allowMediaUpload: true, // true时显示视音频上传按钮。默认值: true
            allowFlashUpload: true, // true时显示Flash上传按钮;默认值: true
            items: ['fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline', 'removeformat',
                'lineheight', '|', 'justifyleft', 'justifycenter', 'justifyright', 'anchor', 'plainpaste', 'wordpaste', 'clearhtml',
                'quickformat', 'insertorderedlist', 'insertunorderedlist', '|', 'emoticons', 'image', "multi_image",
                'diy_video', '|',
                'baidumap', 'link', 'unlink', '|', 'hr', 'table', '|', 'source', 'preview', 'mobile', 'fullscreen'],
            // 单个上传上传的url
            uploadJson: uploadUrl,
            // 批量上传URL
            uploadApi: uploadUrl,
            // 不对服务端返回的可访问URL进行格式化（删除域名前缀）
            formatUploadUrl: false,
            afterCreate: function () {
                // 加载完成后改变皮肤
                var color = $('.panel-header').css('background-color');
                $('.ke-toolbar').css('background-color', color);

                $.acooly.editor.pasteToUpload(this, opts);
            },
            // 失去焦点时，保存
            afterBlur: function () {
                this.sync();
            }
        };
        KindEditor.options.filterMode = false;
        return KindEditor.create('#' + opts.textareaId, options);
    },

    /**
     * 粘贴截图自动上传
     * @param editorObj
     * @param opts
     */
    pasteToUpload: function (editorObj, opts) {
        var doc = editorObj.edit.doc;
        $(doc.body).bind('paste', function (event) {
            setTimeout(function () {
                // 处理bug
                var useless = $(doc.body).find(".__kindeditor_paste__");
                if (useless) {
                    useless.removeAttr("style");
                    useless.removeClass("__kindeditor_paste__");
                }
                var imgs = $(doc.body).find("img");
                $.each(imgs, function (index, item) {
                    // layer
                    layerindex = layer.load(1, {
                        shade: [0.3, '#fff'],
                        content: '转存中',
                        success: function (layero) {
                            layero.find('.layui-layer-content').css({
                                'padding-top': '39px',
                                'width': '120px',
                                'margin-left': '-60px'
                            });
                        }
                    });
                    var _that = $(this);
                    var imgSrc = decodeURIComponent(_that.attr("src"));
                    if (imgSrc.indexOf("data:") > -1) {
                        var blob = $.acooly.editor.dataURLtoBlob(imgSrc);
                        var form = document.imgForm;
                        var formData = new FormData(form);
                        formData.append("file", blob);
                        // 上传粘贴板中的截图到服务器
                        $.ajax({
                            type: "POST",
                            url: opts.uploadUrl,
                            data: formData,
                            dataType: "json",
                            processData: false,
                            contentType: false,
                            success: function (res) {
                                layer.close(layerindex);
                                if (res.success) {
                                    _that.attr('src', res.url);
                                    _that.attr('data-ke-src', res.url);
                                }
                            },
                            fail: function () {
                                layer.close(layerindex);
                            }
                        });
                    } else {
                        // 本站网络图片不处理
                        layer.close(layerindex);
                    }
                });
            }, 10);
        });
    },

    /**
     * base64转blob
     * @param dataurl
     * @returns {Blob}
     */
    dataURLtoBlob: function (dataurl) {
        var arr = dataurl.split(','),
            mime = arr[0].match(/:(.*?);/)[1],
            bstr = atob(arr[1]),
            n = bstr.length,
            u8arr = new Uint8Array(n);
        while (n--) {
            u8arr[n] = bstr.charCodeAt(n);
        }
        return new Blob([u8arr], {type: mime});
    },


};

/***
 * JQuery静态类前缀处理
 */
(function ($) {
    if (!$.acooly) {
        $.acooly = {};
    }
    if (!$.acooly.editor) {
        $.extend($.acooly, {editor: acooly_editor});
    }
})(jQuery);
