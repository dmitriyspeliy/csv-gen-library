package com.effective.mobile_pr2.utils;

import com.effective.mobile_pr2.annotation.ExcludeColumn;
import com.effective.mobile_pr2.utils.exception.ErrorInWriteCsvFile;
import com.opencsv.CSVWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class CsvUtils {

    private Field[] fields;
    private Set<Object> columnsExcludeName;
    private final Logger log = LoggerFactory.getLogger(CsvUtils.class);

    /**
     * Write info from object list to file.csv
     */
    public <T> void writeDataLineByLine(String filePath, List<T> list) throws ErrorInWriteCsvFile {
        log.info("Start writing...");
        File file = checkParamAndCreateFile(list, filePath);
        log.info("Create file in filePath " + filePath);
        try (FileWriter outFile = new FileWriter(file); CSVWriter writer = new CSVWriter(outFile)) {

            T report = list.get(0);
            getExcludeColumn(report);

            fields = report.getClass().getDeclaredFields();

            writer.writeNext(writeHeader());

            //set value cell
            setValueInCell(list, writer);
            log.info("Done");

        } catch (IOException | NoSuchFieldException | IllegalAccessException e) {
            log.error(e.getMessage());
            throw new ErrorInWriteCsvFile(e.getMessage());
        }
    }

    private <T> File checkParamAndCreateFile(List<T> list, String filePath) throws ErrorInWriteCsvFile {
        if (list.size() == 0 || filePath == null || filePath.equals("")) {
            log.error("FilePath is null or list is null");
            throw new ErrorInWriteCsvFile("FilePath is null or list is null");
        }

        return new File(filePath);
    }

    private <T> void getExcludeColumn(T report) {
        columnsExcludeName = new HashSet<>();
        if (report.getClass().isAnnotationPresent(ExcludeColumn.class)) {
            columnsExcludeName = Arrays.stream(report.getClass().getAnnotation(ExcludeColumn.class).nameOfColumns())
                    .collect(Collectors.toSet());
        }
    }

    private String[] writeHeader() {
        List<String> headers = new ArrayList<>();
        //set header
        for (Field field : fields) {
            String fieldName = field.getName();
            if (!columnsExcludeName.contains(fieldName)) {
                headers.add(fieldName.toUpperCase());
            }
        }
        return headers.toArray(new String[0]);
    }

    private <T> void setValueInCell(List<T> list, CSVWriter writer) throws NoSuchFieldException, IllegalAccessException {
        //set value cell
        for (T item : list) {
            ArrayList<String> res = new ArrayList<>();
            for (Field field : fields) {
                String nameOfColumn = field.getName().toLowerCase();
                if (!columnsExcludeName.contains(nameOfColumn)) {
                    Field f = item.getClass().getDeclaredField(nameOfColumn);
                    f.setAccessible(true);
                    String value = (String) f.get(item);
                    res.add(value);
                }
            }
            writer.writeNext(res.toArray(new String[0]));
        }
    }


}
