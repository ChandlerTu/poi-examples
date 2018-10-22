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
				"D:\\Downloads\\SVN\\192.168.200.2\\svn_document_private\\����ϵͳ�ĵ�\\�Զ������Ʒ�ĵ�\\�Զ�����_�����ĵ�\\����ϵͳ�¼ܹ��ĵ�\\����ϵͳ���ݿ��ĵ�\\����ϵͳ��������ҵ���������ݿ��ĵ�.docx");
		List<Table> tables = docxTableFactory.createTables(docxPath);

		XmlTableFactory xmlTableFactory = new XmlTableFactory();
		List<XmlTable> xmlTables = xmlTableFactory.createXmlTables(tables);

		XmlTableWriter writer = new XmlTableWriter();
		Path xmlPath = Paths.get(
				"D:\\Downloads\\SVN\\192.168.200.2\\svn_backup_gsmts_soft\\TSV4\\TSDataCenter\\trunk\\ts-result-processing\\src\\main\\resources\\tablestruct\\TableResult.xml");
		writer.write(xmlTables, xmlPath);
	}

}
