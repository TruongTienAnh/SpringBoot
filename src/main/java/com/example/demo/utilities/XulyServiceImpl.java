package com.example.demo.utilities;

import com.example.demo.entity.xuly;
import com.example.demo.repository.xulyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Component
@Service 
public class XulyServiceImpl implements XulyService{
	@Autowired
	private xulyRepository xulyRepository;
	@Override
	public List<xuly> findAll() {
		return xulyRepository.findAll();
	}
	@Override
	public List<xuly> searchMaTV(String maTV){
		return this.xulyRepository.searchMaTV(maTV);
	}
}