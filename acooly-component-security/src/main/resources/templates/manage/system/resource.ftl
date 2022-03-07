<style>
    .iconSpan {
        display: inline-block;
        margin: 5px;
        padding: 5px;
        width: 180px;
        border: 1px dashed #ddd;
        border-radius: 3px;
    }

    .iconSpan .icon-elm {
        vertical-align: middle;
        display: inline-block;
        font-size: 12px;
    }

    .resource_icons .header {
        height: 35px;
        line-height: 35px;
        vertical-align: middle;
        padding: 2px 10px;
        margin: 0 10px 5px 0;
        display: block;
        background-color: #eeeeee;
        border-bottom: 1px solid #dddddd;
    }

</style>
<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north'" style="overflow: hidden; height: 33px; padding-left: 2px;">
        <div id="manage_resource_toolbar" style="margin-top: 1px;">
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.system.resource.treeToggle(this)">
                <i class="fa fa-minus-square-o fa-fw fa-col"></i> 全部折叠</a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.system.resource.formCreate(true)">
                <i class="fa fa-plus-circle fa-fw fa-col"></i>添加顶级</a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="$.acooly.system.resource.loadTree()">
                <i class="fa fa-refresh fa-fw fa-col"></i>刷新</a>
        </div>
    </div>
    <!-- 菜单树 -->
    <div data-options="region:'west',border:true,split:true" style="width:300px;padding-left: 10px;" align="left">
        <div id="manage_resource_tree" class="ztree"></div>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <form id="manage_resource_editform" class="form-horizontal" action="${rc.contextPath}/manage/system/resource/updateJson.html" method="post">
            <input name="id" id="manage_resource_node_id" type="hidden"/>

            <div class="card-body">
                <div class="form-group row">
                    <label class="col-sm-1 col-form-label">父节点</label>
                    <div class="col-sm-3 col-form-content">
                        <div id="manage_resource_node_parentName"></div>
                        <input id="manage_resource_node_parentId" name="parentId" type="hidden"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-1 col-form-label">资源名称</label>
                    <div class="col-sm-3">
                        <input name="name" type="text" class="easyui-validatebox form-control" data-options="validType:['length[1,32]']" required="true"/>
                    </div>
                    <label class="col-sm-1 col-form-label">加载方式</label>
                    <div class="col-sm-3">
                        <select name="showMode" id="manage_resource_form_showMode" class="form-control select2bs4">
                            <#list allShowModes as k,v>
                                <option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-1 col-form-label">资源类型</label>
                    <div class="col-sm-3">
                        <select name="type" id="manage_resource_form_type" class="form-control select2bs4">
                            <#list allTypes as k,v>
                                <option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                    <label class="col-sm-1 col-form-label">是否显示</label>
                    <div class="col-sm-3">
                        <select name="showState" id="manage_resource_form_showState" class="form-control select2bs4">
                            <#list allShowStates as k,v>
                                <option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-1 col-form-label">资源串(值)</label>
                    <div class="col-sm-7" id="manage_resource_form_value_container">
                        <input name="value" id="manage_resource_form_value" type="text" class="easyui-validatebox form-control" data-options="validType:['length[1,255]']" required="true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-1 col-form-label"></label>
                    <div class="col-sm-7">
                        <button type="button" class="btn btn-primary btn-sm" onclick="$.acooly.system.resource.formSave()"><i class="fa fa-floppy-o"></i> 保存</button>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-1 col-form-label">资源图标</label>
                    <div class="col-sm-11">
                        <div id="manage_resource_form_icon_container">
                            <div class="resource_icons">
                                <a href="javascript:;" onclick="$('#resource_icons_font').toggle()">
                                    <div class="header">
                                        <span>Awesome图标</span>
                                        <div style="float: right;margin-right: 15px;"><i class="fa fa-chevron-down"></i></div>
                                    </div>
                                </a>

                                <div id="resource_icons_font">
                                         <span class="iconSpan">
                                            <input class="icon-elm" type="radio" id="manage_resource_form_icon_first" name="icon" value="fa-circle-o">
                                            <i style="vertical-align:middle;display:inline-block;font-size: 16px;" class="fa fa-circle-o"></i>
                                            <span class="icon-elm">fa-circle-o</span>
                                        </span>
                                    <#list allIcons as e>
                                        <#if e?starts_with("icons") == false  && e != 'fa-circle-o'>
                                            <span class="iconSpan">
                                            <input class="icon-elm" type="radio" name="icon" value="${e}"/>
                                            <i style="vertical-align:middle;display:inline-block;font-size: 16px;" class="fa ${e}"></i>
                                            <span class="icon-elm">${e}</span>
                                        </span>
                                        </#if>
                                    </#list>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>

    <script type="text/javascript">
        /**
         * 页面加载完成后执行
         */
        $(function () {
            $.acooly.system.resource.loadTree();
            $.acooly.system.resource.init();
        });
    </script>
</div>
