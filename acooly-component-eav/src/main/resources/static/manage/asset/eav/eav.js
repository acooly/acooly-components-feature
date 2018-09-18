function appendTable(template, attr) {
    attr.required = !attr.nullable;
    var rendered = null;
    if (attr.attributeType == 'BOOLEAN') {
        var checkedStr = "checked";
        if (attr.forQueryCondition) {
            checkedStr = "";
        }
        var input = '<input name="' + attr.name + '" ' + checkedStr + ' type="radio" value="1"/><label>是</label> <input name="' + attr.name + '"  type="radio" value="0"/><label>否</label>'
        rendered = Mustache.render(template, {
            "attr": attr,
            "input": input
        });
    } else if (attr.attributeType == 'ENUM') {
        var input = '<select name="' + attr.name + '" style="height: 20px">';
        if (attr.forQueryCondition) {
            input += '<option value="">请选择</option>';
        }
        var enums = attr.enumValue.split(",")
        for (var k in enums) {
            var enumValue = enums[k];
            input += '<option value="' + enumValue + '">' + enumValue + '</option>';
        }
        input += '</select>';
        rendered = Mustache.render(template, {
            "attr": attr,
            "input": input
        });
    } else if (attr.attributeType == 'DATE') {
        var inputTemplate = '<input size="15" class="text"  name="{{attr.name}}" required="{{attr.required}}" onfocus="WdatePicker({readOnly:true,dateFmt:\'yyyy-MM-dd\'})" style="height: 20px">';
        var renderedInput = Mustache.render(inputTemplate, {
            "attr": attr,
        });
        rendered = Mustache.render(template, {
            "attr": attr,
            "input": renderedInput
        });
    } else if (attr.attributeType == 'STRING') {
        var minLen = "";
        if (attr.minLength != null) {
            minLen = attr.minLength;
        } else {
            minLen = 0;
        }
        var maxLen = "";
        if (attr.maxLength != null) {
            maxLen = attr.maxLength;
        } else {
            maxLen = "10000";
        }
        var dataOptions = "validType:['length[" + minLen + "," + maxLen + "]']";
        dataOptions += ",required:" + attr.required;
        var inputTemplate = '<input name="{{attr.name}}" type="text" class="easyui-validatebox"  data-options="' + dataOptions + '"  missingMessage="请输入{{attr.displayName}}" invalidMessage="长度必须为{{attr.minLength}}-{{attr.maxLength}}" style="height: 20px"/>'
        var renderedInput = Mustache.render(inputTemplate, {
            "attr": attr,
        });
        rendered = Mustache.render(template, {
            "attr": attr,
            "input": renderedInput
        });
    } else if (attr.attributeType == 'LONG' || attr.attributeType == 'DOUBLE') {
        var dataOptions = "";
        if (attr.minimum != null) {
            dataOptions += "min:{{attr.minimum}},";
        }
        if (attr.maximum != null) {
            dataOptions += "max:{{attr.maximum}}";
        }

        var inputTemplate = '<input name="{{attr.name}}" type="text" class="easyui-numberbox" data-options="' + dataOptions + '" style="height: 20px">'
        if (!attr.forQueryCondition && attr.required) {
            inputTemplate = '<input name="{{attr.name}}" type="text" class="easyui-numberbox" required data-options="' + dataOptions + '" style="height: 20px">'
        }

        var renderedInput = Mustache.render(inputTemplate, {
            "attr": attr,
        });
        rendered = Mustache.render(template, {
            "attr": attr,
            "input": renderedInput
        });
    } else {
        var input = '<input type="text" name="' + attr.name + '" size="12" placeholder="请输入内容..." class="easyui-validatebox text" style="height: 20px" />'
        rendered = "<tr><th>" + attr.displayName + ":</th><td>" + input + "</td></tr>";
    }
    return rendered;
}
