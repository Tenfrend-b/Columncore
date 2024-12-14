package oldMethod;
import java.io.IOException;
import java.io.InputStream;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class BlockViews {
	private InputStream is = BlockViews.class.getClassLoader().getResourceAsStream("blocksData/resource.xml");
	private SAXReader sax = new SAXReader();
	private Element root;

	public void init() {
		try {
			root = sax.read(is).getRootElement();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public String[] seeing(String blockID) {
		if (root == null) {
			String[] answer = { "256", "0", "256", "?" };
			return answer;
		} else {
			Element block = (Element) root.selectSingleNode(blockID);
			if (block == null) {
				String[] answer = { "256", "0", "256", "?" };
				return answer;
			}
			String shape = block.attribute("shape").getValue();
			String R = block.attribute("R").getValue();
			String G = block.attribute("G").getValue();
			String B = block.attribute("B").getValue();
			String[] answer = { shape, R, G, B };
			return answer;
		}
	}

	public void close() {
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
