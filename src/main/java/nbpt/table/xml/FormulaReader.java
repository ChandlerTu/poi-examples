package nbpt.table.xml;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class FormulaReader {

	private Map<String, Map<String, String>> maps;

	public Map<String, Map<String, String>> read(Path path) {
		maps = new HashMap<String, Map<String, String>>();

		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(path.toFile());

			Element root = doc.getDocumentElement();
			NodeList childNodes = root.getChildNodes();
			for (int i = 0; i < childNodes.getLength(); i++) {
				Node child = childNodes.item(i);
				if (child instanceof Element) {
					Element recordElement = (Element) child;

					String key = createKey(recordElement);
					Map<String, String> map = read(recordElement);
					maps.put(key, map);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return maps;
	}

	public String createKey(Element recordElement) {
		String tagName = recordElement.getTagName();

		Element typeListElement = (Element) recordElement.getElementsByTagName("TypeList").item(0);
		Element typeElement = (Element) typeListElement.getElementsByTagName("Type").item(0);
		String type = typeElement.getTextContent();

		return tagName + type;
	}

	public Map<String, String> read(Element recordElement) {
		Map<String, String> map = new HashMap<String, String>();

		Element fieldListElement = (Element) recordElement.getElementsByTagName("FieldList").item(0);

		NodeList fieldNodes = fieldListElement.getElementsByTagName("Field");
		for (int i = 0; i < fieldNodes.getLength(); i++) {
			Element fieldElement = (Element) fieldNodes.item(i);

			String formula = fieldElement.getAttribute("Formula");
			if (formula != null && !formula.isEmpty()) {
				String fieldName = fieldElement.getAttribute("FieldName");
				map.put(fieldName, formula);
			}
		}

		return map;
	}

	public String getFormula(String tableKey, String fieldName) {
		String formula = null;

		Map<String, String> map = maps.get(tableKey);
		if (map != null) {
			formula = map.get(fieldName);
		}

		return formula;
	}

}
