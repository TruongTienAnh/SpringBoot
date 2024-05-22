package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "xuly")

public class xuly {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaXL",nullable = false, unique = true, insertable = true)
    private int maXL;
    @Column(name = "MaTV")
    private int maTV;

    @Column(name = "HinhThucXL")
    private String hinhThucXL;

    @Column(name = "SoTien")
    private Integer  soTien;

    @Column(name = "NgayXL")
    private String ngayXL;

    @Column (name = "TrangThaiXL")
    private Integer trangThaiXL;

    public int getMaXL() {
        return maXL;
    }

    public void setMaXL(int maXL) {
        maXL = maXL;
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        maTV = maTV;
    }

    public String getHinhThucXL() {
        return hinhThucXL;
    }

    public void setHinhThucXL(String hinhThucXL) {
        hinhThucXL = hinhThucXL;
    }

    public Integer getSoTien() {
        return soTien;
    }

    public void setSoTien(Integer soTien) {
        soTien = soTien;
    }

    public String getNgayXL() {
        return ngayXL;
    }

    public void setNgayXL(String ngayXL) {
        ngayXL = ngayXL;
    }

    public int getTrangThaiXL() {
        return trangThaiXL;
    }

    public void setTrangThaiXL(int trangThaiXL) {
        trangThaiXL = trangThaiXL;
    }
    public xuly() {
    }
    public xuly(int maXL, int maTV, String hinhThucXL, int soTien, String ngayXL, int trangThaiXL){
        this.maXL = maXL;
        this.maTV = maTV;
        this.hinhThucXL = hinhThucXL;
        this.soTien = soTien;
        this.ngayXL = ngayXL;
        this.trangThaiXL = trangThaiXL;
    }
}
