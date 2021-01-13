package com.tcb.env.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;



import com.tcb.env.pojo.User;

/**
 * <p>[功能描述]：用户数据库操作接口</p>
 * <p>Copyright (c) 1996-2016 TCB Corporation</p>
 * 
 * @author	任崇彬
 * @version	1.0, 2016年3月16日下午2:09:13
 * @since	EnvDust 1.0.0
 * 
 */
public interface IUserDao {
	
	/**
	 * 
	 * <p>[功能描述]：查询结果个数</p>
	 * 
	 * @author	王垒, 2016年3月18日下午1:45:31
	 * @since	EnvDust 1.0.0
	 *
	 * @param user
	 * @return
	 */
	public int getCount(@Param("user")User user,@Param("ignoredel")boolean ignoredel);
	
	/**
	 * 
	 * <p>[功能描述]：查询User数据</p>
	 * 
	 * @author	王垒, 2016年3月18日上午8:42:16
	 * @since	EnvDust 1.0.0
	 *
	 * @param user：对象参数
	 * @param ignoredel：是否忽略删除标记
	 * @return
	 */
	public List<User> getUser(@Param("user")User user,@Param("ignoredel")boolean ignoredel);
	
	/**
	 * 
	 * <p>[功能描述]：新增User数据</p>
	 * 
	 * @author	王垒, 2016年3月18日上午8:42:56
	 * @since	EnvDust 1.0.0
	 *
	 * @param user：对象参数
	 * @param ignoredel：是否忽略删除标记
	 * @return
	 */
	public int insertUser(@Param("listuser")List<User> listuser);
	
	/**
	 * 
	 * <p>[功能描述]：更新User数据</p>
	 * 
	 * @author	王垒, 2016年3月18日上午8:43:34
	 * @since	EnvDust 1.0.0
	 *
	 * @param user
	 * @return
	 */
	public int updateUser(@Param("listuser")List<User> listuser);
	
	/**
	 * 
	 * <p>[功能描述]：物理删除User数据</p>
	 * 
	 * @author	王垒, 2016年3月18日上午8:44:05
	 * @since	EnvDust 1.0.0
	 *
	 * @param idList
	 * @return
	 */
	public int deleteUser(@Param("idList")List<String> idList);
	
	/**
	 * 
	 * <p>[功能描述]：更新用户删除标识</p>
	 * 
	 * @author	王垒, 2016年3月21日下午2:24:56
	 * @since	EnvDust 1.0.0
	 *
	 * @param userId
	 * @param userDelete
	 * @return
	 */
	public int updateUserDelete(@Param("userId")String userId,@Param("userDelete")String userDelete);
	
	/**
	 * 
	 * <p>[功能描述]：通过条件获取名称</p>
	 * 
	 * @author	王垒, 2016年3月21日下午3:35:26
	 * @since	EnvDust 1.0.0
	 *
	 * @param userId
	 * @return
	 */
	public String getUserNameById(@Param("userid")int userid,@Param("usercode")String usercode);
	
	/**
	 * 
	 * <p>[功能描述]：通过ID获取用户编码</p>
	 * 
	 * @author	王垒, 2016年3月21日下午3:35:26
	 * @since	EnvDust 1.0.0
	 *
	 * @param userId
	 * @return
	 */
	public String getUserCodeById(@Param("userId")String userId);
	
	/**
	 * 
	 * <p>[功能描述]：通过条件（非ID的）获取符合结果个数</p>
	 * 
	 * @author	王垒, 2016年3月22日上午8:52:40
	 * @since	EnvDust 1.0.0
	 *
	 * @param userid：不等于条件
	 * @param usercode：等于条件
	 * @return
	 */
	public int getUserExist(@Param("userid")int userid,@Param("usercode")String usercode);
	
	/**
	 * 
	 * <p>[功能描述]：更新用户密码</p>
	 * 
	 * @author	王垒, 2016年3月22日上午11:05:19
	 * @since	EnvDust 1.0.0
	 *
	 * @param userid
	 * @param userpwd
	 * @return
	 */
	public int updateUserPwd(@Param("userid")int userid,@Param("userpwd")String userpwd);
	
}
