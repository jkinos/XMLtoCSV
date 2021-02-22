package fi.academicwork.xmltocsv;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.Assert.*;

import org.junit.Before;
import java.io.FileReader;
import com.opencsv.CSVReader;
import java.io.File;

@SpringBootTest

class XmlToCsvApplicationTests {

	@Test
	void contextLoads() {
	}

	/**
	 * Rigorous Test :-)
	 */
	@Test
	public void shouldAnswerWithTrue() {
		assertTrue(true);
	}

	@Before
	public void setUp() {
		File xmlFile = new File("src/main/resources/exampleInvoice.xml");
		XmlToCSVConverter converter = new XmlToCSVConverter();
		converter.convertXMLtoCSV(xmlFile);
	}

	@Test
	public void outPutCSVFileMatchesExpectedResult() {
		CSVReader reader1 = null;
		CSVReader reader2 = null;

		String fileExpected = "src/main/resources/exampleInvoice.csv";
		String fileOutput = "src/main/output/output.csv";

		try {
			reader1 = new CSVReader(new FileReader(fileExpected)); // read first file
			reader2 = new CSVReader(new FileReader(fileOutput)); // read second file
			String[] nextLine1;
			String[] nextLine2;

			while (((nextLine1 = reader1.readNext()) != null) && ((nextLine2 = reader2.readNext()) != null)) {
				{

					for (int i = 0; i < nextLine1.length; i++) {
						String first = nextLine1[i];
						String second = nextLine2[i];
						assertEquals(first, second);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
