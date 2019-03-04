/**
 * EAV JS LIB
 * @author zhangpu
 * @date 2019-03-04
 * @type {{}}
 */
var acoolyEavClass = {

    template_search_input: "<input type='text' name='search_'>",
    template_search_select: "",

    loadAllSchemes: function () {
        $.ajax({
            url: "/eav/getEavSchemas",
            async: false,
            success: function (result) {
                var data = [];
                var first = true;
                // $("#search_EQ_schemaId").append("<option value='0'>请选择</option>");
                $.each(result.data, function (i, val) {
                    data.push({"text": val.name, "id": val.id, "selected": first});
                    if (first) {
                        window.data.eavSchemaId = val.id;
                        loadExtraQueryCondition(window.data.eavSchemaId);
                    }
                    first = false
                    $("#search_EQ_schemaId").append("<option value='" + val.id + "'>" + val.name + "</option>");
                })
                window.data.eavSchemas = data;
            }
        });
    },


    loadScheme: function (opts) {
        var defOpts = {
            schemeId: null,
            onSuccess: function (result) {
                console.info("loadScheme:", result)
            },
            renderType: 'search',               //list,search,show,create,edit
            container: null
        }
        var options = $.extends(defOpts, opts);
        $.ajax({
            url: "/eav/getEavSchema?id=" + options.schemeId,
            success: function (result) {
                if (options.onSuccess != null) {
                    options.onSuccess.call(this, result);
                }
                // var attrs = result.data.attributes;
                // var idx = 1;
                // var html = "";
                // var template = '{{attr.displayName}}:{{{input}}}';
                //
                // for (var key in attrs) {
                //     var attr = attrs[key];
                //     attr.nullable = true;
                //     attr.forQueryCondition = true;
                //     html += appendTable(template, attr) + "&nbsp;&nbsp;&nbsp;&nbsp;";
                //     if (idx % 7 == 0) {
                //         html += "<br/>"
                //     }
                //     idx++;
                // }
                // $('#extraQueryCondition').html(html);
            }
        });
    },

    /**
     * 渲染查询条件
     * @param scheme
     */
    renderSearch: function (opts) {

        var options = $.extends({
            scheme: null,
            container: null
        }, opts);

        if (!options.scheme) {
            console.error("scheme不能为空");
            return;
        }
        var attrs = options.scheme.attributes;
        for (var key in attrs) {
            var attr = attrs[key];
            attr.nullable = true;
            attr.forQueryCondition = true;
            html += appendTable(template, attr) + "&nbsp;&nbsp;&nbsp;&nbsp;";
            if (idx % 7 == 0) {
                html += "<br/>"
            }
            idx++;
        }


    }


};

$(function () {
    if ($.acooly == null) {
        $.acooly = {};
    }
    $.extends($.acooly, {eav: acoolyEavClass});
});