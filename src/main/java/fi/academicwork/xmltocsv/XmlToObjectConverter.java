package fi.academicwork.xmltocsv;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


// validation feature commented out for now, test data is not valid invoice and fails validation because of missing required fields

/*import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;
import javax.xml.XMLConstants;
import org.xml.sax.SAXException;*/

public class XmlToObjectConverter {

  public Finvoice convertXMLToObject(File xmlFile, String xsdFile) throws JAXBException/*, SAXException*/ {

    Finvoice finvoice = new Finvoice();


      // Get JAXBContext
      JAXBContext jaxbContext = JAXBContext.newInstance(Finvoice.class);

      // Create Unmarshaller
      Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

      // Setup schema validator  
        /*SchemaFactory sf =
        SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI); Schema
        finvoiceSchema = sf.newSchema(new File(xsdFile));
        jaxbUnmarshaller.setSchema(finvoiceSchema);*/
       

      // Unmarshal xml file
      finvoice = (Finvoice) jaxbUnmarshaller.unmarshal(xmlFile);
    

    return finvoice;
    }
}