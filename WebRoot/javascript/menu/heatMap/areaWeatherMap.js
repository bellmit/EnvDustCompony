/************************************
 * 功能：实时统计
 * 日期：2016-3-24 09:43:09
 ************************************/
var appendAreaWeatherMapcontent = '<iframe id="ifmAreaWeather" src = "https://embed.windy.com/embed2.html?zoom=5&level=surface&overlay=wind&menu=&message=&marker=&calendar=true&pressure=&type=map&location=coordinates&detail=&metricWind=default&metricTemp=default&radarRange=-1" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>'
addPanel("区域气象分布图", appendAreaWeatherMapcontent);