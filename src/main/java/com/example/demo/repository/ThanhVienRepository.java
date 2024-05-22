    package com.example.demo.repository;

    import com.example.demo.entity.ThanhVien;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.data.jpa.repository.Query;
    import org.springframework.stereotype.Repository;

    import java.util.List;

    @Repository
    public interface ThanhVienRepository extends JpaRepository<ThanhVien, Integer> {
        ThanhVien findByMaTV(int maTV);
        ThanhVien findByEmail(String email);
        ThanhVien findBySdt(String sdt);
        ThanhVien findByMaTVAndPassword(int maTV, String password);
        ThanhVien findByHoTen(String hoTen);
        ThanhVien findByHoTenContainingIgnoreCase(String hoTen);

        void deleteByMaTVIn(List<Integer> maTVs);
        @Query("SELECT tv FROM ThanhVien tv WHERE " +
                "CAST(tv.maTV AS string) LIKE CONCAT('%', :keyword, '%') " +
                "OR tv.hoTen LIKE CONCAT('%', :keyword, '%') " +
                "OR tv.khoa LIKE CONCAT('%', :keyword, '%') " +
                "OR tv.nganh LIKE CONCAT('%', :keyword, '%') " +
                "OR tv.sdt LIKE CONCAT('%', :keyword, '%') " +
                "OR tv.email LIKE CONCAT('%', :keyword, '%')")
        List<ThanhVien> findByKeyword(String keyword);



    }