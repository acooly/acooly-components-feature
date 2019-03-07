/**
 * EAV JS LIB
 * @author zhangpu
 * @date 2019-03-04
 * @type {{}}
 */
var acoolyEavClass = {

    schemeId: '',

    template: {
        search_number: '<%=displayName%>: <input type="text" name="search_EQ_<%=name%>" class="easyui-validatebox <%=cssClass%>" style="<%=cssStyle%>">',
        search_input: '<%=displayName%>: <input type="text" name="search_LIKE_<%=name%>" class="easyui-validatebox <%=cssClass%>" style="<%=cssStyle%>">',
        search_date: '<%=displayName%>: <input type="text" name="search_GTE_<%=name%>" onFocus="WdatePicker({readOnly:true,dateFmt:\'yyyy-MM-dd\'})" class="easyui-validatebox <%=cssClass%>" style="<%=cssStyle%>"> ' +
            '至 <input type="text" name="search_LTE_<%=name%>" onFocus="WdatePicker({readOnly:true,dateFmt:\'yyyy-MM-dd\'})" class="easyui-validatebox <%=cssClass%>" style="<%=cssStyle%>">',
        search_select: '<%=displayName%>: <select name="search_EQ_<%=name%>" editable="false" panelHeight="auto" class="easyui-combobox <%=cssClass%>" style="<%=cssStyle%>">' +
            '<% for(var i=0;i<options.length;i++){ var e=options[i]; %>' +
            '<option value="<%=e.code%>"><%=e.name%></option>' +
            '<% } %>' +
            '</select>'
    },

    showType: {
        'SEARCH': 1,
        'LIST': 2,
        'CREATE': 4,
        'UPDATE': 8,
        'SHOW': 16
    },


    loadAllSchemes: function () {
        // 如果设置了指定的schemeId,则不拉去所有的scheme
        if ($.acooly.eav.schemeId && $.acooly.eav.schemeId != '') {
            this.loadScheme({
                schemeId: $.acooly.eav.schemeId,
                container: "extraQueryCondition",
                showType: this.showType.SEARCH
            });
            return;
        }
        var That = this;
        $.ajax({
            url: "/eav/getEavSchemas.html",
            success: function (result) {
                var data = [];
                var first = true;
                $.each(result.data, function (i, val) {
                    data.push({"text": val.name, "id": val.id, "selected": first});
                    if (first) {
                        $.acooly.eav.schemeId = val.id;
                        That.loadScheme({
                            schemeId: $.acooly.eav.schemeId,
                            container: "extraQueryCondition",
                            showType: That.showType.SEARCH
                        });
                    }
                    first = false
                    $("#search_EQ_schemaId").append("<option value='" + val.id + "'>" + val.name + "</option>");
                })
            }
        });
    },


    loadScheme: function (opts) {
        var defOpts = {
            schemeId: null,
            container: null,
            showType: 1,
            onSuccess: null
        }
        var options = $.extend(defOpts, opts);
        var That = this;
        $.ajax({
            url: "/eav/getEavSchema?id=" + options.schemeId,
            success: function (result) {
                if (options.onSuccess != null) {
                    options.onSuccess.call(this, result);
                } else {
                    var obj = $('#' + options.container).html(That.render(result.data, options.showType));
                    $.parser.parse(obj);
                }
            }
        });
    },

    hasPermission: function (allPerm, perm) {
        return ((allPerm & perm) == perm);
    },

    /**
     * 渲染查询条件
     * @param scheme
     */
    render: function (scheme, showType) {

        var attrs = scheme.attributes;
        var html = "";
        for (var key in attrs) {
            var attr = attrs[key];
            if (!this.hasPermission(attr.showType, 1)) {
                continue;
            }

            var template = null;
            if (attr.attributeType.startsWith('NUMBER')) {
                template = this.template.search_number;
            } else if (attr.attributeType == 'DATE') {

                template = this.template.search_date;
            } else if (attr.attributeType == 'ENUM') {
                template = this.template.search_select;
            } else {
                template = this.template.search_input;
            }
            html += " " + $.acooly.template.render(template, attr);
        }
        return html;
    }


};

$(function () {
    if ($.acooly == null) {
        $.acooly = {};
    }
    $.extend($.acooly, {eav: acoolyEavClass});
});