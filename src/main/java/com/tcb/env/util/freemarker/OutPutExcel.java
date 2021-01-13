package com.tcb.env.util.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.tcb.env.util.DefaultArgument;

import freemarker.template.Configuration;
import freemarker.template.Template;

/**
 * 
 * <p>[功能描述]：导出excel工具类</p>
 * <p>Copyright (c) 1997-2017 TCB Corporation</p>
 * 
 * @author	王垒
 * @version	1.0, 2017年2月27日上午11:49:58
 * @since	EnvDust 1.0.0
 *
 */
public class OutPutExcel {

	private Map<String,Template> getTemplateByType(String type){
		String templateName = null;
		if(type != null && type.equals(DefaultArgument.REPORT_ENVSTA_DMY)){
			templateName = "envStaReportDMYExcel.ftl";
		}else if(type != null && type.equals(DefaultArgument.REPORT_ENVSTA_TIMES)){
			templateName = "envStaReportTimesExcel.ftl";
		}else if(type != null && type.equals(DefaultArgument.REPORT_ENVSTA_OLRD)){
			templateName = "envStaReportOlrDayExcel.ftl";
		}else if(type != null && type.equals(DefaultArgument.REPORT_ENVSTA_OLRM)){
			templateName = "envStaReportOlrMonthExcel.ftl";
		}else if(type != null && type.equals(DefaultArgument.REPORT_ENVSTA_OLRY)){
			templateName = "envStaReportOlrYearExcel.ftl";
		}else if(type != null && type.equals(DefaultArgument.REPORT_ENVSTA_OLRT)){
			templateName = "envStaReportOlrTimesExcel.ftl";
		}else if(type != null && type.equals(DefaultArgument.REPORT_ENVSTA_COMPARE)){
			templateName = "dataCompare.ftl";
		}else if(type != null && type.equals(DefaultArgument.REPORT_ENVSTA_OVER)){
			templateName = "overAlarmReport.ftl";
		}else if(type != null && type.equals(DefaultArgument.REPORT_ENVSTA_DIS)){
			templateName = "envDischargeReport.ftl";
		}
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_28);
		configuration.setDefaultEncoding("UTF-8");
		configuration.setClassForTemplateLoading(this.getClass(), "/templates/freemarker/");
		Map<String,Template> allTemplate = new HashMap<String,Template>();
		try{
			allTemplate.put(type, configuration.getTemplate(templateName));
		}catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return allTemplate;
	}

	public File createExcel(String type,Map<?,?> dataMap){
		String name = "report"+(int)(Math.random()*100000)+".xlsx";
		File f = new File(name);
		Template t = getTemplateByType(type).get(type);
		try{
			//这个地方不能使用FileWriter因为需要指定编码类型否则声场的word文档会因为有无法识别的编码而无法打开
			Writer w = new OutputStreamWriter(new FileOutputStream(f),"utf-8");
			t.process(dataMap,w);
			w.close();
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return f;
	}

}
