<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />

<#--待实现-->

<div>
    <form method="post" enctype="multipart/form-data">
        <input type="hidden" id="_csrf" name="_csrf"
               value="${Request['org.springframework.security.web.csrf.CsrfToken'].token}"/>
        <input type="file" name="file" id="file" multiple="multiple"/>

        <div style="text-align:center;padding-top: 10px;">
            <button id="saveForm" class="btn btn-success" onclick="fileUpload()"> 确 认 上 传</button>
        </div>

    </form>
</div>


<script>


    // 待实现
    function  fileUpload(){
        $.ajax({
            url: "/ofile/upload.html",
            type: "post",
            dataType: "multipart/form-data",
            data: {
                data : $('#form').serialize()
            },
            success: function (result) {
                console.log(result);
            }
        })
    }

</script>