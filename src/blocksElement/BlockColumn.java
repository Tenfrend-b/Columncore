package blocksElement;

import org.dom4j.Attribute;
import org.dom4j.Element;

public class BlockColumn extends Packer {

	public BlockColumn(Element columnElement) {
		super(columnElement);
	}

	// column层面的跳转
	public BlockColumn getE() {// 没写好，似乎这个办法需要Organizer参与
		return null;
	}

	public BlockColumn getW() {// 同上
		return null;
	}

	// 基础属性
	public String sx() {
		return attributeValue("x");
	}

	public String sy() {
		return getParent().attributeValue("y");
	}

	public String sfZ() {
		return attributeValue("fZ");
	}

	public String stZ() {
		return attributeValue("tZ");
	}

	// 快捷办法
	public int x() {
		return Integer.valueOf(sx());
	}

	public int y() {
		return Integer.valueOf(sy());
	}

	public int fZ() {
		return Integer.valueOf(sfZ());
	}

	public int tZ() {
		return Integer.valueOf(stZ());
	}

	// 带link的坐标方法由于不一定有对应属性，有可能返回null值
	// 类似于上面的常规坐标，不过link坐标的值是要与东西两向的column协同计算才会生成的
	// 这些属性的值会由Organizer计算并填写
	private boolean ifLinked() {
		return (attributeValue("lfZ") != null);
	}

	// 以下方法分String与Integer重写
	public Integer linkFromZ() {
		return (ifLinked() ? (Integer.parseInt(attribute("linkFromZ").getText())) : null);
	}

	public Integer linkToZ() {
		return (ifLinked() ? (Integer.parseInt(attribute("linkToZ").getText())) : null);
	}

	public Integer linkStartZ() {
		return (ifLinked() ? (Math.min(linkFromZ(), linkToZ())) : null);
	}

	public Integer linkEndZ() {
		return (ifLinked() ? (Math.max(linkFromZ(), linkToZ())) : null);
	}

	public Integer[] linkRange() {
		if (!ifLinked())
			return null;
		else {
			Integer[] linkRange = { x(), y(), linkFromZ(), linkToZ() };
			return linkRange;
		}
	}

	public String ifInSpace() {
		return String.valueOf(attribute("openedBy") != null);
	}

	public BlockCircle circle() {
		return new BlockCircle(getParent());
	}

	public BlockCube cube() {
		return new BlockCube(circle().getParent());
	}

	// Organizer专用
	public BlockCircle remove() {
		Element parent;
		(parent = getParent()).remove(element);
		return new BlockCircle(parent);
	}

	public BlockColumn changeZ(String newFrom, String newTo) {
		attribute("fZ").setValue(newFrom);
		attribute("tZ").setValue(newTo);
		return this;
	}

	public BlockColumn changeLinkZ(String newFrom, String newTo) {
		Attribute lfZ, ltZ = null;
		if ((lfZ = attribute("lfZ")) != null) {
			lfZ.setValue(newFrom);
			(ltZ = attribute("ltZ")).setValue(newTo);
		} else {
			addAttribute("lfZ", newFrom);
			addAttribute("ltZ", newTo);
		}
		if (newFrom.equals("")) {
			remove(lfZ);
			remove(ltZ);
		}
		return this;
	}
}
