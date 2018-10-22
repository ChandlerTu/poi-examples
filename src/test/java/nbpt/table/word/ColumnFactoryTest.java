package nbpt.table.word;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import nbpt.table.mysql.Column;
import nbpt.table.word.CellIndex;
import nbpt.table.word.ColumnFactory;

public class ColumnFactoryTest {

	@Test
	public void testCreateColumn() {
		ColumnFactory columnFactory = new ColumnFactory();

		List<String> docRow = new ArrayList<String>();
		docRow.add("1.");
		docRow.add("Id");
		docRow.add("Varcha(36)");
		docRow.add("36");
		docRow.add("N");
		docRow.add("Uuid");

		CellIndex cellIndex = new CellIndex(1, 2, 3, 4);

		Column column = columnFactory.createColumn(docRow, cellIndex);

		Assert.assertEquals("Id", column.getName());
		Assert.assertEquals("varchar(36)", column.getType());
		Assert.assertEquals(false, column.isNullable());
	}

}
