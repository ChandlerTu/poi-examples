package nbpt.table.xml;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.Test;

import nbpt.table.xml.FormulaReader;

public class FormulaReaderTest {

	@Test
	public void test() {
		FormulaReader reader = new FormulaReader();
		Path xmlPath = Paths.get(
				"D:\\Downloads\\SVN\\192.168.200.2\\svn_backup_gsmts_soft\\TSV4\\TSDataCenter\\trunk\\TSDataCenterV6\\tablestruct\\TableResult.xml");
		Map<String, Map<String, String>> maps = reader.read(xmlPath);
		
		System.out.println(maps);
	}

}
