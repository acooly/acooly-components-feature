<script type="text/javascript">
    $(function () {
        $.acooly.framework.registerKeydown('manage_onlineFile_searchform', 'manage_onlineFile_datagrid');
    });
</script>
<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'north',border:false" style="padding:5px; overflow: hidden;" align="left">
        <form id="manage_onlineFile_searchform" onsubmit="return false">
            <table class="tableForm" width="100%">
                <tr>
                    <td align="left">
                        文件名:<input type="text" size="15" name="search_LIKE_fileName"/>
                        ObjectId:<input type="text" size="15" name="search_LIKE_objectId"/>
                        文件类型:<select name="search_EQ_fileType" editable="false" style="width: 80px;" panelHeight="auto"
                                     class="easyui-combobox">
                        <option value="">所有</option>
                        <#list allFileTypes as k,v><option value="${k}">${v}</option></#list></select>
                        文件名:<input type="text" size="15" name="search_LIKE_originalName"/>
                        上传时间:<input id="search_GTE_createTime" name="search_GTE_createTime" size="10"
                                    onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                        至<input id="search_LTE_createTime" name="search_LTE_createTime" size="10"
                                onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                        <a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:false"
                           onclick="$.acooly.framework.search('manage_onlineFile_searchform','manage_onlineFile_datagrid');">查询</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_onlineFile_datagrid" class="easyui-datagrid" url="${rc.contextPath}/manage/module/ofile/onlineFile/listJson.html"
               toolbar="#manage_onlineFile_toolbar" fit="true" border="false" fitColumns="false"
               pagination="true" idField="id" pageSize="20" pageList="[ 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true">
            <thead>
            <tr>
                <th field="showCheckboxWithId" checkbox="true" data-options="formatter:function(value, row, index){ return row.id }">编号</th>
                <th field="id">id</th>
                <th field="objectId">objectId</th>
<#--                <th field="thumbnail"-->
<#--                    data-options="formatter:function(value,row,index){ if(row.fileType == 'picture'){ return '<div style=\'padding:5px;\'><img src=\'/ofile/thumb/'+row.id+'.html\' width=\'64\'></div>'; }else{ return '' } }">-->
<#--                    缩略图-->
<#--                </th>-->
<#--                <th field="filePath"-->
<#--                    data-options="formatter:function(value,row,index){ return linkFormatter('/ofile/image/'+row.id+'.html') + ' ' + value;}">-->
<#--                    路径-->
<#--                </th>-->
                <th field="filePath">路径</th>
                <th field="originalName">原文件名</th>
                <th field="fileType" formatter="mappingFormatter">文件类型</th>
                <th field="fileSize" formatter="formatFileSize">大小</th>
                <th field="module">模块分类</th>
                <th field="userName">用户名</th>
                <th field="createTime">上传时间</th>
                <th field="rowActions"
                    data-options="formatter:function(value, row, index){return formatAction('manage_onlineFile_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <!-- 每行的Action动作模板 -->
        <div id="manage_onlineFile_action" style="display: none;">
            <a onclick="window.open('/ofile/download/{0}')"
               href="#" title="下载"><i class="fa fa-download fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.show('/manage/module/ofile/onlineFile/show.html?id={0}',600,600);"
               href="#" title="查看"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <a onclick="$.acooly.framework.remove('/manage/module/ofile/onlineFile/deleteJson.html','{0}','manage_onlineFile_datagrid');"
               href="#" title="删除"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>

        <!-- 表格的工具栏 -->
        <div id="manage_onlineFile_toolbar">
            <a href="#" class="easyui-linkbutton" plain="true" onclick="window.open('/ofile/test.html')">
                <i class="fa fa-blind fa-fw fa-col"></i>签名认证测试</a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.framework.removes('/manage/module/ofile/onlineFile/deleteJson.html','manage_onlineFile_datagrid')">
                <i class="fa fa-trash-o fa-lg fa-fw fa-col"></i>批量删除</a>


            <a onclick="$.acooly.framework.get({url:'/manage/module/ofile/onlineFile/uploadFilePage.html?bussId=111',width:700,height:600,title:'文件上传'});" href="#" title="文件上传插件"><i class="fa fa-plus-circle fa-lg fa-fw fa-upload"></i>文件上传(点击/拖动)</a>


<#--            <a onclick="$.acooly.framework.show('/manage/module/ofile/onlineFile/uploadFilePage.html?bussId=111',700,600);" href="#" title="文件上传插件"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>文件上传(拖拽)</a>-->

<#--            <a onclick="$.acooly.framework.show('/manage/module/ofile/onlineFile/uploadFilePage_multiple.html',700,600);" href="#" title="文件上传插件"><i class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>文件上传(多文件)</a>-->

            <#--文件上传的类型指定-->
<#--            <input id="acceptedFileTypes" type="hidden" value=".log,.xlsx,.jpg,.png">-->

        </div>
    </div>

</div>

<script type="text/javascript">

    // /**
    //  * 文件delete结果
    //  * @param callBack
    //  */
    // var oFileDeleteSuccess = function (bussId, oFile){
    //     console.log("manage_onlineFile_searchform-回调函数",bussId,oFile);
    // }
    //
    // /**
    //  * 文件success结果
    //  * @param callBack
    //  */
    // var oFileUploadSuccess = function(bussId, oFile){
    //     console.log("manage_onlineFile_searchform-回调函数",bussId,oFile);
    // }
    //
    // /**
    //  * 文件列表点击回调
    //  * @param callBack
    //  */
    // var oFileClickItem = function (bussId, oFile){
    //     console.log("manage_onlineFile_searchform-回调函数",bussId,oFile);
    // }
    //
    // /**
    //  * 文件回显需要的id
    //  * @param callBack
    //  */
    // function oFileUploadShow(){
    //     // 1是id  2是objectId
    //     let showFiles = {1: '331,332,333,334'};
    //     //let showFiles = {2: 'd6745e6ed6a037771ee1f4a903cf2ff7145ffa64,2fddc049922b113a02fa9d39671e037580f9cc61,fd0e43440e45c63fbe9d86b8fafaf5669a7d5bb3,2dedccdedff819e5b968e9c69861177274356014'};
    //     oFileinit.call(this,showFiles,oFileDeleteSuccess,oFileUploadSuccess,oFileClickItem);
    // }



</script>