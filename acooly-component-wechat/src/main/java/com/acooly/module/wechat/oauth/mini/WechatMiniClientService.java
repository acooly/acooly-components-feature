package com.acooly.module.wechat.oauth.mini;

import com.acooly.module.wechat.oauth.mini.dto.WechatMiniSession;

/**
 * 微信小程序授权
 * 
 * @author CuiFuQ
 *
 */
public interface WechatMiniClientService {

	/**
	 * 获取小程序全局唯一后台接口调用凭据（access_token）
	 * 
	 * @return
	 */
	public String getAccessToken();

	/**
	 * 登录凭证校验
	 * 
	 * @param jsCode
	 *               <li>调用接口获取登录凭证（code）
	 *               <li>详情参考 wx.login(Object object)
	 * 
	 * @return
	 */
	public WechatMiniSession loginAuthVerify(String jsCode);

	/**
	 * 获取小程序码，适用于需要的码数量极多的业务场景。
	 * 
	 * @param scene
	 *              <li>
	 *              最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用
	 *              urlencode 处理，请使用其他编码方式）
	 * @param page
	 *              <li>必须是已经发布的小程序存在的页面（否则报错），例如 pages/index/index, 根路径前不要填加
	 *              /,不能携带参数（参数请放在scene字段里），如果不填写这个字段，默认跳主页面
	 * @return
	 */

	public String getMiniProgramImgCode(String scene, String page);

}
