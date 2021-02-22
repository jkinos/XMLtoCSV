package fi.academicwork.xmltocsv;

import java.util.List;
import java.io.File;

public class XmlToCSVConverter {

    public void convertXMLtoCSV(File xmlFile) {

        String xsdFile = "src/main/xsd/finvoice.xsd";
        String outputFile = "src/main/output/output.csv";

        XmlToObjectConverter converter = new XmlToObjectConverter();
        ObjectParser parser = new ObjectParser();
        DataToCSVWriter writer = new DataToCSVWriter();

        // java object from xml file
        Finvoice invoiceObject = converter.convertXMLToObject(xmlFile, xsdFile);

        try {
            // parsing data from object to list of lines
            List<String[]> csvRows = parser.parseInvoiceObject(invoiceObject);

            // writing datalines to csv file
            writer.writeToCSV(csvRows, outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
