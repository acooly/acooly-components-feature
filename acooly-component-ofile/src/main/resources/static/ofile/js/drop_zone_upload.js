//div做上传区域时使用的配置
Dropzone.autoDiscover = false;// 禁止对所有元素的自动查找，由于Dropzone会自动查找class为dropzone的元素，自动查找后再在这里进行初始化
var drop_zone = new Dropzone("#dropzone-img-div", {
    url: "/ofile/upload.html",
    //改变文件框中的文字提示
    dictDefaultMessage: '<span class="bigger-150 bolder"><i class="icon-caret-right red"></i> 拖动文件至该处</span><span class="smaller-80 grey">(或点击此处)</span> <br /><i class="upload-icon icon-cloud-upload blue icon-3x"></i>',
    //方式指定
    method: 'post',
    filesizeBase: 102400000,//这个选项将设置在计算文件大小时使用 1000 还是使用 1024作为基本单位
    //dropzone同时上传的文件不能超过6个，所以parallelUploads的设置就不能大于6，大于6后就会出现“超过最大长度”   //错误，导致上传失败
    parallelUploads: 200,//有多少文件将上载到并行处理,默认2(一次最多上传不能超过6个，小于等于6个的传完后，再上传  //第二批的文件)
    maxFilesize: 3072,//以MB为单位[上传文件的大小限制]
    autoProcessQueue: false,//关闭自动上传功能，默认会true会自动上传,也就是添加一张图片向服务器发送一次请求
    addRemoveLinks: true,//在每个预览文件下面添加一个remove[删除]或者cancel[取消](如果文件已经上传)上传文件的链  接
    uploadMultiple: true,//允许上传多文件
    dictCancelUpload: '取消',
    dictRemoveFile: '删除',
    dictFallbackMessage: '不好意思，您的浏览器不支持！',//如果浏览器不支持,默认消息将被替换为这个文本。默认为 “Your browser does not support drag'n'drop file uploads.”。
    dictInvalidFileType: '该文件不允许上传',//如果文件类型不匹配时显示的错误消息。
    dictResponseError: '上传失败，请稍后重试',//如果服务器响应是无效的时候显示的错误消息。 {{statusCode}} ` 将被  替换为服务器端返回的状态码。
    //函数绑定
    init: function () {
        var submitButton = $("#saveForm")
        myDropzone = this;
        //为上传按钮添加点击事件
        submitButton.on("click", function () {
            //此处可以动态设置url，为了向文件上传的后台提供项目对应的id
            myDropzone.options.url = '/ofile/upload.html';
            //手动上传所有图片
            //判断上传的文件不为空时,（保证没有选择文件的时候不进行上传操作）
            if (myDropzone.getAcceptedFiles().length != 0) {
                //如果你设置了选项 autoProcessQueue 为 true, 然后队列就会被立即处理, 在
                //文件拖放到zone或者上传完成后, 调用.processQueue() 来检查当前有多少文件
                //正在被上传,如果它的值小于options.parallelUploads的设置值时,就会调用
                //.processFile(file)上传文件
                // 如果你设置 autoProcessQueue为false, 那么 .processQueue() 方法是不会
                //被隐式调用的. 这意味着你必须在你想要上传队列中的所有文件时，自己去调用这
                //个方法（引用自：
                //http://wxb.github.io/dropzonejs.com.zh-CN/dropzonezh-CN/#installation）

                //手动指定
                myDropzone.processQueue();
            } else {
                //操作

            }
            // }
        });
        this.on("removedfile", function (file) {
            $.ajax(
                {
                    type: "POST",
                    url: "/manage/module/ofile/onlineFile/deleteJson.html",
                    dataType:"JSON",
                    data:{
                        "id":file.id,
                        "_csrf": $('#_csrf').val(),
                    },
                    success:function(result){
                        console.log("删除ofile文件：",result.message);
                    },
                    error:function(result){
                        console.log("删除ofile文件异常：",result.message);
                    }
                });
        });
        this.on("success", function (file, res) {
            var fileListSize = res.rows.length;
            for (i = 0; i < fileListSize; i++) {
                var serviceFile = res.rows[i];
                var originalName = serviceFile.originalName;
                if (originalName == file.name) {
                    file.id=serviceFile.id;
                    // console.log(file.name, file.upload.uuid,serviceFile.id,originalName);
                }
            }
        });
    }
});