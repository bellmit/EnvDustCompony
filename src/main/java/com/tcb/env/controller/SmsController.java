package com.tcb.env.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tcb.env.model.ResultListModel;
import com.tcb.env.model.ResultModel;
import com.tcb.env.model.SmsModel;
import com.tcb.env.model.UserModel;
import com.tcb.env.pojo.Device;
import com.tcb.env.pojo.Sms;
import com.tcb.env.pojo.SmsUser;
import com.tcb.env.pojo.Status;
import com.tcb.env.pojo.User;
import com.tcb.env.service.ISmsService;
import com.tcb.env.service.IUserService;
import com.tcb.env.util.DateUtil;
import com.tcb.env.util.DefaultArgument;

/**
 * <p>[功能描述]：短信设置控制器</p>
 * <p>Copyright (c) 1997-2018 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2018年4月12日下午3:00:46
 * @since EnvDust 1.0.0
 */
@Controller
@RequestMapping("/SmsController")
public class SmsController {

    /**
     * 日志输出标记
     */
    private static final String LOG = "SmsController";
    /**
     * 声明日志对象
     */
    private static Logger logger = Logger.getLogger(SmsController.class);
    /**
     * 声明短信设置服务
     */
    @Resource
    private ISmsService smsService;
    /**
     * 声明User服务
     */
    @Resource
    private IUserService userService;

    @RequestMapping(value = "/querySms", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<SmsModel> querySms(
            SmsModel smsModel, HttpSession httpsession) {
        // 创建类
        ResultListModel<SmsModel> resultListModel = new ResultListModel<SmsModel>();
        List<SmsModel> smsModelList = new ArrayList<SmsModel>();
        List<Sms> smsList = new ArrayList<Sms>();
        Sms sms = ConvertSms(smsModel, httpsession);
        int count = smsService.getSmsCount(sms);
        if (count > 0) {
            smsList = smsService.getSms(sms);
            // 依次转换成model
            for (Sms temp : smsList) {
                SmsModel smsModelTemp = ConvertSmsModel(temp);
                if (smsModelTemp != null) {
                    smsModelList.add(smsModelTemp);
                }
            }
            resultListModel.setRows(smsModelList);
        }
        resultListModel.setTotal(count);
        return resultListModel;
    }

    /**
     * <p>[功能描述]：查询短信设置接收人ID</p>
     *
     * @param smsId
     * @param httpsession
     * @return
     * @author 王垒, 2018年4月19日下午4:12:56
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/querySmsUser", method = {RequestMethod.POST})
    public @ResponseBody
    ResultListModel<String> querySmsUser(
            String smsId, HttpSession httpsession) {
        // 创建类
        ResultListModel<String> resultListModel = new ResultListModel<String>();
        List<String> userIdList = new ArrayList<String>();
        SmsUser smsUser = new SmsUser();
        Sms sms = new Sms();
        sms.setSmsId(Long.valueOf(smsId));
        smsUser.setSms(sms);
        List<SmsUser> smsUserList = smsService.getSmsUser(smsUser);
        if (smsUserList != null && smsUserList.size() > 0) {
            for (SmsUser temp : smsUserList) {
                User user = temp.getUser();
                if (user != null) {
                    userIdList.add(String.valueOf(user.getUserId()));
                }
            }
            resultListModel.setRows(userIdList);
        }
        resultListModel.setTotal(userIdList.size());
        resultListModel.setResult(true);
        return resultListModel;
    }

    /**
     * <p>[功能描述]：新增短信设置</p>
     *
     * @param smsModel
     * @param userIdList
     * @param httpsession
     * @return
     * @author 王垒, 2018年4月17日上午10:37:06
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/insertSms", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel insertSms(
            SmsModel smsModel, @RequestParam(value = "list[]") List<String> userIdList, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (smsModel != null && userIdList != null && userIdList.size() > 0) {
            try {
                if (smsService.getSmsExist(smsModel.getDeviceId(), smsModel.getStatusCode(), smsModel.getThingCode()) == 0) {
                    // 获取当前操作者
                    UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                    Sms sms = ConvertSms(smsModel, httpsession);
                    List<SmsUser> smsUserList = new ArrayList<SmsUser>();
                    for (String userId : userIdList) {
                        SmsUser smsUser = new SmsUser();
                        smsUser.setSms(sms);
                        User user = new User();
                        user.setUserId(Integer.valueOf(userId));
                        user.setOptUser(sms.getOptUser());
                        smsUser.setUser(user);
                        smsUser.setOptUser(loginuser.getUserId());
                        smsUserList.add(smsUser);
                    }
                    int intresult = smsService.insertSms(sms, smsUserList);
                    if (intresult > 0) {
                        resultModel.setResult(true);
                    } else {
                        resultModel.setResult(false);
                        resultModel.setDetail("新增设备短信设置失败！");
                    }
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("系统已存在此设备的短信设置信息，可以选择更新操作！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("新增设备短信设置失败！");
                logger.error(LOG + "：新增设备短信设置失败，信息为：" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * <p>[功能描述]：更新短信设置</p>
     *
     * @param smsModel
     * @param userIdList
     * @param httpsession
     * @return
     * @author 王垒, 2018年4月17日上午10:40:07
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/updateSms", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel updateSms(
            SmsModel smsModel, @RequestParam(value = "list[]") List<String> userIdList, HttpSession httpsession) {
        ResultModel resultModel = new ResultModel();
        if (smsModel != null && userIdList != null && userIdList.size() > 0) {
            try {
                // 获取当前操作者
                UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
                Sms sms = ConvertSms(smsModel, httpsession);
                List<SmsUser> smsUserList = new ArrayList<SmsUser>();
                for (String userId : userIdList) {
                    SmsUser smsUser = new SmsUser();
                    smsUser.setSms(sms);
                    User user = new User();
                    user.setUserId(Integer.valueOf(userId));
                    user.setOptUser(sms.getOptUser());
                    smsUser.setUser(user);
                    smsUser.setOptUser(loginuser.getUserId());
                    smsUserList.add(smsUser);
                }
                int intresult = smsService.updateSms(sms, smsUserList);
                if (intresult > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("更新短信设置失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("更新短信设置失败！");
                logger.error(LOG + "：更新短信设置失败，信息为：" + e.getMessage());
            }
        }
        return resultModel;
    }

    /**
     * <p>[功能描述]：删除短信设置</p>
     *
     * @param list
     * @return
     * @author 王垒, 2018年4月17日上午10:44:53
     * @since EnvDust 1.0.0
     */
    @RequestMapping(value = "/deleteSms", method = {RequestMethod.POST})
    public @ResponseBody
    ResultModel deleteSms(
            @RequestParam(value = "list[]") List<String> list) {
        ResultModel resultModel = new ResultModel();
        if (list != null && list.size() > 0) {
            try {
                int intresult = smsService.deleteSms(list);
                if (intresult > 0) {
                    resultModel.setResult(true);
                } else {
                    resultModel.setResult(false);
                    resultModel.setDetail("删除设备短信设置失败！");
                }
            } catch (Exception e) {
                resultModel.setResult(false);
                resultModel.setDetail("删除设备短信设置失败！");
                logger.error(LOG + "：删除设备短信设置失败，信息为：" + e.getMessage());
            }
        } else {
            resultModel.setResult(false);
            resultModel.setDetail("删除设备短信设置失败，错误原因：服务器未接收到删除数据！");
        }
        return resultModel;
    }

    /**
     * <p>[功能描述]：SmsModel转换成Sms</p>
     *
     * @param smsModel
     * @param httpsession
     * @return
     * @author 王垒, 2018年4月13日上午9:26:51
     * @since EnvDust 1.0.0
     */
    private Sms ConvertSms(SmsModel smsModel, HttpSession httpsession) {
        Sms sms = new Sms();
        if (smsModel != null) {
            if (smsModel.getSmsId() != null && !smsModel.getSmsId().isEmpty()) {
                sms.setSmsId(Long.valueOf(smsModel.getSmsId()));
            }
            Device device = new Device();
            if (smsModel.getDeviceId() != null && !smsModel.getDeviceId().isEmpty()) {
                device.setDeviceId(Integer.valueOf(smsModel.getDeviceId()));
            }
            device.setDeviceCode(smsModel.getDeviceCode());
            device.setDeviceName(smsModel.getDeviceName());
            sms.setDevice(device);
            Status status = new Status();
            status.setStatusCode(smsModel.getStatusCode());
            status.setStatusName(smsModel.getStatusName());
            sms.setStatus(status);
            sms.setThingCode(smsModel.getThingCode());
            sms.setThingName(smsModel.getThingName());
            if (smsModel.getBeginTime() != null && !smsModel.getBeginTime().isEmpty()) {
                sms.setBeginTime(DateUtil.StringToTimestampSecond(smsModel.getBeginTime()));
            }
            if (smsModel.getEndTime() != null && !smsModel.getEndTime().isEmpty()) {
                sms.setEndTime(DateUtil.StringToTimestampSecond(smsModel.getEndTime()));
            }
            sms.setEffectiveFlag(smsModel.getEffectiveFlag());
            if(!StringUtils.isEmpty(smsModel.getReceiveFlag())){
                sms.setReceiveFlag(Integer.valueOf(smsModel.getReceiveFlag()));
            }
            // 获取当前操作者并设置
            UserModel loginuser = (UserModel) httpsession.getAttribute(DefaultArgument.LOGIN_USER);
            if (loginuser != null) {
                sms.setOptUser(loginuser.getUserId());
            }
            sms.setRowCount(smsModel.getRows());
            sms.setRowIndex((smsModel.getPage() - 1) * smsModel.getRows());
        }
        return sms;
    }

    /**
     * <p>[功能描述]：Sms转换为SmsModel</p>
     *
     * @param sms
     * @return
     * @author 王垒, 2018年4月13日下午1:33:44
     * @since EnvDust 1.0.0
     */
    private SmsModel ConvertSmsModel(Sms sms) {
        SmsModel smsModel = new SmsModel();
        if (sms != null) {
            smsModel.setSmsId(String.valueOf(sms.getSmsId()));
            if (sms.getDevice() != null) {
                smsModel.setDeviceId(String.valueOf(sms.getDevice().getDeviceId()));
                smsModel.setDeviceCode(sms.getDevice().getDeviceCode());
                smsModel.setDeviceName(sms.getDevice().getDeviceName());
            }
            if (sms.getArea() != null) {
                smsModel.setAreaId(String.valueOf(sms.getArea().getAreaId()));
                smsModel.setAreaName(sms.getArea().getAreaName());
            }
            if (sms.getStatus() != null) {
                smsModel.setStatusCode(sms.getStatus().getStatusCode());
                smsModel.setStatusName(sms.getStatus().getStatusName());
            }
            smsModel.setThingCode(sms.getThingCode());
            smsModel.setThingName(sms.getThingName());
            smsModel.setBeginTime(DateUtil.TimestampToString(sms.getBeginTime(), DateUtil.DATA_TIME));
            smsModel.setEndTime(DateUtil.TimestampToString(sms.getEndTime(), DateUtil.DATA_TIME));
            smsModel.setEffectiveFlag(sms.getEffectiveFlag());
            smsModel.setReceiveFlag(String.valueOf(sms.getReceiveFlag()));
            smsModel.setOptUserName(userService.getUserNameById(sms.getOptUser(), null));
            smsModel.setOptTime(DateUtil.TimestampToString(sms.getOptTime(), DateUtil.DATA_TIME));
        }
        return smsModel;
    }

}
