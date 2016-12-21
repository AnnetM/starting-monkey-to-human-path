package PO41.Morozova.wdad.learn.xml;


import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class TestXmlTask {
    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException, SAXException {

        XmlTask test = new XmlTask();
        System.out.println("Avg salary: " + test.salaryAverage());
        System.out.println("Avg design salary: " + test.salaryAverage("design"));
        test.setSalary("Abigail", "James", 1000);
    }
}
