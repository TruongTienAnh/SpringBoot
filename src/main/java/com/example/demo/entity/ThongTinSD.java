package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "thongtinsd")
public class ThongTinSD {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaTT")
    private int maTT;

    @Column(name = "tgvao")
    private LocalDateTime tgVao;

    @Column(name = "tgmuon")
    private LocalDateTime  tgMuon;

    @Column(name = "tgtra")
    private LocalDateTime tgTra;

    @Column(name = "tgdat_cho")
    private LocalDateTime  tgDatCho;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaTV", insertable = true, updatable = true)
    private ThanhVien thanhVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaTB", insertable = true, updatable = true)
    private ThietBi thietBi;
}
