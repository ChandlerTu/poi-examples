package nbpt.table.word;

import java.util.ArrayList;
import java.util.List;

import nbpt.table.word.CellIndex;
import nbpt.table.word.DocxTableFactory;

public class TableFactoryTest {

	public void testCreateTable() {

		List<String> docRow1 = new ArrayList<String>();
		docRow1.add("1.");
		docRow1.add("Id");
		docRow1.add("Varcha(36)");
		docRow1.add("36");
		docRow1.add("N");
		docRow1.add("Uuid");

		List<String> docRow2 = new ArrayList<String>();
		docRow2.add("2.");
		docRow2.add("Id2");
		docRow2.add("Varcha(36)");
		docRow2.add("36");
		docRow2.add("N");
		docRow2.add("Uuid");

		List<List<String>> docRows = new ArrayList<List<String>>();
		docRows.add(docRow1);
		docRows.add(docRow2);

		CellIndex cellIndex = new CellIndex(1, 2, 3, 4);

		DocxTableFactory tableFactory = new DocxTableFactory();
		// Table table = tableFactory.createTable(docRows, cellIndex);
	}

}
