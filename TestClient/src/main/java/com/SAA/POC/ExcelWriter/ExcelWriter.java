package com.SAA.POC.ExcelWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.*;

public class ExcelWriter {

    private static Sheet sheet = null;
    private static Cell cell = null;
    private static String path = "./src/Test_result.xls";
    private static FileInputStream inputStream = null;
    private static Workbook workbook = null;
    private static int rowNumber=2;

    public static void init() {

        try {
            inputStream = new FileInputStream(new File(path));
            workbook = WorkbookFactory.create(inputStream);

            sheet = workbook.getSheetAt(0);

        } catch (IOException e) {
            System.out.println("Couldn't initiate write to Test_results file.");
            e.printStackTrace();
        }

    }

    public static void writeToFile() {
        try {
            inputStream.close();
            FileOutputStream outputStream = new FileOutputStream(path);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

        } catch (IOException e) {
            System.out.println("Error while performing final write to file");
            e.printStackTrace();
        }
    }

    public static void writeResults(ArrayList<Long> JSONResults, ArrayList<Long> ProtobuffResults, boolean fixed) {
        //System.out.println("JSON results" + JSONResults.size());
        // System.out.println("protobuff results" + ProtobuffResults.size());
        // 105 rows

        int iterations;
        int index = 0;
        int increment;
        if (fixed == true) {
            iterations = 10301;
            increment = 0;
        } else {
            iterations = 1;
            increment = 100;
        }
        long protoSum = 0, jsonSum = 0;

        for (rowNumber = 2; rowNumber < JSONResults.size() + 2; rowNumber++) {
            sheet.createRow(rowNumber + 1);
            Row row = sheet.getRow(rowNumber + 1);
            row.createCell(0);
            row.createCell(1);
            row.createCell(2);
            Cell cell = row.getCell(0);
            cell.setCellValue((int) iterations);
            cell = row.getCell(1);
            cell.setCellValue(JSONResults.get(index));
            jsonSum += JSONResults.get(index);
            cell = row.getCell(2);
            cell.setCellValue(ProtobuffResults.get(index));
            protoSum += ProtobuffResults.get(index);
            index++;
            iterations += increment;

        }

        float jsonAvg = (jsonSum / JSONResults.size());
        float protoAvg = (protoSum / ProtobuffResults.size());
        sheet.createRow(rowNumber + 1);
        Row row = sheet.getRow(rowNumber + 1);
        row.createCell(0);
        row.createCell(1);
        row.createCell(2);
        Cell cell = row.getCell(0);
        cell.setCellValue("Avg response time in ms");
        cell = row.getCell(1);

        cell.setCellValue(jsonAvg);
        cell = row.getCell(2);

        cell.setCellValue(protoAvg);
        sheet.createRow(rowNumber + 2);
        row = sheet.getRow(rowNumber + 2);
        row.createCell(0);
        row.createCell(1);
        cell = row.getCell(0);
        cell.setCellValue("performance gain in %");
        cell = row.getCell(1);
        protoAvg = jsonAvg - protoAvg;
        cell.setCellValue((protoAvg / jsonAvg) * 100);
        rowNumber += 3;

    }


    public static void writeSize(ArrayList<Float> protobufSize, ArrayList<Float> jsonSize, boolean fixed) {
        int iterations;
        int index = 0;
        int increment;
        if (fixed == true) {
            iterations = 10301;
            increment = 0;
        } else {
            iterations = 1;
            increment = 100;

        }
        sheet.createRow(rowNumber + 1);
        Row row = sheet.getRow(rowNumber + 1);
        row.createCell(0);
        row.createCell(1);
        row.createCell(2);
        Cell cell = row.getCell(0);
        cell.setCellValue("Size of request in KB");
        cell = row.getCell(1);
        cell.setCellValue("JSON");
        cell = row.getCell(2);
        cell.setCellValue("gRPC");
        rowNumber++;
        for (rowNumber = rowNumber + 1; rowNumber < protobufSize.size() + 113; rowNumber++) {
            sheet.createRow(rowNumber + 1);
            row = sheet.getRow(rowNumber + 1);
            row.createCell(0);
            row.createCell(1);
            row.createCell(2);
            cell = row.getCell(0);
            cell.setCellValue((double) iterations);
            cell = row.getCell(1);
            cell.setCellValue(jsonSize.get(index));
            cell = row.getCell(2);
            cell.setCellValue(protobufSize.get(index));

            index++;
            iterations += increment;

        }
        rowNumber = 2; // setting to start of sheet.
    }
}
