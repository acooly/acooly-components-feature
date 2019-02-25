<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div align="center">
    <form id="manage_portallet_editform"
          action="${rc.contextPath}/manage/system/portallet/<#if action == 'create'>save<#else>update</#if>Json.html" method="post">
      <@jodd.form bean="portallet" scope="request">
          <input name="id" type="hidden"/>
          <table class="tableForm" width="100%">
              <tr>
                  <th width="25%">题标</th>
                  <td><input type="text" name="title" class="easyui-validatebox" data-options="required:true" validType="length[1,64]"/>
                  </td>
              </tr>
              <tr>
                  <th>块高度：</th>
                  <td><input type="text" name="height" class="easyui-numberbox" data-options="required:true,min:100"/></td>
              </tr>
              <tr>
                  <th>块收缩：</th>
                  <td>
                      <select name="collapsible" editable="false" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<#list allCollapsibles as k,v>
                        <option value="${k}">${v}</option></#list>
                      </select>
                  </td>
              </tr>
              <tr>
                  <th>内容类型：</th>
                  <td>
                      <select name="loadMode" id="manage_portallet_edit_loadMode" editable="false" panelHeight="auto"
                              class="easyui-combobox" data-options="required:true,height:40">
					<#list allLoadModes as k,v>
                        <option value="${k}">${v}</option></#list>
                      </select>
                  </td>
              </tr>
              <tr id="loadMode_url">
                  <th>链接内容：</th>
                  <td>
                      <div style="margin-bottom:5px;">加载方式：
                          <select name="showMode" editable="false" panelHeight="auto" class="easyui-combobox" data-options="required:true">
					<#list allShowModes as k,v>
                        <option value="${k}">${v}</option></#list>
                          </select>
                      </div>
                      <div><input type="text" name="href" style="width:400px;" class="easyui-validatebox" validType="length[1,128]"/>
                          <div>
                              <div style="margin-top:5px;">注意：如果是站内jsp或html(controller)URL,该链接需要授权才能正常访问</div>
                  </td>
              </tr>
              <tr id="loadMode_html">
                  <th>文本内容：</th>
                  <td>
                      <textarea name="content" rows="8" cols="60" style="width:400px;" class="easyui-validatebox"></textarea>
                  </td>
              </tr>
          </table>
      </@jodd.form>
    </form>
</div>
<script>
    function switchLoadMode(v) {
        if (!v) v = $('#manage_portallet_edit_loadMode').val();
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
        $('#manage_portallet_edit_loadMode').combobox({
            onChange: function (n, o) {
                switchLoadMode(n);
            }
        });
    });

</script>
