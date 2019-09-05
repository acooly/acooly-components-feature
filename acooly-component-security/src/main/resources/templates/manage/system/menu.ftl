<ul class="sidebar-menu" data-widget="tree">
    <li class="header">
        功能菜单
        <span class="pull-right-container">
            <#--<a title="刷新菜单(重新授权后)" href="javascript:;"><i style="margin-top: 0.3em;" class="fa fa-refresh pull-right"></i></a>-->
            <a onclick="$.acooly.admin.headerToggle();" title="最大/小化菜单" href="javascript:;">
                <i id="menu-toggle-icon" style="margin-top: 0.3em;" class="fa fa-expand pull-right"></i>
            </a>
        </span>
    </li>

    <#list menu as e1>
        <#--第一层-->
         <li class="treeview<#if e1?index==0> menu-open active</#if>">
             <a href="javascript:;">
                 <#if e1.iconSkin??><i class="fa ${e1.iconSkin}"></i><#else><span class="line-action ${e1.icon}"></span></#if>
                 <span>${e1.name}</span>
                 <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>
             </a>
             <ul class="treeview-menu">
                 <#if e1.children??>
                 <#list e1.children as e2>
                    <#if e2.children??>
                        <li class="treeview">
                            <a href="javascript:;" onclick="$.acooly.layout.accessResource({type:'URL',name:'${e2.name}',value:'${e2.value}',showMode:'${e2.showMode}',icon:'${e2.icon}'})">
                                <#if e2.iconSkin??><i class="fa ${e2.iconSkin}"></i><#else><span class="line-action ${e2.icon}"></span></#if>
                                ${e2.name}
                                <span class="pull-right-container"><i class="fa fa-angle-left pull-right"></i></span>
                            </a>
                             <ul class="treeview-menu">
                                <#list e2.children as e3>
                                 <li>
                                     <a href="javascript:;" onclick="$.acooly.layout.accessResource({type:'URL',name:'${e3.name}',value:'${e3.value}',showMode:'${e3.showMode}',icon:'${e3.icon}'})">
                                     <#if e3.iconSkin??><i class="fa ${e3.iconSkin}"></i><#else><span class="line-action ${e3.icon}"></span></#if>${e3.name}</a>
                                 </li>
                                </#list>
                             </ul>
                         </li>
                    <#else>
                        <li><a href="javascript:;" onclick="$.acooly.layout.accessResource({type:'URL',name:'${e2.name}',value:'${e2.value}',showMode:'${e2.showMode}',icon:'${e2.icon}'})">
                            <#if e2.iconSkin??><i class="fa ${e2.iconSkin}"></i><#else><span class="line-action ${e2.icon}"></span></#if>${e2.name}
                        </a></li>
                    </#if>
                 </#list>
                 </#if>
             </ul>
         </li>
    </#list>
</ul>