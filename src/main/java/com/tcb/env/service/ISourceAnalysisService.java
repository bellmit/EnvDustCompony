package com.tcb.env.service;

import com.tcb.env.model.SourceAnalysisResultModel;

public interface ISourceAnalysisService {

    SourceAnalysisResultModel getPollutionSourceData(int areaId, double longitude, double latitude, String datetime, double radius);
}
