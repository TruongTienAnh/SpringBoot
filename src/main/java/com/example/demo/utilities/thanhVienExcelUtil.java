package com.example.demo.utilities;

import com.example.demo.entity.ThanhVien;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class thanhVienExcelUtil {
    public static List<ThanhVien> convertToThanhVienList(List<List<String>> data) {
        List<ThanhVien> thanhVienList = new ArrayList<>();
        DecimalFormat decimalFormat = new DecimalFormat("0");

        // Ensure data has at least two rows (one header row and one data row)
        if (data.size() <= 1) {
            throw new IllegalArgumentException("Input data is insufficient: at least one data row is required.");
        }

        for (int i = 1; i < data.size(); i++) {
            List<String> row = data.get(i);

            // Check if row has enough columns (at least 7 columns)
            if (row.size() < 7) {
                // Log the insufficient columns error, but do not throw an exception
                System.err.println("Row " + (i + 1) + " has insufficient columns. Expected 7 columns, found " + row.size() + ". Skipping this row.");
                continue;
            }

            int id;
            try {
                String idString = row.get(0);
                if (idString.contains(".")) {
                    double idDouble = Double.parseDouble(idString);
                    idString = decimalFormat.format(idDouble);
                }
                id = Integer.parseInt(idString);
            } catch (NumberFormatException e) {
                System.err.println("Invalid integer value in row " + (i + 1) + ": " + row.get(0) + ". Skipping this row.");
                continue;
            }

            String hoTen = row.get(1);
            String khoa = row.get(2);
            String nganh = row.get(3);
            String sdt = row.get(4);
            String password = row.get(5);  // No conversion needed if already in plaintext
            String email = row.get(6);

            ThanhVien model = new ThanhVien(id, hoTen, khoa, nganh, sdt, password, email);
            thanhVienList.add(model);
        }

        return thanhVienList;
    }
}
