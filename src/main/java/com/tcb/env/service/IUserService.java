package com.tcb.env.service;

import java.util.List;

import com.tcb.env.pojo.User;

/**
 * <p>[功能描述]：User操作服务类接口</p>
 * <p>Copyright (c) 1993-2016 TCB Corporation</p>
 *
 * @author 王垒
 * @version 1.0, 2016年3月17日上午11:32:41
 * @since EnvDust 1.0.0
 */
public interface IUserService {

    /**
     * <p>[功能描述]：根据查询条件查询结果个数</p>
     *
     * @param user：对象参数
     * @param ignoredel：是否忽略删除标记
     * @return
     * @author 王垒, 2016年3月18日下午1:43:26
     * @since EnvDust 1.0.0
     */
    int getCount(User user, boolean ignoredel);

    /**
     * <p>[功能描述]：根据条件查询User数据</p>
     *
     * @param user：对象参数
     * @param ignoredel：是否忽略删除标记
     * @return
     * @author 王垒, 2016年3月17日上午11:33:00
     * @since EnvDust 1.0.0
     */
    List<User> getUser(User user, boolean ignoredel);

    /**
     * <p>[功能描述]：新增User数据</p>
     *
     * @param listuser
     * @return
     * @throws Exception
     * @author 王垒, 2016年3月18日上午8:40:03
     * @since EnvDust 1.0.0
     */
    int insertUser(List<User> listuser) throws Exception;

    /**
     * <p>[功能描述]：更新user数据</p>
     *
     * @param listuser
     * @return
     * @throws Exception
     * @author 王垒, 2016年3月18日上午8:40:47
     * @since EnvDust 1.0.0
     */
    int updateUser(List<User> listuser) throws Exception;

    /**
     * <p>[功能描述]：物理删除User数据</p>
     *
     * @param listid
     * @return
     * @throws Exception
     * @author 王垒, 2016年3月18日上午8:41:25
     * @since EnvDust 1.0.0
     */
    int deleteUser(List<String> listid) throws Exception;

    /**
     * <p>[功能描述]：更新用户删除标识</p>
     *
     * @param userId
     * @param userDelete
     * @return
     * @throws Exception
     * @author 王垒, 2016年3月21日下午2:40:54
     * @since EnvDust 1.0.0
     */
    int updateUserDelete(String userId, String userDelete) throws Exception;

    /**
     * <p>[功能描述]：根据id获取用户名</p>
     *
     * @param userid
     * @param usercode
     * @return
     * @author 王垒, 2016年3月21日下午11:01:12
     * @since EnvDust 1.0.0
     */
    String getUserNameById(int userid, String usercode);

    /**
     * <p>[功能描述]：根据id获取用编码</p>
     *
     * @param userId
     * @return
     * @author 王垒, 2016年3月21日下午11:01:12
     * @since EnvDust 1.0.0
     */
    String getUserCodeById(String userId);

    /**
     * <p>[功能描述]：通过条件（非ID的）获取符合结果个数</p>
     *
     * @param userid：不等于条件
     * @param usercode：等于条件
     * @return
     * @author 王垒, 2016年3月22日上午8:54:55
     * @since EnvDust 1.0.0
     */
    int getUserExist(int userid, String usercode);

    /**
     * <p>[功能描述]：更新用户密码</p>
     *
     * @param userid
     * @param userpwd
     * @return
     * @author 王垒, 2016年3月22日上午11:05:19
     * @since EnvDust 1.0.0
     */
    int updateUserPwd(int userid, String userpwd);

}
