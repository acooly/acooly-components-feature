<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_schedulerRule_editform" class="form-horizontal" action="/manage/schedulerRule/<#if action=='create'>saveJson<#else>updateJson</#if>.html" method="post">
        <@jodd.form bean="schedulerRule" scope="request">
            <input name="id" type="hidden"/>
            <div class="card-body">
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">任务名</label>
                    <div class="col-sm-9">
                        <input type="text" name="memo" placeholder="请输入任务名..." class="easyui-validatebox form-control" data-options="validType:['text','length[1,64]'],required:true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">cron表达式</label>
                    <div class="col-sm-9">
                        <input type="text" name="cronString" placeholder="请输入cron表达式..." class="easyui-validatebox form-control"
                               data-options="required:true"
                               validType="commonRegExp['^\\s*($|#|\\w+\\s*=|(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[01]?\\d|2[0-3])(?:(?:-|\/|\\,)(?:[01]?\\d|2[0-3]))?(?:,(?:[01]?\\d|2[0-3])(?:(?:-|\/|\\,)(?:[01]?\\d|2[0-3]))?)*)\\s+(\\?|\\*|(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?(?:,(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?)*)\\s+(\\?|\\*|(?:[1-9]|1[012])(?:(?:-|\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?(?:,(?:[1-9]|1[012])(?:(?:-|\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?)*|\\?|\\*|(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?(?:,(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?)*)\\s+(\\?|\\*|(?:[0-6])(?:(?:-|\/|\\,|#)(?:[0-6]))?(?:L)?(?:,(?:[0-6])(?:(?:-|\/|\\,|#)(?:[0-6]))?(?:L)?)*|\\?|\\*|(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?(?:,(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?)*)(|\\s)+(\\?|\\*|(?:|\\d{4})(?:(?:-|\/|\\,)(?:|\\d{4}))?(?:,(?:|\\d{4})(?:(?:-|\/|\\,)(?:|\\d{4}))?)*))$','请填写正确的corn表达式']"
                        />
                        <div style="margin-top: 5px;">0 0/10 * * * ? 每10分钟执行一次；<br>0/10 * * * * ? 每10秒执行一次；<br>0 59 23 *
                            * ? 每天23点29分钟执行一次；<br>0 0 9,11 * * ? 每天9点11点执行一次
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">任务类型</label>
                    <div class="col-sm-9">
                        <select name="actionType" class="form-control select2bs4">
                            <#list allTaskTypes as k,v>
                                <option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                </div>

                <div class="form-group row" id="manage_schedulerRule_properties_row">
                    <label class="col-sm-3 col-form-label">HTTP地址</label>
                    <div class="col-sm-9">
                        <input type="text" name="properties" placeholder="请输入通知的HTTP地址..." class="easyui-validatebox form-control"
                               data-inputmask="'alias':'url'" data-mask data-options="validType:['url','length[1,255]']"/>
                    </div>
                </div>
                <div class="form-group row" id="manage_schedulerRule_className_row">
                    <label class="col-sm-3 col-form-label">类名</label>
                    <div class="col-sm-9">
                        <input type="text" name="className" placeholder="全路径类名，并把此类注解为spring bean" class="easyui-validatebox form-control" data-options="validType:['text','length[1,255]']"/>
                    </div>
                </div>
                <div class="form-group row" id="manage_schedulerRule_methodName_row">
                    <label class="col-sm-3 col-form-label">方法名</label>
                    <div class="col-sm-9">
                        <input type="text" name="methodName" placeholder="方法名" class="easyui-validatebox form-control" data-options="validType:['text','length[1,255]']"/>
                    </div>
                </div>

                <div class="form-group row" id="manage_schedulerRule_dubboGroup_row">
                    <label class="col-sm-3 col-form-label">Dubbo 组名</label>
                    <div class="col-sm-9">
                        <input type="text" name="dubboGroup" placeholder="Dubbo Group" class="easyui-validatebox form-control" data-options="validType:['text','length[1,255]']"/>
                    </div>
                </div>
                <div class="form-group row" id="manage_schedulerRule_dubboVersion_row">
                    <label class="col-sm-3 col-form-label">Dubbo 版本</label>
                    <div class="col-sm-9">
                        <input type="text" name="dubboVersion" placeholder="Dubbo Version, 默认:1.0" class="easyui-validatebox form-control" data-options="validType:['text','length[1,255]']"/>
                    </div>
                </div>
                <div class="form-group row" id="manage_schedulerRule_dubboParam_row">
                    <label class="col-sm-3 col-form-label">Dubbo 参数</label>
                    <div class="col-sm-9">
                        <div>可选，例如:key:value,...,key:value 服务端取值用:Map attachments =
                            RpcContext.getContext().getAttachments();
                        </div>
                        <input type="text" name="dubboParam" placeholder="dubbo参数" class="easyui-validatebox form-control" data-options="validType:['text','length[1,255]']"/>
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">开始时间</label>
                    <div class="col-sm-9">
                        <input type="text" name="validityStart" placeholder="开始时间" class="easyui-validatebox form-control"
                               onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               value="<#if (schedulerRule.validityStart)?? >${schedulerRule.validityStart?string('yyyy-MM-dd HH:mm:ss')}<#else>${createValidityStart?string('yyyy-MM-dd HH:mm:ss')}</#if>"
                               data-options="validType:['text','length[1,255]'],required:true"/>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">结束时间</label>
                    <div class="col-sm-9">
                        <input type="text" name="validityEnd" placeholder="结束时间" class="easyui-validatebox form-control"
                               onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"
                               value="<#if (schedulerRule.validityEnd)?? >${schedulerRule.validityEnd?string('yyyy-MM-dd HH:mm:ss')}<#else>${createValidityEnd?string('yyyy-MM-dd HH:mm:ss')}</#if>"
                               data-options="validType:['text','length[1,255]'],required:true"/>
                    </div>
                </div>

                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">状态</label>
                    <div class="col-sm-9">
                        <select name="status" class="form-control select2bs4">
                            <#list allStatuss as k,v>
                                <option value="${k}">${v}</option></#list>
                        </select>
                    </div>
                </div>
            </div>
        </@jodd.form>
    </form>
    <script type="text/javascript">

        function manage_schedulerRule_selectRow(name) {
            return $('#manage_schedulerRule_' + name + '_row');
        }
        function manage_schedulerRule_selectForm(name) {
            return $.acooly.framework.getFormItem("manage_schedulerRule_editform", name);
        }

        var propertiesRow = manage_schedulerRule_selectRow('properties');
        var classNameRow = manage_schedulerRule_selectRow("className");
        var methodNameRow = manage_schedulerRule_selectRow("methodName");
        var dubboGroupRow = manage_schedulerRule_selectRow("dubboGroup");
        var dubboVersionRow = manage_schedulerRule_selectRow("dubboVersion");
        var dubboParamRow = manage_schedulerRule_selectRow("dubboParam");

        var properties = manage_schedulerRule_selectForm('properties');
        var className = manage_schedulerRule_selectForm("className");
        var methodName = manage_schedulerRule_selectForm("methodName");
        var dubboGroup = manage_schedulerRule_selectForm("dubboGroup");
        var dubboVersion = manage_schedulerRule_selectForm("dubboVersion");
        var dubboParam = manage_schedulerRule_selectForm("dubboParam");

        function manage_schedulerRule_hideAll(){
            propertiesRow.hide();
            classNameRow.hide();
            methodNameRow.hide();
            dubboGroupRow.hide();
            dubboVersionRow.hide();
            dubboParamRow.hide();
            properties.validatebox('options').required = false;
            dubboGroup.validatebox('options').required = false;
            dubboVersion.validatebox('options').required = false;
            className.validatebox('options').required = false;
            methodName.validatebox('options').required = false;
        }

        function manage_schedulerRule_changeInputText(code) {
            manage_schedulerRule_hideAll();
            if (code == "HTTP") {
                propertiesRow.show();
                properties.validatebox('options').required = true;
            }
            if (code == "DUBBO") {
                dubboGroupRow.show();
                dubboVersionRow.show();
                dubboParamRow.show();
                dubboGroup.validatebox('options').required = true;
                dubboVersion.validatebox('options').required = true;
            }
            if (code == "LOCAL") {
                className.validatebox('options').required = true;
                methodName.validatebox('options').required = true;
                classNameRow.show();
                methodNameRow.show();
            }
        }


        $(function () {
            $.acooly.framework.getFormItem("manage_schedulerRule_editform", "actionType").on('select2:select', function (e) {
                manage_schedulerRule_changeInputText(e.params.data.id);
            });

            // 编辑时的加载
            manage_schedulerRule_changeInputText($.acooly.framework.getFromItemValue("manage_schedulerRule_editform", "actionType"));
        });


    </script>
</div>
