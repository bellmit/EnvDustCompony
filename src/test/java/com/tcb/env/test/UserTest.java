package com.tcb.env.test;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tcb.env.model.shareshine.SsResult;

public class UserTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String jsonMessage = "{\"Success\":true,\"Code\":\"1\",\"Message\":{\\\"UserId\\\":\\\"87fcff2f-4bda-4cad-aa2b-80ef56df7950\\\",\\\"Code\\\":\\\"vocadmin\\\",\\\"UserName\\\":\\\"Voc用户\\\",\\\"Account\\\":\\\"vocadmin\\\",\\\"Password\\\":\\\"5f06495c82fa3c129ac9e365de212b84\\\",\\\"LogTime\\\":\\\"2019-11-26T15:18:08.9917628+08:00\\\",\\\"Secretkey\\\":\\\"aac0a927e004f3ec\\\",\\\"Gender\\\":\\\"男\\\",\\\"CompanyId\\\":null,\\\"DepartmentId\\\":null,\\\"ObjectId\\\":\\\"0b9c7b73-89b6-44d8-b3d7-af7041b8a052,87fcff2f-4bda-4cad-aa2b-80ef56df7950\\\",\\\"IPAddress\\\":\\\"192.168.1.1\\\",\\\"IPAddressName\\\":\\\"FileDataError\\\",\\\"IsSystem\\\":false,\\\"SoftwareAuthority\\\":\\\"9\\\",\\\"CurrentSoftwareCode\\\":\\\"9\\\",\\\"Authorty\\\":\\\"28cc0486-cb92-4c4f-ba7f-40d788314f17,25C3C47D-302A-4443-9593-0CE1ADA8F0F1,;\\\",\\\"GridList\\\":[],\\\"SiteId\\\":null,\\\"GridRoleLevel\\\":\\\"\\\"}}";
		Gson gson = new Gson();
		try {
			SsResult res = gson.fromJson(jsonMessage, new TypeToken<SsResult>() {
			}.getType());
			System.out.println(res.getSuccess());
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
