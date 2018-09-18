<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>

<script type="text/javascript">

    var properties = $('#rule tr[id="properties"]');
    var className = $('#rule tr[id="className"]');
    var methodName = $('#rule tr[id="methodName"]');

    var DGroup = $('#rule tr[id="DGroup"]');
    var DVersion = $('#rule tr[id="DVersion"]');
    var DParam = $('#rule tr[id="DParam"]');

    changeInputText($("#actionTypee").val());

    $("#actionTypee").combobox({
        onChange: function (n, o) {
            changeInputText(n);
        }
    });

    function changeInputText(code) {
        if (code == "HTTP") {
            properties.show();
            className.hide();
            methodName.hide();
            DGroup.hide();
            DVersion.hide();
            DParam.hide();
        }
        if (code == "DUBBO") {
            properties.hide();
            className.hide();
            methodName.hide();
            DGroup.show();
            DVersion.show();
            DParam.show();
        }
        if (code == "LOCAL") {
            properties.hide();
            className.show();
            methodName.show();
            DGroup.hide();
            DVersion.hide();
            DParam.hide();
        }

        function httpShow() {
            properties.show();
            className.hide();
            methodName.hide();
            DGroup.hide();
            DVersion.hide();
            DParam.hide();
        }
    }

</script>
<div>
    <form id="manage_schedulerRule_editform"
          action="${pageContext.request.contextPath}/manage/schedulerRule/${action=='create'?'saveJson':'updateJson'}.html"
          method="post">
        <jodd:form bean="schedulerRule" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%" id="rule">
                <tr>
                    <th width="25%">任务名：</th>
                    <td><textarea rows="1" cols="40" style="width:300px;" name="memo" class="easyui-validatebox"
                                  data-options="required:true" validType="byteLength[1,255]"></textarea></td>
                </tr>
                <tr>
                    <th width="25%">cron表达式：</th>
                    <td>
                        <div style="margin-top: 5px">0 0/10 * * * ? 每10分钟执行一次；<br>0/10 * * * * ? 每10秒执行一次；<br>0 59 23 *
                            * ? 每天23点29分钟执行一次；<br>0 0 9,11 * * ? 每天9点11点执行一次
                        </div>
                        <textarea rows="1" cols="60" style="width:300px;" name="cronString" class="easyui-validatebox"
                                  data-options="required:true"
                                  validType="commonRegExp['^\\s*($|#|\\w+\\s*=|(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[0-5]?\\d)(?:(?:-|\/|\\,)(?:[0-5]?\\d))?(?:,(?:[0-5]?\\d)(?:(?:-|\/|\\,)(?:[0-5]?\\d))?)*)\\s+(\\?|\\*|(?:[01]?\\d|2[0-3])(?:(?:-|\/|\\,)(?:[01]?\\d|2[0-3]))?(?:,(?:[01]?\\d|2[0-3])(?:(?:-|\/|\\,)(?:[01]?\\d|2[0-3]))?)*)\\s+(\\?|\\*|(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?(?:,(?:0?[1-9]|[12]\\d|3[01])(?:(?:-|\/|\\,)(?:0?[1-9]|[12]\\d|3[01]))?)*)\\s+(\\?|\\*|(?:[1-9]|1[012])(?:(?:-|\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?(?:,(?:[1-9]|1[012])(?:(?:-|\/|\\,)(?:[1-9]|1[012]))?(?:L|W)?)*|\\?|\\*|(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?(?:,(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)(?:(?:-)(?:JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC))?)*)\\s+(\\?|\\*|(?:[0-6])(?:(?:-|\/|\\,|#)(?:[0-6]))?(?:L)?(?:,(?:[0-6])(?:(?:-|\/|\\,|#)(?:[0-6]))?(?:L)?)*|\\?|\\*|(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?(?:,(?:MON|TUE|WED|THU|FRI|SAT|SUN)(?:(?:-)(?:MON|TUE|WED|THU|FRI|SAT|SUN))?)*)(|\\s)+(\\?|\\*|(?:|\\d{4})(?:(?:-|\/|\\,)(?:|\\d{4}))?(?:,(?:|\\d{4})(?:(?:-|\/|\\,)(?:|\\d{4}))?)*))$','请填写正确的corn表达式']"></textarea>
                    </td>
                </tr>
                <tr>
                    <th width="25%">任务类型：</th>
                    <td>
                        <select name="actionType" id="actionTypee" editable="false" style="height:27px;"
                                panelHeight="auto" class="easyui-combobox" onchange="changeInputText()"
                                data-options="required:true">
                            <c:forEach items="${allTaskTypes}" var="e">
                                <option value="${e.key}">${e.value}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr id="properties">
                    <th>HTTP地址 ：</th>
                    <td><textarea rows="1" cols="50" style="width:300px;" name="properties" class="easyui-validatebox"
                                  validType="byteLength[1,255]"></textarea></td>
                </tr>
                <tr id="className">
                    <th>类名：</th>
                    <td><textarea rows="1" cols="40" style="width:300px;" name="className"
                                  placeholder="全路径类名，并把此类注解为spring bean" class="easyui-validatebox"
                                  validType="byteLength[1,455]"></textarea></td>
                </tr>
                <tr id="methodName">
                    <th>方法名：</th>
                    <td><textarea rows="1" cols="40" style="width:300px;" name="methodName" placeholder="方法名"
                                  class="easyui-validatebox"
                                  validType="byteLength[1,255]"></textarea></td>
                </tr>

                <tr id="DGroup">
                    <th>dubbo组名：</th>
                    <td><textarea rows="1" cols="40" style="width:300px;" name="DGroup" placeholder="dubbo group"
                                  class="easyui-validatebox"
                                  validType="byteLength[1,455]"></textarea></td>
                </tr>
                <tr id="DVersion">
                    <th>dubbo版本：</th>
                    <td><textarea rows="1" cols="40" style="width:300px;" name="DVersion" placeholder="dubbo version"
                                  class="easyui-validatebox"
                                  validType="byteLength[1,255]"></textarea></td>
                </tr>
                <tr id="DParam">
                    <th>dubbo参数：</th>
                    <td>
                        <div>可选，例如:key:value,...,key:value 服务端取值用:Map attachments =
                            RpcContext.getContext().getAttachments();
                        </div>

                        <textarea rows="1" cols="40" style="width:300px;" name="DParam" class="easyui-validatebox"
                                  validType="byteLength[1,255]"></textarea></td>
                </tr>


                <tr>
                    <th>开始时间：</th>
                    <td><input type="text" name="validityStart" size="20" class="easyui-validatebox text"
                               data-options="required:true"
                               value="<fmt:formatDate value="${schedulerRule.validityStart ==null?createValidityStart:schedulerRule.validityStart}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
                </tr>
                <tr>
                    <th>结束时间：</th>
                    <td><input type="text" name="validityEnd" size="20" class="easyui-validatebox text"
                               data-options="required:true"
                               value="<fmt:formatDate value="${schedulerRule.validityEnd == null?createValidityEnd:schedulerRule.validityEnd}" pattern="yyyy-MM-dd HH:mm:ss"/>"
                               onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
                </tr>
                <tr>
                    <th>状态：</th>
                    <td><select name="status" editable="false" style="height:27px;" panelHeight="auto"
                                class="easyui-combobox" data-options="required:true">
                        <c:forEach items="${allStatuss}" var="e">
                            <option value="${e.key}">${e.value}</option>
                        </c:forEach>
                    </select></td>
                </tr>
            </table>
        </jodd:form>
    </form>
</div>
