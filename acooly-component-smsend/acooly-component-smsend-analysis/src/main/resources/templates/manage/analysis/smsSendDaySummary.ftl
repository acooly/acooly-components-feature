<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_smsSendDaySummary_editform" class="form-horizontal" action="/manage/analysis/smsSendDay/summary.html" method="post">
        <input name="id" type="hidden"/>
        <div class="card-body">
            <div class="form-group row">
                <label class="col-sm-3 col-form-label">选择汇总日期</label>
                <div class="col-sm-9">
                    <input type="text" name="period" placeholder="请选择汇总日期..." class="easyui-validatebox form-control" required="true" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd'})"/>
                </div>
            </div>
            <div class="form-group row">
                <label class="col-sm-3 col-form-label">是否重新汇总</label>
                <div class="col-sm-9">
                    <select name="redo" class="form-control select2bs4">
                        <option value="true">是：如果已汇总过，则删除重新汇总</option>
                        <option value="false">否：如果已汇总过，则报错提示</option>
                    </select>
                </div>
            </div>
        </div>
    </form>
</div>
