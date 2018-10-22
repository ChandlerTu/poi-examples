package nbpt.table.word;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Before;
import org.junit.Test;

import nbpt.table.mysql.Column;
import nbpt.table.mysql.Table;
import nbpt.table.word.CellIndexFactory;
import nbpt.table.word.DocxTableFactory;
import nbpt.table.word.InternetResultTableDocxTableNameFactory;
import nbpt.table.word.TableNameFactory;

public class DocxTableFactoryTest {

	private DocxTableFactory tableFactory;

	private TableNameFactory tableNameFactory;

	private CellIndexFactory cellIndexFactory;

	private Path docx;

	@Before
	public void before() throws URISyntaxException {
		Path parent = Paths.get(getClass().getResource("").toURI());
		docx = parent.resolve("拨测系统【互联网业务结果表】数据库文档2.docx");
		tableFactory = new DocxTableFactory();
		tableNameFactory = new InternetResultTableDocxTableNameFactory();
		cellIndexFactory = new CellIndexFactory();
	}

	@Test
	public void testGetBodyElementClassSet() {
		Path dir = Paths.get(
				"D:\\Downloads\\SVN\\192.168.200.2\\svn_document_private\\拨测系统文档\\自动拨测产品文档\\自动拨测_公共文档\\拨测系统新架构文档\\拨测系统数据库文档");

		try (Stream<Path> paths = Files.list(dir)) {
			paths.forEach(path -> {
				Set<String> set = tableFactory.getBodyElementClassSet(path);

				System.out.println(path);
				System.out.println(set);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateTables() {
		List<Table> list = tableFactory.createTables(docx);

		for (Table table : list) {
			List<Column> columns = table.getColumns();
			System.out.println(table.getName() + ": " + columns.size());
			for (Column column : columns) {
				System.out.println(column.getName() + ", " + column.getType() + ", " + column.isNullable());
			}

		}
	}

	// @Test
	// public void testGetBodyElementsAndSplitByTitles() throws
	// URISyntaxException {
	// Map<String, List<IBodyElement>> map =
	// docxReader.getBodyElementsAndSplitByTitles();
	// Assert.assertEquals(4, map.size());
	//
	// for (Entry<String, List<IBodyElement>> entry : map.entrySet()) {
	// System.out.println(entry.getKey());
	// }
	// }
	//
	// @Test
	// public void testGetBodyElementsMapContainsTables() throws
	// URISyntaxException {
	// Map<String, List<IBodyElement>> map =
	// docxReader.getBodyElementsMapContainsTables();
	// Assert.assertEquals(2, map.size());
	//
	// for (Entry<String, List<IBodyElement>> entry : map.entrySet()) {
	// System.out.println(entry.getKey());
	// }
	// }

	// @Test
	// public void testGet() {
	//
	// Path dir = Paths.get(
	// "D:\\Downloads\\SVN\\192.168.200.2\\svn_document_private\\拨测系统文档\\自动拨测产品文档\\自动拨测_公共文档\\拨测系统新架构文档\\拨测系统数据库文档");
	//
	// try (Stream<Path> paths = Files.list(dir)) {
	// paths.forEach(path -> {
	// System.out.println(path.getFileName().toString());
	// List<String> strikeThroughList = new ArrayList<String>();
	//
	// DocxTableFactory docxReader = new DocxTableFactory();
	//
	// Map<String, List<IBodyElement>> map =
	// docxReader.getBodyElementsMapContainsTables();
	//
	// for (Entry<String, List<IBodyElement>> entry : map.entrySet()) {
	// String title = entry.getKey();
	// String tableName = tableNameFactory.createTableName(title);
	// System.out.println(title);
	// List<XWPFTable> tables = docxReader.getXWPFTables(entry.getValue());
	// if (tables.size() != 1) {
	// //System.err.println(title + ", table size: " + tables.size());
	// }
	//
	// for (XWPFTable table : tables) {
	// List<XWPFTableRow> rows = table.getRows();
	//
	// List<String> titles = docxReader.getStringList(rows.get(0));
	// CellIndex cellIndex = cellIndexFactory.createCellIndex(titles);
	//
	// List<List<String>> docRows = new ArrayList<List<String>>();
	// for (int i = 1; i < rows.size(); i++) {
	// XWPFTableRow row = rows.get(i);
	//
	// List<XWPFTableCell> cells = row.getTableCells();
	//
	// // List<String> cells = docxReader.getStringList();
	//
	// for (int j = 0; j < cells.size(); j++) {
	// XWPFTableCell cell = cells.get(j);
	//
	// XWPFParagraph p1 = cell.getParagraphs().get(0);
	// List<XWPFRun> runs = p1.getRuns();
	//
	// if (runs.size() > 0) {
	// XWPFRun run = runs.get(0);
	// if (run.isStrikeThrough()) {
	// System.out.println(cell.getText());
	// strikeThroughList.add(cell.getText());
	// }
	//
	// // if (run.isStrike() ||
	// // run.isStrikeThrough() ||
	// // run.isDoubleStrikeThrough()) {
	// // System.out.println(cell.getText());
	// // System.out.println(run.isStrike());
	// // System.out.println(run.isStrikeThrough());
	// // System.out.println(run.isDoubleStrikeThrough());
	// // }
	// }
	//
	// }
	// }
	//
	// tableFactory.createTable(docRows, cellIndex);
	//
	// // for (int i = 1; i < rows.size(); i++) {
	// // XWPFTableRow row = rows.get(i);
	// //
	// // List<XWPFTableCell> cells = row.getTableCells();
	// //
	// // for (int j = 0; j < cells.size(); j++) {
	// // XWPFTableCell cell = cells.get(j);
	// // System.out.println(cell.getText());
	// // }
	// // }
	//
	// }
	//
	// }
	//
	// System.out.println(strikeThroughList.size());
	//
	// });
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	//
	// }

}
