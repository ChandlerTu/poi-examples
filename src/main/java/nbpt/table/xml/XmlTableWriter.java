package nbpt.table.xml;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XmlTableWriter {

	public void write(List<XmlTable> tables, Path path) {
		try {
			Document doc = getXmlDocument(tables);

			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(path.toFile())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Document getXmlDocument(List<XmlTable> tables) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.newDocument();

		Element rootElement = doc.createElement("tablestruct");
		doc.appendChild(rootElement);

		for (XmlTable table : tables) {
			Element element = doc.createElement(table.getTableNamePrefix());
			rootElement.appendChild(element);

			Element typeListElement = doc.createElement("TypeList");
			element.appendChild(typeListElement);

			Element typeElement = doc.createElement("Type");
			typeListElement.appendChild(typeElement);

			typeElement.appendChild(doc.createTextNode(table.getServiceType()));

			Element fieldListElement = doc.createElement("FieldList");
			element.appendChild(fieldListElement);

			List<Field> fields = table.getFieldList();
			for (Field field : fields) {
				Element fieldElement = doc.createElement("Field");
				fieldListElement.appendChild(fieldElement);

				fieldElement.setAttribute("FieldName", field.getFieldName());
				fieldElement.setAttribute("FileFlag", field.getFileFlag());
				fieldElement.setAttribute("Formula", field.getFormula());
				fieldElement.setAttribute("javaType", field.getJavaType());
			}
		}

		return doc;
	}

}
