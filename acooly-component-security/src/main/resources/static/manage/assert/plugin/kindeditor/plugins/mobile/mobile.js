KindEditor.plugin('mobile', function (K) {
    var self = this, name = 'mobile';
    var oldWidth = self.width;
    var oldHeight = self.height;
    self.clickToolbar(name, function () {
        var html = '<ul class="ke-mobile-ul" style="margin-top: 15px;margin-left: 25px">\n' +
            '  <li>\n' +
            '    <input type="radio" name="mobile" value="0"><span>&nbsp;还原</span>\n' +
            '  </li>\n' +
            '  <li>\n' +
            '    <input type="radio" name="mobile" value="1"><span>&nbsp;iPhone 5/SE（320 * 568）</span>\n' +
            '  </li>\n' +
            '  <li>\n' +
            '    <input type="radio" name="mobile" value="2"><span>&nbsp;iPhone 6/7/8（375 * 667）</span>\n' +
            '  </li>\n' +
            '  <li>\n' +
            '    <input type="radio" name="mobile" value="3"><span>&nbsp;iPhone 6/7/8 Plus（414 * 736）</span>\n' +
            '  </li>\n' +
            '  <li>\n' +
            '    <input type="radio" name="mobile" value="4"><span>&nbsp;iPhone X（375 * 812）</span>\n' +
            '  </li>\n' +
            '</ul>',

            dialog = self.createDialog({
                name: name,
                width: 300,
                title: self.lang(name),
                body: html,
                yesBtn: {
                    name: self.lang('yes'),
                    click: function (e) {
                        var width = oldWidth;
                        var height = oldHeight;
                        var value = $(".ke-mobile-ul input[name='mobile']:checked").val();
                        switch (parseInt(value)) {
                            case 1:
                                width = 320;
                                height = 568;
                                break;
                            case 2:
                                width = 375;
                                height = 667;
                                break;
                            case 3:
                                width = 414;
                                height = 736;
                                break;
                            case 4:
                                width = 375;
                                height = 812;
                                break;
                            default:
                                break;
                        }
                        self.resize(width, height);
                        $(".ke-container").css("width", width + "px");
                        self.hideDialog().focus();
                    }
                }
            });

        // 监听点击li事件
        $(".ke-mobile-ul li span").mouseover(function () {
            $(this).parent().css("background-color", "#ccc");
        }).mouseout(function () {
            $(this).parent().css("background-color", "white");
        }).click(function () {
            $(this).parent().find("input").click();
        });

    });


});