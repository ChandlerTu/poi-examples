package nbpt.table.xml;

import java.util.ArrayList;
import java.util.List;

import nbpt.table.mysql.Column;
import nbpt.table.mysql.Table;

public class FieldFactory {

	private FormulaFactory formulaFactory = new FormulaFactory();

	public List<Field> createFieldList(Table table) {
		List<Field> fieldList = new ArrayList<Field>();

		String tableKey = getTableKey(table.getName());

		List<Column> columns = table.getColumns();

		for (Column column : columns) {
			Field field = createField(column, tableKey, columns);
			fieldList.add(field);
		}

		return fieldList;
	}

	public String getTableKey(String tableName) {
		String tableKey;

		if (tableName.startsWith("TestRecord") || tableName.startsWith("CalledTestRecord")) {
			tableKey = tableName;
		} else {
			tableKey = tableName + "00";
		}

		return tableKey;
	}

	public Field createField(Column column, String tableKey, List<Column> columns) {
		Field field = new Field();

		field.setFieldName(column.getName());
		field.setFileFlag(createFileFlag(column.getName()));
		field.setFormula(createFormula(column.getName(), tableKey, columns));
		field.setJavaType(createJavaType(column.getType()));

		return field;
	}

	public String createFileFlag(String name) {
		String fileFlag;

		if (name.endsWith("File")) {
			fileFlag = "1";
		} else {
			fileFlag = "0";
		}

		return fileFlag;
	}

	public String createFormula(String columnName, String tableKey, List<Column> columns) {

		String formula = formulaFactory.getFormula(tableKey, columnName);

		if (formula == null) {
			if (columnName.endsWith("OffsetTime")) {
				String prefix = columnName.replaceAll("OffsetTime", "");
				String startTime = prefix + "StartTime";
				String endTime = prefix + "EndTime";
				if (exists(startTime, columns) && exists(endTime, columns)) {
					formula = "( " + endTime + " - " + startTime + " ) / 1000";
				}
			} else if (columnName.equals("DealSpeed")) {
				if (exists("FileSize", columns) && exists("DealEndTime", columns) && exists("DealStartTime", columns)) {
					formula = "1000 * FileSize / ( DealEndTime - DealStartTime )";
				}
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

	public String createJavaType(String mysqlType) {
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
