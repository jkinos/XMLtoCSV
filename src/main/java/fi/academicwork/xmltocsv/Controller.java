package fi.academicwork.xmltocsv;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import java.io.File;
import java.io.FileInputStream;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class Controller {

    @PostMapping("/xmltocsv")
    public ResponseEntity<Resource> convertXMLtoCSV(@RequestParam("file") MultipartFile file) throws Exception {

        // transfer multipart file to file
        File xmlFile = new File("src/main/resources/xml/targetFile.xml");
        file.transferTo(xmlFile.toPath());

        // convert xml file to csv
        XmlToCSVConverter converter = new XmlToCSVConverter();
        converter.convertXMLtoCSV(xmlFile);

        // response body
        File csvFile = new File("src/main/output/output.csv");
        Resource resource = new InputStreamResource(new FileInputStream(csvFile));

        // response headers
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + "invoice.csv" + "\"");
        responseHeaders.set(HttpHeaders.CONTENT_TYPE, "text/csv");

        // send response
        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(resource);
    }
}