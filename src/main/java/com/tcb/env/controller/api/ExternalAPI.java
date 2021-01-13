package com.tcb.env.controller.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.tcb.env.config.Dom4jConfig;
import com.tcb.env.controller.UserController;
import com.tcb.env.model.UserModel;
import com.tcb.env.model.shareshine.SsResult;
import com.tcb.env.pojo.User;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DefaultArgument;
import com.tcb.env.util.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * [功能描述]：外部系统接口
 * </p>
 * <p>
 * Copyright (c) 1993-2016 TCB Corporation
 * </p>
 *
 * @author 王垒
 * @version 1.0, 2019年11月15日下午14:13:33
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping(value = "/ExternalApi")
public class ExternalAPI {

    /**
     * 日志输出标记
     */
    private static final String LOG = "ExternalAPI";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(ExternalAPI.class);

    /**
     * 声明User服务
     */
    @Resource
    private IUserService userService;

    /**
     * 声明用户控制器服务
     */
    @Resource
    private UserController userController;

    /**
     * 声明配置文件服务
     */
    @Resource
    private Dom4jConfig dom4jConfig;

    private String charset = "utf-8";

    /**
     * 外部免登录跳转（津南同阳协议）
     *
     * @param seId
     * @param code
     * @param mv
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @RequestMapping(value = {"/shareShineLogin"}, method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView login(String seId, String code, ModelAndView mv, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", httpServletRequest.getHeader("Origin"));//处理跨域
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");//表示是否允许发送Cookie
        if (!StringUtils.isEmpty(seId) && !StringUtils.isEmpty(code)) {
            String userCode = getLoginUserCodeBySid(seId);
//            if (StringUtils.isEmpty(userCode)) {
//                userCode = "admin";
//            }
            if (!StringUtils.isEmpty(userCode)) {
                UserModel userModel = new UserModel();
                userModel.setUserCode(userCode);
                String result = userController.validateUserNativeByCode(userModel, false, httpServletRequest.getSession());
                if (userController.SUCCESS.equals(result)) {
                    User user = userController.ConvertUser(userModel, true, httpServletRequest.getSession());
                    List<User> listUser = userService.getUser(user, false);
                    UserModel sessionUser = userController.ConvertUserModel(listUser.get(0));
                    sessionUser.setUserPassword(user.getUserPassword());
                    if (listUser != null && listUser.size() > 0) {
                        httpServletRequest.getSession().removeAttribute(DefaultArgument.LOGIN_USER);
                        httpServletRequest.getSession().setAttribute(DefaultArgument.LOGIN_USER, sessionUser);
                    }
                    mv.setViewName("/html/main");
                } else {
                    mv.setViewName("redirect:/462.html");
                }
            } else {
                mv.setViewName("redirect:/461.html");
            }
        }
        return mv;
    }

    /**
     * 获取UserCode
     *
     * @param sessionId
     * @return
     */
    private String getLoginUserCodeBySid(String sessionId) {
        String userCode = "";
        String host = "";
        String path = dom4jConfig.getDeDevConfig().getExternalApiUrl();
        Map<String, String> headerList = new HashMap<String, String>();
//        headerList.put("Content-Type", "application/x-www-form-urlencoded");
        //请求参数
        Map<String, String> queryList = new HashMap<String, String>();
        Map<String, String> bodyList = new HashMap<String, String>();
        bodyList.put("sessionId", sessionId);
        try {
            HttpResponse httpResponse = HttpUtil.doPost(host, path, headerList, queryList, bodyList);
            if (httpResponse != null) {
                HttpEntity resEntity = httpResponse.getEntity();
                if (resEntity != null) {
                    String result = EntityUtils.toString(resEntity, charset);

                    //针对同阳错误格式修改成真正的GSON对象格式
                    result = result.substring(result.indexOf("{"));
                    result = result.substring(0,result.lastIndexOf("}")+1);
                    result = result.replace("}\"","}").replace("\"{","{");
                    result = result.replaceAll("\\\\\"", "\"");

                    logger.info(LOG + ":return result is:" + result);
                    Gson gson = new GsonBuilder().create();
                    SsResult res = gson.fromJson(result, new TypeToken<SsResult>() {
                    }.getType());
                    if (res != null && res.getSuccess()) {
                        //更新UserCode
                        userCode = res.getMessage().getCode();
                    }
                }
            }
        } catch (Exception e) {
            logger.error(LOG + "：获取LoginUserCode失败，异常信息为：" + e.getMessage());
        }
        return userCode;
    }

}
