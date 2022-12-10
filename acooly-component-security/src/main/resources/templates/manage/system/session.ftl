<div class="easyui-layout" data-options="fit : true,border : false">
    <!-- 查询条件 -->
    <div data-options="region:'west',border:false" style="overflow: hidden; width: 300px;">
        <div class="card-body">
            <dl class="row">
                <dt class="col-sm-9">活动会话总数：</dt>
                <dd class="col-sm-3">${sessionCount}</dd>
                <dt class="col-sm-9">会话序列化模式：</dt>
                <dd class="col-sm-3">${config.redisSerializeType}</dd>
                <dt class="col-sm-9">会话缓存超时时长(分钟)：</dt>
                <dd class="col-sm-3">${config.redisTimeout}</dd>
                <dt class="col-sm-9">会话超时时长(分钟)：</dt>
                <dd class="col-sm-3">${config.timeout/60}</dd>
                <dt class="col-sm-9">会话超时检查间隔(秒)：</dt>
                <dd class="col-sm-3">${config.checkInterval}</dd>
                <dt class="col-sm-9">踢人模式开关：</dt>
                <dd class="col-sm-3">${config.enableKickOut}</dd>
                <dt class="col-sm-9">单用户最大登录数：</dt>
                <dd class="col-sm-3">${config.maxSessionPerUser}</dd>
            </dl>
        </div>

    </div>
    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <table id="manage_session_datagrid" class="easyui-datagrid" url="/manage/session/actives.html"
               fit="true" fitColumns="false" pagination="true" idField="id" pageSize="20" pageList="[5, 10, 20, 30, 40, 50 ]" sortName="id" sortOrder="desc"
               checkOnSelect="true" selectOnCheck="true" singleSelect="false">
            <thead>
            <tr>
                <th field="id" sortable="true">sessionId</th>
<#--                <th field="startTimestamp" formatter="dateTimeFormatter">startTimestamp</th>-->
<#--                <th field="stopTimestamp" formatter="dateTimeFormatter">stopTimestamp</th>-->
<#--                <th field="lastAccessTime" formatter="dateTimeFormatter">lastAccessTime</th>-->
<#--                <th field="timeout" data-options="formatter:function(v){ return v/1000/60;}">timeout(m)</th>-->
<#--                <th field="host" >host</th>-->
<#--                <th field="expired">expired</th>-->
                <th field="action" data-options="formatter:function(value, row, index){return formatAction('manage_user_action',value,row)}">动作
                </th>
            </tr>
            </thead>
        </table>

        <div id="manage_user_action" style="display: none;">
            <a title="查看"  onclick="$.acooly.framework.show('/manage/session/show.html?sessionId={0}',1024,768);" href="#"><i class="fa fa-file-o fa-lg fa-fw fa-col"></i></a>
            <a title="删除" onclick="$.acooly.framework.remove('/manage/session/deleteJson.html','{0}','manage_session_datagrid');" href="#"><i class="fa fa-trash-o fa-lg fa-fw fa-col"></i></a>
        </div>
    </div>
</div>
<script type="text/javascript">

    /**
     * 页面加载完成后执行
     */
    $(function () {
        //注册按键回车直接提交查询
        $.acooly.framework.initPage('manage_user_searchform', 'manage_user_datagrid');
    });
</script>
