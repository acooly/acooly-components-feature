<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_portallet_editform" class="form-horizontal"
          action="${rc.contextPath}/manage/system/portallet/<#if action == 'create'>save<#else>update</#if>Json.html" method="post">
        <@jodd.form bean="portallet" scope="request">
            <input name="id" type="hidden"/>
            <div class="card-body">
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">题标</label>
                    <div class="col-sm-10"><input type="text" name="title" class="form-control easyui-validatebox" data-options="required:true" validType="length[1,64]"/></div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">块高度：</label>
                    <div class="col-sm-10"><input type="text" name="height" class="form-control" data-inputmask="'alias':'integer','min':100,'max':1000" data-mask data-options="validType:['number[100,1000]']"/></div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">块收缩：</label>
                    <div class="col-sm-10">
                        <select name="collapsible" class="form-control select2bs4">
                            <#list allCollapsibles as k,v><option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-2 col-form-label">内容类型：</label>
                    <div class="col-sm-10">
                        <select name="loadMode" id="manage_portallet_edit_loadMode" class="form-control select2bs4">
                            <#list allLoadModes as k,v>
                                <option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                </div>
                <div class="form-group row" id="loadMode_url">
                    <label class="col-sm-2 col-form-label">链接内容：</label>
                    <div class="col-sm-10">
                        <div class="input-group row">
                            <div class="col-sm-4"><select name="showMode" class="form-control select2bs4" data-options="required:true"><#list allShowModes as k,v><option value="${k}">${v}</option></#list></select></div>
                            <div class="col-sm-8"><input type="text" name="href" class="form-control easyui-validatebox" validType="length[1,128]"/></div>
                        </div>
                        <div style="margin-top:5px;">注意：如果是站内jsp或html(controller)URL,该链接需要授权才能正常访问</div>
                    </div>
                </div>
                <div class="form-group row" id="loadMode_html">
                    <label class="col-sm-2 col-form-label">文本内容：</label>
                    <div class="col-sm-10">
                        <textarea name="content" rows="4" cols="60" class="form-control easyui-validatebox"></textarea>
                    </div>
                </div>
            </div>
        </@jodd.form>
    </form>
</div>
<script>
    function switchLoadMode() {
        var v = $('#manage_portallet_edit_loadMode').val();
        if (v == 1) {
            $('#loadMode_url').show();
            $('#loadMode_html').hide();
        } else {
            $('#loadMode_url').hide();
            $('#loadMode_html').show();
        }
    }

    $(function () {
        switchLoadMode();
        $("#manage_portallet_edit_loadMode").on("select2:select", function (e) {
            switchLoadMode();
        });
    });

</script>
