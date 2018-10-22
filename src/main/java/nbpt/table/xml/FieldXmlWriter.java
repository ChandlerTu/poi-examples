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

import nbpt.table.mysql.Column;
import nbpt.table.mysql.Table;

public class FieldXmlWriter {

	public void write(List<Table> tables, Path path) {
		try {
			Document doc = getXmlDocument(tables);

			Transformer t = TransformerFactory.newInstance().newTransformer();
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(doc), new StreamResult(new FileOutputStream(path.toFile())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Document getXmlDocument(List<Table> tables) throws Exception {
		DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		Document doc = builder.newDocument();

		Element rootElement = doc.createElement("tablestruct");
		doc.appendChild(rootElement);

		for (Table table : tables) {
			String tagName = getTagName(table.getName());
			Element element = doc.createElement(tagName);
			rootElement.appendChild(element);

			Element typeListElement = doc.createElement("TypeList");
			element.appendChild(typeListElement);

			Element typeElement = doc.createElement("Type");
			typeListElement.appendChild(typeElement);

			String type = getServiceType(table.getName());
			typeElement.appendChild(doc.createTextNode(type));

			Element fieldListElement = doc.createElement("FieldList");
			element.appendChild(fieldListElement);

			List<Column> columns = table.getColumns();
			for (Column column : columns) {
				Element fieldElement = doc.createElement("Field");
				fieldListElement.appendChild(fieldElement);

				fieldElement.setAttribute("FieldName", column.getName());

				String fileFlag = getFileFlag(column.getName());
				fieldElement.setAttribute("FileFlag", fileFlag);

				String formula = getFormula(column.getName(), columns);
				fieldElement.setAttribute("Formula", formula);

				String javaType = getJavaType(column.getType());
				fieldElement.setAttribute("javaType", javaType);
			}
		}

		return doc;
	}

	public String getTagName(String tableName) {
		String tagName;

		if (tableName.startsWith("TestRecord")) {
			tagName = "TestRecord";
		} else if (tableName.startsWith("CalledTestRecord")) {
			tagName = "CalledTestRecord";
		} else {
			tagName = tableName;
		}

		return tagName;
	}

	public String getServiceType(String tableName) {
		String serviceType;

		if (tableName.startsWith("TestRecord")) {
			serviceType = tableName.substring("TestRecord".length(), tableName.length());
		} else if (tableName.startsWith("CalledTestRecord")) {
			serviceType = tableName.substring("CalledTestRecord".length(), tableName.length());
		} else {
			serviceType = "00";
		}

		return serviceType;
	}

	public String getFileFlag(String name) {

		String fileFlag;

		if (name.endsWith("File")) {
			fileFlag = "1";
		} else {
			fileFlag = "0";
		}

		return fileFlag;
	}

	public String getFormula(String name, List<Column> columns) {
		String formula = "";

		if (name.endsWith("OffsetTime")) {
			String prefix = name.replaceAll("OffsetTime", "");
			String startTime = prefix + "StartTime";
			String endTime = prefix + "EndTime";
			if (exists(startTime, columns) && exists(endTime, columns)) {
				formula = "( " + endTime + " - " + startTime + " ) / 1000";
			}
		} else if (name.equals("DealSpeed")) {
			if (exists("FileSize", columns) && exists("DealEndTime", columns) && exists("DealStartTime", columns)) {
				formula = "1000 * FileSize / ( DealEndTime - DealStartTime )";
			}
		}

		return formula;
	}

	public boolean exists(String name, List<Column> columns) {
		boolean exists = false;

		for (Column column : columns) {
			if (column.getName().equals(name)) {
				exists = true;
			}
		}

		return exists;
	}

	public String getJavaType(String mysqlType) {
		String javaType;

		if (mysqlType.startsWith("varchar")) {
			javaType = "String";
		} else if (mysqlType.startsWith("char")) {
			javaType = "String";
		} else if (mysqlType.equals("datetime")) {
			javaType = "Date";
		} else if (mysqlType.startsWith("decimal")) {
			javaType = "double";
		} else if (mysqlType.startsWith("numeric")) {
			javaType = "double";
		} else if (mysqlType.equals("tinyint")) {
			javaType = "int";
		} else if (mysqlType.equals("smallint")) {
			javaType = "int";
		} else if (mysqlType.equals("bigint")) {
			javaType = "long";
		} else {
			javaType = mysqlType;
		}

		return javaType;
	}

}
