package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tcb.env.pojo.Oranization;



/**
 * <p>[功能描述]：组织dao</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年3月17日下午3:27:30
 * @since	EnvDust 1.0.0
 * 
 */
public interface IOranizationDao {
	//传入的user名字在xml中名字为user
		/**
		 * <p>[功能描述]：查询数据个数是否模糊查询用到获方法</p>
		 * 
		 * @author	任崇彬, 2016年3月25日下午2:13:03
		 * @since	EnvDust 1.0.0
		 *
		 * @param oranization
		 * @param ignoredel
		 * @return 
		 */
		public int getCount(@Param("oranization")Oranization oranization,@Param("isLike")boolean isLike);
		/**
		 * <p>[功能描述]：查询组织数据</p>
		 * 
		 * @author	任崇彬, 2016年3月25日下午2:17:05
		 * @since	EnvDust 1.0.0
		 *
		 * @param oranization
		 * @return 
		 */
		public List<Oranization> getOranization(@Param("oranization")Oranization oranization);
		/**
		 * <p>[功能描述]：增加组织数据</p>
		 * 
		 * @author	任崇彬, 2016年3月25日下午2:17:20
		 * @since	EnvDust 1.0.0
		 *
		 * @param listoranization
		 * @return 
		 */
		public int insertOranization(@Param("oranization")Oranization oranization);
		/**
		 * <p>[功能描述]：删除组织数据</p>
		 * 
		 * @author	任崇彬, 2016年3月25日下午2:17:32
		 * @since	EnvDust 1.0.0
		 *
		 * @param oranizationid
		 * @return 
		 */
		public int deleteOranization(@Param("oranizationid")List<Integer> oranizationid);
		/**
		 * <p>[功能描述]：更新组织数据</p>
		 * 
		 * @author	任崇彬, 2016年3月25日下午2:17:41
		 * @since	EnvDust 1.0.0
		 *
		 * @param list
		 * @return 
		 */
		public int updateOranization(@Param("list")List<Oranization> list);
		/**
		 * <p>[功能描述]：判断组织数据是否存在</p>
		 * 
		 * @author	任崇彬, 2016年3月25日下午2:17:49
		 * @since	EnvDust 1.0.0
		 *
		 * @param oranizationid
		 * @param oranizationname
		 * @return 
		 */
		public int getOranizationExist(@Param("oranizationid")int oranizationid,@Param("oranizationname")String oranizationname);
		/**
		 * <p>[功能描述]：通过ID得到组织路径</p>
		 * 
		 * @author	任崇彬, 2016年3月25日下午2:18:02
		 * @since	EnvDust 1.0.0
		 *
		 * @param orgid
		 * @return 
		 */
		public String getOranizationPathById(@Param("orgid")int orgid);
}
