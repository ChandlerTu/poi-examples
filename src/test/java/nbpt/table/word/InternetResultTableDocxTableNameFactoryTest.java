package nbpt.table.word;

import org.junit.Assert;
import org.junit.Test;

import nbpt.table.word.InternetResultTableDocxTableNameFactory;
import nbpt.table.word.TableNameFactory;

public class InternetResultTableDocxTableNameFactoryTest {

	@Test
	public void test() {
		TableNameFactory tableNameFactory = new InternetResultTableDocxTableNameFactory();
		Assert.assertEquals("TraceRouter", tableNameFactory.createTableName("TraceRouterµÄ±í", 0));
		Assert.assertEquals("TestRecord40000", tableNameFactory.createTableName("ÍøÒ³ä¯ÀÀ£¨40000£©", 0));
	}

}
