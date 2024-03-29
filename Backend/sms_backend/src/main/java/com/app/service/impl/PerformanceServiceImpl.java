package com.app.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.PerformanceDao;
import com.app.dao.StudentDao;
import com.app.dao.SubjectDao;
import com.app.dto.performance.PerforDto;
import com.app.entities.secondary.Performance;
import com.app.service.PerformanceService;

@Service
@Transactional
public class PerformanceServiceImpl implements PerformanceService{
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private PerformanceDao perforDao;
	@Autowired
	private StudentDao studDao;
	@Autowired
	private SubjectDao subDao;
	
	@Override
	public List<PerforDto> getPerforList() {
		return perforDao.findAll()
							.stream()
							.map((perforEnt)->
												{
													PerforDto perforDto=mapper.map(perforEnt, PerforDto.class);
													perforDto.setStudId(perforEnt.getStudent().getStudId());
													perforDto.setSubId(perforEnt.getSubject().getSubId());
													return perforDto;
												})
							.collect(Collectors.toList());
	}
	
	@Override
	public Performance addPerfor(Long studId,Long subId, PerforDto perforDto) {
		Performance perforEnt=mapper.map(perforDto, Performance.class);
		perforEnt.setStudent(studDao.findById(studId).orElseThrow());
		perforEnt.setSubject(subDao.findById(subId).orElseThrow());
		return perforDao.save(perforEnt);
	}

	@Override
	public Performance updatePerfor(Long perforId, PerforDto perforDto) {
		Performance perforOld=perforDao.findById(perforId).orElseThrow();
		Performance perforNew=mapper.map(perforDto, Performance.class);
		perforNew.setPerfId(perforOld.getPerfId());
		perforNew.setStudent(perforOld.getStudent());
		perforNew.setSubject(perforOld.getSubject());
		return perforDao.save(perforNew);
	}

	@Override
	public void deletePerfor(Long perforId) {
		perforDao.deleteById(perforId);
		perforDao.flush();
	}
}
