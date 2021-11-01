/*******************************************************************************
* KindEditor - WYSIWYG HTML Editor for Internet
* Copyright (C) 2006-2011 kindsoft.net
*
* @author Roddy <luolonghao@gmail.com>
* @site http://www.kindsoft.net/
* @licence http://www.kindsoft.net/license.php
*******************************************************************************/

KindEditor.plugin('clearhtml', function(K) {
	var self = this, name = 'clearhtml';
	self.clickToolbar(name, function() {
		self.focus();
		var html = self.html();
		html = html.replace(/(<script[^>]*>)([\s\S]*?)(<\/script>)/ig, '');
		html = html.replace(/(<style[^>]*>)([\s\S]*?)(<\/style>)/ig, '');
		html = K.formatHtml(html, {
      font : ['id', 'class', 'color', 'size', 'face', '.background-color'],
      span : [
        'id', 'class', '.color', '.background-color', '.font-size', '.font-family', '.background',
        '.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.line-height'
      ],
      div : [
        'id', 'class', 'align', '.border', '.margin', '.padding', '.text-align', '.color',
        '.background-color', '.font-size', '.font-family', '.font-weight', '.background',
        '.font-style', '.text-decoration', '.vertical-align', '.margin-left'
      ],
      table: [
        'id', 'class', 'border', 'cellspacing', 'cellpadding', 'width', 'height', 'align', 'bordercolor',
        '.padding', '.margin', '.border', 'bgcolor', '.text-align', '.color', '.background-color',
        '.font-size', '.font-family', '.font-weight', '.font-style', '.text-decoration', '.background',
        '.width', '.height', '.border-collapse'
      ],
      'td,th': [
        'id', 'class', 'align', 'valign', 'width', 'height', 'colspan', 'rowspan', 'bgcolor',
        '.text-align', '.color', '.background-color', '.font-size', '.font-family', '.font-weight',
        '.font-style', '.text-decoration', '.vertical-align', '.background', '.border'
      ],
      a : ['id', 'class', 'href', 'target', 'name'],
      embed : ['id', 'class', 'src', 'width', 'height', 'type', 'loop', 'autostart', 'quality', '.width', '.height', 'align', 'allowscriptaccess', 'wmode'],
      img : ['id', 'class', 'src', 'width', 'height', 'border', 'alt', 'title', 'align', '.width', '.height', '.border'],
      'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6' : [
        'id', 'class', 'align', '.text-align', '.color', '.background-color', '.font-size', '.font-family', '.background',
        '.font-weight', '.font-style', '.text-decoration', '.vertical-align', '.text-indent', '.margin-left'
      ],
      pre : ['id', 'class'],
      hr : ['id', 'class', '.page-break-after'],
      'br,tbody,tr,strong,b,sub,sup,em,i,u,strike,s,del' : ['id', 'class'],
      iframe : ['id', 'class', 'src', 'frameborder', 'width', 'height', '.width', '.height','allowFullScreen','allowfullscreen']
    });
		self.html(html);
		self.cmd.selection(true);
		self.addBookmark();
	});
});
