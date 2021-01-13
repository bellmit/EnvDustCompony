/**
 * Copyright (c) 1996-2016天津通广集团电子信息部，版权所有，复制必究。
 * 此程序版权归天津通广集团电子信息部所有，任何侵犯版权的行为将被追究
 * 法律责任。未经天津通广集团电子信息部的书面批准，不得将此程序的任何
 * 部分以任何形式、采用任何手段、或为任何目的，进行复制或扩散。
 */
package com.tcb.env.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcb.env.dao.IManufacturerDao;
import com.tcb.env.pojo.Manufacturer;
import com.tcb.env.service.IManufacturerService;

/**
 * <p>
 * [功能描述]：
 * </p>
 * <p>
 * Copyright (c) 1996-2016 TCB Corporation
 * </p>
 * 
 * @author 任崇彬
 * @version 1.0, 2016年3月24日上午9:30:54
 * @since EnvDust 1.0.0
 * 
 */
@Service("manufacturerService")
@Transactional(rollbackFor = Exception.class)
class ManufacturerServiceImpl implements IManufacturerService {
	@Resource
	private IManufacturerDao manufacturerDao;

	@Override
	public int getCount(Manufacturer manufacturer, boolean isLike) {

		return manufacturerDao.getCount(manufacturer, isLike);
	}

	@Override
	public List<Manufacturer> getManufacturer(Manufacturer manufacturer) {

		return manufacturerDao.getManufacturer(manufacturer);
	}

	@Override
	public int insertManufacturer(List<Manufacturer> listmanufacturer) throws Exception {

		return manufacturerDao.insertManufacturer(listmanufacturer);
	}

	@Override
	public int deleteManufacturer(List<Integer> manufacturerid) throws Exception  {

		return manufacturerDao.deleteManufacturer(manufacturerid);
	}

	@Override
	public int updateManufacturer(List<Manufacturer> listmanufacturer) throws Exception  {

		return manufacturerDao.updateManufacturer(listmanufacturer);
	}

	@Override
	public int getManufacturerExist(int manufacturerid, String manufacturercode) {

		return manufacturerDao.getManufacturerExist(manufacturerid,
				manufacturercode);
	}

}
