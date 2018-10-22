package nbpt.table.word;

import java.util.ArrayList;
import java.util.List;

public class InternetResultTableDocxTableNameFactory implements TableNameFactory {

	@Override
	public List<String> createTableName(String title, int index) {
		List<String> tableNames = new ArrayList<String>();

		switch (title) {
		case "TraceRouter�ı�":
			tableNames.add("TraceRouter");
			break;
		case "Ԫ����ҳ��Դ��WebResourcesElement":
			tableNames.add("WebResourcesElement");
			break;
		case "PING��¼��WebResourcesPing":
			tableNames.add("WebResourcesPing");
			break;
		case "Trace route��¼��":
			tableNames.add("WebResourcesTrace");
			break;
		case "Trace route Hop��¼��":
			tableNames.add("WebResourcesTraceHops");
			break;
		case "�ֶ�Pingҵ��(40100)":
			break;
		case "�ֶ�pingҵ���¼�ֱ�":
			break;
		case "������Ϸ��40700��":
			break;
		case "HLS��Ƶ�㲥��41304��":
			break;
		case "HLS��Ƶֱ����41305��":
			break;
		case "P2P��������IP��Դ":
			tableNames.add("P2PIpList");
			break;
		case "DHCP���Խ����":
			tableNames.add("TestRecord41800");
			break;
		case "http�����ش������":
			tableNames.add("TestRecord42000");
			break;
		case "������Ϸ":
			tableNames.add("TestRecord44302");
			break;
		case "��Ϸ���׶�IP�������":
			tableNames.add("GameStageInfo");
			break;
		case "��Ϸ���׶�IP�������ͳ�Ʊ�(Сʱ)":
			break;
		case "��Ϸ����IP Ping���":
			tableNames.add("GameIPPing");
			break;
		case "��Ϸ����IP Pingͳ�Ʊ�Сʱ��":
			break;
		case "������վ����ҵ��":
			tableNames.add("TestRecord41405");
			break;
		default:
			int beginIndex = title.lastIndexOf("��");
			if (beginIndex == -1) {
				beginIndex = title.lastIndexOf("(");
			}

			int endIndex = title.lastIndexOf("��");
			if (endIndex == -1) {
				endIndex = title.lastIndexOf(")");
			}

			if (beginIndex >= 0 && endIndex >= 0) {
				String serviceType = title.substring(beginIndex + 1, endIndex);

				String[] serviceTypes = serviceType.split("��");
				for (String type : serviceTypes) {
					if (title.indexOf("����") >= 0) {
						tableNames.add("CalledTestRecord" + type);
					} else {
						tableNames.add("TestRecord" + type);
					}
				}
			}
			break;
		}

		if (tableNames.size() == 0) {
			System.err.println(title);
		}

		return tableNames;
	}

}
