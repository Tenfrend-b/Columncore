package blocksManeger;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;

import blocksElement.*;

public class Stenfatic {
	Document structureDoc;// 存档
	List<Organizer> organizers;// 世界中调用的Organizer

	// 当前焦点
	BlockCore readingCore;
	BlockColumn readingColumn;
	BlockCircle readingCircle;
	BlockCube readingCube;

	// 包装存档，生成Organizer，重置其index
	public Stenfatic(Document structureDoc) {
		this.structureDoc = structureDoc;
		List<Element> countryElements;
		if (!(countryElements = buildings().elements()).isEmpty()) {
			Element next;
			for (int i = 0; (next = countryElements.get(i)) != null; i++)
				organizers.add(i, new Organizer(new BlockCountry(next, i), i));
		}
	}

	// 获取节点位置，简化某些表述
	public Element blocks() {
		return structureDoc.getRootElement().element("blocks");
	}

	public Element buildings() {
		return structureDoc.getRootElement().element("buildings");
	}

	// 通过坐标获得方块对象
	public BlockCore loadblock(int x, int y, int z) {
		byte stepDone = 0;
		Element site = blocks(), site1 = blocks();
		try {// 此处代码的错误来自于对null调用方法，有意为之，无需截获
			stepDone += (site1 = site.element("Y" + String.valueOf(y))) == null ? 0 : 1;
			stepDone += (site = site1.element("X" + String.valueOf(x))) == null ? 0 : 1;
			stepDone += (site1 = site.element("Z" + String.valueOf(z))) == null ? 0 : 1;
		} finally {
			String s = "";// 用于简化描述，但是好像参数的装配顺序未定义？
			switch (stepDone) {
			// 0~2为生成空气，存档中普通空气保存时会被删除
			case (0):
				(site1 = site.addElement("Y" + (s = String.valueOf(y)), s)).setText(s);
			case (1):
				(site = site1.addElement("X" + (s = String.valueOf(x)), s)).setText(s);
			case (2):
				site1 = site.addElement("Z" + (s = String.valueOf(z)), s);
				site1.addAttribute("z", s).addAttribute("id", "air");
			}
		}
		return new BlockCore(site1);
	}

	// 同loadblock，并更改readingCore
	public BlockCore readblock(int x, int y, int z) {
		return readingCore = loadblock(x, y, z);
	}

	// 返回的是处理后的方块元素
	public BlockCore setblock(int x, int y, int z, String id) {
		if(readblock(x, y, z).country()==null)
			return readingCore.setID(id);
		return
	}

	public BlockCore placeBlock(int x, int y, int z, String id) {
		return readblock(x, y, z).id().equals("air") ? readingCore.setID(id) : readingCore;
	}

	public BlockCore destroyBlock(int x, int y, int z) {
		return readblock(x, y, z).id().equals("air") ? readingCore : readingCore.setID("air");
	}

	// 设置country区域，生成新的Organizer
	// 应该会非常消耗资源
	public BlockCountry setCountry(String name, int fX, int fY, int fZ, int tX, int tY, int tZ) {
		int index;
		Element pC = buildings().addElement(name, String.valueOf(index = organizers.size() + 1));
		BlockCountry c = new BlockCountry(BlockCountry.ranged(pC, fX, fY, fZ, tX, tY, tZ), index);
		organizers.add(index, new Organizer(c, index).check());
		return c;
	}

	// 在country里调用Organizer进行building建造
	private BlockCore setInCountry(BlockCountry country, int x, int y, int z, String id) {
		return null;
	}

	private BlockCore placeInCountry(BlockCountry country, int x, int y, int z, String id) {
		return null;
	}

	private BlockCore destroyInCountry(BlockCountry country, int x, int y, int z, String id) {
		return null;
	}

	// 解散country，同时删除其Organizer并重组organizers
	// 解散后该国下的壳体将被移至balloons下(与blocks, buildings同级)
	// 未组成壳体的环与柱将被移除
	public void delCountry(BlockCountry country) {
		int px, py, pz;
		BlockColumn col,umn;
		for (Element circle : country.element("defaultCube").elements()) {
			py = new BlockCircle(circle).y();
			for (Element column : circle.elements()) {
				px = (col = new BlockColumn(column)).x();
				if((umn = col.element("N"))!=null)
			}
		}
		//可读性极差，希望将来不要出bug
		for (Element cube : country.elements()) {
			for (Element circle : cube.elements()) {
				py = (new BlockCircle(circle)).y();
				for (Element column : circle.elements()) {
					px = (col = new BlockColumn(column)).x();
				}
			}
		}
	}
}
