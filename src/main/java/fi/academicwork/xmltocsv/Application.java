package fi.academicwork.xmltocsv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.File;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

		File xmlFile = new File("src/main/resources/exampleInvoice.xml");
		XmlToCSVConverter converter = new XmlToCSVConverter();
		converter.convertXMLtoCSV(xmlFile);
	}

}
