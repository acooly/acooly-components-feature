<#if ssoEnable>
    <#include "*/include.ftl">
</#if>
<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_certificationRecord_searchform', 'manage_certificationRecord_datagrid');
    });

</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_certificationRecord_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        <div>
                            真实姓名: <input type="text" class="text" size="15" name="search_LIKE_realName"/>
                            身份证号: <input type="text" class="text" size="15" name="search_LIKE_idCarNo"/>
                            状态: <select style="width:80px;height:27px;" name="search_EQ_status" editable="false" panelHeight="auto"
                                        class="easyui-combobox">
                            <option value="">所有</option>
                            <#list allStatuss as key,val>
                                <option value="${key}">${val}</option>
                            </#list>
                        </select>
                            <a href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:false"
                               onclick="$.acooly.framework.search('manage_certificationRecord_searchform','manage_certificationRecord_datagrid');"><i
                                    class="fa fa-search fa-lg fa-fw fa-col"></i>查询</a>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_certificationRecord_datagrid" class="easyui-datagrid"
               url="${pageContext.request.contextPath}/manage/module/certification/certificationRecord/listJson.html"
               toolbar="#manage_certificationRecord_toolbar" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id" sum="true">ID</th>
                <th field="realName">真实姓名</th>
                <th field="idCarNo">身份证号</th>
                <th field="sex">性别</th>
                <th field="address">所在地址</th>
                <th field="birthday">出生日期</th>
                <th field="status"
                    data-options="formatter:function(value){ return formatRefrence('manage_certificationRecord_datagrid','allStatuss',value);} ">
                    状态
                </th>
                <th field="createTime" formatter="formatDate">创建时间</th>
                <th field="updateTime" formatter="formatDate">更新时间</th>
            </tr>
            </thead>
        </table>
    </div>
