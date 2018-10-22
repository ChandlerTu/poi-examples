package nbpt.table.word;

import java.util.ArrayList;
import java.util.List;

public class InternetResultTableDocxTableNameFactory implements TableNameFactory {

	@Override
	public List<String> createTableName(String title, int index) {
		List<String> tableNames = new ArrayList<String>();

		switch (title) {
		case "TraceRouter的表":
			tableNames.add("TraceRouter");
			break;
		case "元素网页资源表WebResourcesElement":
			tableNames.add("WebResourcesElement");
			break;
		case "PING记录表WebResourcesPing":
			tableNames.add("WebResourcesPing");
			break;
		case "Trace route记录表":
			tableNames.add("WebResourcesTrace");
			break;
		case "Trace route Hop记录表":
			tableNames.add("WebResourcesTraceHops");
			break;
		case "分段Ping业务(40100)":
			break;
		case "分段ping业务记录字表":
			break;
		case "网络游戏（40700）":
			break;
		case "HLS视频点播（41304）":
			break;
		case "HLS视频直播（41305）":
			break;
		case "P2P下载下载IP资源":
			tableNames.add("P2PIpList");
			break;
		case "DHCP测试结果表":
			tableNames.add("TestRecord41800");
			break;
		case "http上下载带宽测试":
			tableNames.add("TestRecord42000");
			break;
		case "网络游戏":
			tableNames.add("TestRecord44302");
			break;
		case "游戏各阶段IP网络情况":
			tableNames.add("GameStageInfo");
			break;
		case "游戏各阶段IP网络情况统计表(小时)":
			break;
		case "游戏交互IP Ping结果":
			tableNames.add("GameIPPing");
			break;
		case "游戏交互IP Ping统计表（小时）":
			break;
		case "航空网站测试业务":
			tableNames.add("TestRecord41405");
			break;
		default:
			int beginIndex = title.lastIndexOf("（");
			if (beginIndex == -1) {
				beginIndex = title.lastIndexOf("(");
			}

			int endIndex = title.lastIndexOf("）");
			if (endIndex == -1) {
				endIndex = title.lastIndexOf(")");
			}

			if (beginIndex >= 0 && endIndex >= 0) {
				String serviceType = title.substring(beginIndex + 1, endIndex);

				String[] serviceTypes = serviceType.split("、");
				for (String type : serviceTypes) {
					if (title.indexOf("被叫") >= 0) {
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
