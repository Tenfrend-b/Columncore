package blocksElement;

import org.dom4j.Element;

public class BlockCore extends Packer {

	public BlockCore(Element coreElement) {
		super(coreElement);
	}
	
	//路径跳转
	private Element blocks() {
		return getDocument().getRootElement().element("blocks");
	}

	private Element buildings() {
		return getDocument().getRootElement().element("buildings");
	}

	private Element balloons() {
		return getDocument().getRootElement().element("balloons");
	}

	// 下面五个是固有属性，理论上不应该报错或回null
	public String id() {
		return attribute("id").getText();
	}

	public String sz() {
		return attributeValue("z");
	}

	public String sx() {
		return getParent().getText();
	}

	public String sy() {
		return getParent().getParent().getText();
	}

	// 应对一些场景的快速方法
	public int z() {
		return Integer.parseInt(sz());
	}

	public int x() {
		return Integer.parseInt(sx());
	}

	public int y() {
		return Integer.parseInt(sy());
	}
	
	public BlockCore setID(String id) {// 更改id(setblock),此方法没有与Organizer协作
		attribute("id").setText(id);
		return this;
	}
	
	//以下四个方法在存档不规范时会报NPF，Ctrlframe那里要做好容错
	public BlockCountry country() {//返回其country
		String name;
		Element country;
		if ((name = getText()) != null) // country名字以Text形式在core里保存
			return new BlockCountry(country = buildings().element(name), Integer.parseInt(country.getText()));
		return null;
	}

	public BlockCube cube() {//返回其cube
		String name;
		BlockCountry country;
		if ((country = country()) != null) { // 若存在这个country
			if ((name = attributeValue("C")) != null) // C是否为default
				return new BlockCube(country.element(name));// 是则这条
			return new BlockCube(country.element("defaultCube"));// 不是则这条
		} else if ((name = attributeValue("C")) != null)// 没有这个country
			return new BlockCube(balloons().element(name));// 若存在C则走此分支(balloons中)
		return null;// 既不在country里又不属于balloons-cube
	}

	public BlockCircle circle() {//返回其circle
		BlockCube cube;
		if ((cube = cube()) != null) {// 有一个cube
			if (!cube.getName().equals("defaultCube")) { // 不是defaultCube
				return new BlockCircle(cube.element(attributeValue("B")));// 以B的值在cube下找
			} else {// 是defaultCube(意味着下面的B不确定)
				String name;
				if ((name = attributeValue("B")) != null)
					return new BlockCircle(cube.element(name));// 检索(非defaultCircle)
			}
			return new BlockCircle(cube.element("defaultCircle"));// 没有在cube中找到circle,则为defaultCircle
		}
		return null;// 没有cube，因此不会有circle
	}

	public BlockColumn column() {// 返回此core其column,若core为空气则会返回其依附的column(在其南)
		//不存在defaultColumn这种东西,所有的column都会有特定的名字与唯一的位置
		BlockCircle circle;
		return (circle = circle())!=null?new BlockColumn(circle.element(attributeValue("A"))):null;
	}

	//Organizer专用
	public BlockCore columnize(String A) {// 规定一个方块的归属
		if(attributeValue("A")!=null)
			attribute("A").setValue(A);
		else
			addAttribute("A", A);
		if(A.equals(""))
			remove(attribute("A"));
		return this;
	}
	
	public BlockCore circlize(String B) {// 规定一个方块的归属
		if(attributeValue("B")!=null)
			attribute("B").setValue(B);
		else
			addAttribute("B", B);
		if(B.equals("")||B.equals("defaultCircle"))
			remove(attribute("B"));
		return this;
	}
	
	public BlockCore cubilize(String C) {// 规定一个方块的归属
		if(attributeValue("C")!=null)
			attribute("C").setValue(C);
		else
			addAttribute("C", C);
		if(C.equals("")||C.equals("defaultCube"))
			remove(attribute("C"));
		return this;
	}

}
