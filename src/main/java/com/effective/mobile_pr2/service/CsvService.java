package com.effective.mobile_pr2.service;

import com.effective.mobile_pr2.utils.CsvUtils;
import com.effective.mobile_pr2.utils.exception.ErrorInWriteCsvFile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CsvService {

    private final CsvUtils csvUtils;

    public CsvService(CsvUtils csvUtils) {
        this.csvUtils = csvUtils;
    }

    public <T> void makeReport(String filePath, List<T> list) throws ErrorInWriteCsvFile {
        csvUtils.writeDataLineByLine(filePath, list);
    }
}
