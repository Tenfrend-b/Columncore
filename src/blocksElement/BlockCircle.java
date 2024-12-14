package blocksElement;

import org.dom4j.Element;

public class BlockCircle extends Packer {

	public BlockCircle(Element elementCircle) {
		super(elementCircle);
	}

	//返回该环体可能占用的坐标
	public int[][] range() {
		int width = elements().size();
		if (width == 0)
			return null;
		else {
			int[][] range = new int[width][];
			int i = 0;
			for (Element e : elements()) {
				range[i] = (new BlockColumn(e)).range();
				i++;
			}
			return range;
		}
	}

	public int y() {
		return Integer.parseInt(attribute("y").getText());
	}
}
