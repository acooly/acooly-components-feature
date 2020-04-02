<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div align="center">
    <table class="tableForm" width="100%">
        <tr>
            <td>根据文件扩展名自动适配导入文件类型，目前支持的格式包括：Excel和CSV。 请：<a href="/manage/assert/data/import_user_template.xlsx" target="_blank">模板下载</a>。</td>
        </tr>
        <tr>
            <td>
                <div class="uploadfive-queue" id="manage_user_import_uploader_queue"></div>
            </td>
        </tr>
        <tr>
            <td height="20">
                <div id="manage_user_import_uploader_message" style="color: red;text-align: center;"></div>
            </td>
        </tr>
        <tr>
            <td align="center"><input type="file" name="manage_user_import_uploader_file" id="manage_user_import_uploader_file" multiple="true"/></td>
        </tr>
    </table>
    <script type="text/javascript">
        $(function () {
            $.acooly.framework.createUploadify({
                /** 上传导入的URL */
                url: "/manage/system/user/importJson.html?_csrf=${Request['org.springframework.security.web.csrf.CsrfToken'].token}&importIgnoreTitle=true&splitKey=v",
                /** 导入操作消息容器 */
                messager: "manage_user_import_uploader_message",
                queueID: "manage_user_import_uploader_queue",
                /** 上传导入文件表单ID */
                uploader: "manage_user_import_uploader_file"
            });
        });
    </script>
</div>
