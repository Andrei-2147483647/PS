package com.example.demo.factory;

import com.example.demo.DAO.OrderDAO;
import com.example.demo.domain.Order;
import com.example.demo.domain.Product;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

public class ExcelExporter implements Exporter{

    @Override
    public byte[] export(List<Order> orders) {
        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Orders");


            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Order ID");
            headerRow.createCell(1).setCellValue("Order Date");
            headerRow.createCell(2).setCellValue("Order Products");

            int rowNum = 1;
            for (Order order : orders) {

                Row dataRow = sheet.createRow(rowNum++);
                dataRow.createCell(0).setCellValue(order.getId());
                dataRow.createCell(1).setCellValue(order.getDate());

                StringBuilder s = new StringBuilder();
                for (Product product : OrderDAO.findOrderById(order.getId())) {
                    s.append(product.getName());
                    s.append(" - ");
                    s.append(String.valueOf(product.getStock()));
                }
                dataRow.createCell(2).setCellValue(s.toString());
            }


            int columnCount = sheet.getRow(0).getLastCellNum();
            for (int col = 0; col < columnCount; col++) {
                sheet.autoSizeColumn(col);
            }


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
