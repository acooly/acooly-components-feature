<script type="text/javascript">
    /**
     * 页面加载完成后执行
     */
    $(function () {
        manage_resource_loadTree();
    });
</script>

<div class="easyui-layout" data-options="fit : true,border : false">
    <div data-options="region:'north'" style="overflow: hidden; height:30px;padding-left: 2px;">
        <div id="manage_resource_toolbar" style="margin-top: 1px;">
            <a href="#" class="easyui-linkbutton" plain="true" onclick="manage_resource_tree_toggle()"><i
                    class="fa fa-plus-square fa-lg fa-fw fa-col"></i>展开/收起菜单</a>
            <a href="#" class="easyui-linkbutton" plain="true" onclick="manage_resource_form_add(true)"><i
                    class="fa fa-plus-circle fa-lg fa-fw fa-col"></i>添加顶级菜单</a>
        </div>
    </div>
    <!-- 菜单树 -->
    <div data-options="region:'west',border:true,split:true" style="width:300px;padding-left: 10px;" align="left">
        <div id="manage_resource_tree" class="ztree"></div>
    </div>

    <!-- 列表和工具栏 -->
    <div data-options="region:'center',border:false">
        <form id="manage_resource_editform" action="${rc.contextPath}/manage/system/resource/updateJson.html" method="post">
            <jodd:form bean="resource" scope="request">
                <input name="id" id="manage_resource_node_id" type="hidden"/>
                <table class="tableForm" width="100%">
                    <tr>
                        <th style="width: 100px">父节点：</th>
                        <td colspan="5">
                            <div id="manage_resource_node_parentName"></div>
                            <input id="manage_resource_node_parentId" name="parentId" type="hidden"/>
                        </td>
                    </tr>
                    <tr>
                        <th style="width: 80px">资源名称：</th>
                        <td colspan="5"><input name="name" type="text" class="easyui-validatebox" data-options="required:true"/></td>
                    </tr>
                    <tr>
                        <th>加载方式：</th>
                        <td>
                            <select name="showMode" style="width: 150px;" editable="false" panelHeight="auto" class="easyui-combobox" data-options="required:true">
                                <#list allShowModes as k,v><option value="${k}">${v}</option></#list>
                            </select>
                        </td>
                        <th>资源类型：</th>
                        <td>
                            <select name="type" id="manage_resource_form_type" editable="false" panelHeight="auto" style="width: 150px;"
                                    class="easyui-combobox"
                                    data-options="required:true">
                                <#list allTypes as k,v><option value="${k}">${v}</option></#list>
                            </select>
                        </td>
                        <th>是否显示：</th>
                        <td>
                            <select id="manage_resource_form_showState" name="showState" style="width: 150px;" editable="false" panelHeight="auto" class="easyui-combobox" data-options="required:true">
                                <#list allShowStates as k,v><option value="${k}">${v}</option></#list>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>资源串(值)：</th>
                        <td colspan="5">
                            <input name="value" id="manage_resource_form_value" type="text" class="easyui-validatebox" size="50"/>
                            <a href="#" class="easyui-linkbutton" onclick="manage_resource_form_submit()"><i class="fa fa-floppy-o"></i> 保存</a>
                        </td>
                    </tr>
                    <tr>
                        <th style="vertical-align: initial;">资源图标：</th>
                        <td colspan="5">
                            <input name="customIcon" type="text" class="easyui-validatebox"/> <small>可自定义填值定义其他class图标，请确保通过自定义样式加入到框架中，推荐从下面选择。</small>
                            <div id="iconContainer">
<#--                                <div class="resource_icons">-->
<#--                                    <a href="javascript:;" onclick="$('#resource_icons_old').toggle()">-->
<#--                                    <div class="header">-->
<#--                                        <span>图片图标（不推荐了...）</span>-->
<#--                                        <div style="float: right;margin-right: 15px;"><i class="fa fa-chevron-down"></i></div>-->
<#--                                    </div>-->
<#--                                    </a>-->
<#--                                    <div id="resource_icons_old" style="display: none;">-->
<#--                                        <#list allIcons as e>-->
<#--                                        <#if e?starts_with("icons")>-->
<#--                                            <span style="width: 50px;">-->
<#--                                                <input type="radio" name="icon" value="${e}"/>-->
<#--                                                <i style='vertical-align:middle;display:inline-block; width:16px; height:16px;' class="${e}"></i>-->
<#--                                            </span>-->
<#--                                        </#if>-->
<#--                                        </#list>-->
<#--                                    </div>-->
<#--                                </div>-->
                                
                                <div class="resource_icons">

                                    <a href="javascript:;" onclick="$('#resource_icons_font').toggle()">
                                        <div class="header">
                                            <span>Font图标（推荐）</span>
                                            <div style="float: right;margin-right: 15px;"><i class="fa fa-chevron-down"></i></div>
                                        </div>
                                    </a>

                                    <div id="resource_icons_font">
                                         <span class="iconSpan">
                                            <input class="icon-elm" type="radio" name="icon" value="fa-circle-o">
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
                        </td>
                    </tr>
                </table>
            </jodd:form>
        </form>
    </div>
    <style>
        .iconSpan{
            display: inline-block;
            margin: 5px;
            padding: 5px;
            width:150px;
            border: 1px dashed #ddd;
            border-radius: 3px;
        }
        .iconSpan .icon-elm {vertical-align:middle;display:inline-block;font-size: 12px;}
        .resource_icons .header {
            height: 35px; line-height: 35px; vertical-align: middle; padding: 2px 10px; margin: 0 10px 5px 0;
            display: block;background-color: #eeeeee;border-bottom: 1px solid #dddddd;
        }

    </style>
</div>
