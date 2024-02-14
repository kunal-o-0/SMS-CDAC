package com.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.subject.SubDto;
import com.app.entities.secondary.Subject;
import com.app.service.SubjectService;

@RestController
@RequestMapping("/subject")
public class SubjectController {
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private SubjectService subService;
	
	@GetMapping
	public List<SubDto> getSubList()
	{
		return subService.getSubList();
	}
	
	@PostMapping("/{courseId}")
	public void addSub(@PathVariable Long courseId,@RequestBody SubDto subDto)
	{
		Subject subEnt=subService.addSub(courseId, subDto);
	}
}
