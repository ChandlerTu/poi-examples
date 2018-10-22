package nbpt.table.xml;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Test;

import nbpt.table.mysql.Table;
import nbpt.table.word.DocxTableFactory;
import nbpt.table.xml.XmlTable;
import nbpt.table.xml.XmlTableFactory;
import nbpt.table.xml.XmlTableWriter;

public class XmlTableFactoryTest {

	@Test
	public void test() {
		DocxTableFactory docxTableFactory = new DocxTableFactory();
		Path docxPath = Paths.get(
				"D:\\Downloads\\SVN\\192.168.200.2\\svn_document_private\\拨测系统文档\\自动拨测产品文档\\自动拨测_公共文档\\拨测系统新架构文档\\拨测系统数据库文档\\拨测系统【互联网业务结果表】数据库文档.docx");
		List<Table> tables = docxTableFactory.createTables(docxPath);

		XmlTableFactory xmlTableFactory = new XmlTableFactory();
		List<XmlTable> xmlTables = xmlTableFactory.createXmlTables(tables);

		XmlTableWriter writer = new XmlTableWriter();
		Path xmlPath = Paths.get(
				"D:\\Downloads\\SVN\\192.168.200.2\\svn_backup_gsmts_soft\\TSV4\\TSDataCenter\\trunk\\ts-result-processing\\src\\main\\resources\\tablestruct\\TableResult.xml");
		writer.write(xmlTables, xmlPath);
	}

}
