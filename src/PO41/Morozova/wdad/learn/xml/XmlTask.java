package PO41.Morozova.wdad.learn.xml;


import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;

public class XmlTask {

    private Document document;
    private String path = "src/PO41/Morozova/wdad/learn/xml/Inditex.xml";


    public XmlTask() throws IOException, ParserConfigurationException, SAXException {
        createDocument();
    }

    // Document generator.
    private void createDocument() throws IOException, ParserConfigurationException, SAXException {
        File xmlFile = new File(path);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        document = builder.parse(xmlFile);
    }
    
    // Document re-writer.
    private void rewriteDocument() throws TransformerException, IOException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(path));
        transformer.transform(domSource, streamResult);
    }
    
    // Average salary counter.
    private int salaryAverage(NodeList nodeList) {
        int salary = 0;
        
        for (int i = 0; i < nodeList.getLength(); i++) {
            salary += Integer.valueOf(nodeList.item(i).getTextContent());
        }
        
        return salary / nodeList.getLength();
    }

    // Empolyee finder to prevent redundant code.
    private Node employee(String firstName, String secondName) {
        NodeList employees = document.getElementsByTagName("employee");
        NamedNodeMap nodeMap;

        for (int i = 0; i < employees.getLength(); i++) {
            if (employees.item(i).getAttributes().getNamedItem("firstname").getNodeValue().equalsIgnoreCase(firstName)
                    && employees.item(i).getAttributes().getNamedItem("secondname").getNodeValue().equalsIgnoreCase(secondName))
                return employees.item(i);
        }


        return null;
    }
    
    
    
    // Возвращает среднюю заработную плату сотрудников.
    public int salaryAverage() {
        return salaryAverage(document.getElementsByTagName("salary"));
    }

    // Возвращает среднюю заработную плату сотрудников заданного отдела.
    public int salaryAverage(String departmentName) {
        NodeList departments = document.getElementsByTagName("department");
        Node department;

        for (int i = 0; i < departments.getLength(); i++) {
            department = departments.item(i);
            if (departmentName.equalsIgnoreCase(department.getAttributes().item(0).getNodeValue())) {
                return salaryAverage (((Element) department).getElementsByTagName("salary"));
            }
        }
        return 0;
    }

    // Изменяет должность сотрудника.
    public void setJobTitle(String firstName, String secondName, String newJobTitle) throws IOException, TransformerException {
        NodeList employee = employee(firstName, secondName).getChildNodes();
        Node node;
        for (int i = 0; i < employee.getLength(); i++) {
            node = employee.item(i);
            if ("jobtitle".equalsIgnoreCase(employee.item(i).getNodeName()))
                node.getAttributes().getNamedItem("value").setNodeValue(newJobTitle);
        }
        rewriteDocument();
    }

    // Изменяет размер заработной платы сотрудника.
    public void setSalary(String firstName, String secondName, int newSalary) throws IOException, TransformerException {
        NodeList employee = employee(firstName, secondName).getChildNodes();

        for (int i = 0; i < employee.getLength(); i++) {
            if ("salary".equalsIgnoreCase(employee.item(i).getNodeName()))
                employee.item(i).setTextContent(String.valueOf(newSalary));
        }
        rewriteDocument();
    }

    // Удаляет информацию о сотруднике.

    public void fireEmployee(String firstName, String secondName) throws IOException, TransformerException {
        Node employee = employee(firstName, secondName);
        employee.getParentNode().removeChild(employee);
        rewriteDocument();
    }
}

