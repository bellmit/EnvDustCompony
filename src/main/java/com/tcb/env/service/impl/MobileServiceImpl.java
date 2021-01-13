package com.tcb.env.service.impl;

import com.tcb.env.dao.IMobileDao;
import com.tcb.env.model.MobileMonitorModel;
import com.tcb.env.model.MobilePointModel;
import com.tcb.env.service.IMobileService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: WangLei
 * @Description: 移动端服务接口实现类
 * @Date: Create in 2019/4/10 14:21
 * @Modify by WangLei
 */
@Service("mobileService")
class MobileServiceImpl implements IMobileService {

    @Resource
    private IMobileDao mobileDao;

    @Override
    public List<MobilePointModel> getMobilePoint(List<String> deviceCodeList, String thingCode, String dataType, String levelNo) {
        return mobileDao.getMobilePoint(deviceCodeList, thingCode, dataType, levelNo);
    }

    @Override
    public MobilePointModel getPointRecentData(String deviceCode, String dataType) {
        List<MobilePointModel> mobilePointModelList = mobileDao.getPointRecentData(deviceCode, dataType);
        if (mobilePointModelList != null && mobilePointModelList.size() > 0) {
            return mobilePointModelList.get(mobilePointModelList.size() - 1);
        } else {
            return null;
        }
    }

    @Override
    public MobilePointModel getPointsRecentData(List<String> deviceCodeList, List<String> thingCodeList, String dataType) {
        MobilePointModel mobilePointModel = new MobilePointModel();
        mobilePointModel = mobileDao.getPointsRecentData(deviceCodeList, thingCodeList, dataType);
        if (mobilePointModel != null && mobilePointModel.getPointListData() != null
                && mobilePointModel.getPointListData().size() > 0) {
            List<MobileMonitorModel> mobileMonitorModelList = mobilePointModel.getPointListData();
            for (MobileMonitorModel temp : mobileMonitorModelList) {
                String thingValue = temp.getThingValue();
                if (!StringUtils.isEmpty(thingValue)) {
                    temp = mobileDao.getThingLevel(temp);
                }
            }
        }
        return mobilePointModel;
    }

}
