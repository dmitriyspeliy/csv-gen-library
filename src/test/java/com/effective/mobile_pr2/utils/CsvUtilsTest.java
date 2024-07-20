package com.effective.mobile_pr2.utils;


import com.effective.mobile_pr2.dto.Report;
import com.effective.mobile_pr2.dto.ReportTestWithAllExcludeColumn;
import com.effective.mobile_pr2.dto.ReportTestWithExcludeCountry;
import com.effective.mobile_pr2.utils.exception.ErrorInWriteCsvFile;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CsvUtilsTest {

    private String filePath = "src/main/resources/test.csv";
    private CsvUtils csvUtils;
    private List<Report> listWithReportTest;
    private List<ReportTestWithExcludeCountry> reportTestWithExcludeCountries;
    private List<ReportTestWithAllExcludeColumn> reportTestWithAllExcludeColumnList;

    @BeforeEach
    public void init() {
        csvUtils = new CsvUtils();
        listWithReportTest = new ArrayList<>();

        Report report1 = new Report("TEST1", "VALUE1", "COLUMN1", "COLUMN2", "COLUMN2", "COLUMN2");
        Report report2 = new Report("TEST1", "VALUE1", "COLUMN1", "COLUMN2", "COLUMN2", "COLUMN2");
        Report report3 = new Report("TEST1", "VALUE1", "COLUMN1", "COLUMN2", "COLUMN2", "COLUMN2");

        listWithReportTest.add(report1);
        listWithReportTest.add(report2);
        listWithReportTest.add(report3);

        ReportTestWithExcludeCountry reportTestWithExcludeCountry1 = new ReportTestWithExcludeCountry("TEST1", "VALUE1");
        ReportTestWithExcludeCountry reportTestWithExcludeCountry2 = new ReportTestWithExcludeCountry("TEST2", "VALUE2");
        ReportTestWithExcludeCountry reportTestWithExcludeCountry3 = new ReportTestWithExcludeCountry("TEST3", "VALUE3");

        reportTestWithExcludeCountries = new ArrayList<>();
        reportTestWithExcludeCountries.add(reportTestWithExcludeCountry1);
        reportTestWithExcludeCountries.add(reportTestWithExcludeCountry2);
        reportTestWithExcludeCountries.add(reportTestWithExcludeCountry3);

        ReportTestWithAllExcludeColumn reportTestWithAllExcludeColumn1 = new ReportTestWithAllExcludeColumn("TEST1", "VALUE1");
        ReportTestWithAllExcludeColumn reportTestWithAllExcludeColumn2 = new ReportTestWithAllExcludeColumn("TEST2", "VALUE2");
        ReportTestWithAllExcludeColumn reportTestWithAllExcludeColumn3 = new ReportTestWithAllExcludeColumn("TEST3", "VALUE3");

        reportTestWithAllExcludeColumnList = new ArrayList<>();
        reportTestWithAllExcludeColumnList.add(reportTestWithAllExcludeColumn1);
        reportTestWithAllExcludeColumnList.add(reportTestWithAllExcludeColumn2);
        reportTestWithAllExcludeColumnList.add(reportTestWithAllExcludeColumn3);
    }

    @AfterEach
    public void close() {
        csvUtils = null;
        listWithReportTest = null;
        reportTestWithExcludeCountries = null;
        reportTestWithAllExcludeColumnList = null;
    }

    @Test
    public void smoke_test() throws ErrorInWriteCsvFile {
        csvUtils.writeDataLineByLine(filePath, listWithReportTest);

        List<List<String>> lists = readFile(filePath);

        assertEquals(4, lists.size());

    }

    @Test
    public void when_exclude_one_column_get_list_size_equals_one() throws ErrorInWriteCsvFile {
        csvUtils.writeDataLineByLine(filePath, reportTestWithExcludeCountries);

        List<List<String>> lists = readFile(filePath);

        assertEquals(1, lists.get(0).size());
    }

    @Test
    public void when_exclude_all_columns_get_list_size_equals_blank() throws ErrorInWriteCsvFile {
        csvUtils.writeDataLineByLine(filePath, reportTestWithAllExcludeColumnList);

        List<List<String>> lists = readFile(filePath);

        assertEquals("", lists.get(0).get(0));
    }

    @Test
    public void when_get_param_null_return_exception() {
        Exception exception = assertThrows(ErrorInWriteCsvFile.class, () -> {
            csvUtils.writeDataLineByLine(null, reportTestWithAllExcludeColumnList);
        });

        String expectedMessage = "FilePath is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void when_get_param_empty_return_exception() {
        Exception exception = assertThrows(ErrorInWriteCsvFile.class, () -> {
            csvUtils.writeDataLineByLine("", reportTestWithAllExcludeColumnList);
        });

        String expectedMessage = "FilePath is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void when_get_param_list_is_empty_return_exception() {
        Exception exception = assertThrows(ErrorInWriteCsvFile.class, () -> {
            csvUtils.writeDataLineByLine(filePath, List.of());
        });

        String expectedMessage = "list is null";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    private List<List<String>> readFile(String filePath) {
        List<List<String>> records = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(filePath))) {
            String[] values;
            while ((values = csvReader.readNext()) != null) {
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return records;
    }
}