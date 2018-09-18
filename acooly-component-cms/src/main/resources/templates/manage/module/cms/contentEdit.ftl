<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<head>
    <style type="text/css">
        th {
            width: 20%;
        }
    </style>
    <script type="text/javascript">

        var pubdate = $("#pubDateStr").val();
        var today;
        if (pubdate) {
            today = pubdate;
        } else {
            today = formateDate(new Date());
        }

        $(document).ready(function () {
            $("#pubDate").val(today);
        });


        function formateDate(dateObject) {
            var d = new Date(dateObject);
            var day = d.getDate();
            var month = d.getMonth() + 1;
            var year = d.getFullYear();
            var hours = d.getHours();
            var minutes = d.getMinutes();
            var sec = d.getSeconds();
            if (day < 10) {
                day = "0" + day;
            }
            if (month < 10) {
                month = "0" + month;
            }
            //yyyy-MM-dd HH:mm:ss
            var date = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + sec;
            return date;
        };
    </script>
</head>
<div>
    <form id="manage_content${RequestParameters.code}_editform"
          action="${rc.contextPath}/manage/module/cms/content/<#if action == 'create'>save<#else>update</#if>Json.html"
          method="post"
          enctype="multipart/form-data">
    <@jodd.form bean="content" scope="request">
        <input name="id" type="hidden"/>
        <input type="hidden" name="code" value="${RequestParameters.code}"/>
        <input type="hidden" name="pubDateStr" id="pubDateStr"/>


        <table class="tableForm" width="100%">
            <tr>
                <th width="20%">标题：</th>
                <td>
                    <input type="text" style="width: 300px;" class="text" name="title" size="128"
                           class="easyui-validatebox"
                           data-options="required:true" class="text" validType="byteLength[1,128]"/>
                    <#if RequestParameters.cmsType != 'banner'>
                    <span style="margin-left: 10px;">编码: <select name="keycode" editable="false" style="width: 80px;"
                                                                 panelHeight="auto"
                                                                 class="easyui-combobox"><option value="">选择编码</option>
                                     <#list allCodes as k,v>
                                         <option value="${k}">${v}</option></#list>
                               </select></span>
                    </#if>
                </td>
            </tr>
            <#if RequestParameters.cmsType != 'banner'>
            <tr>
                <th>页面标题(SEO)：</th>
                <td><input type="text" style="width: 300px;" class="text" name="webTitle" size="128"
                           class="easyui-validatebox" class="text"
                           validType="byteLength[1,128]"/>
                    <span style="margin-left: 10px;">关键字（SEO）：<input type="text" class="text" name="keywords" size="20"
                                                                     class="easyui-validatebox"
                                                                     validType="byteLength[1,128]"/></span>
                </td>
            </tr>
            <tr>
                <th>简介(SEO)：</th>
                <td><textarea name="subject" cols="80" rows="2" style="width:700px;"></textarea></td>
            </tr>
            </#if>
            <tr>
                <th>pc图片(封面)：</th>
                <td>
                    <input type="file" name="cover_f" id="cover_f" class="easyui-validatebox"
                           validType="validImg['jpg,gif,png','只能上传jpg,gif,png格式的图片']"/>
                    <#if content.cover?? && content.cover != '' && action!='create'>
                        <div><a href="${mediaRoot}/${content.cover}" target="_blank" data-lightbox="cover"><img
                                src="${mediaRoot}/${content.cover}" width="200"></a></div>
                    </#if>
                </td>
            </tr>
            <tr>
                <th>app图片(封面)：</th>
                <td>
                    <input type="file" name="cover_app" id="cover_f" class="easyui-validatebox"
                           validType="validImg['jpg,gif,png','只能上传jpg,gif,png格式的图片']"/>
                    <#if content.appcover?? && content.appcover != '' && action!='create'>
                        <div><a href="${mediaRoot}/${content.appcover}" target="_blank" data-lightbox="cover"><img
                                src="${mediaRoot}/${content.appcover}" width="200"></a></div>
                    </#if>
                </td>
            </tr>
            <#if RequestParameters.cmsType = 'banner'>
            <tr>
                <th width="20%">简介：</th>
                <td><textarea name="subject" cols="100" rows="2" style="width:360px;"></textarea></td>
            </tr>
            <tr>
                <th>链接：</th>
                <td><input type="text" style="width: 300px;" class="text" name="link" size="128"
                           class="easyui-validatebox"
                           data-options="required:true" class="text" validType="byteLength[1,128]"/></td>
            </tr>
            <#else>
                <tr>
                    <th>链接：</th>
                    <td><input type="text" style="width: 300px;" class="text" name="link" size="128"
                               class="easyui-validatebox"
                               class="text" validType="byteLength[1,128]"/></td>
                </tr>
                <tr>
                    <th>是否推送事件通知：</th>
                    <td><input type="checkbox" name="isEventNotify" value="isEventNotify"/></td>
                </tr>
                <tr>
                    <th>发布日期：</th>
                    <td><input size="20" class="text" id="pubDate" name="pubDate"
                               onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/></td>
                </tr>
            <tr>
                <td colspan="2">
                    <textarea id="contentId" name="contents" data-options="required:true"
                              style="width:100%;height:430px;">${content.contentBody.body}</textarea>
                </td>
            </tr>
                <script type="text/javascript">
                    $(function () {
                        var token = $("meta[name='X-CSRF-TOKEN']").attr("content");// 从meta中获取token
                        var ke = $.acooly.framework.kingEditor({
                            uploadUrl: '/ofile/kindEditor.html?_csrf=' + token,
                            minHeight: '310',
                            textareaId: 'contentId'
                        });
                    });
                </script>
            </#if>
            <tr>
                <th>备注：</th>
                <td><textarea name="comments" rows="2" style="width:300px;"></textarea></td>
            </tr>

        </table>
    </@jodd.form>
    </form>
</div>
