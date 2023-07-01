package com.example.demo.factory;

import org.springframework.stereotype.Component;

@Component
public class ExporterFactory {

    public Exporter createExporter(String format) {
        if (format.equals("csv")) {
            return new CsvExporter();
        } else if (format.equals("excel")) {
            return new ExcelExporter();
        }
        throw new IllegalArgumentException("Unsupported format: " + format);
    }
}
