package com.example.demo.utilities;

import com.example.demo.entity.ThietBi;
import org.springframework.data.domain.Page;

public interface TTSDService {
    Page<ThietBi> listAll(int PageNum);
}
