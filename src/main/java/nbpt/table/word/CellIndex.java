package nbpt.table.word;

public class CellIndex {

	private Integer columnNameIndex;
	private Integer columnTypeIndex;
	private Integer columnLengthIndex;
	private Integer isNullableIndex;

	public CellIndex() {
	}

	public CellIndex(Integer columnNameIndex, Integer columnTypeIndex, Integer columnLengthIndex,
			Integer isNullableIndex) {
		this.columnNameIndex = columnNameIndex;
		this.columnTypeIndex = columnTypeIndex;
		this.columnLengthIndex = columnLengthIndex;
		this.isNullableIndex = isNullableIndex;
	}

	public Integer getColumnNameIndex() {
		return columnNameIndex;
	}

	public void setColumnNameIndex(Integer columnNameIndex) {
		this.columnNameIndex = columnNameIndex;
	}

	public Integer getColumnTypeIndex() {
		return columnTypeIndex;
	}

	public void setColumnTypeIndex(Integer columnTypeIndex) {
		this.columnTypeIndex = columnTypeIndex;
	}

	public Integer getColumnLengthIndex() {
		return columnLengthIndex;
	}

	public void setColumnLengthIndex(Integer columnLengthIndex) {
		this.columnLengthIndex = columnLengthIndex;
	}

	public Integer getIsNullableIndex() {
		return isNullableIndex;
	}

	public void setIsNullableIndex(Integer isNullableIndex) {
		this.isNullableIndex = isNullableIndex;
	}

}
