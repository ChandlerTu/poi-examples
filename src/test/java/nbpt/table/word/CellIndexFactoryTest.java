package nbpt.table.word;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import nbpt.table.word.CellIndex;
import nbpt.table.word.CellIndexFactory;

public class CellIndexFactoryTest {

	@Test
	public void testCreateCellIndex() {
		List<String> titles = new ArrayList<String>();
		titles.add("序号");
		titles.add("字段名");
		titles.add("类型");
		titles.add("长度");
		titles.add("为空");
		titles.add("描述");

		CellIndexFactory cellIndexFactory = new CellIndexFactory();
		CellIndex cellIndex = cellIndexFactory.createCellIndex(titles);

		Assert.assertEquals(1, cellIndex.getColumnNameIndex().intValue());
		Assert.assertEquals(2, cellIndex.getColumnTypeIndex().intValue());
		Assert.assertEquals(3, cellIndex.getColumnLengthIndex().intValue());
		Assert.assertEquals(4, cellIndex.getIsNullableIndex().intValue());
	}

}
