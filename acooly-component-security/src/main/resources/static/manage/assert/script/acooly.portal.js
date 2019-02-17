var panels = [];

function acooly_portal_init() {
    $.ajax({
        url: contextPath + '/manage/system/portallets.html',
        success: function (result) {
            panels = result;
            //alert(dwr.util.toDescriptiveString(panels,2))
            $('#layout_portal_portal').portal({
                border: false,
                fit: true,
                onStateChange: function () {
                    var portalState = getPortalState();
                    console.info("portal-state: " + portalState);
                    $.cookie('portal-state', portalState, {
                        expires: 7
                    });
                }
            });
            var state = $.cookie('portal-state');
            if (!state || !checkPortalletCookie(state)) {
                //冒号代表列，逗号代表行
                state = getDefaultStates();
            }
            addPortalPanels(state);
            $('#layout_portal_portal').portal('resize');
        }
    });
}

function getPanelOptions(id) {
    for (var i = 0; i < panels.length; i++) {
        if (panels[i].id == id) {
            return panels[i];
        }
    }
    return undefined;
}

/**
 * 检查cookies是否有效
 * @param portalState
 * @returns {Boolean} true：有效，false:无效
 */
function checkPortalletCookie(portalState) {
    var columns = portalState.split(':');
    var x = 0;
    for (var i = 0; i < columns.length; i++) {
        var cc = columns[i].split(',');
        for (var j = 0; j < cc.length; j++) {
            x++;
            var options = getPanelOptions(cc[j]);
            if (!options) {
                return false;
            }
        }
    }

    if (x != panels.length) {
        return false;
    }
    return true;
}

function cleanPortalletCookie() {
    $.cookie('portal-state', "");
}

function getDefaultStates() {
    var aa = [];
    var cc = [];
    var mid = 0;
    if (panels.length > 2) {
        mid = panels.length % 2 == 0 ? panels.length / 2 : (panels.length - 1) / 2
    }
    for (var i = 0; i < panels.length; i++) {
        cc.push(panels[i].id);
        if (i == mid) {
            aa.push(cc.join(','));
            cc = [];
        }
    }
    aa.push(cc.join(','));
    var result = aa.join(':');
    return result;
}


function getPortalState() {
    var aa = [];
    for (var columnIndex = 0; columnIndex < 2; columnIndex++) {
        var cc = [];
        var panels = $('#layout_portal_portal').portal('getPanels', columnIndex);
        for (var i = 0; i < panels.length; i++) {
            cc.push(panels[i].attr('id'));
        }
        aa.push(cc.join(','));
    }
    return aa.join(':');
}


function addPortalPanels(portalState) {
    var columns = portalState.split(':');
    for (var columnIndex = 0; columnIndex < columns.length; columnIndex++) {
        var cc = columns[columnIndex].split(',');
        for (var j = 0; j < cc.length; j++) {
            var options = getPanelOptions(cc[j]);
            if (options) {
                var p = $('<div/>').attr('id', options.id).appendTo('body');
                if (options.loadMode == 1) {
                    //url链接内容
                    if (options.showMode == 2) {
                        // iframe方式加载
                        var url = options.href;
                        options.href = '';
                        options.content = '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>';
                    }
                } else {
                    //文本内容
                    options.href = '';
                    options.content = '<div style="padding:5px;">' + options.content + '</div>'
                }
                p.panel(options);
                $('#layout_portal_portal').portal('add', {
                    panel: p,
                    columnIndex: columnIndex
                });
            }
        }
    }
}