/**
 * 文件处理
 * @type {{play: acooly_file.play, SERVER_ROOT: string, download: acooly_file.download, playMedia: acooly_file.playMedia, playImage: acooly_file.playImage, playPdf: acooly_file.playPdf, parse: (function(*): {}), fileType: {image: {ext: string, name: string, icon: string}, other: {ext: string, name: string, icon: string}, pdf: {ext: string, name: string, icon: string}, archive: {ext: string, name: string, icon: string}, media: {ext: string, name: string, icon: string}, office: {ext: string, name: string, icon: string, getIcon: (function(*=): (string|string))}}}}
 */
let acooly_file = {
    play: function (resourceUrl, opts) {
        if(!resourceUrl || resourceUrl == '') return;
        if (this.SERVER_ROOT != '') {
            if (!resourceUrl.startsWith(this.SERVER_ROOT)) {
                resourceUrl = this.SERVER_ROOT + resourceUrl;
            }
        }
        let data = this.parse(resourceUrl);
        if (data.type == this.fileType.image) {
            this.playImage(resourceUrl, opts);
        } else if (data.type == this.fileType.pdf) {
            this.playPdf(resourceUrl, opts);
        } else if (data.type == this.fileType.media) {
            this.playMedia(resourceUrl, opts);
        } else {
            this.download(resourceUrl, opts);
        }
    },

    download: function (resourceUrl) {
        window.open(resourceUrl);
    },

    playMedia: function (resourceUrl, opts) {
        let data = this.parse(resourceUrl);
        let defOpts = {
            width: 650, height: 530,
            maximizable: false, modal: true,
            title: " <i class='fa " + data.icon + "' aria-hidden='true'></i> " + data.name,
            onClose: function () {
                $(this).dialog('destroy');
            }
        };
        let config = $.extend(defOpts, opts);

        let w = config.width - 10, h = config.height - 50;
        config.content = "<video class='video-js vjs-big-play-centered' playsinline controls preload='auto' width='" + w + "' height='" + h + "'\n" +
            "               data-setup='{}' language=\"zh\"\n" +
            "    <source src='" + resourceUrl + "' type='video/mp4'>\n" +
            "    <p class='vjs-no-js'>\n" +
            "        To view this video please enable JavaScript, and consider upgrading to a web browser that\n" +
            "        <a href='https://videojs.com/html5-video-support/' target='_blank'>supports HTML5 video</a>\n" +
            "    </p>\n" +
            "</video>"
        $('<div/>').dialog(config);
    },

    playPdf: function (resourceUrl, opts) {
        let data = this.parse(resourceUrl);
        let defOpts = {
            width: 1024, height: 768,
            maximizable: true, modal: true,
            title: " <i class='fa " + data.icon + "' aria-hidden='true'></i> " + data.name,
            content: "<a class='media' href='" + resourceUrl + "'></a>",
            onClose: function () {
                $(this).dialog('destroy');
            },
            onOpen: function () {
                let options = $(this).dialog('options');
                $('.media').media({width: options.width - 10, height: options.height});
            }
        };
        let config = $.extend(defOpts, opts);
        $('<div/>').dialog(config);
    },

    playImage: function (resourceUrl, opts) {
        let data = this.parse(resourceUrl);
        let defOpts = {
            maximizable: false, modal: true,
            title: " <i class='fa " + data.icon + "' aria-hidden='true'></i> " + data.name,
            onClose: function () {
                $(this).dialog('destroy');
            }
        };
        let options = $.extend(defOpts, opts);
        if(!options.width){
            options.width = (document.documentElement.clientWidth ||  document.body.clientWidth) - 100;
        }
        if(!options.height){
            options.height = (document.documentElement.clientHeight || document.body.clientHeight) - 100;
        }
        let realWidth, realHeight;
        $("<img/>").attr("src", resourceUrl).load(function () {
            realWidth = this.width;
            realHeight = this.height;
            let maxWidth = options.width, maxHeight = options.height;
            let width = realWidth, height = realHeight;
            if (realWidth > maxWidth) {
                width = maxWidth;
                height = (width * realHeight / realWidth);
            }
            realHeight = height;
            if(height > maxHeight){
                height = maxHeight
                width = (height * width) / realHeight
            }
            options.width = width + 20;
            options.height = height + 55;
            options.width = options.width < 300 ? 300:options.width;
            options.height = options.height < 200 ? 200:options.height;
            options.content = "<div style='text-align: center;'><a href='" + resourceUrl + "' target='_blank'>" +
                "<img style='width: " + width + "px;height: " + height + "px' src='" + resourceUrl + "'></a></div>"
            $('<div/>').dialog(options);
        });
    },

    /**
     * 简单解析文件URL
     * @param resourceUrl
     * @returns {{name:"",type:"",icon:""}}
     */
    parse: function (resourceUrl) {
        let data = {};
        if (resourceUrl.indexOf(".") == -1) {
            data.type = this.fileType.other;
        } else {
            let fileExtension = resourceUrl.split('.').pop().toLowerCase();
            let fileType, fileIcon;
            if (this.fileType.image.ext.indexOf(fileExtension) != -1) {
                fileType = this.fileType.image;
                fileIcon = this.fileType.image.icon;
            } else if (this.fileType.pdf.ext.indexOf(fileExtension) != -1) {
                fileType = this.fileType.pdf;
                fileIcon = this.fileType.pdf.icon;
            } else if (this.fileType.media.ext.indexOf(fileExtension) != -1) {
                fileType = this.fileType.media;
                fileIcon = this.fileType.media.icon;
            } else if (this.fileType.office.ext.indexOf(fileExtension) != -1) {
                fileType = this.fileType.office;
                fileIcon = this.fileType.office.getIcon(fileExtension);
            } else if (this.fileType.archive.ext.indexOf(fileExtension) != -1) {
                fileType = this.fileType.archive;
                fileIcon = this.fileType.archive.icon;
            } else {
                fileType = this.fileType.other;
                fileIcon = this.fileType.other.icon;
            }
            data.type = fileType;
            data.icon = fileIcon;
        }
        if (resourceUrl.indexOf("/") == -1) {
            data.name = resourceUrl;
        } else {
            data.name = resourceUrl.split('/').pop();
        }
        return data;
    },

    fileType: {
        image: {name: "图片", ext: "jpg,jpeg,png,gif,bmp", icon: 'fa-picture-o'},
        pdf: {name: "PDF", ext: "pdf", icon: "fa-file-pdf-o"},
        media: {
            name: "媒体", ext: "aif,aiff,aac,au,bmp,gsm,mov,mid,midi,mpg,mpeg,mp4,m4a,psd,qt,qtif,qif,qti,snd,tif,tiff,wav,3g2,3pg,flv, mp3, swf,asx, asf, avi, wma, wmv,ra, ram, rm, rpm, rv, smi, smil,xaml", icon: "fa-file-video-o"
        },
        archive: {name: '归档', ext: "zip,rar,iso", icon: "fa-file-archive-o"},
        office: {
            name: "文档", ext: "doc,docx,xls,xlsx,rtf,ppt,pptx", icon: "fa-file-o", getIcon: function (ext) {
                if (!ext || ext == '') return this.icon;
                if (ext.startsWith('doc') || ext == "rtf") {
                    return 'fa-file-word-o';
                } else if (ext.startsWith('xls')) {
                    return "fa-file-excel-o";
                } else if (ext.startsWith('ppt')) {
                    return "fa-file-powerpoint-o";
                } else {
                    return this.icon;
                }
            }
        },
        other: {name: "其他", ext: "", icon: 'fa-file-o'}
    },

    /**
     * 媒体服务器根路径
     */
    SERVER_ROOT: ""
};

/***
 * JQuery静态类前缀处理
 */
(function ($) {
    if (!$.acooly) {
        $.acooly = {};
    }
    if (!$.acooly.file) {
        $.extend($.acooly, {file: acooly_file});
    }
})(jQuery);

