package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name = "thietbi")
public class ThietBi {
    @Id
    @Column(name = "MaTB", unique = true)
    private int maTB;

    @Column(name = "TenTB")
    private String tenTB;

    @Column(name = "mo_ta")
    private String moTa;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "thietBi")
    private List<ThongTinSD> listTTSD;
}
