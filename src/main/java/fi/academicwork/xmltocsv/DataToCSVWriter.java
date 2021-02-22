package fi.academicwork.xmltocsv;

import java.util.List;
import java.io.FileWriter;
import com.opencsv.CSVWriter;

public class DataToCSVWriter {

    public void writeToCSV(List<String[]> datalines, String outputFile) throws Exception {

        // Instantiating the CSVWriter class
        CSVWriter writer = new CSVWriter(new FileWriter(outputFile), ';', CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);

        // Writing data to the csv file
        writer.writeAll(datalines);
        writer.flush();
        System.out.println("Data entered");
    }

}
