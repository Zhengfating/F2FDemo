package com.alipay.api.domain;

import com.alipay.api.AlipayObject;
import com.alipay.api.internal.mapping.ApiField;

/**
 * 创建小程序管理员虚拟 ID
 *
 * @author auto create
 * @since 1.0, 2018-02-26 15:45:35
 */
public class AlipayOpenMiniInneraccountCreateModel extends AlipayObject {

	private static final long serialVersionUID = 6286582248429128531L;

	/**
	 * 业务类型
	 */
	@ApiField("client_type")
	private String clientType;

	/**
	 * 外部业务主体ID
	 */
	@ApiField("out_biz_id")
	private String outBizId;

	public String getClientType() {
		return this.clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public String getOutBizId() {
		return this.outBizId;
	}
	public void setOutBizId(String outBizId) {
		this.outBizId = outBizId;
	}

}
