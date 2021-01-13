package com.tcb.env.service.impl;

import com.tcb.env.dao.IDeviceDao;
import com.tcb.env.dao.IWeatherDao;
import com.tcb.env.model.LocationModel;
import com.tcb.env.model.PollutionSourceModel;
import com.tcb.env.model.SourceAnalysisResultModel;
import com.tcb.env.service.ISourceAnalysisService;
import com.tcb.env.util.algorithm.diffusion.LatLngUtils;
import com.tcb.env.util.algorithm.diffusion.SamplingPoint;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
class SourceAnalysisServiceImpl implements ISourceAnalysisService {

    private final static double DEFAULT_VALUE = 0.0;

    private final static double DEFAULT_HEIGHT = 0.0;

    private final static double THRESHOLD_DEGREE = 30;
    private final static double THRESHOLD_DENSITY = 0.000001;

    private final static String TOTAL_CLOUD = "totalCloud";
    private final static String MIN_CLOUD = "minCloud";

    private final static String WIND_SPEED = "windSpeed";
    private final static String WIND_DEGREE = "windDeg";
    private final static String WIND_SC = "windSc";
    private final static int MAX_WIND_SC = 7;

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm";

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Comparator<SamplingPoint> distanceComparator = new Comparator<SamplingPoint>() {
        @Override
        public int compare(SamplingPoint point1, SamplingPoint point2) {
            return (int) Math.round((point2.getDistance()-point1.getDistance())*1000);
        }
    };

    @Resource
    private IWeatherDao weatherDao;
    @Resource
    private IDeviceDao deviceDao;


    @Override
    public SourceAnalysisResultModel getPollutionSourceData(int areaId, double longitude, double latitude, String datetime,double radius) {
        Date date = getDateFromDateStr(datetime);
        Map<String, Double> cloudData = getCloudData(areaId, date);
        Map<String, Double> windData = getWindData(areaId, datetime);
        List<List<Integer>> windRoseData = getWindRoseData(areaId, datetime);

        List<LocationModel> locationModels = deviceDao.getDeviceTvocData(areaId,timeMinusOneDay(date),datetime);

        List<SamplingPoint> samplingPoints = new ArrayList<>();
        double windDeg = windData.get(WIND_DEGREE);
        for(LocationModel point:locationModels){
            double degree = LatLngUtils.angleBetweenPoint( longitude, latitude, point.getLng(),point.getLat());
            double distance = LatLngUtils.distanceBetweenPoint(point.getLng(),point.getLat(), longitude, latitude);
            if(Math.abs(degree-windDeg) <= THRESHOLD_DEGREE && distance < radius){

                SamplingPoint samplingPoint = new SamplingPoint(point.getCode(),point.getInfo(),point.getLat(),point.getLng());
                Calendar calendar = DateUtils.toCalendar(date);
                int year = calendar.get(Calendar.DAY_OF_YEAR);
                double hour = calendar.get(Calendar.HOUR_OF_DAY)*1.0+calendar.get(Calendar.MINUTE)/60.0;
                samplingPoint.setParamsValue(year,hour,cloudData.get(TOTAL_CLOUD),cloudData.get(MIN_CLOUD),windData.get(WIND_SPEED)/3.6,windData.get(WIND_DEGREE),point.getVal());
                samplingPoint.setDistance(distance);
                samplingPoints.add(samplingPoint);
            }
        }
        SourceAnalysisResultModel result = new SourceAnalysisResultModel();
        //result.setPollutionSources(calResult(samplingPoints,longitude,latitude));
        result.setPollutionSources(calResultWithoutInterference(samplingPoints,longitude,latitude));//排除干扰的方法
        result.setWindDegree(windDeg);
        result.setWindSpeed(windData.get(WIND_SPEED));
        result.setWindSc(windData.get(WIND_SC).intValue());
        result.setWindRoseData(windRoseData);
        return result;
    }

    /**
     * 计算并生成结果
     * @param samplingPoints
     * @param longitude
     * @param latitude
     * @return PollutionSourceModel实体的集合
     */
    private List<PollutionSourceModel> calResult(List<SamplingPoint> samplingPoints, double longitude, double latitude){
        List<PollutionSourceModel> result = new ArrayList<>();
        double totalDensity = 0.0;
        for(SamplingPoint point:samplingPoints){
            try {
                double density = point.toPointDensity(longitude,latitude,DEFAULT_HEIGHT);
                if(density > THRESHOLD_DENSITY) {
                    PollutionSourceModel model = new PollutionSourceModel();
                    model.setDeviceCode(point.getDeviceCode());
                    model.setDeviceName(point.getDeviceName());
                    model.setLatitude(point.getLatitude());
                    model.setLongitude(point.getLongitude());
                    totalDensity += density;
                    model.setDensity(density);
                    result.add(model);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<result.size();i++){
            result.get(i).calculatePercent(totalDensity);
        }
        return result;
    }

    /**
     * 计算并生成结果（排除干扰的方法）
     * @param samplingPoints
     * @param longitude
     * @param latitude
     * @return PollutionSourceModel实体的集合
     */
    private List<PollutionSourceModel> calResultWithoutInterference(List<SamplingPoint> samplingPoints, double longitude, double latitude){
        Collections.sort(samplingPoints,distanceComparator);
        List<PollutionSourceModel> result = new ArrayList<>();
        double totalDensity = 0.0;
        for(int i=0;i<samplingPoints.size();i++){
            try {
                SamplingPoint point = samplingPoints.get(i);
                for(int j=0;j<i;j++){
                    samplingPoints.get(i).minusQInterference(samplingPoints.get(j).toPointDensity(point.getLongitude(),point.getLatitude(),DEFAULT_HEIGHT));
                }
                double density = point.toPointDensity(longitude,latitude,DEFAULT_HEIGHT);
                if(density > THRESHOLD_DENSITY) {
                    PollutionSourceModel model = new PollutionSourceModel();
                    model.setDeviceCode(point.getDeviceCode());
                    model.setDeviceName(point.getDeviceName());
                    model.setLatitude(point.getLatitude());
                    model.setLongitude(point.getLongitude());
                    totalDensity += density;
                    model.setDensity(density);
                    result.add(model);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        for(int i=0;i<result.size();i++){
            result.get(i).calculatePercent(totalDensity);
        }
        return result;
    }


    /**
     * 获取风速风向信息
     * @param areaId
     * @param datetime
     * @return
     */
    private Map<String, Double> getWindData(int areaId, String datetime) {
        Map<String, Double> result = new HashMap<>(2);
        List<Map<String, Object>> resultList = weatherDao.getWindDataByDay(areaId, datetime);
        if (resultList != null && resultList.size() > 0 && resultList.get(0) != null) {
            result.put(WIND_DEGREE, Double.parseDouble(resultList.get(0).get(WIND_DEGREE)+""));
            result.put(WIND_SPEED, Double.parseDouble(resultList.get(0).get(WIND_SPEED)+""));
            result.put(WIND_SC, Double.parseDouble(resultList.get(0).get(WIND_SC)+""));
        } else {
            result.put(WIND_DEGREE, DEFAULT_VALUE);
            result.put(WIND_SPEED, DEFAULT_VALUE);
            result.put(WIND_SC, DEFAULT_VALUE);
        }
        return result;
    }

    private List<List<Integer>> getWindRoseData(int areaId, String datetime) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i <= MAX_WIND_SC; i++) {
            result.add(getRoseWindData(areaId, datetime, i, 1));
        }
        return result;
    }

    /**
     * 获取云量信息
     * @param areaId
     * @param datetime
     * @return
     */
    private Map<String, Double> getCloudData(int areaId, Date datetime) {
        String beginTime = timeMinusOneDay(datetime);
        Map<String, Double> result = new HashMap<>(2);
        List<Map<String, Object>> resultList = weatherDao.getCloudDataByDay(areaId, beginTime, sdf.format(datetime));
        if (resultList != null && resultList.size() > 0 && resultList.get(0) != null) {
            result.put(TOTAL_CLOUD, Double.parseDouble(resultList.get(0).get(TOTAL_CLOUD)+""));
            result.put(MIN_CLOUD, Double.parseDouble(resultList.get(0).get(MIN_CLOUD)+""));
        } else {
            result.put(TOTAL_CLOUD, DEFAULT_VALUE);
            result.put(MIN_CLOUD, DEFAULT_VALUE);
        }
        return result;
    }

    private Date getDateFromDateStr(String datetime) {
        Date date = null;
        try {
            date = DateUtils.parseDate(datetime, DATE_FORMAT);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 时间往前倒退一小时
     *
     * @param datetime
     * @return
     * @throws ParseException
     */
    private String timeMinusOneHour(Date datetime) {
        Date date = DateUtils.addHours(datetime, -1);
        return sdf.format(date);
    }

    /**
     * 时间往前倒退一天
     *
     * @param datetime
     * @return
     * @throws ParseException
     */
    private String timeMinusOneDay(Date datetime) {
        Date date = DateUtils.addDays(datetime, -1);
        return sdf.format(date);
    }

    private List<Integer> getRoseWindData(int areaId, String dateTime, int windSc, int days) {
        List<Integer> resultList = new ArrayList<>();
        List<Integer> result = weatherDao.getRoseWindData(areaId, dateTime, windSc, days);
        int objNum = 1;
        int maxNum = 8;
        resultList.add(Collections.frequency(result, 0) + Collections.frequency(result, maxNum));
        while (objNum < maxNum) {
            resultList.add(Collections.frequency(result, objNum));
            objNum++;
        }
        return resultList;
    }
}
