package nbpt.table.word;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.xwpf.usermodel.IBodyElement;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import nbpt.table.mysql.Column;
import nbpt.table.mysql.Table;

public class DocxTableFactory {

	private TableNameFactory tableNameFactory = new InternetResultTableDocxTableNameFactory();
	private ColumnFactory columnFactory = new ColumnFactory();
	private CellIndexFactory cellIndexFactory = new CellIndexFactory();

	private static final String BEFORE_FIRST_TITLE = "Before First Title";

	public List<Table> createTables(Path docxPath) {
		List<Table> list = null;

		try (InputStream in = new FileInputStream(docxPath.toFile())) {
			try (XWPFDocument doc = new XWPFDocument(in)) {
				list = createTables(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (list != null) {
			Collections.sort(list, new Comparator<Table>() {
				@Override
				public int compare(Table o1, Table o2) {
					return o1.getName().compareTo(o2.getName());
				}
			});
		}

		return list;
	}

	private List<Table> createTables(XWPFDocument doc) {
		List<Table> list = new ArrayList<Table>();

		Map<String, List<IBodyElement>> map = getBodyElementsMapContainsTables(doc);
		for (Entry<String, List<IBodyElement>> entry : map.entrySet()) {
			list.addAll(createTables(entry.getKey(), entry.getValue()));
		}

		return list;
	}

	private List<Table> createTables(String title, List<IBodyElement> bodyElements) {
		List<Table> list = new ArrayList<Table>();

		List<XWPFTable> tables = getXWPFTables(bodyElements);

		for (int i = 0; i < tables.size(); i++) {
			XWPFTable table = tables.get(i);

			List<String> tableNames = tableNameFactory.createTableName(title, i);

			for (String tableName : tableNames) {
				list.add(createTable(tableName, table));
			}
		}

		return list;
	}

	private Table createTable(String tableName, XWPFTable xwpfTable) {
		Table table = new Table();
		table.setName(tableName);

		List<XWPFTableRow> rows = xwpfTable.getRows();

		List<List<String>> docRows = new ArrayList<List<String>>();

		List<String> xwpfTableTitle = getStringList(rows.get(0));

		CellIndex cellIndex = cellIndexFactory.createCellIndex(xwpfTableTitle);

		for (int i = 1; i < rows.size(); i++) {
			XWPFTableRow row = rows.get(i);
			List<String> list = getStringListWithoutStrikeThrough(row);
			if (list != null) {
				docRows.add(list);
			}
		}

		List<Column> columns = columnFactory.createColumns(docRows, cellIndex);
		table.setColumns(columns);

		return table;
	}

	public List<String> getStringList(XWPFTableRow row) {
		return getStringList(row.getTableCells());
	}

	public List<String> getStringList(List<XWPFTableCell> cells) {
		List<String> list = new ArrayList<String>();

		for (XWPFTableCell cell : cells) {
			list.add(cell.getText().trim());
		}

		return list;
	}

	public List<String> getStringListWithoutStrikeThrough(XWPFTableRow row) {
		return getStringListWithoutStrikeThrough(row.getTableCells());
	}

	public List<String> getStringListWithoutStrikeThrough(List<XWPFTableCell> cells) {
		List<String> list = new ArrayList<String>();

		for (XWPFTableCell cell : cells) {
			if (isStrikeThrough(cell)) {
				return null;
			}

			list.add(cell.getText().trim());
		}

		return list;
	}

	public boolean isStrikeThrough(XWPFTableCell cell) {
		boolean isStrikeThrough = false;

		List<XWPFRun> runs = cell.getParagraphs().get(0).getRuns();
		if (runs.size() > 0) {
			XWPFRun run = runs.get(0);
			if (run.isStrikeThrough()) {
				isStrikeThrough = true;
			}
		}

		return isStrikeThrough;
	}

	public List<XWPFTable> getXWPFTables(List<IBodyElement> bodyElements) {
		List<XWPFTable> tables = new ArrayList<XWPFTable>();

		for (IBodyElement bodyElement : bodyElements) {
			if (bodyElement instanceof XWPFTable) {
				tables.add((XWPFTable) bodyElement);
			}
		}

		return tables;
	}

	public Map<String, List<IBodyElement>> getBodyElementsMapContainsTables(XWPFDocument doc) {
		Map<String, List<IBodyElement>> tables = new LinkedHashMap<String, List<IBodyElement>>();

		Map<String, List<IBodyElement>> map = getBodyElementsAndSplitByTitles(doc);
		for (Entry<String, List<IBodyElement>> entry : map.entrySet()) {
			String key = entry.getKey();

			if (key.equals(BEFORE_FIRST_TITLE)) {
				continue;
			}

			List<IBodyElement> bodyElements = entry.getValue();
			if (hasTable(bodyElements)) {
				tables.put(key, bodyElements);
			}
		}

		return tables;
	}

	public boolean hasTable(List<IBodyElement> bodyElements) {
		boolean hasTable = false;

		for (IBodyElement bodyElement : bodyElements) {
			if (bodyElement instanceof XWPFTable) {
				hasTable = true;
				break;
			}
		}

		return hasTable;
	}

	public Map<String, List<IBodyElement>> getBodyElementsAndSplitByTitles(XWPFDocument doc) {
		Map<String, List<IBodyElement>> map = new LinkedHashMap<String, List<IBodyElement>>();

		List<IBodyElement> list = new ArrayList<IBodyElement>();
		map.put(BEFORE_FIRST_TITLE, list);

		List<IBodyElement> iBodyElements = doc.getBodyElements();
		for (IBodyElement iBodyElement : iBodyElements) {
			if (iBodyElement instanceof XWPFParagraph) {
				XWPFParagraph xwpfParagraph = (XWPFParagraph) iBodyElement;
				String style = xwpfParagraph.getStyle();
				if (isTitle(style)) {
					String title = xwpfParagraph.getParagraphText();
					list = new ArrayList<IBodyElement>();
					map.put(title, list);
				}
			}

			list.add(iBodyElement);
		}

		return map;
	}

	public boolean isTitle(String style) {
		boolean isTitle = false;

		if ("1".equals(style) || "2".equals(style) || "3".equals(style)) {
			isTitle = true;
		}

		return isTitle;
	}

	public Set<String> getBodyElementClassSet(Path docxPath) {
		Set<String> set = null;

		try (InputStream in = new FileInputStream(docxPath.toFile())) {
			try (XWPFDocument doc = new XWPFDocument(in)) {
				set = getBodyElementClassSet(doc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return set;
	}

	public Set<String> getBodyElementClassSet(XWPFDocument doc) {
		Set<String> set = new HashSet<String>();

		List<IBodyElement> iBodyElements = doc.getBodyElements();
		for (IBodyElement iBodyElement : iBodyElements) {
			set.add(iBodyElement.getClass().toString());
		}

		return set;
	}

}
