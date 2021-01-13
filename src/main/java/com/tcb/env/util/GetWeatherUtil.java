package com.tcb.env.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * <p>
 * [功能描述]：获取天气信息
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 * 
 * @author 王垒
 * @version 1.0, 2016年5月19日下午4:19:39
 * @since EnvDust 1.0.0
 *
 */
public class GetWeatherUtil {

	private static String http_Url = "https://free-api.heweather.net/s6/weather/now";
	private static String param_key = "f84f1c36df6f45aa9977039826da641f";

	// String httpArg = "cityname=天津";
	// String jsonResult = request(httpArg);
	// System.out.println(jsonResult);

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param httpParam
	 *            :参数（城市ID或拼音）
	 * @return 返回结果
	 */
	public static String request(String param) {
        StringBuilder sb = new StringBuilder();
    	InputStream is=null;
        BufferedReader br=null;
		String result = null;
		String httpParam = "key="+param_key+"&"+ param;

		try {
			URL url = new URL(http_Url);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setReadTimeout(5000);
            connection.setConnectTimeout(10000);
            connection.setRequestProperty("accept", "*/*");
            //发送参数
            connection.setDoOutput(true);
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(httpParam);
            out.flush();
            //接收结果
            is = connection.getInputStream();
            br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String line;
            //缓冲逐行读取
            while((line = br.readLine()) != null ) {
                sb.append(line);
            }
            result = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
            //输出结果
            System.out.println(sb.toString());
            //关闭流
            try {
                if(is!=null){
                    is.close();
                }
                if(br!=null){
                    br.close();
                }
            } catch (IOException e2) {}
        }
		return result;
	}
}
