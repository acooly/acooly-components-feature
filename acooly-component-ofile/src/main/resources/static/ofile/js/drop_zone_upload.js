/*
文件拖拽上传方案：参考：onlineFile.ftl
1. 集成文件上传按钮，访问地址  ：  /manage/module/ofile/onlineFile/uploadFilePage.html?bussId=111
2. oFileinit 文件初始化
3. 接受文件类型，支持但页面配置 示例： <input id="acceptedFileTypes" type="hidden" value=".log,.xlsx,.jpg,.png">
4. oFileinit 文件初始化-支持动态回调函数（文件回显函数，文件删除回调，文件上传成功回调，文件列表点击回调）
 */

//div做上传区域时使用的配置
// console.log("文件上传(拖拽)-加载完成");

Dropzone.autoDiscover = false;// 禁止对所有元素的自动查找，由于Dropzone会自动查找class为dropzone的元素，自动查找后再在这里进行初始化
//业务ID
var bussId = $('#bussId').val();

//支持文件类型(页面调用者指定，文本隐藏)
//例如： <input id="acceptedFileTypes" type="hidden" value=".log,.xlsx,.jpg,.png">
var acceptedFileTypes=$('#acceptedFileTypes').val();

//上传文件列表
var uploadFile = [];

/**
 * 初始化调用
 * function oFileUploadInit(){
 *     let ids = [];
 *     init.call(this,ids);
 * }
 */

if (typeof oFileUploadShow === 'function') {
    // oFileUploadShow exists, call it
    oFileUploadShow();
} else {
    // oFileUploadShow doesn't exist
    oFileinit([],function () {},function () {},function () {})
}

/**
 * 文件上传初始化
 * ids：回显-内容
 * oFileDeleteSuccess：文件删除回调
 * oFileUploadSuccess：文件上传成功回调
 * oFileClickItem：文件列表点击回调
 * */
function oFileinit(ids,oFileDeleteSuccess,oFileUploadSuccess,oFileClickItem){
    console.log("ids 文件：", ids);
    let jsonIds = JSON.stringify(ids);
    //数据为空不请求后端
    if (ids.length === 0) {
        ofileInitResult();
    } else {
        // 初始化函数 根据bussId查找文件list
        $.ajax(
            {
                type: "POST",
                url: "/manage/module/ofile/onlineFile/getInitFiles.html",
                dataType: "JSON",
                data: {
                    "ids": jsonIds,
                    "_csrf": $('#_csrf').val(),
                },
                success: function (result) {
                    for (let fileListKey in result.rows) {
                        let file = {
                            name: result.rows[fileListKey].fileName, //需要显示给用户的图片名
                            id: result.rows[fileListKey].id, //需要显示给用户的图片名
                            size: result.rows[fileListKey].fileSize, //图片尺寸
                            height: 200,
                            width: 200,
                            objectId: result.rows[fileListKey].objectId,
                            accessUrl: result.rows[fileListKey].accessUrl,
                            type: result.rows[fileListKey].fileExt,//图片文件类型
                            url: result.rows[fileListKey].accessThumbnailUrl//mix图片文件
                        };
                        console.log("add ofile文件：", file.name, file.url, file.type);
                        uploadFile.push(file);
                    }
                    // 准备好数据后，去渲染ofile组件
                    ofileInitResult(oFileDeleteSuccess,oFileUploadSuccess,oFileClickItem);
                },
                error: function (result) {
                    console.log("add ofile文件异常：", result.message);
                }
            });

    }



}
function ofileDownload() {
    //var s = ${this.options.dictRemoveFile};
    console.log(this);
}

/**
 * 文件处理结果
 * @param callBack
 */
function ofileInitResult(oFileDeleteSuccess,oFileUploadSuccess,oFileClickItem) {
    var drop_zone = new Dropzone("#dropzone-img-div", {
        url: "/ofile/upload.html",
        //指定上传的文件类型
        acceptedFiles:acceptedFileTypes,
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
        dictRemoveFile: '删除',//
        previewTemplate: '<div class="dz-preview dz-file-preview"> <div class="dz-image"><img data-dz-thumbnail width="110px" height="110px"/></div> <div class="dz-details" data-dz-details=""> <div class="dz-size"><span data-dz-size></span></div> <div class="dz-filename"><span data-dz-name></span></div> </div> <div class="dz-progress"> <span class="dz-upload" data-dz-uploadprogress></span> </div> <div class="dz-error-message"><span data-dz-errormessage></span></div> <div class="dz-success-mark"> <svg width="54px" height="54px" viewBox="0 0 54 54" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"> <title>Check</title> <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"> <path d="M23.5,31.8431458 L17.5852419,25.9283877 C16.0248253,24.3679711 13.4910294,24.366835 11.9289322,25.9289322 C10.3700136,27.4878508 10.3665912,30.0234455 11.9283877,31.5852419 L20.4147581,40.0716123 C20.5133999,40.1702541 20.6159315,40.2626649 20.7218615,40.3488435 C22.2835669,41.8725651 24.794234,41.8626202 26.3461564,40.3106978 L43.3106978,23.3461564 C44.8771021,21.7797521 44.8758057,19.2483887 43.3137085,17.6862915 C41.7547899,16.1273729 39.2176035,16.1255422 37.6538436,17.6893022 L23.5,31.8431458 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z" stroke-opacity="0.198794158" stroke="#747474" fill-opacity="0.816519475" fill="#FFFFFF"></path> </g> </svg> </div> <div class="dz-error-mark"> <svg width="54px" height="54px" viewBox="0 0 54 54" version="1.1" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink"> <title>Error</title> <g stroke="none" stroke-width="1" fill="none" fill-rule="evenodd"> <g stroke="#747474" stroke-opacity="0.198794158" fill="#FFFFFF" fill-opacity="0.816519475"> <path d="M32.6568542,29 L38.3106978,23.3461564 C39.8771021,21.7797521 39.8758057,19.2483887 38.3137085,17.6862915 C36.7547899,16.1273729 34.2176035,16.1255422 32.6538436,17.6893022 L27,23.3431458 L21.3461564,17.6893022 C19.7823965,16.1255422 17.2452101,16.1273729 15.6862915,17.6862915 C14.1241943,19.2483887 14.1228979,21.7797521 15.6893022,23.3461564 L21.3431458,29 L15.6893022,34.6538436 C14.1228979,36.2202479 14.1241943,38.7516113 15.6862915,40.3137085 C17.2452101,41.8726271 19.7823965,41.8744578 21.3461564,40.3106978 L27,34.6568542 L32.6538436,40.3106978 C34.2176035,41.8744578 36.7547899,41.8726271 38.3137085,40.3137085 C39.8758057,38.7516113 39.8771021,36.2202479 38.3106978,34.6538436 L32.6568542,29 Z M27,53 C41.3594035,53 53,41.3594035 53,27 C53,12.6405965 41.3594035,1 27,1 C12.6405965,1 1,12.6405965 1,27 C1,41.3594035 12.6405965,53 27,53 Z"></path> </g> </g> </svg> </div> </div> ',
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

                    myDropzone.processQueue();
                } else {
                    //操作

                }
                // }
            });

            this.on("addedfile", function(file) {
                let fileItemEvent = (e) => {
                    if (typeof oFileClickItem === 'function') {
                        /**
                         * function oFileClickItem(bussId, serviceFile){  }
                         */
                        oFileClickItem(bussId, file);

                    }
                };
                console.log("addedfile"+ file);

                for (let itemLink of file.previewElement.querySelectorAll(
                    "[data-dz-details]"
                )) {
                    itemLink.addEventListener("click", fileItemEvent);
                }
            });

            //文件删除成功
            this.on("removedfile", function (file) {
                let deleteFile = file;
                //查看上传组件的文件状态机是否是已经成功的，状态机源码位置js 3888行
                if (file.status == "success") {
                    $.ajax(
                        {
                            type: "POST",
                            url: "/manage/module/ofile/onlineFile/deleteJson.html",
                            dataType: "JSON",
                            data: {
                                "id": file.id,
                                "_csrf": $('#_csrf').val(),
                            },
                            success: function (result) {
                                if (result.success) {
                                    console.log("删除ofile文件成功--调用回调函数-oFileDeleteSuccess");
                                    if (typeof oFileDeleteSuccess === 'function') {
                                        /**
                                         * function oFileDeleteSuccess(serviceFile){ }
                                         */
                                        oFileDeleteSuccess( bussId, deleteFile);
                                        console.log("删除ofile文件：", deleteFile.id, deleteFile.name, deleteFile.upload.uuid, result.message);
                                    }
                                }
                            },
                            error: function (result) {
                                console.log("删除ofile文件异常：", result.message);
                            }
                        });
                }

            });

            //文件上传成功
            this.on("success", function (file, res) {
                var fileListSize = res.rows.length;
                for (i = 0; i < fileListSize; i++) {
                    var serviceFile = res.rows[i];
                    var originalName = serviceFile.originalName;
                    if (originalName == file.name) {
                        file.id = serviceFile.id;
                        file.objectId = serviceFile.objectId;
                        file.accessUrl = serviceFile.accessUrl;

                        console.log("文件上传成功--准备调用回调函数-oFileUploadSuccess");

                        if (typeof oFileUploadSuccess === 'function') {
                            /**
                             * function oFileUploadSuccess(serviceFile){  }
                             */
                            oFileUploadSuccess(bussId, serviceFile);
                        }
                    }
                }
            });

            //当事件全部添加成功后 再循环需要回显的文件
            for (let uploadFileKey in uploadFile) {
                // 添加文件
                myDropzone.addFile(uploadFile[uploadFileKey]);
                // 添加缩略图
                myDropzone.options.thumbnail(uploadFile[uploadFileKey], uploadFile[uploadFileKey].url);
                // 这里需要调用一次完成事件，否则图片显示的是未上传的样子
                myDropzone.emit("complete", uploadFile[uploadFileKey]);
                // 设置当前文件为 已上传成功
                uploadFile[uploadFileKey].status = "success";
            }


        }
    });


}



