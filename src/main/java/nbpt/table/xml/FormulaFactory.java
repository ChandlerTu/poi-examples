package nbpt.table.xml;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FormulaFactory {

	private FormulaReader formulaReader = new FormulaReader();

	public FormulaFactory() {
		Path xmlPath = Paths.get(
				"D:\\Downloads\\SVN\\192.168.200.2\\svn_backup_gsmts_soft\\TSV4\\TSDataCenter\\trunk\\TSDataCenterV6\\tablestruct\\TableResult.xml");
		formulaReader.read(xmlPath);
	}

	public String getFormula(String tableKey, String fieldName) {
		String formula = formulaReader.getFormula(tableKey, fieldName);
		return formula;
	}

}
