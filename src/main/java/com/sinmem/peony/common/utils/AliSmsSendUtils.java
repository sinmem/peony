package com.sinmem.peony.common.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.sinmem.peony.common.context.SmsBean;
import com.sinmem.peony.common.enums.Msg;
import com.sinmem.peony.common.exception.ErrorPhoneNumberException;
import com.sinmem.peony.common.exception.SmsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @version V1.0
 * @BelongsProject peony
 * @BelongsPackage com.sinmem.peony.common.utils
 * @Author sinmem
 * @CreateTime 2020-01-05 02:30
 * @Description 阿里云短信发送工具类
 */
public class AliSmsSendUtils {
    private static final Logger LOG = LoggerFactory.getLogger(AliSmsSendUtils.class);
    private static final SmsBean smsBean= new SmsBean();
    public static boolean sendSms(String phoneNumber, String code){
        DefaultProfile profile = DefaultProfile.getProfile(smsBean.getF(), smsBean.getB(), smsBean.getA());
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain(smsBean.getC());
        request.setVersion(smsBean.getD());
        request.setAction(smsBean.getE());
        request.putQueryParameter("RegionId", smsBean.getF());
        request.putQueryParameter("PhoneNumbers", phoneNumber);
        request.putQueryParameter("SignName", smsBean.getG());
        request.putQueryParameter("TemplateCode", smsBean.getH());
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code +"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            if(data.indexOf("\"Message\":\"OK\"") > 0){
                LOG.info("验证码发送成功"+data);
                return true;
            }else if (data.indexOf("isv.MOBILE_NUMBER_ILLEGAL") > 0){
                throw new ErrorPhoneNumberException(Msg.E40010);
            }else {
                throw new SmsException(11112, "未知的短信错误"+data);
            }
        } catch (ServerException e) {
            throw new SmsException(11112, "未知的短信错误"+e.getErrMsg());
        } catch (ClientException e) {
            throw new SmsException(11112, "未知的短信错误"+e.getErrMsg());
        }
    }
}
