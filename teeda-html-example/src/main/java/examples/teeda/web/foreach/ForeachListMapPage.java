package examples.teeda.web.foreach;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ForeachListMapPage {

	private String foo;

	private String bar;

	private List aaaItems;

	public List getAaaItems() {
		if (aaaItems == null) {
			aaaItems = new ArrayList();
			Map map = new HashMap();
			map.put("foo", "FOO");
			map.put("bar", "BAR");
			aaaItems.add(map);
			Map map2 = new HashMap();
			map2.put("foo", "FOO2");
			map2.put("bar", "BAR2");
			aaaItems.add(map2);
		}
		return aaaItems;
	}

	public void setAaaItems(List fooItems) {
		this.aaaItems = fooItems;
	}

	public String getBar() {
		return bar;
	}

	public void setBar(String bar) {
		this.bar = bar;
	}

	public String getFoo() {
		return foo;
	}

	public void setFoo(String foo) {
		this.foo = foo;
	}

	public static class FooItem {

		private String foo;

		private String bar;

		public String getBar() {
			return bar;
		}

		public void setBar(String bar) {
			this.bar = bar;
		}

		public String getFoo() {
			return foo;
		}

		public void setFoo(String foo) {
			this.foo = foo;
		}
	}

}