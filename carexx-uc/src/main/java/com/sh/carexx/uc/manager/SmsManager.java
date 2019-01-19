package com.sh.carexx.uc.manager;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.sh.carexx.common.CarexxConstant;
import com.sh.carexx.common.ErrorCode;
import com.sh.carexx.common.exception.BizException;

/**
 * 
 * ClassName: SmsManager <br/>
 * Function: 短信发送管理器 <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * Date: 2018年7月4日 上午10:31:53 <br/>
 * 
 * @author WL
 * @since JDK 1.8
 */
@Service
public class SmsManager {
	/** 验证码有效期（分钟） */
	private static final int SMS_VERIFY_CODE_EXPIRE = 5;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// 产品名称:云通信短信API产品
	@Value("${sms.product}")
	private String product;
	// 产品域名
	@Value("${sms.domain}")
	private String domain;

	// 用户ID
	@Value("${sms.accessKeyId}")
	private String accessKeyId;
	// 密钥
	@Value("${sms.accessKeySecret}")
	private String accessKeySecret;

	@Value("${sms.signName}")
	private String signName;

	@Value("${sms.templateCode}")
	private String templateCode;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	private void sendSms(String mobiles, String signName, String templateCode, String templateParam)
			throws BizException, ClientException {

		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", this.accessKeyId, this.accessKeySecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", this.product, this.domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		// 组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		// 必填:待发送手机号
		request.setPhoneNumbers(mobiles);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(signName); // TODO 改这里
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(templateCode); // TODO 改这里
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的用户,您的验证码为${code}"时,此处的值为
		request.setTemplateParam(templateParam);

		// 选填-上行短信扩展码(无特殊需求用户请忽略此字段)
		// request.setSmsUpExtendCode("90997");

		// 可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
		request.setOutId("yourOutId");

		// hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = null;
		try {
			sendSmsResponse = acsClient.getAcsResponse(request);

		} catch (Exception e) {
			throw new BizException(ErrorCode.SYS_ERROR, e);

		}
		if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) {
			this.logger.info("短信发送成功！接收手机号为：[{}]", mobiles);
		} else {
			this.logger.info("短信发送失败！接收手机号为：[{}]，Code：[{}]，Message：[{}]", mobiles, sendSmsResponse.getBizId(),
					sendSmsResponse.getMessage());
			throw new BizException((String) sendSmsResponse.getCode(), (String) sendSmsResponse.getMessage());
		}
	}

	/**
	 * 
	 * sendVerifyCode:发送验证码 <br/>
	 * 
	 * @author WL
	 * @param mobile
	 * @throws BizException
	 * @since JDK 1.8
	 */
	public void sendVerifyCode(String mobile) throws BizException, ClientException {
		String verifyCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
		String templateParam = String.format("{\"code\":\"" + verifyCode + "\"}");
		this.sendSms(mobile, this.signName, this.templateCode, templateParam);
		this.redisTemplate.opsForValue().set(CarexxConstant.CachePrefix.CAREXX_SMS_VERIFY_CODE + mobile, verifyCode,
				SMS_VERIFY_CODE_EXPIRE * 60, TimeUnit.SECONDS);
	}

	/**
	 * 
	 * checkSmsVerifyCode:检查短信验证码 <br/>
	 * 
	 * @author WL
	 * @param mobile
	 * @param verifyCode
	 * @throws BizException
	 * @since JDK 1.8
	 */
	public void checkSmsVerifyCode(String mobile, String verifyCode) throws BizException {
		String storedVerifyCode = this.redisTemplate.opsForValue()
				.get(CarexxConstant.CachePrefix.CAREXX_SMS_VERIFY_CODE + mobile);
		if (StringUtils.isBlank(storedVerifyCode)) {
			throw new BizException(ErrorCode.SMS_VERIFY_CODE_INVALID);
		}
		if (!storedVerifyCode.equals(verifyCode)) {
			throw new BizException(ErrorCode.SMS_VERIFY_CODE_INPUT_ERROR);
		}
		this.redisTemplate.delete(CarexxConstant.CachePrefix.CAREXX_SMS_VERIFY_CODE + mobile);
	}
}
