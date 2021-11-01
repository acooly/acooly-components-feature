KindEditor.plugin('diy_video', function (K) {
  var self = this, name = 'diy_video';
  self.clickToolbar(name, function () {
    var html = ['<div style="padding:10px 20px;">',
        '<p style="color:red">支持优酷、爱奇艺、土豆、腾讯视频、56等视频网站通用代码</p>',
        '<textarea class="ke-textarea" style="width:408px;height:160px;" placeholder="形如：\r\n<iframe frameborder=\'0\' src=\'https://v.qq.com/txp/iframe/player.html?vid=e086759f7nn\' allowFullScreen=\'true\' height=\'498\' width=\'510\'></iframe>"></textarea>',
        '</div>'].join(''),
      dialog = self.createDialog({
        name: name,
        width: 450,
        title: self.lang(name),
        body: html,
        yesBtn: {
          name: self.lang('yes'),
          click: function (e) {
            var code = textarea.val(),
              html = '' + code + '';
            if (K.trim(code) === '') {
              alert("请输入视频代码！");
              textarea[0].focus();
              return;
            }
            self.insertHtml(html).hideDialog().focus();
          }
        }
      }),
      textarea = K('textarea', dialog.div);
    textarea[0].focus();
  });
});