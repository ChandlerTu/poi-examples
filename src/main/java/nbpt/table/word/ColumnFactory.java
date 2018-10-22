package nbpt.table.word;

import java.util.ArrayList;
import java.util.List;

import nbpt.table.mysql.Column;

public class ColumnFactory {

	public List<Column> createColumns(List<List<String>> docRows, CellIndex cellIndex) {
		List<Column> list = new ArrayList<Column>();

		for (List<String> docRow : docRows) {
			Column column = createColumn(docRow, cellIndex);
			if (column != null) {
				list.add(column);
			}
		}

		return list;
	}

	public Column createColumn(List<String> docRow, CellIndex cellIndex) {
		String columnName = docRow.get(cellIndex.getColumnNameIndex());
		String columnType = docRow.get(cellIndex.getColumnTypeIndex());
		// String columnLength = docRow.get(cellIndex.getColumnLengthIndex());
		String isNullable = docRow.get(cellIndex.getIsNullableIndex());

		String type = createColumnType(columnType);
		boolean nullable = createIsNullable(isNullable);

		Column column = null;
		if (!columnName.isEmpty()) {
			column = new Column();
			column.setName(columnName);
			column.setType(type);
			column.setNullable(nullable);
		}
		return column;
	}

	public String createColumnType(String columnType) {
		columnType = columnType.toLowerCase();
		columnType = columnType.replaceAll("varcha\\(", "varchar\\(");
		columnType = columnType.replaceAll("number", "int");
		return columnType;
	}

	public boolean createIsNullable(String isNullable) {
		boolean nullable = true;

		if (isNullable.equals("N")) {
			nullable = false;
		}

		return nullable;
	}

}
