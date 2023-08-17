/**
 * acooly framework basic
 *
 * @author zhangpu
 * @date 2012-4-15
 * @version: 1.0.0
 * @required JQuery EasyUI
 */
(function ($) {
    if (!$.acooly) {
        $.acooly = {};
    }
    $.extend($.acooly, {
        acooly: {
            selectedMenu: null,
            westTree: null,
            divdialog: null
        }
    });
    $.extend($.acooly, {
        framework: {
            // 各个表单的查询条件,用于导出
            afterQueryParams: {},
            /**
             * 添加
             */
            create: function (opts) {
                if (opts.entity) {
                    $.acooly.framework.doCreate(opts.url, "manage_" + opts.entity + "_editform", "manage_" + opts.entity + "_datagrid", opts.title, opts.top, opts.width, opts.height, opts.addButton, opts.reload, opts.maximizable, opts.onSubmit, opts.onSuccess, (opts.onFail || opts.onFailure), (opts.onCloseWindow || opts.onClose), opts.ajaxData, opts.buttons, opts.hideSaveBtn);
                } else {
                    $.acooly.framework.doCreate(opts.url, opts.form, opts.datagrid, opts.title, opts.top, opts.width, opts.height, opts.addButton, opts.reload, opts.maximizable, opts.onSubmit, opts.onSuccess, (opts.onFail || opts.onFailure), (opts.onCloseWindow || opts.onClose), opts.ajaxData, opts.buttons, opts.hideSaveBtn);
                }
            },
            /**
             * 提交表单处理
             * @param type
             * @param onSubmit
             * @param onSuccess
             * @param OnFailure
             */
            ajaxSubmitHandler: function (type, thisObject, form, datagrid, reload, onSubmit, onSuccess, onFailure) {
                var d = $(thisObject).closest('.window-body');
                $('#' + form).ajaxSubmit({
                    beforeSerialize: function (formData, options) {
                        // 编辑页面beforeSubmit方法
                        try {
                            var _beforeSubmit = eval(form + "_beforeSubmit");
                            if (typeof (_beforeSubmit) == "function") {
                                _beforeSubmit.call(this, formData);
                            }
                        } catch (e) {
                        }
                    },
                    beforeSubmit: function (formData, jqForm, options) {
                        // 如果有回调函数则处理回调函数的验证
                        if (onSubmit && !onSubmit.call(this, arguments)) {
                            return false;
                        }
                        // 编辑页面onSubmit方法
                        try {
                            var defaultFunc = eval(form + "_onSubmit");
                            if (defaultFunc && typeof (defaultFunc) == "function" && !defaultFunc.call(this, arguments)) {
                                return false;
                            }
                        } catch (e) {
                            // ig
                        }
                        // 默认easy-ui-validator验证
                        var result = $('#' + form).form('validate');
                        $(thisObject).linkbutton(result ? 'disable' : 'enable');
                        return result;
                    },
                    success: function (result, statusText) {
                        $(thisObject).linkbutton('enable');
                        if (typeof (result) == 'string') {
                            result = eval('(' + result + ')');
                        }
                        try {
                            if (result.success) {
                                // 如果有回调函数则处理回调函数
                                if (onSuccess) {
                                    onSuccess.call(d, result);
                                }

                                if (type == 'create') {
                                    $.acooly.framework.onSaveSuccess(d, datagrid, result, reload);
                                } else {
                                    $.acooly.framework.onUpdateSuccess(d, datagrid, result, reload);
                                }

                                $.acooly.divdialog.dialog('destroy');
                            } else {
                                if (onFailure) {
                                    onFailure.call(d, result);
                                }
                            }
                            if (result.message) {
                                $.acooly.messager("提示", result.message, result.success ? 'success' : 'danger');
                            }
                        } catch (e) {
                            $.acooly.messager('错误', e, 'danger');
                        }

                    },
                    error: function (XmlHttpRequest, textStatus, errorThrown) {
                        $(thisObject).linkbutton('enable');
                        $.acooly.messager('错误', errorThrown, 'danger');
                    }
                });
            },


            /**
             * 执行添加
             */
            doCreate: function (url, form, datagrid, title, top, width, height,
                                addButton, reload, maximizable, onSubmit, onSuccess, onFailure, onClose,
                                ajaxData, buttons, hideSaveBtn) {

                $('#' + datagrid).datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');

                var t = top ? top : null;
                var w = width ? width : 500;
                var h = height ? height : 'auto';
                var tt = title ? title : '添加';
                tt = '<i class="fa fa-lg fa-fw fa-col fa-floppy-o" ></i>' + tt;
                var addB = addButton ? addButton : '增加';
                addB = '<i class="fa fa-lg fa-fw fa-col fa-plus-circle" ></i>' + addB;
                var max = maximizable ? maximizable : false;
                url = $.acooly.framework.buildCanonicalUrl(url, ajaxData);

                // 构建buttons
                var saveBtn = {
                    id: 'acooly-framework-add-btn',
                    text: addB,
                    handler: function () {
                        $.acooly.framework.ajaxSubmitHandler("create", $(this), form, datagrid, reload, onSubmit, onSuccess, onFailure);
                    }
                };

                var closeBtn = {
                    text: '<i class="fa fa-times-circle fa-lg fa-fw fa-col" ></i>关闭',
                    handler: function () {
                        // var d = $(this).closest('.window-body');
                        d.dialog('close');

                    }
                };

                var buttons = buttons || [];
                if (!hideSaveBtn) {
                    buttons.push(saveBtn);
                }
                buttons.push(closeBtn);

                var d = null;
                $.acooly.divdialog = d = $('<div/>').dialog({
                    href: url,
                    title: tt,
                    top: t,
                    width: w,
                    height: h,
                    modal: true,
                    /*iconCls : 'icon-save',*/
                    maximizable: max,
                    buttons: buttons,
                    onClose: function () {
                        if (onClose) {
                            onClose.call(d);
                        }
                        $(this).dialog('destroy');
                    },
                    onOpen: function () {
                        // 打开dialog，EASYUI渲染完成后，处理combobox的宽度
                        var that = $(this);
                        $.parser.onComplete = function () {
                            //要执行的操作
                            $.acooly.framework.extendCombobox($(that));
                            //Initialize
                            $.acooly.framework.initPlugins($(that));
                            //最后把坑爹的事件绑定解除
                            $.parser.onComplete = function () {
                            };
                        }
                    }
                });
            },

            /**
             * 新增保存成功后，默认的处理：在datagrid的第一行添加该数据，同时关闭窗口。
             */
            onSaveSuccess: function (dialog, datagrid, result, reload) {
                var className = $('#' + datagrid).attr('class');
                if (!className || className == '') {
                    return;
                }
                if (reload) {
                    $.acooly.framework.gridReload(datagrid);
                } else {
                    if (className.indexOf('easyui-treegrid') != -1) {
                        var node = $('#' + datagrid).treegrid('getSelected');
                        if (node) {
                            if ($('#' + datagrid).treegrid('getChildren', node.id).length > 0) {
                                $('#' + datagrid).treegrid('reload', node.id);
                            } else {
                                $('#' + datagrid).treegrid('reload', node.parentId);
                            }
                        } else {
                            $('#' + datagrid).treegrid('reload');
                        }
                    } else {
                        $('#' + datagrid).datagrid('insertRow', {
                            index: 0,
                            row: result.entity
                        });
                    }
                }
            },

            /**
             * reload表格
             * @param datagrid
             * @private
             */
            gridReload: function (datagrid) {
                var className = $('#' + datagrid).attr('class');
                if (!className || className == '') {
                    return;
                }
                if (className.indexOf('easyui-treegrid') != -1) {
                    $('#' + datagrid).treegrid('reload');
                } else {
                    $('#' + datagrid).datagrid('reload');
                }
            },


            /**
             * 编辑
             */
            edit: function (opts) {
                if (opts.entity) {
                    $.acooly.framework.doEdit(opts.url, opts.id, "manage_" + opts.entity + "_editform", "manage_" + opts.entity + "_datagrid", opts.title, opts.top, opts.width, opts.height, opts.editButton, opts.reload, opts.maximizable, opts.onSubmit, opts.onSuccess,
                        (opts.onFail || opts.onFailure), (opts.onCloseWindow || opts.onClose), opts.ajaxData, opts.buttons, opts.hideSaveBtn);
                } else {
                    $.acooly.framework.doEdit(opts.url, opts.id, opts.form, opts.datagrid, opts.title, opts.top, opts.width, opts.height, opts.editButton, opts.reload, opts.maximizable, opts.onSubmit, opts.onSuccess,
                        (opts.onFail || opts.onFailure), (opts.onCloseWindow || opts.onClose), opts.ajaxData, opts.buttons, opts.hideSaveBtn);
                }
            },

            /**
             * 执行编辑
             */
            doEdit: function (url, id, form, datagrid, title, top, width, height, editButton, reload, maximizable, onSubmit, onSuccess, onFailure, onClose,
                              ajaxData, buttons, hideSaveBtn) {
                // changelog 删除编辑时自动unselect所有的行。 by zhangpu on 2013-06-07
                // $('#'+datagrid).datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
                var t = top ? top : null;
                var w = width ? width : 500;
                var h = height ? height : 'auto';
                var tt = title ? title : '编辑';
                tt = '<i class="fa fa-lg fa-fw fa-col fa-floppy-o" ></i>' + tt;
                var editB = editButton ? editButton : '修改';
                editB = '<i class="fa fa-lg fa-fw fa-col fa-plus-circle" ></i>' + editB;
                var max = maximizable ? maximizable : false;
                // changelog: 根据当前选中的行获取操作的ID. by zhangpu on 2013-06-07
                if (!id || id == '') {
                    var selectedRow = $.acooly.framework.getSelectedRow(datagrid);
                    if (selectedRow && selectedRow != null)
                        id = selectedRow.id;
                }
                // changelog-end
                if (!id || id == '') {
                    $.acooly.messager('提示', '请选择需要编辑的数据', 'primary');
                    return;
                }
                var href = $.acooly.framework.getCanonicalUrl(url, id);
                href = $.acooly.framework.buildCanonicalUrl(href, ajaxData);

                // 构建buttons
                var saveBtn = {
                    id: 'acooly-framework-edit-btn',
                    text: editB,
                    handler: function () {
                        $.acooly.framework.ajaxSubmitHandler("edit", $(this), form, datagrid, reload, onSubmit, onSuccess, onFailure);
                    }
                };
                var closeBtn = {
                    text: '<i class="fa fa-times-circle fa-lg fa-fw fa-col" ></i>关闭',
                    handler: function () {
                        // var d = $(this).closest('.window-body');
                        d.dialog('close');
                    }
                };

                var buttons = buttons || [];
                if (!hideSaveBtn) {
                    buttons.push(saveBtn);
                }
                buttons.push(closeBtn);

                var d = null;
                $.acooly.divdialog = d = $('<div/>').dialog({
                    href: href,
                    title: tt,
                    top: t,
                    width: w,
                    height: h,
                    modal: true,
                    /*iconCls : 'icon-save',*/
                    maximizable: max,
                    buttons: buttons,
                    onClose: function () {
                        if (onClose) {
                            onClose.call(d);
                        }
                        $(this).dialog('destroy');
                    },
                    onOpen: function () {
                        // 打开dialog，EASYUI渲染完成后，
                        var that = $(this);
                        $.parser.onComplete = function () {
                            // 处理combobox的宽度
                            $.acooly.framework.extendCombobox($(that));
                            //Initialize
                            $.acooly.framework.initPlugins($(that));
                            // 最后把坑爹的事件绑定解除
                            $.parser.onComplete = function () {
                            };
                        }
                    }
                });
            },

            /**
             * 修改更新保存成功后
             */
            onUpdateSuccess: function (dialog, datagrid, result, reload) {
                // changelog: by zhangpu on 2013-5-28, do compatibe with
                // datagrid and treegrid
                var className = $('#' + datagrid).attr('class');
                if (!className || className == '') {
                    return;
                }
                if (reload) {
                    $.acooly.framework.gridReload(datagrid);
                } else {
                    if (className.indexOf('easyui-treegrid') != -1) {
                        $('#' + datagrid).treegrid('update', {
                            id: result.entity.id,
                            row: result.entity
                        });
                    } else {
                        $('#' + datagrid).datagrid('updateRow', {
                            index: $('#' + datagrid).datagrid('getRowIndex', result.entity.id),
                            row: result.entity
                        });
                    }
                }
            },

            /**
             * search
             *
             * @param searchForm
             * @param datagrid
             */
            search: function (searchForm, datagrid) {
                // 查询页面beforeSubmit方法
                try {
                    var defaultFunc = eval(searchForm + "_beforeSubmit");
                    if (defaultFunc && typeof (defaultFunc) == "function" && !defaultFunc.call(this, arguments)) {
                        return;
                    }
                } catch (e) {
                }
                var queryParams = serializeObject($('#' + searchForm));
                if (isEmptyObject(queryParams)) {
                    queryParams = serializeObjectFromContainer($('#' + searchForm));
                }
                $.acooly.framework.afterQueryParams[searchForm] = queryParams;
                if (!this._isDatagrid(datagrid)) {
                    $('#' + datagrid).treegrid('load', queryParams);
                } else {
                    $('#' + datagrid).datagrid('load', queryParams);
                }

            },

            /**
             * 按键回车直接执行查询
             *
             * @param searchForm
             * @param datagride
             */
            registerKeydown: function (searchForm, datagride) {
                var form = $('#' + searchForm);
                $('input', form).keydown(function (event) {
                    if (event.keyCode == 13) {
                        $.acooly.framework.search(searchForm, datagride);
                    }
                });

            },

            initPage: function (searchForm, datagride) {
                this.registerKeydown(searchForm, datagride);
                this.extendCombobox(searchForm);
                this.initPlugins(searchForm);
                // console.info("$.acooly.system.config", $.acooly.system.config);
                // 如果开启开关，则初始化datagrid列显示扩展
                if ($.acooly.system.config && $.acooly.system.config.plugin["datagridExt"]) {
                    this.initDatagridMenu(datagride);
                }
            },

            /**
             * 查詢并導出數據
             *
             * @param url
             * @param searchForm
             * @param fileName
             */
            exports: function (url, searchForm, fileName, confirmTitle, confirmMessage) {
                var queryParams = $.acooly.framework.afterQueryParams[searchForm];
                if (isEmptyObject(queryParams)) {
                    queryParams = serializeObject($('#' + searchForm));
                }
                if (fileName) {
                    $(queryParams).attr('exportFileName', fileName);
                }

                $.acooly.framework.createAndSubmitForm(url, queryParams);
            },

            /**
             * 动态创建表单和提交表单
             */
            createAndSubmitForm: function (url, inputObjects) {
                var form = $('<form action="' + contextPath + url + '" method="POST"></form>');
                var token = $("meta[name='X-CSRF-TOKEN']").attr("content")
                form.append($('<input type="hidden" name="_csrf" value="' + token + '">'));

                var xss = false;
                try {
                    if (typeof (eval("filterXSS")) == "function") {
                        xss = true;
                    }
                } catch (e) {
                }

                for (var key in inputObjects) {
                    if (inputObjects[key] != '') {
                        var val = inputObjects[key];
                        if (xss) {
                            val = filterXSS(val);
                        }
                        var inputForm = $('<input type="hidden" name="' + key + '" value="' + val + '" />');
                        form.append(inputForm);
                    }
                }
                form.appendTo($('body')).submit();
                form.remove();
            },

            /**
             * show
             */
            show: function (url, width, height) {
                return this.get({
                    url: url,
                    width: width,
                    height: height
                });
            },
            get: function (opts) {
                var url = opts.url;
                var width = opts.width != null ? opts.width : 400;
                var height = opts.height != null ? opts.height : 300;
                var title = opts.title != null ? opts.title : '<i class="fa fa-file-o fa-lg fa-fw fa-col"></i>查看'
                var buttonLable = opts.buttonLable != null ? opts.buttonLable : '关闭';
                var d = $('<div/>').dialog({
                    href: contextPath + url,
                    width: width,
                    height: height,
                    modal: true,
                    title: title,
                    buttons: [{
                        text: '<i class="fa fa-times-circle fa-lg fa-fw fa-col" ></i>关闭',
                        handler: function () {
                            // var d = $(this).closest('.window-body');
                            d.dialog('close');
                        }
                    }],
                    onClose: function () {
                        $(this).dialog('destroy');
                    }
                });
                return d;
            },

            /**
             * 创建uploadify上传组件
             * @param options:可覆盖所有uploadify标准参数，自定扩展参数包括: {
             *     formData: {},        // 附带提交的post参数
             *     jsessionid: ''       // 可选，目前使用uploadfive，已不需要,
             *     messager: ''         // 用于显示上传提示信息的容器ID
             * }
             */
            createUploadify: function (options) {
                var uploadOptions = {
                    'auto': false,
                    // 'buttonClass': 'btn',
                    'buttonText': '选择文件',
                    'fileSizeLimit': 10240,
                    'fileType': false,
                    'height': 25,
                    'whith': 120,
                    'uploadLimit': 20,
                    'queueSizeLimit': 20,
                    // 'queueID': 'queue',
                    'dnd': true,
                    'uploadScript': contextPath + options.url + "&k=v;jsessionid=" + options.jsessionid,
                    'formData': options.formData,
                    onUploadComplete: function (file, data) {
                        var result = $.parseJSON(data);
                        if (result.success) {
                            $('#' + options.messager).html(result.message);
                        } else {
                            $('#' + options.messager).html('导入失败:' + result.message);
                        }
                    },
                    onProgress: function (file, e) {
                        if (e.lengthComputable) {
                            var percent = Math.round((e.loaded / e.total) * 100);
                            $('#' + options.messager).html("正在上传文件: [" + Math.round(e.loaded / 1024) + "K/" + Math.round(e.total / 1024) + "K]");
                        } else {
                            $('#' + options.messager).html("正在保存上传数据...");
                        }
                    },
                    onAddQueueItem: function (file) {
                        $('#' + options.messager).html(file.name);
                    },
                    onCancel: function (file) {
                        $('#' + options.uploader).val("");
                        /* 注意：取消后应重新设置uploadLimit */
                        var $data = $(this).data('uploadifive');
                        var settings = $data.settings;
                        settings.uploadLimit++;
                    },
                    onFallback: function () {
                        $('#' + options.messager).html("该浏览器无法使用!");
                    },
                    onError: function (errorType) {
                        var msg = errorType;
                        var $data = $(this).data('uploadifive');
                        var settings = $data.settings;
                        if (errorType == 'QUEUE_LIMIT_EXCEEDED') {
                            msg = "最多只能选择" + settings.uploadLimit + "个文件！";
                        } else if (errorType == "FILE_SIZE_LIMIT_EXCEEDED") {
                            msg = "上传文件最大不允许超过:" + settings.fileSizeLimit / 1024 / 1024 + "MB！";
                        }
                        $('#' + options.messager).html(msg);
                    }
                }
                var uploadfiveOptions = $.extend(uploadOptions, options);
                if (!uploadfiveOptions.queueID) {
                    uploadfiveOptions.queueID = false;
                }

                $('#' + options.uploader).uploadifive(uploadfiveOptions);
            },
            imports_dialog: '',

            /**
             * 导入视图
             * @param opts {width,height,title,url,datagrid,uploader}
             */
            imports: function (opts) {
                var w = opts.width ? opts.width : 400;
                var h = opts.height ? opts.height : 280;
                var load = (opts.datagrid) ? true : false;
                $.acooly.framework.imports_dialog = $('<div/>').dialog({
                    href: contextPath + opts.url,
                    width: w,
                    height: h,
                    modal: true,
                    title: opts.title ? opts.title : '<i class="fa fa-cloud-upload fa-lg fa-fw fa-col"></i> 数据导入',
                    buttons: [{
                        text: '<i class="fa fa-arrow-circle-o-up fa-lg fa-fw fa-col"></i> 上传导入',
                        handler: function () {
                            let uploadifiveOptions = $('#' + opts.uploader).data('uploadifive').settings;
                            if (uploadifiveOptions.onFormData) {
                                var data = uploadifiveOptions.onFormData.call(this);
                                if (uploadifiveOptions.formData) {
                                    data = $.extend(uploadifiveOptions.formData, data);
                                }
                                $('#' + opts.uploader).data('uploadifive').settings.formData = data;
                            }
                            $('#' + opts.uploader).uploadifive('upload');
                        }
                    }, {
                        text: '<i class="fa fa-times-circle fa-lg fa-fw fa-col" ></i> 关闭',
                        handler: function () {
                            $.acooly.framework.imports_dialog.dialog('close');
                        }
                    }],
                    onClose: function () {
                        // $('#' + opts.uploader).uploadifive('destroy');
                        if (load) {
                            $.acooly.framework.gridReload(opts.datagrid);
                        }
                        $(this).dialog('destroy');
                    }
                });
            },

            /**
             * 提交通过ID方式修改记录各种状态的submit。支持删除，移动，状态变更等
             */
            confirmSubmit: function (url, id, datagrid, confirmTitle, confirmMessage, successCallBack) {
                var title = confirmTitle ? confirmTitle : '确定';
                var message = confirmMessage ? confirmMessage : '您是否要提交该操作？';
                $.messager.confirm(title, message, function (r) {
                    if (r) {
                        $.ajax({
                            url: contextPath + url,
                            data: {
                                id: id
                            },
                            success: function (result) {
                                if (typeof (result) == 'string')
                                    result = eval('(' + result + ')');
                                if (result.success) {
                                    var className = $('#' + datagrid).attr('class');
                                    if (className.indexOf('easyui-treegrid') != -1) {
                                        $('#' + datagrid).treegrid('reload');
                                        // var node = $('#' + datagrid).treegrid('getSelected');
                                        // var parent = $('#' + datagrid).treegrid('getParent', node.id);
                                        // if (parent) {
                                        //     $('#' + datagrid).treegrid('reload', parent.id);
                                        // } else {
                                        //     $('#' + datagrid).treegrid('reload');
                                        // }
                                    } else {
                                        $('#' + datagrid).datagrid('reload');
                                    }
                                    if (successCallBack) {
                                        successCallBack.call(this);
                                    }
                                }
                                if (result.message) {
                                    $.acooly.messager('提示', result.message, result.success ? 'success' : 'danger');
                                }
                            }
                        });
                    }
                });
            },

            confirmRequest: function (url, requestData, datagrid, confirmTitle, confirmMessage, onSuccess, onFailure) {
                var title = confirmTitle ? confirmTitle : '确定';
                var message = confirmMessage ? confirmMessage : '您是否要提交该操作？';
                $.messager.confirm(title, message, function (r) {
                    if (r) {
                        $.ajax({
                            url: contextPath + url,
                            data: requestData,
                            success: function (result) {
                                if (result.success) {
                                    $('#' + datagrid).datagrid('reload');
                                    $('#' + datagrid).datagrid('uncheckAll').datagrid('unselectAll').datagrid('clearSelections');
                                    if (onSuccess) {
                                        onSuccess.call(this, arguments);
                                    }
                                } else {
                                    if (onFailure) {
                                        onFailure.call(this, arguments);
                                    }
                                }
                                if (result.message) {
                                    $.acooly.messager('提示', result.message, result.success ? 'success' : 'danger');
                                }
                            }
                        });
                    }
                });
            },

            /**
             * 排序移动，支持置顶和上移 confirmTitle,confirmMessage 为可选
             */
            move: function (url, id, datagrid, confirmTitle, confirmMessage) {
                $.post(url, {
                    id: id
                }, function (result) {
                    if (result.success) {
                        var className = $('#' + datagrid).attr('class');
                        if (className.indexOf('easyui-treegrid') != -1) {
                            var node = $('#' + datagrid).treegrid('getSelected');
                            var parent = $('#' + datagrid).treegrid('getParent', node.id);
                            if (parent) {
                                $('#' + datagrid).treegrid('reload', parent.id);
                            } else {
                                $('#' + datagrid).treegrid('reload');
                            }
                        } else {
                            $('#' + datagrid).datagrid('reload');
                        }
                    }
                    if (result.message) {
                        $.acooly.messager('提示', result.message, result.success ? 'success' : 'danger');
                    }
                });
            },

            /**
             * 删除 置顶ID，多个ID可以使用逗号分割
             */
            remove: function (url, ids, datagrid, confirmTitle, confirmMessage, successCallBack) {
                $.acooly.framework.confirmSubmit(url, ids, datagrid, confirmTitle, confirmMessage, successCallBack);
            },

            /**
             * 批量刪除
             *
             * @param url
             * @param datagrid
             */
            removes: function (url, datagrid, confirmTitle, confirmMessage, successCallBack) {
                var rows = $.acooly.framework._getCheckedRows(datagrid);
                var ids = [];
                if (rows.length > 0) {
                    for (var i = 0; i < rows.length; i++) {
                        ids.push(rows[i].id);
                    }
                    $.acooly.framework.remove(url, ids.join(','), datagrid, confirmTitle, confirmMessage, successCallBack);
                } else {
                    $.acooly.messager('提示', "请勾选要删除的记录", 'warning');
                }
            },
            logout: function () {
                $.messager.confirm('提醒', '确定要注销退出吗？', function (r) {
                    if (r) {
                        window.location.href = contextPath + '/manage/logout.html';
                    }
                });
            },
            ajaxLogout: function (queryString) {
                $.messager.confirm('提醒', '确定要注销退出吗？', function (r) {
                    if (r) {
                        $.ajax({
                            url: contextPath + '/manage/logout.html',
                            success: function (data, txtStatus) {
                                console.info("txtStatus:" + txtStatus);
                                if (queryString) {
                                    window.location.href = contextPath + '/manage/login.html?' + queryString;
                                } else {
                                    window.location.href = contextPath + '/manage/login.html';
                                }
                            }
                        });
                    }
                });
            },
            changePassword: function () {
                var d = $('<div/>').dialog({
                    href: contextPath + '/manage/system/changePasswordView.html',
                    width: 500,
                    height: 400,
                    modal: true,
                    title: ' <i class="fa fa-lock fa-lg"></i> 修改密码',
                    buttons: [{
                        text: '<i class="fa fa-check fa-col"></i> 提交',
                        handler: function () {
                            $('#user_changePassword_form').ajaxSubmit({
                                beforeSubmit: function (formData, jqForm, options) {
                                    return $('#user_changePassword_form').form('validate');
                                },
                                success: function (result, statusText) {
                                    try {
                                        if (result.success) {
                                            d.dialog('destroy');
                                            // 注销，重新登录
                                            $.messager.alert('修改密码', '密码修改成功，请重新登录', "info",
                                                function () {
                                                    window.location.href = contextPath + '/manage/logout.html';
                                                }
                                            );
                                        } else {
                                            $.acooly.framework.changePasswordCaptcha();
                                            $.acooly.messager('提示', result.message, "danger");
                                        }
                                    } catch (e) {
                                        $.acooly.framework.changePasswordCaptcha();
                                        $.acooly.messager('错误', result, 'danger');
                                    }

                                },
                                error: function (XmlHttpRequest, textStatus, errorThrown) {
                                    $.acooly.messager('错误', errorThrown, 'danger');
                                }
                            });
                        }
                    }],
                    onClose: function () {
                        $(this).dialog('destroy');
                    },
                    onLoad: function () {

                    }
                });
            },
            changePasswordCaptcha: function () {
                $('#user_changePassword_jcaptchaImage').show();
                $('#user_changePassword_jcaptchaImage').attr("src", "/jcaptcha.jpg?" + new Date());
                $('#user_changePassword_passwordCaptcha').val('');
            },

            /**
             * KingEditor富文本框编辑器
             * @deprecated
             * @see $.acooly.editor.kindEditor
             */
            kingEditor: function (opts) {

                $.acooly.editor.kindEditor(opts);
            },

            /******************
             * 常用easyui工具方法
             */

            /**
             * 获取Datagrid或TreeGrid当前选中的行
             */
            getSelectedRow: function (grid) {
                var className = $('#' + grid).attr('class');
                if (className == 'easyui-treegrid') {
                    return $('#' + grid).treegrid('getSelected');
                } else {
                    return $('#' + grid).datagrid('getSelected');
                }
            },

            /**
             * 获取当前选择的tab的index
             * @param tabsId
             * @returns {*|{js, css, dependencies}}
             */
            getSelectedTabIndex: function (tabsId) {
                var tab = $('#' + tabsId).tabs('getSelected');
                return $('#' + tabsId).tabs('getTabIndex', tab);
            },

            /**
             * 选中grid的行，如果未选择则提示错误，否则回调传入的函数逻辑
             */
            fireSelectRow: function (gridId, selectedCallBack, errorMessage) {
                var row = $.acooly.framework.getSelectedRow(gridId);
                if (!row) {
                    var msg = '请先选择操作的数据行';
                    if (errorMessage) msg = errorMessage;
                    $.acooly.messager('提示', msg, 'warning');
                    return null;
                }
                selectedCallBack.call(this, row);
            },

            /**
             * 获取Datagrid或TreeGrid当前选中checkbox的行
             */
            _getCheckedRows: function (grid) {
                var className = $('#' + grid).attr('class');
                if (className == 'easyui-treegrid') {
                    return $('#' + grid).treegrid('getSelections');
                } else {
                    return $('#' + grid).datagrid('getSelections');
                }
            },

            /**
             * 重新加载grid数据
             */
            reloadGrid: function (grid) {
                var className = $('#' + datagrid).attr('class');
                if (className.indexOf('easyui-treegrid') != -1) {
                    $('#' + grid).treegrid('reload');
                } else {
                    $('#' + grid).datagrid('reload');
                }
            },

            /**
             * ajax方式手动load datagrid.
             *
             * opts:gridId,url,ajaxData
             */
            loadGrid: function (opts) {
                var data = {"sort": "id", "order": "desc", "page": 1, "rows": 20};
                $.extend(data, opts.ajaxData);
                $.ajax({
                    url: opts.url + '?' + new Date(),
                    data: data,
                    beforeSend: function () {
                        $('#' + opts.gridId).datagrid('loading');
                    },
                    complete: function () {
                        $('#' + opts.gridId).datagrid('loaded');
                    },
                    success: function (result) {
                        if (result.success) {
                            $('#' + opts.gridId).datagrid('loadData', result);
                        } else {
                            $.messager.show({title: '提示', msg: result.message});
                        }
                    },
                    error: function (r, e) {
                        $.messager.show({title: '提示', msg: e});
                    }
                });
            },

            /**
             * 组装待ID参数的URL
             */
            getCanonicalUrl: function (url, id) {
                url = contextPath + url;
                url += (url.indexOf('?') != -1 ? '&' : '?');
                url += 'id=' + id
                return url;
            },

            /**
             * 组装queryString参数的URL
             */
            buildCanonicalUrl: function (url, ajaxData) {
                var data = {};
                $.extend(data, ajaxData);
                url = contextPath + url;
                url += (url.indexOf('?') != -1 ? '' : '?');
                for (var k in data) {
                    url += ('&' + k + '=' + data[k]);
                }
                return url;
            },


            /**
             * 动态渲染组件
             * @param rowId
             * @private
             */
            renderDynamic: function (container) {
                let obj = container && container.jquery ? container : $('#' + container);
                // select2渲染（下拉选项）
                $(obj).find('.select2bs4').select2({theme: 'bootstrap4'});
                // input-mask and easyui 初始化渲染
                $(obj).find('[data-mask]').inputmask();
                // easyui组件动态渲染
                $.parser.parse($('#' + $(obj).attr('id')));
            },

            /**
             * 根据变动名称获取表单对象
             * @param form form表单的Id
             * @param formItemName form表单下字段的name
             */
            getFormItem: function (form, formItemName) {
                var itemObj = $("#" + form + " input[name='" + formItemName + "']");
                if (itemObj.length == 0) {
                    itemObj = $("#" + form + " select[name='" + formItemName + "']");
                }
                if (itemObj.length == 0) {
                    itemObj = $("#" + form + " textarea[name='" + formItemName + "']");
                }

                if (!itemObj.attr('class') && $(itemObj).prev() && $(itemObj).prev().attr('class') && $(itemObj).prev().attr('class').indexOf("easyui-numberbox") >= 0) {
                    itemObj = $(itemObj).prev();
                }
                if (itemObj.attr('class') && itemObj.attr('class').indexOf("combo-value") >= 0 && $(itemObj).parent() && $(itemObj).parent().prev() && $(itemObj).parent().prev().attr('class') && $(itemObj).parent().prev().attr('class').indexOf("easyui-combobox") >= 0) {
                    itemObj = $(itemObj).parent().prev();
                }
                return itemObj;
            },

            getFromItemValue: function (form, formItemName) {
                var itemObj = this.getFormItem(form, formItemName);
                var itemClass = itemObj.attr('class');
                var itemValue;
                if (itemClass.indexOf("easyui-combobox") >= 0) {
                    itemValue = $(itemObj).combobox("getValue");
                } else if (itemClass.indexOf("easyui-numberbox") >= 0) {
                    itemValue = $(itemObj).numberbox('getValue');
                } else {
                    itemValue = itemObj.val();
                }
                return itemValue;
            },

            setFormItemValue: function (form, formItemName, formItemValue) {
                var itemObj = this.getFormItem(form, formItemName);
                var itemClass = itemObj.attr('class');
                var itemValue;
                if (itemClass.indexOf("easyui-combobox") >= 0) {
                    itemValue = $(itemObj).combobox("select", formItemValue);
                } else if (itemClass.indexOf("easyui-numberbox") >= 0) {
                    itemValue = $(itemObj).numberbox('setValue', formItemValue);
                } else {
                    itemValue = itemObj.val(formItemValue);
                }
            },

            setFormItemDefaultValue: function (form, formItemName, formItemValue) {
                var itemValue = this.getFromItemValue(form, formItemName);
                if (!itemValue && itemValue == '') {
                    this.setFormItemValue(form, formItemName, formItemValue);
                }
            },

            _isDatagrid: function (datagrid) {
                var className = $('#' + datagrid).attr('class');
                return (className && className.indexOf('easyui-datagrid') != -1);
            },

            /**
             * 初始化搜索分页下拉
             * @param inputId 渲染的对象ID
             * @param data 数组[{...},{...}]
             * @param opts {keyField,showField,listSize,onSelect,onClear,formatter}
             * @see https://terryz.gitee.io/selectpage/demo.html
             */
            selectPage: function (inputId, data, opts) {
                let defOpts = {
                    pagination: false,
                    listSize: 10,
                    multiple: false,
                    data: data,
                    formatItem: function (row) {
                        if (opts && opts.formatter) {
                            return opts.formatter.call(this, row);
                        } else {
                            return opts.showField;
                        }
                    },
                    eSelect: function (row) {
                        if (opts && opts.onSelect) {
                            opts.onSelect.call(this, row);
                        }
                    },
                    eClear: function () {
                        if (opts && opts.onClear) {
                            opts.onClear.call(this);
                        }
                    }
                };
                let options = $.extend(defOpts, opts);
                $('#' + inputId).selectPage(options);
            },

            extendCombobox: function (container) {
                var obj = container && container.jquery ? container : $('#' + container)
                $(obj).find('.easyui-combobox').combobox({
                    onLoadSuccess: function () {
                        if ($(this).attr('style').indexOf("width") != -1) {
                            return;
                        }
                        let originalWidth = $(this).next().css('width');
                        if (originalWidth) {
                            if (originalWidth.indexOf('px') != -1) {
                                originalWidth = originalWidth.substring(0, originalWidth.indexOf('px'));
                                const newWidth = parseInt(originalWidth) + 20;
                                $(this).combobox("resize", newWidth);
                            }
                        }
                    }
                });
            },

            /**
             * 初始化select2和data-mask
             * 已废弃，参考：acooly.framework.initPlugins
             * @param container
             * @deprecated
             */
            initSelect: function (container) {
                var obj = container && container.jquery ? container : $('#' + container)
                $(obj).find('.select2bs4').select2({theme: 'bootstrap4'});
                $(obj).find('[data-mask]').inputmask();
            },

            /**
             * 初始化编辑界面
             * @param container
             */
            initPlugins: function (container) {
                let obj = container && container.jquery ? container : $('#' + container)
                // select2
                $(obj).find('.select2bs4').select2({theme: 'bootstrap4'});
                // data-mask
                $(obj).find('[data-mask]').inputmask();
                // bootstrap-tooltip
                $('[data-toggle="tooltip"]').tooltip();
                // init FileInput
                if (bsCustomFileInput) {
                    bsCustomFileInput.init();
                }
                // init textarea的字数统计
                $.acooly.framework.wordsCount(container);
            },

            /**
             * 构建表格的头部右键菜单
             * @param target
             * @returns {*}
             */
            buildDatagridMenu: function (target) {
                var state = $(target).data('datagrid');
                var that = this;
                if (!state.columnMenu) {
                    state.columnMenu = $('<div></div>').appendTo('body');
                    state.columnMenu.menu({
                        onHide: function () {
                            $(target).datagrid('columnMenu').menu('show');
                        },
                        hideOnUnhover: false,
                        onClick: function (item) {
                            let datagridId = $(target).attr("id");
                            if (item.iconCls == 'tree-checkbox1') {
                                $(target).datagrid('hideColumn', item.name);
                                $(this).menu('setIcon', {target: item.target, iconCls: 'tree-checkbox0'});
                                // 添加不显示的列到cookie
                                that.dgColumnAppend(datagridId, item.name);
                            } else {
                                $(target).datagrid('showColumn', item.name);
                                $(this).menu('setIcon', {target: item.target, iconCls: 'tree-checkbox1'});
                                // 从cookie中删除不显示的列
                                that.dgColumnRemove(datagridId, item.name);
                            }
                        }
                    })
                    let fields = $(target).datagrid('getColumnFields', true).concat($(target).datagrid('getColumnFields', false));
                    let datagridId = $(target).attr("id");
                    let unShowFields = this.dgColumnRead(datagridId) || "";
                    for (var i = 0; i < fields.length; i++) {
                        var field = fields[i];
                        var col = $(target).datagrid('getColumnOption', field);
                        state.columnMenu.menu('appendItem', {
                            text: col.title, name: field,
                            iconCls: (unShowFields && unShowFields.indexOf(field + ",") != -1 ? 'tree-checkbox0' : 'tree-checkbox1')
                        });
                    }
                }
                return state.columnMenu;
            },

            /**
             * 初始化表格的头部右键菜单
             * @param container
             */
            initDatagridMenu: function (datagridId) {

                // 初始化表格的头部右键菜单动态选择显示列
                let target = $('#' + datagridId);
                let fields = $(target).datagrid('getColumnFields', true).concat($(target).datagrid('getColumnFields', false));
                let unShowFields = this.dgColumnRead(datagridId) || "";
                for (var i = 0; i < fields.length; i++) {
                    var field = fields[i];
                    // 初始化时隐藏不显示的列
                    if (unShowFields && unShowFields.indexOf(field + ",") != -1) {
                        $(target).datagrid('hideColumn', field);
                    }
                }

                // 给dg附件columnMenu方法
                $.extend($.fn.datagrid.methods, {
                    columnMenu: function (jq) {
                        return $.acooly.framework.buildDatagridMenu(jq[0]);
                    }
                });

                // 动态绑定onHeaderContextMenu事件
                $('#' + datagridId).datagrid('getPanel').find('.datagrid-view').off('contextmenu.datagrid').on('contextmenu.datagrid', '.datagrid-header', function (e) {
                    e.preventDefault();
                    // 处理onHeaderContextMenu事件的逻辑
                    $("#" + datagridId).datagrid('columnMenu').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                });
            },

            /**
             * datagrid的隐藏列列表
             *
             * Cookie的数据结构：
             * key: cookie_datagrid_hideColumns
             * value: {
             *     datagridId1: "field1,field2,field3,",
             *     datagridId2: "field1,field2,field3,",
             *     ...
             * }
             *
             * @param datagridId
             */
            dgColumnRead: function (datagridId) {
                let unShowAllFields = this.dgColumnReadAll();
                if (unShowAllFields) {
                    return unShowAllFields[datagridId];
                }
                return null;
            },

            dgColumnReadAll: function () {
                var unShowAllFields = $.cookie(this._dgColumnCookieKey());
                if (unShowAllFields) {
                    return JSON.parse(unShowAllFields);
                } else {
                    return null;
                }
            },

            dgColumnAppend: function (datagridId, field) {
                let unShowFields = this.dgColumnRead(datagridId);
                unShowFields = !unShowFields ? field + "," : unShowFields + field + ",";
                let unShowAllFields = this.dgColumnReadAll() || {};
                unShowAllFields[datagridId] = unShowFields;
                let unShowAllFieldsJsonString = JSON.stringify(unShowAllFields);
                $.cookie(this._dgColumnCookieKey(), unShowAllFieldsJsonString);
            },

            dgColumnRemove: function (datagridId, field) {
                let unShowFields = this.dgColumnRead(datagridId);
                if (unShowFields && unShowFields.indexOf(field + ",") != -1) {
                    unShowFields = unShowFields.replace(field + ",", "");
                    let unShowAllFields = this.dgColumnReadAll();
                    if (!unShowAllFields) {
                        unShowAllFields = {datagridId: unShowFields};
                    } else {
                        unShowAllFields[datagridId] = unShowFields;
                    }
                    let unShowAllFieldsJsonString = JSON.stringify(unShowAllFields);
                    $.cookie(this._dgColumnCookieKey(), unShowAllFieldsJsonString);
                }
            },

            _dgColumnCookieKey: function () {
                return "cookie_datagrid_hideColumns";
            },

            wordsCount: function (container) {
                let obj = container && container.jquery ? container : $('#' + container);
                $.each(obj.find(".form-words"), function (index, e) {
                    var count = $(e).data("words");
                    if (count) {
                        $(e).after("<div style='text-align: right;padding: 2px 5px;'>剩余字数：<span>" + count + "</span></div>");
                        $(e).on("input focus keyup", function () {
                            let length = $(this).val().length;
                            let num = count - length;
                            $(this).next().find('span').html(num);
                        });
                        $(e).trigger('keyup');
                    }
                });
            },

            /**
             * 播放媒体文件（弹出框）
             * PDF,媒体文件
             * @param resourceUrl
             * @param opts
             */
            play: function (resourceUrl, opts) {
                if (!resourceUrl) {
                    return;
                }
                var url = resourceUrl;
                if (resourceUrl.indexOf("?") != -1) {
                    url = resourceUrl.split("?")[0]
                }
                let urlArr = url.split("/");
                let filename = urlArr[urlArr.length - 1];

                let defOpts = {
                    width: 800,
                    height: 600,
                    title: " <i class='fa fa-play' aria-hidden='true'></i> 播放：" + filename,
                    showCloseBtn: true,
                };
                let options = $.extend(defOpts, opts);

                if (options.showCloseBtn) {
                    options.buttons = [
                        {
                            text: '<i class="fa fa-times-circle fa-lg fa-fw fa-col" ></i>关闭',
                            handler: function () {
                                d.dialog('close');
                            }
                        }
                    ]
                }
                options.content = "<a class='media' href='" + resourceUrl + "'></a>";
                options.onClose = function () {
                    $(this).dialog('destroy');
                };
                options.onOpen = function () {
                    $('.media').media({width: options.width, height: options.height});
                }
                var d = $('<div/>').dialog(options);
            },

            /**
             * 根据容器ID（formId）内的表单名称选择
             * @param containerId
             * @param inputName
             */
            getInputByName: function (containerId, inputName) {
                return $('#' + containerId + ' input[name="' + inputName + '"]');
            }

        }
    });
})(jQuery);
