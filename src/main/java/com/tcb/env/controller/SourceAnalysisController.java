package com.tcb.env.controller;

import com.tcb.env.model.PollutionSourceModel;
import com.tcb.env.model.ResultDataModel;
import com.tcb.env.model.SourceAnalysisResultModel;
import com.tcb.env.service.ISourceAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Comparator;

@Controller
@RequestMapping("/SourceAnalysisController")
public class SourceAnalysisController {

    @Autowired
    private ISourceAnalysisService sourceAnalysisService;

    /**
     * 倒序排列的 Comparator
     */
    private Comparator<PollutionSourceModel> descComparator = new Comparator<PollutionSourceModel>() {
        @Override
        public int compare(PollutionSourceModel o1, PollutionSourceModel o2) {
            return (int) Math.round((o2.getDensity()-o1.getDensity())*100000);
        }
    };

    /**
     * 正序排列的 Comparator
     */
    private Comparator<PollutionSourceModel> ascComparator = new Comparator<PollutionSourceModel>() {
        @Override
        public int compare(PollutionSourceModel o1, PollutionSourceModel o2) {
            return (int) Math.round((o1.getDensity()-o2.getDensity())*100000);
        }
    };

    @RequestMapping(value = "/analyzeSource", method = {RequestMethod.POST})
    @ResponseBody
    public ResultDataModel analyzeSource(int areaId, double lng, double lat, String time, double radius) {
        ResultDataModel result = new ResultDataModel();
        if (areaId == 0 || lat == 0 || lng == 0 || time == null || time.isEmpty()) {
            result.setResult(false);
            result.setDetail("传入参数不正确！！！");
            return result;
        } else {
            SourceAnalysisResultModel data = sourceAnalysisService.getPollutionSourceData(areaId, lng, lat, time, radius);
            Collections.sort(data.getPollutionSources(),descComparator);
            result.setData(data);
            result.setResult(true);
            return result;
        }
    }

}
