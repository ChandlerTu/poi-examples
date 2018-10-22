package nbpt.table.word;

import java.util.List;

public class CellIndexFactory {

	public CellIndex createCellIndex(List<String> titles) {
		CellIndex cellIndex = new CellIndex();

		for (int i = 0; i < titles.size(); i++) {
			String title = titles.get(i);
			switch (title) {
			case "�ֶ���":
				cellIndex.setColumnNameIndex(i);
				break;
			case "����":
			case "�ֶ�����":
				cellIndex.setColumnTypeIndex(i);
				break;
			case "����":
				cellIndex.setColumnLengthIndex(i);
				break;
			case "Ϊ��":
			case "�Ƿ�Ϊ��":
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
