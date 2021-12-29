<#assign jodd=JspTaglibs["http://www.springside.org.cn/jodd_form"] />
<div>
    <form id="manage_content${RequestParameters.code}_editform" class="form-horizontal" action="${rc.contextPath}/manage/module/cms/content/<#if action == 'create'>save<#else>update</#if>Json.html" method="post" enctype="multipart/form-data">
        <@jodd.form bean="content" scope="request">
            <input name="id" type="hidden"/>
            <input type="hidden" name="code" value="${RequestParameters.code}"/>
            <input type="hidden" name="pubDateStr" id="pubDateStr"/>
            <!-- 文章 -->
            <#if RequestParameters.cmsType != 'banner'>
                <div class="card-body">
                    <div class="form-group row">
                        <label class="col-sm-1 col-form-label">标题</label>
                        <div class="col-sm-11">
                            <div class="input-group">
                                <input name="title" type="text" placeholder="请输入标题..." class="easyui-validatebox form-control" data-options="validType:['length[1,128]']" required="true"/>
                                <div class="input-group-append">
                                    <button type="button" onclick="manage_content${RequestParameters.code}_editform_meta_toggle(this);" class="btn btn-tool btn-flat"><i class="fa fa-plus"></i></button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div id="manage_content${RequestParameters.code}_editform_meta" style="display: none;">
                        <div class="form-group row">
                            <label class="col-sm-1 col-form-label">发布日期</label>
                            <div class="col-sm-5">
                                <input size="20" class="easyui-validatebox form-control" id="manage_content${RequestParameters.code}_editform_pubData"
                                       value="<#if content.pubDate??>${content.pubDate?string('yyyy-MM-dd HH:mm:ss')}</#if>"
                                       name="pubDate" onFocus="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
                            </div>
                            <label class="col-sm-1 col-form-label">编码</label>
                            <div class="col-sm-5">
                                <#if RequestParameters.cmsType != 'banner'>
                                    <select name="keycode" editable="false" class="form-control input-sm select2bs4"><option value="">选择编码</option>
                                        <#list allCodes as k,v>
                                            <option value="${k}">${v}</option>
                                        </#list>
                                    </select>
                                </#if>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-1 col-form-label">SEO标题</label>
                            <div class="col-sm-5">
                                <input name="webTitle" type="text" placeholder="请输入页面标题(SEO)..." class="easyui-validatebox form-control" data-options="validType:['length[1,128]']"/>
                            </div>
                            <label class="col-sm-1 col-form-label">SEO关键字</label>
                            <div class="col-sm-5">
                                <input name="keywords" type="text" placeholder="请输入关键字（SEO）..." class="easyui-validatebox form-control" data-options="validType:['length[1,128]']"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-1 col-form-label">简介</label>
                            <div class="col-sm-11">
                                <textarea class="easyui-validatebox form-control" placeholder="请输入简介(同时用于SEO)..." id="subject" name="subject" cols="80" rows="2" data-options="validType:['length[1,128]']"></textarea>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-1 col-form-label">Web封面</label>
                            <div class="col-sm-5">
                                <#if content.cover?? && content.cover != ''>
                                    <div class="row col-form-content">
                                        <div class="col-sm-6"><a href="javascript:;" onclick="$.acooly.file.play('${mediaRoot}${content.cover}');">查看</a></div>
                                        <div class="col-sm-6" style="text-align: right;"><a href="javascript:;" onclick="$('#manage_content${RequestParameters.code}_editform_cover_container').toggle();">重新上传</a></div>
                                    </div>
                                </#if>
                                <div class="custom-file" id="manage_content${RequestParameters.code}_editform_cover_container" <#if content != null && content.cover != ''>style="display: none;"</#if>>
                                    <input type="file" name="cover_f" class="easyui-validatebox custom-file-input" accept="image/*" validType="validImg['jpg,gif,png','只能上传jpg,gif,png格式的图片']"/>
                                    <label class="custom-file-label">请选择上传的文件</label>
                                </div>
                            </div>
                            <label class="col-sm-1 col-form-label">App封面</label>
                            <div class="col-sm-5">
                                <#if content.appcover?? && content.appcover != ''>
                                    <div class="row col-form-content">
                                        <div class="col-sm-6"><a href="javascript:;" onclick="$.acooly.file.play('${mediaRoot}${content.appcover}');">查看</a></div>
                                        <div class="col-sm-6" style="text-align: right;"><a href="javascript:;" onclick="$('#manage_content${RequestParameters.code}_editform_appcover_container').toggle();">重新上传</a></div>
                                    </div>
                                </#if>
                                <div class="custom-file" id="manage_content${RequestParameters.code}_editform_appcover_container" <#if content != null && content.appcover != ''>style="display: none;"</#if>>
                                    <input type="file" name="cover_app" class="easyui-validatebox custom-file-input" accept="image/*" validType="validImg['jpg,gif,png','只能上传jpg,gif,png格式的图片']">
                                    <label class="custom-file-label">请选择上传的文件</label>
                                </div>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-1 col-form-label">链接</label>
                            <div class="col-sm-11">
                                <input type="text" name="link" class="easyui-validatebox form-control" data-inputmask="'alias':'url'" data-mask data-options="validType:['url','length[1,128]']"/>
                            </div>
                        </div>
                        <div class="form-group row">
                            <label class="col-sm-1 col-form-label">是否推送</label>
                            <div class="col-sm-11">
                                <div class="icheck-primary">
                                    <input type="checkbox" id="checkboxPrimary1" name="isEventNotify" value="isEventNotify">
                                    <label for="checkboxPrimary1"><small>注意：选中后每次提交则推送，不保持对应状态</small></label>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <div class="col-sm-12">
                            <textarea id="manage_content${RequestParameters.code}_editform_contentId" name="contents" data-options="required:true" style="width:100%;height:430px;">${content.contentBody.body}</textarea>
                        </div>
                    </div>
                </div>
            </#if>

            <!-- banner -->
            <#if RequestParameters.cmsType == 'banner'>
                <div class="card-body">
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">标题</label>
                        <div class="col-sm-10">
                            <input name="title" type="text" placeholder="请输入标题..." class="easyui-validatebox form-control" data-options="validType:['length[1,128]']" required="true"/>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">简介</label>
                        <div class="col-sm-10">
                            <textarea class="easyui-validatebox form-control" placeholder="请输入简介(同时用于SEO)..." id="subject" name="subject" cols="80" rows="2" data-options="validType:['length[1,128]']"></textarea>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">Web封面</label>
                        <div class="col-sm-10">
                            <#if content.cover?? && content.cover != ''>
                                <div class="row col-form-content">
                                    <div class="col-sm-6"><a href="javascript:;" onclick="$.acooly.file.play('${mediaRoot}${content.cover}');">查看</a></div>
                                    <div class="col-sm-6" style="text-align: right;"><a href="javascript:;" onclick="$('#manage_content${RequestParameters.code}_editform_cover_container').toggle();">重新上传</a></div>
                                </div>
                            </#if>
                            <div class="custom-file" id="manage_content${RequestParameters.code}_editform_cover_container" <#if content != null && content.cover != ''>style="display: none;"</#if>>
                                <input type="file" name="cover_f" class="easyui-validatebox custom-file-input" accept="image/*" validType="validImg['jpg,gif,png','只能上传jpg,gif,png格式的图片']">
                                <label class="custom-file-label">请选择上传的文件</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">App封面</label>
                        <div class="col-sm-10">
                            <#if content.appcover?? && content.appcover != ''>
                                <div class="row col-form-content">
                                    <div class="col-sm-6"><a href="javascript:;" onclick="$.acooly.file.play('${mediaRoot}${content.appcover}');">查看</a></div>
                                    <div class="col-sm-6" style="text-align: right;"><a href="javascript:;" onclick="$('#manage_content${RequestParameters.code}_editform_appcover_container').toggle();">重新上传</a></div>
                                </div>
                            </#if>
                            <div class="custom-file" id="manage_content${RequestParameters.code}_editform_appcover_container" <#if content != null && content.appcover != ''>style="display: none;"</#if>>
                                <input type="file" name="cover_app" class="easyui-validatebox custom-file-input" accept="image/*" validType="validImg['jpg,gif,png','只能上传jpg,gif,png格式的图片']">
                                <label class="custom-file-label">请选择上传的文件</label>
                            </div>
                        </div>
                    </div>
                    <div class="form-group row">
                        <label class="col-sm-2 col-form-label">链接</label>
                        <div class="col-sm-10">
                            <input type="text" name="link" class="easyui-validatebox form-control" data-inputmask="'alias':'url'" data-mask data-options="validType:['url','length[1,128]']"/>
                        </div>
                    </div>
                </div>
            </#if>
        </@jodd.form>
    </form>

    <script>
        $(function () {
            // 添加时，初始化发布时间
            let formPubDate = $('#manage_content${RequestParameters.code}_editform_pubData').val();
            if (formPubDate == '') {
                $('#manage_content${RequestParameters.code}_editform_pubData').val($.acooly.format.date(new Date()));
            }

            // 初始化多媒体编辑器
            $.acooly.editor.kindEditor({
                minHeight: '310',
                textareaId: 'manage_content${RequestParameters.code}_editform_contentId'
            });


            /**
             * 表单提交前拦截验证
             * @returns {boolean}
             * @private
             */
            function manage_content${RequestParameters.code}_editform_onSubmit() {
                var isPush = $("#isEventNotify").prop("checked");
                if (!isPush) {
                    return true;
                }
                if (!$('#title').val()) {
                    $.messager.alert('提示', '事件通知时,标题不能为空');
                    return false;
                }
                if (!$('#subject').val()) {
                    $.messager.alert('提示', '事件通知时,简介(SEO)不能为空');
                    return false;
                }
                return true;
            }

        });

        function manage_content${RequestParameters.code}_editform_meta_toggle(obj) {
            let mateContainer = $('#manage_content${RequestParameters.code}_editform_meta');
            mateContainer.toggle();
            var classes = $(obj).children().first().attr("class");
            if (classes.indexOf("fa-plus") != -1) {
                $(obj).html("<i class=\"fa fa-minus\"></i>");
            } else {
                $(obj).html("<i class=\"fa fa-plus\"></i>");
            }
        }

    </script>

</div>
