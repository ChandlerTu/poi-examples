package nbpt.table.word;

import java.util.List;

public class CellIndexFactory {

	public CellIndex createCellIndex(List<String> titles) {
		CellIndex cellIndex = new CellIndex();

		for (int i = 0; i < titles.size(); i++) {
			String title = titles.get(i);
			switch (title) {
			case "字段名":
				cellIndex.setColumnNameIndex(i);
				break;
			case "类型":
			case "字段类型":
				cellIndex.setColumnTypeIndex(i);
				break;
			case "长度":
				cellIndex.setColumnLengthIndex(i);
				break;
			case "为空":
			case "是否为空":
				cellIndex.setIsNullableIndex(i);
				break;
			default:
				break;
			}
		}

		if (cellIndex.getColumnNameIndex() == null || cellIndex.getColumnTypeIndex() == null
				 || cellIndex.getIsNullableIndex() == null) {
			System.err.println(titles);
		}

		return cellIndex;
	}

}
