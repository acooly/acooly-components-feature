<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />

<#--不集成boss，需要的引入文件-->
<#--<link href="/manage/assert/plugin/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">-->
<#--<script src="/manage/assert/plugin/jquery/jquery-3.3.1.min.js"></script>-->
<#--<script src="/manage/assert/plugin/bootstrap/3.3.7/js/bootstrap.min.js"></script>-->

<#--文件拖动上传-->
<link rel="stylesheet" href="/ofile/dropzone/dropzone.min.css" type="text/css"/>
<script src="/ofile/dropzone/dropzone.min.js"></script>

<div>
    <input type="hidden" id="_csrf" name="_csrf"
           value="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>
    <div id="dropzone-img-div" class="dropzone" style=""></div>

    <div style="text-align:center;padding-top: 10px;">
        <button id="saveForm" class="btn btn-success"> 确 认 上 传</button>
    </div>


</div>

<#--文件上传-删除-->
<script src="/ofile/js/drop_zone_upload.js"></script>