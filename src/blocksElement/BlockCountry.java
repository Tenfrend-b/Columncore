package blocksElement;

import org.dom4j.Element;

public class BlockCountry extends Packer {

	public static Element ranged(Element preCountry, int fX, int fY, int fZ, int tX, int tY, int tZ) {
		Element pC = preCountry;// 用于简写
		pC = pC.attribute("fX") == null ? pC.addAttribute("fX", String.valueOf(fX)) : pC;
		pC = pC.attribute("fY") == null ? pC.addAttribute("fY", String.valueOf(fY)) : pC;
		pC = pC.attribute("fZ") == null ? pC.addAttribute("fZ", String.valueOf(fZ)) : pC;
		pC = pC.attribute("tX") == null ? pC.addAttribute("tX", String.valueOf(tX)) : pC;
		pC = pC.attribute("tY") == null ? pC.addAttribute("tY", String.valueOf(tY)) : pC;
		pC = pC.attribute("tZ") == null ? pC.addAttribute("tZ", String.valueOf(tZ)) : pC;
		return preCountry;
	}

	public BlockCountry(Element country, int index) {
		super(country);
		setIndex(index);
	}
	
	public int[] range() {
		int[] positions = new int[6];
		positions[0] = Integer.parseInt(attributeValue("fX"));
		positions[1] = Integer.parseInt(attributeValue("fY"));
		positions[2] = Integer.parseInt(attributeValue("fZ"));
		positions[3] = Integer.parseInt(attributeValue("tX"));
		positions[4] = Integer.parseInt(attributeValue("tY"));
		positions[5] = Integer.parseInt(attributeValue("tZ"));
		return positions;
	}

	public BlockCountry setIndex(int index) {
		return (BlockCountry) setText(String.valueOf(index));
	}
}
