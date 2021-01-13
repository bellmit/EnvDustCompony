package com.tcb.env.service.impl;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.tcb.env.dao.ITestDao;
import com.tcb.env.service.ITestService;

@Service("testService")  
class TestServiceImpl implements ITestService {

	@Resource
	private ITestDao testDao;
	
	@Override
	public String getTest() {
		// TODO Auto-generated method stub
		return testDao.getTest();
	}

}
