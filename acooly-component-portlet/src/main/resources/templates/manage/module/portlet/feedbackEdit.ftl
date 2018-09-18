<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_feedback_editform" action="${rc.contextPath}/manage/module/portlet/feedback/handle.html" method="post">
		<@jodd.form bean="feedback" scope="request">
            <input name="id" type="hidden"/>
            <table class="tableForm" width="100%">
                <tr>
                    <th width="25%">标题:</th>
                    <td colspan="3">${feedback.title}</td>
                </tr>
                <tr>
                    <th width="25%">类型:</th>
                    <td>${feedback.type}</td>
                    <th>提交时间:</th>
                    <td>${feedback.createTime?datetime}</td>
                </tr>
                <tr>
                    <th>内容:</th>
                    <td colspan="3">${feedback.content}</td>
                </tr>
                <tr>
                    <th>用户名:</th>
                    <td>${feedback.userName}</td>
                    <th>联系电话:</th>
                    <td>${feedback.telephone}</td>
                </tr>
                <tr>
                    <th>联系地址:</th>
                    <td>${feedback.address}</td>
                    <th>联系信息:</th>
                    <td>${feedback.contactInfo}</td>
                </tr>
                <tr>
                    <th>处理状态：</th>
                    <td colspan="3">
                        <select style="width:80px;height:27px;" name="status" editable="false" panelHeight="auto" class="easyui-combobox">
                        <#list allStatuss as k,v>
                            <option value="${k}">${v}</option></#list>
                        </select>
                    </td>
                </tr>

                <tr>
                    <th>处理意见：</th>
                    <td colspan="3"><textarea rows="3" cols="40" name="comments" class="easyui-validatebox"
                                              validType="byteLength[1,255]"></textarea></td>
                </tr>
            </table>
        </@jodd.form>
    </form>
</div>
