package com;

import com.constants.FilePath;
import org.apache.commons.lang.RandomStringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.StringReader;


/**
 * Преобразование строк в XML и запись в отдельный файл
 */

// TODO: Придумать адекватный вариант обработки XML, без сохранения на комп...
public class XmlProcessing {

    /**
     * Конвертирование прилетевшей из ответа сервера строки с XML-содержимым в полноценный XML-файл
     * и сохранение его в директории на машине.
     * @param sourceString
     */
    public static void convertStringToXml(String sourceString) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder;
        try {
            documentBuilder = factory.newDocumentBuilder();

            // use String reader
            Document document = documentBuilder.parse(new InputSource(new StringReader(sourceString)));

            // создание XML-файла
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            Source src = new DOMSource(document);
            String randomFileName = String.format("%s.%s", RandomStringUtils.randomAlphanumeric(5), "xml");
            Result dest = new StreamResult(new File(FilePath.XML_FOLDER_PATH + randomFileName));
            transformer.transform(src, dest);

            // извлечение из XML результата распознаной фразы
            extractRecognizedPhrase(FilePath.XML_FOLDER_PATH + randomFileName);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Извлечение распознанного текста из XML-ответа, который был сохранен в директории машины.
     * @param path - путь до лежащего в директории XML-файла
     * @return - возврат результа распознавания
     */
    public static String extractRecognizedPhrase(String path) {
        String result = "";
        try {
            System.out.println("* Пытаюсь найти файл: " + path);
            File xmlFile = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            doc.getDocumentElement().normalize();

            System.out.println("Root element: " + doc.getDocumentElement().getNodeName());

            NodeList nodeList = doc.getElementsByTagName("recognitionResults");

            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Node node = nodeList.item(temp);
                System.out.println("\nCurrent Element: " + node.getNodeName());

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    System.out.println("First variant: " + element.getElementsByTagName("variant").item(0).getTextContent());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;

    }



}
