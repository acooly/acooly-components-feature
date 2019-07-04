/**
 * EAV JS LIB
 * @author zhangpu
 * @date 2019-03-04
 * @type {{}}
 */
var acoolyEavClass = {

    TEMPLATE: {
        SEARCH_FORM : '<form id="<%=elementIds.searchform%>" onsubmit="return false">' +
        '        <table class="tableForm" width="100%">' +
        '            <tr>' +
        '                <td align="left">' +
        '                    <div>' +
        '                        ID:<input type="text" name="search_EQ_id" style="width: 100px;">' +
        '                        <% if(schemes != null){ %>' +
        '                        方案:<select name="search_EQ_schemeId" id="search_EQ_schemeId">' +
        '                            <%for(var i=0;i<schemes.length;i++){ var e = schemes[i]; %>' +
        '                            <option value="<%=e.id%>"<%if(e.id==entity.id){%> selected<%}%>><%=e.title%></option>' +
        '                            <%}%>' +
        '                        <select>' +
        '                        <%}else{%>' +
        '                        <input type="hidden" name="search_EQ_schemeId" value="<%=entity.id%>">' +
        '                        <%}%>' +
        '                        <%' +
        '                        for (var key in entity.attributes) {' +
        '                            var attr = entity.attributes[key];' +
        '                            if(!$.acooly.eav.hasPermission(attr.showType,$.acooly.eav.showType.SEARCH)){ continue; }' +
        '                        %>' +
        '                        <%' +
        '                            if (attr.attributeType.startsWith(\'NUMBER\')) {' +
        '                        %>' +
        '                            <%=attr.displayName%>: <input type="text" name="search_EQ_<%=name%>" class="easyui-numberbox <%=attr.cssClass%>" style="<%=attr.cssStyle%>">' +
        '                        <%' +
        '                            } else if (attr.attributeType == \'DATE\') {' +
        '                        %>' +
        '                            <%=attr.displayName%>: <input type="text" name="search_GTE_<%=attr.name%>" onFocus="WdatePicker({readOnly:true,dateFmt:\'<%=$.acooly.eav.formatDate(attr.showFormat)%>\'})" class="easyui-validatebox <%=attr.cssClass%>" style="<%=attr.cssStyle%>">' +
        '                            至 <input type="text" name="search_LTE_<%=attr.name%>" onFocus="WdatePicker({readOnly:true,dateFmt:\'<%=$.acooly.eav.formatDate(attr.showFormat)%>\'})" class="easyui-validatebox <%=attr.cssClass%>" style="<%=attr.cssStyle%>">' +
        '                        <%' +
        '                            } else if (attr.attributeType == \'ENUM\') {' +
        '                        %>' +
        '                        <%=attr.displayName%>: <select name="search_EQ_<%=attr.name%>" editable="false" panelHeight="auto" class="easyui-combobox <%=attr.cssClass%>" style="<%=attr.cssStyle%>">' +
        '                            <option value="">所有</option>' +
        '                            <% for(var i=0;i<attr.options.length;i++){ var e=attr.options[i]; %>' +
        '                            <option value="<%=e.code%>"><%=e.name%></option>' +
        '                            <% } %>' +
        '                            </select>' +
        '                        <%' +
        '                            } else {' +
        '                        %>' +
        '                            <%=attr.displayName%>: <input type="text" name="search_LIKE_<%=attr.name%>" class="easyui-validatebox <%=attr.cssClass%>" style="<%=attr.cssStyle%>">' +
        '                        <%' +
        '                            }' +
        '                        }' +
        '                        %>' +
        '                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false" onclick="$.acooly.framework.search(\'<%=elementIds.searchform%>\',\'<%=elementIds.datagrid%>\');"><i class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>' +
        '                    </div>' +
        '                </td>' +
        '            </tr>' +
        '        </table>' +
        '    </form>',
        EDIT_FORM   : '        <form id="<%=elementIds.editform%>" action="/manage/module/eav/eavEntity/<%if(options.entityId != \'\'){%>save<%}else{%>update<%}%>Json.html" method="post">' +
        '            <input name="id" type="hidden" value="<%=options.entityId%>"/>' +
        '            <input name="schemeId" id="schemeId" type="hidden" value="<%=options.schemeId%>"/>' +
        '            <table class="tableForm" width="100%">' +
        '                <tr>' +
        '                    <th width="25%">方案：</th>' +
        '                    <td><%=entity.title%></td>' +
        '                </tr>' +
        '                <%' +
        '                var prevTag = null;' +
        '                for(var key in entity.attributes){' +
        '                var e = entity.attributes[key];' +
        '                %>' +
        '                <%if(prevTag != e.tag && e.tag != \'\'){%>' +
        '                <tr><td colspan="2"><div style="border-bottom: 1px solid #ddd;padding: 5px;margin-bottom: 10px;"><%=e.tag%></div></td></tr>' +
        '                <%}%>' +
        '                <tr>' +
        '                    <th><%=e.displayName%>：</th>' +
        '                    <td>' +
        '                        <% if(e.attributeType.startsWith("NUMBER_INTEGER")){ %>' +
        '                        <input type="text" name="<%=e.name%>" size="20"  style="min-width:300px;line-height: 30px; height: 30px;<%=e.cssStyle%>"' +
        '                               class="easyui-numberbox <%=e.cssClass%>" <%if(e.nullable=="no"){%>required="true"<%}%>' +
        '                                <%if(e.minimum!=null){%>min="<%=e.minimum%>"<%}%> <%if(e.maximum!=null){%>max="<%=e.maximum%>"<%}%>/>' +
        '                        <%}else if(e.attributeType == "NUMBER_MONEY"){%>' +
        '                        <input type="text" name="<%=e.name%>" size="20"  style="min-width:300px;line-height: 30px; height: 30px;<%=e.cssStyle%>"' +
        '                               class="easyui-numberbox <%=e.cssClass%>" <%if(e.nullable=="no"){%>required="true"<%}%>' +
        '                            <%if(e.minimum!=null){%>min="<%=e.minimum/100%>"<%}%> <%if(e.maximum!=null){%>max="<%=e.maximum/100%>"<%}%>' +
        '                            data-options="precision:2"/>' +
        '                        <%}else if(e.attributeType == "DATE"){%>' +
        '                        <input type="text" <%if(e.nullable=="no"){%>required="true"<%}%> name="<%=e.name%>" onFocus="WdatePicker({readOnly:true,dateFmt:$.acooly.eav.formatDate(\'<%=e.showFormat%>\')})"' +
        '                        class="easyui-validatebox <%=e.cssClass%>" style="min-width:300px; <%=e.cssStyle%>">' +
        '                        <%}else if(e.attributeType == "ENUM" && e.options && e.options.length > 0){%>' +
        '                        <select name="<%=e.name%>" <%if(e.nullable=="no"){%>required="true"<%}%> editable="false" panelHeight="auto" class="easyui-combobox <%=cssClass%>" style="min-width:300px;<%=cssStyle%>">' +
        '                        <% for(var j=0;j<e.options.length;j++){ var o=e.options[j]; %>' +
        '                        <option value="<%=o.code%>"><%=o.name%></option>' +
        '                        <% } %>' +
        '                        </select>' +
        '                        <%}else{%>' +
        '                            <%if(e.maxLength!=null&&e.maxLength>128){%><textarea rows="3" <%}else{%><input type="text" size="20" <%}%>' +
        '                               name="<%=e.name%>" style="min-width:300px;<%=e.cssStyle%>"' +
        '                               class="easyui-validatebox <%=e.cssClass%>" <%if(e.nullable=="no"){%>required="true"<%}%>' +
        '                                data-options="validType:[\'length[<%=e.minLength%>,<%=e.maxLength%>]\'<%if(e.regex != null && e.regex != \'\'){%>,\'regExp[\\\'<%=e.regex%>\\\', \\\'<%=e .regexMessage%>\\\']\'<%}%>]"/>' +
        '                            <%if(e.maxLength!=null&&e.maxLength>128){%></textarea><%}%>' +
        '                        <%}%>' +
        '                    </td>' +
        '                </tr>' +
        '                <%' +
        '                    prevTag = e.tag;' +
        '                }' +
        '                %>' +
        '            </table>' +
        '        </form>'


    },

    showType: {
        'SEARCH': 1,
        'LIST': 2,
        'CREATE': 4,
        'UPDATE': 8,
        'SHOW': 16
    },

    SchemePermission: {
        'LIST': 1,
        'CREATE': 2,
        'UPDATE': 4,
        'DELETE': 8,
        'SHOW': 16
    },


    /**
     * 加载所有的方案
     */
    loadAllSchemes: function (containerSearch,containerDatagrid,schemeId) {
        // 如果设置了指定的schemeId,则不拉去所有的scheme
        if (schemeId && schemeId != '') {
            this.init({
                schemeId: schemeId,
                single: true,               // 表示指定schemeId初始化
                containerSearch: containerSearch,
                containerDatagrid: containerDatagrid
            });
            return;
        }
        var That = this;
        $.ajax({
            url: "/manage/module/eav/eavEntity/getEavSchemas.html",
            success: function (result) {
                $.acooly.eav.schemes = result.data;
                var first = true;
                $.each(result.data, function (i, val) {
                    if (first) {
                        // $.acooly.eav.schemeId = val.id;
                        That.init({
                            schemeId:val.id,
                            schemes:result.data,
                            single: false,
                            containerSearch: containerSearch,
                            containerDatagrid: containerDatagrid
                        });
                    }
                    first = false
                });
            }
        });
    },


    /**
     * 初始化列表视图
     * @param opts
     */
    init: function (opts) {
        var options = $.extend({
            schemeId: null,
            schemes:null,
            single:false,
            containerSearch: null,
            containerDatagrid: null,
            onComplete: null
        }, opts);
        var That = this;
        this.loadScheme({
            schemeId: options.schemeId,
            onSuccess: function (result) {
                var scheme = result.entity;
                // 初始化生成本sheme对应的ID
                // That.getElementIds(scheme,options.single);
                // 初始化查询条件
                result.schemes = options.schemes;
                result.elementIds = That.getElementIds(scheme,options.single);
                result.single=options.single;
                That.renderSearch(result, options.containerSearch);
                $("#search_EQ_schemeId").on('change', function () {
                    // $.acooly.eav.schemeId = $(this).val();
                    That.init({
                        schemeId: $(this).val(),
                        schemes: options.schemes,
                        single: false,
                        containerSearch: options.containerSearch,
                        containerDatagrid: options.containerDatagrid
                    });
                });
                // 初始化列表
                That.renderGrid(result, options.containerDatagrid);
                // 注册查询事件
                $.acooly.framework.registerKeydown(result.elementIds.searchform, result.elementIds.datagrid);
                if (options.onComplete) {
                    options.onComplete.call(result, this);
                }
            }
        });
    },


    /**
     * 初始化编辑视图
     */
    initEdit: function (opts) {
        var options = $.extend({
            entityId: null,
            entity: null,
            schemeId: null,
            single:null,
            template: null,
            container: null,
        }, opts);
        var That = this;
        this.loadScheme({
            schemeId: options.schemeId,
            onSuccess: function (result) {
                var template = options.template;
                if (!template) {
                    template = That.TEMPLATE.EDIT_FORM;
                }
                result.elementIds = That.getElementIds(result.entity,options.single);
                result.options=options;
                obj = $('#' + options.container).html(That.renderForm(result, template));
                $.parser.parse(obj);
                if (options.entity) {
                    $("#"+result.elementIds.editform).form('load', options.entity);
                }
            }
        })
    },


    /**
     * 加载方案数据
     * @param opts
     */
    loadScheme: function (opts) {
        var defOpts = {
            schemeId: null,
            onSuccess: null
        }
        var options = $.extend(defOpts, opts);
        $.ajax({
            url: "/manage/module/eav/eavEntity/getEavScheme?id=" + options.schemeId,
            success: function (result) {
                $.acooly.eav.scheme = result.entity;
                $.acooly.eav.schemeId = result.entity.id;
                if (options.onSuccess != null) {
                    options.onSuccess.call(this, result);
                }
            }
        });
    },

    /**
     * 渲染表格
     * @param result
     * @param containerDatagrid
     */
    renderGrid: function (result, containerDatagrid) {
        $('#' + containerDatagrid).html("<div id='" + result.elementIds.datagrid + "' class='easyui-datagrid'></div>");
        var attrs = result.entity.attributes;
        var columns = [];
        columns.push({
            "field": "showCheckboxWithId", "title": "编号", "width": 5, "checkbox": true,
            "formatter": function (value, row) {
                return row.id;
            }
        });
        columns.push({"field": "id", "title": "ID"});
        for (var key in attrs) {
            var attr = attrs[key]
            if (!this.hasPermission(attr.showType, this.showType.LIST)) {
                continue;
            }
            var column = {
                "field": attr.name,
                "title": attr.displayName
            }
            if (attr.attributeType == 'ENUM') {
                column['formatter'] = function (value, row, index, data, field) {
                    return mappingFormatter(value, row, index, data, field);
                };
            }
            columns.push(column);
        }
        columns.push({"field": "createTime", "title": "创建时间",formatter:function(value){ return dateTimeFormatter(value)}});
        columns.push({"field": "updateTime", "title": "修改时间",formatter:function(value){ return dateTimeFormatter(value)}});
        var c = []
        c.push(columns)
        $('#' + result.elementIds.datagrid).datagrid({
            url: '/manage/module/eav/eavEntity/listJson.html?search_EQ_schemeId=' + result.entity.id,
            columns: c,
            fit: true,
            border: false,
            fitColumns: false,
            pagination: true,
            idField: "id",
            pageSize: 20,
            pageList: [10, 20, 30, 40, 50],
            sortName: "id",
            sortOrder: "desc",
            checkOnSelect: true,
            selectOnCheck: true,
            singleSelect: true,
            toolbar: this.renderToobar(result)
        });
    },

    renderToobar: function (result) {
        var scheme = result.entity;
        var That = this;
        var permission = scheme.permission;
        var buttons = [];
        // 添加按钮
        if (this.hasPermission(permission, this.SchemePermission.CREATE)) {
            buttons.push({
                text: '<i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加',
                handler: function () {
                    $.acooly.framework.create({
                        url: '/manage/module/eav/eavEntity/create.html?schemeId=' + scheme.id + '&single='+result.single,
                        form: result.elementIds.editform, datagrid:result.elementIds.datagrid,
                        width: scheme.panelWidth, height: scheme.panelHeight
                    });
                }
            });
        }
        // 编辑按钮
        if (this.hasPermission(permission, this.SchemePermission.UPDATE)) {
            buttons.push({
                text: '<i class="fa fa-pencil fa-lg fa-fw fa-col"></i>编辑',
                handler: function () {
                    $.acooly.framework.fireSelectRow(result.elementIds.datagrid, function (row) {
                        $.acooly.framework.edit({
                            url: '/manage/module/eav/eavEntity/edit.html?single='+result.single, id: row.id,
                            form: result.elementIds.editform, datagrid:result.elementIds.datagrid,
                            width: scheme.panelWidth, height: scheme.panelHeight
                        });
                    });
                }
            });
        }
        // 查看按钮
        if (this.hasPermission(permission, this.SchemePermission.SHOW)) {
            buttons.push({
                text: '<i class="fa fa-file-o fa-lg fa-fw fa-col"></i>查看',
                handler: function () {
                    $.acooly.framework.fireSelectRow(result.elementIds.datagrid, function (row) {
                        $.acooly.framework.show('/manage/module/eav/eavEntity/show.html?id=' + row.id, scheme.panelWidth, scheme.panelHeight);
                    });
                }
            });
        }
        // 删除按钮
        if (this.hasPermission(permission, this.SchemePermission.DELETE)) {
            buttons.push({
                text: '<i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>删除',
                handler: function () {
                    $.acooly.framework.fireSelectRow(result.elementIds.datagrid, function (row) {
                        $.acooly.framework.removes('/manage/module/eav/eavEntity/deleteJson.html?single='+result.single, result.elementIds.datagrid)
                    });
                }
            });
        }
        return buttons;
    },

    /**
     * 渲染查询条件
     * @param scheme
     */
    renderSearch: function (result, container) {
        var template = this.TEMPLATE.SEARCH_FORM;
        var obj = $('#' + container).html($.acooly.template.render(template, result));
        $.parser.parse(obj);
    },


    /**
     * 渲染form表单
     * @param result
     * @param template
     * @returns {*}
     */
    renderForm: function (result, template) {
        return $.acooly.template.render(template, result);
    },

    formatDate: function (formatType) {
        if (formatType == 'FORMAT_DATA_TIME') {
            return 'yyyy-MM-dd HH:mm:ss';
        } else if (formatType == 'FORMAT_TIME') {
            return 'HH:mm:ss';
        } else {
            return 'yyyy-MM-dd';
        }
    },


    hasPermission: function (allPerm, perm) {
        return ((allPerm & perm) == perm);
    },

    getElementIds:function(scheme, single){
        var elementIds = {
            datagrid: "manage_eavEntity_datagrid",
            searchform: "manage_eavEntity_searchform",
            editform: "manage_eavEntity_editform"
        }
        if(single == true || single == 'true'){
            elementIds.datagrid = elementIds.datagrid+ "_" + scheme.id;
            elementIds.searchform = elementIds.searchform+ "_" + scheme.id;
            elementIds.editform = elementIds.editform+ "_" + scheme.id;
        }
        return elementIds;
    }
};

$(function () {
    if ($.acooly == null) {
        $.acooly = {};
    }
    $.extend($.acooly, {eav: acoolyEavClass});
});