package nbpt.table.xml;

import java.util.ArrayList;
import java.util.List;

import nbpt.table.mysql.Table;

public class XmlTableFactory {

	private FieldFactory fieldFactory = new FieldFactory();

	public List<XmlTable> createXmlTables(List<Table> tables) {
		List<XmlTable> xmlTables = new ArrayList<XmlTable>();

		for (Table table : tables) {
			xmlTables.add(createXmlTable(table));
		}

		return xmlTables;
	}

	public XmlTable createXmlTable(Table table) {
		XmlTable xmlTable = new XmlTable();

		xmlTable.setTableNamePrefix(createTagName(table.getName()));
		xmlTable.setServiceType(getServiceType(table.getName()));
		xmlTable.setFieldList(fieldFactory.createFieldList(table));

		return xmlTable;
	}

	public String createTagName(String tableName) {
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

}
