package com.baidu.yun.push;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;

public class PushMsgTest {

	
	public static void main(String[] args) throws PushClientException, PushServerException {
		String apiKey = "D45KE8z5q27lCMa9OrMBAPXs";
		String secretKey = "dam314sj5j4sqdag812HerKLwGxVSGC1 ";
		PushKeyPair pair = new PushKeyPair(apiKey, secretKey);
		
		BaiduPushClient pushClient = new BaiduPushClient(pair, BaiduPushConstants.CHANNEL_REST_URL);
		
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				// TODO Auto-generated method stub
				System.out.println(event.getMessage());
			}
		});
		
		PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
				.addChannelId("eqw234qwe")
				.addMsgExpires(new Integer(3600))
				.addMessageType(1)
				.addMessage("{\"title\":\"TEST\",\"description\":\"Hello Baidu push!\"}")
				.addDeviceType(3);
		
		try {
			PushMsgToSingleDeviceResponse response = pushClient.pushMsgToSingleDevice(request);
			System.out.println("msgId:" + response.getMsgId() + ",sendTime: " + response.getSendTime());
		} catch (PushClientException e) {
			// TODO Auto-generated catch block
			if (BaiduPushConstants.ERROROPTTYPE) { 
		        throw e;
		    } else {
		        e.printStackTrace();
		    }
		} catch (PushServerException e) {
			// TODO Auto-generated catch block
			if (BaiduPushConstants.ERROROPTTYPE) {
		        throw e;
		    } else {
		        System.out.println(String.format(
		                "requestId: %d, errorCode: %d, errorMessage: %s",
		                e.getRequestId(), e.getErrorCode(), e.getErrorMsg()));
		    }
		}
		
	}
	
}
