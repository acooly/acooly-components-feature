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
        search_select: '<%=displayName%>: <select name="search_EQ_<%=name%>" editable="false" panelHeight="auto" class="easyui-combobox' +
            ' <%=cssClass%>" style="<%=cssStyle%>"><option value="">所有</option>' +
            '<% for(var i=0;i<options.length;i++){ var e=options[i]; %>' +
            '<option value="<%=e.code%>"><%=e.name%></option>' +
            '<% } %></select>',
        form:'        <table class="tableForm" width="100%">\n' +
            '            <tr>\n' +
            '                <th width="25%">方案：</th>\n' +
            '                <td><span id="schemaName"><%=entity.title%></span></td>\n' +
            '            </tr>\n' +
            '            <%\n' +
            '                for(var i=0;i<entity.attributes.length;i++){\n' +
            '                    var e = attributes[i];\n' +
            '            %>\n' +
            '            <tr>\n' +
            '                <th width="25%"><%e.displayName%>：</th>\n' +
            '                <td>\n' +
            '                    <% if(e.attributeType.startsWith("NUMBER")){ %>\n' +
            '                    <input type="text" name="<%=e.name%>" size="20"  style="width: 200px;line-height: 30px; height: 30px;<%=e.cssStyle%>"\n' +
            '                           class="easyui-numberbox <%=e.cssClass%>" <%if(e.nullable=="no"){%>required="true"<%}%> data-options="validType:[\'length[<%=e.minimum%>,<%=e.maximum%>]\']"/>\n' +
            '                    <%}else if(e.attributeType == "DATE"){%>\n' +
            '                    <input type="text" <%if(e.nullable=="no"){%>required="true"<%}%> name="<%=e.name%>" onFocus="WdatePicker({readOnly:true,dateFmt:\'yyyy-MM-dd\'})"\n' +
            '                           class="easyui-validatebox <%=e.cssClass%>" style="<%=e.cssStyle%>">\n' +
            '                    <%}else if(e.attributeType == "ENUM" && e.options && e.options.length > 0){%>\n' +
            '                    <select name="<%=name%>" <%if(e.nullable=="no"){%>required="true"<%}%> editable="false" panelHeight="auto" class="easyui-combobox <%=cssClass%>" style="<%=cssStyle%>">\n' +
            '                        <% for(var j=0;j<e.options.length;j++){ var o=options[j]; %>\n' +
            '                        <option value="<%=o.code%>"><%=o.name%></option>\n' +
            '                        <% } %>\n' +
            '                    </select>\n' +
            '                    <%}else{%>\n' +
            '                    <input type="text" name="<%=e.name%>" size="20"  style="width: 200px;line-height: 30px; height: 30px;<%=e.cssStyle%>" class="easyui-validatebox <%=e.cssClass%>"\n' +
            '                           <%if(e.nullable=="no"){%>required="true"<%}%> data-options="validType:[\'length[<%=e.minimum%>,<%=e.maximum%>]\']"/>\n' +
            '                    <%}%>\n' +
            '                </td>\n' +
            '            </tr>\n' +
            '            <%\n' +
            '                }\n' +
            '            %>\n' +
            '        </table>'



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
            template: null,
            container: null,
            showType: this.showType.SEARCH,
            onSuccess: null
        }
        var options = $.extend(defOpts, opts);
        var That = this;
        $.ajax({
            url: "/eav/getEavScheme?id=" + options.schemeId,
            success: function (result) {
                if (options.onSuccess != null) {
                    options.onSuccess.call(this, result);
                } else {
                    var obj;
                    if (options.showType == That.showType.SEARCH) {
                        obj = $('#' + options.container).html(That.renderSearch(result.entity));
                    } else if (options.showType == That.showType.CREATE) {
                        console.info("loadScheme",result);
                        obj = $('#' + options.container).html(That.renderForm(result, That.template.form));
                    }
                    $.parser.parse(obj);
                }
            }
        });
    },

    prepScheme: function (scheme) {

        scheme.attributes.forEach()


    },

    hasPermission: function (allPerm, perm) {
        return ((allPerm & perm) == perm);
    },

    /**
     * 渲染查询条件
     * @param scheme
     */
    renderSearch: function (scheme) {

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
    },


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


};

$(function () {
    if ($.acooly == null) {
        $.acooly = {};
    }
    $.extend($.acooly, {eav: acoolyEavClass});
});