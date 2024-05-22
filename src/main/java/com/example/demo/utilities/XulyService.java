package com.example.demo.utilities;

import com.example.demo.entity.xuly;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Component
@Service
public interface XulyService{
	List<xuly> findAll();
	List<xuly> searchMaTV(String maTV);

}