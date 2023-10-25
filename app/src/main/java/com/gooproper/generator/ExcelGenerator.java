package com.gooproper.generator;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.gooproper.model.ListingModel;

//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelGenerator {
    public static void createExcel(Context context, List<ListingModel> dataList) {
        /*String fileName = "data_excel.xlsx";
        String filePath = Environment.getExternalStorageDirectory().getPath() + "/" + fileName;

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Data");

            // Membuat header
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nama");
            headerRow.createCell(1).setCellValue("Alamat");

            // Menambahkan data ke Excel
            int rowNum = 1;
            for (ListingModel data : dataList) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(data.getIdListing());
                row.createCell(1).setCellValue(data.getNamaListing());
            }

            // Menyimpan file Excel
            FileOutputStream outputStream = new FileOutputStream(new File(filePath));
            workbook.write(outputStream);
            outputStream.close();

            Log.d("ExcelGenerator", "File Excel berhasil dibuat: " + filePath);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ExcelGenerator", "Gagal membuat file Excel: " + e.getMessage());
        }*/
    }
}
