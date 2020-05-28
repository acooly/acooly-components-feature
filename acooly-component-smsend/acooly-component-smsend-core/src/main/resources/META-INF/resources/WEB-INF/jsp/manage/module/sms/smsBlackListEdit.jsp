<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/jsp/manage/common/taglibs.jsp" %>
<div>
    <form id="manage_smsBlackList_editform" class="form-horizontal" action="${pageContext.request.contextPath}/manage/module/sms/smsBlackList/${action=='create'?'saveJson':'updateJson'}.html"
          method="post">
        <jodd:form bean="smsBlackList" scope="request">
            <input name="id" type="hidden"/>
            <div class="card-body">
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">手机号码</label>
                    <div class="col-sm-9">
                        <input type="text" name="mobile" placeholder="请输入手机号码..." class="easyui-validatebox form-control" data-options="validType:['text','length[1,16]']" required="true"/>
                    </div>
                </div>


                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">状态</label>
                    <div class="col-sm-9">
                        <select name="status" class="form-control select2bs4" data-options="required:true">
                            <c:forEach items="${allStatuss}" var="e">
                                <option value="${e.key}">${e.value}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
                <div class="form-group row">
                    <label class="col-sm-3 col-form-label">备注</label>
                    <div class="col-sm-9">
                        <textarea rows="3" cols="40" placeholder="请输入备注..." name="description" class="easyui-validatebox form-control"></textarea>
                    </div>
                </div>
            </div>
        </jodd:form>
    </form>
</div>
