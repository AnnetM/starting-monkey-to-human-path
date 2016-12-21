package PO41.Morozova.wdad.data.managers;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class PreferencesManager {
    private static PreferencesManager instance;
    private String path = "src/PO41/Morozova/wdad/resources/configuration/appconfig.xml";
    private Document document;


    private PreferencesManager() throws IOException, ParserConfigurationException, SAXException {
        createDocumnet();
    }

    public static PreferencesManager getInstance() throws IOException, ParserConfigurationException, SAXException {
        if (instance == null) {
            instance = new PreferencesManager();
        }
        return instance;
    }

    private void createDocumnet() throws IOException, ParserConfigurationException, SAXException {
        File file = new File(path);
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
    }

    private void rewriteDocument() throws TransformerException {
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        DOMSource domSource = new DOMSource(document);
        StreamResult streamResult = new StreamResult(new File(path));
        transformer.transform(domSource, streamResult);
    }

    public boolean isCreateRegistry() {
        NodeList nodeList = document.getElementsByTagName("createregistry");
        return ("yes".equalsIgnoreCase(nodeList.item(0).getTextContent()));

    }

    public void setCreateRegistry(boolean createRegistry) throws IOException, TransformerException {
        String textContent;
        if (createRegistry) {
            textContent = "yes";
        } else textContent = "no";

        document.getElementsByTagName("createregistry").item(0).setTextContent(textContent);
        rewriteDocument();
    }

    public String getRegistryAddress() {
        return  document.getElementsByTagName("registryaddress").item(0).getTextContent();
    }

    public void setRegistryAddress(String newRegistryAddress) throws IOException, TransformerException {
        document.getElementsByTagName("registryaddress").item(0).setTextContent(newRegistryAddress);
        rewriteDocument();
    }

    public String getRegistryPort() {
        return document.getElementsByTagName("registryport").item(0).getTextContent();
    }

    public void setRegistryPort(String newRegistryPort) throws IOException, TransformerException {
        document.getElementsByTagName("registryport").item(0).setTextContent(newRegistryPort);
        rewriteDocument();
    }

    public String getPolicyPath() {
        return document.getElementsByTagName("policypath").item(0).getTextContent();
    }

    public void setPolicyPath(String newPolicyPath) throws IOException, TransformerException {
        document.getElementsByTagName("policypath").item(0).setTextContent(newPolicyPath);
        rewriteDocument();
    }

    public boolean isUseCodebaseOnly() {
        NodeList nodeList = document.getElementsByTagName("usecodebaseonly");
        return ("yes".equalsIgnoreCase(nodeList.item(0).getTextContent()));

    }

    public void setUseCodebaseOnly(boolean useCodebaseOnly) throws IOException, TransformerException {
        String textContent;
        if (useCodebaseOnly) {
            textContent = "yes";
        } else textContent = "no";

        document.getElementsByTagName("usecodebaseonly").item(0).setTextContent(textContent);
        rewriteDocument();
    }

    public String getClassProvider() {
        return document.getElementsByTagName("classprovider").item(0).getTextContent();
    }

    public void setClassProvider(String newClassProvider) throws IOException, TransformerException {
        document.getElementsByTagName("classprovider").item(0).setTextContent(newClassProvider);
        rewriteDocument();
    }
}
