package oldMethod;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

//Stenfatic即Tenfrend_b+Schematic
public class Stenfatic {
	static final byte O = 0;
	static final byte E = 1;
	static final byte W = 2;
	static final byte U = 3;
	static final byte D = 4;
	static final byte S = 5;
	static final byte N = 6;
	static final byte P = 7;
	private Document doc = null;
	private InputStream is = null;
	private SAXReader rr = new SAXReader();
	Element readingPoint = null;
	private BlockViews blockview = new BlockViews();
	// 每次上线都给我好好看看自己是什么环境！
	String evirPath = "C:\\Users\\seewo\\Documents\\My Bluetooth\\STENFATICA\\src\\";
//	String evirPath = "C:\\Users\\Administrator\\eclipse-workspace\\高220杨东箭学习资料\\src\\";
//	String evirPath = "D:\\Program Files\\206.eclipse\\workingSpace\\STENFATICA\\src\\";
//	String evirPath = "/root/eclipse-workspace/高220杨东箭学习资料/src/";
	String filePath = "blocksData/structure.xml";
	// OK
	public void read(String path) {
		try {
			blockview.init();
			is = Stenfatic.class.getClassLoader().getResourceAsStream(path);
			doc = rr.read(is);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// OK
	public void close() {
		try {
			is.close();
			blockview.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// OK
	public void saveClose(String EnPath, String path) throws Exception {
		save(EnPath, path);
		close();
	}

	// OK
	public boolean save(String EnPath, String path) {
		FileOutputStream outer;
		boolean ifSucceed = false;
		try {
			outer = new FileOutputStream(new File(EnPath + path));
			XMLWriter wtr = null;

			wtr = new XMLWriter(outer, OutputFormat.createPrettyPrint());
			wtr.write(doc);
			wtr.flush();
			wtr.close();
			ifSucceed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ifSucceed;
	}

	// OK
	public boolean placeblock(int x, int y, int z, String id) {
		if (readblock(x, y, z).equals("air")) {
			readingPoint.attribute("id").setData(id);
			return true;
		} else
			return false;
	}

	// OK
	public boolean destroyblock(int x, int y, int z) {
		if (readblock(x, y, z).equals("air") == false) {
			readingPoint.attribute("id").setData("air");
			return true;
		} else
			return false;
	}

	// OK
	public String readblock(int x, int y, int z) {
		String[] subpath = new String[4];
		subpath[0] = "X" + Integer.toString(x);
		subpath[1] = "Y" + Integer.toString(y);
		subpath[2] = "Z" + Integer.toString(z);
		subpath[3] = "Block";
		String id = "void";
		Element fromBlocks = doc.getRootElement().element("blocks");
		byte stepDone = 0;
		if (fromBlocks != null)
			for (; (stepDone <= 3) && (fromBlocks.selectSingleNode(subpath[stepDone]) != null); stepDone++)
				fromBlocks = (Element) fromBlocks.selectSingleNode(subpath[stepDone]);

		switch (stepDone) {
		case 0:
			fromBlocks = fromBlocks.addElement("X" + Integer.toString(x));
		case 1:
			fromBlocks = fromBlocks.addElement("Y" + Integer.toString(y));
		case 2:
			fromBlocks = fromBlocks.addElement("Z" + Integer.toString(z));
		case 3:
			fromBlocks = fromBlocks.addElement("Block");
			fromBlocks.addAttribute("x", Integer.toString(x));
			fromBlocks.addAttribute("y", Integer.toString(y));
			fromBlocks.addAttribute("z", Integer.toString(z));
			fromBlocks.addAttribute("id", "air");
		case 4:
			id = fromBlocks.attributeValue("id");
			readingPoint = fromBlocks;
		}
		return id;
	}

	// OK
	public String readblock(boolean from000, int deltaX, int deltaY, int deltaZ) {
		if (from000) {
			return readblock(deltaX, deltaY, deltaZ);
		} else {
			int fromX = Integer.valueOf(readingPoint.attribute("x").getStringValue());
			int fromY = Integer.valueOf(readingPoint.attribute("y").getStringValue());
			int fromZ = Integer.valueOf(readingPoint.attribute("z").getStringValue());
			return (readblock(fromX + deltaX, fromY + deltaY, fromZ + deltaZ));
		}
	}

	public String readblock(byte direction) {
		switch (direction) {
		case (O):
			return (readblock(false, 0, 0, 0));
		case (E):
			return (readblock(false, 1, 0, 0));
		case (W):
			return (readblock(false, -1, 0, 0));
		case (U):
			return (readblock(false, 0, 1, 0));
		case (D):
			return (readblock(false, 0, -1, 0));
		case (S):
			return (readblock(false, 0, 0, 1));
		case (N):
			return (readblock(false, 0, 0, -1));
		case (P):
			if (readingPoint.getParent().element("Port") == null) {
				Element clone = readingPoint.getParent().addElement("Port");
				clone.addAttribute("toX", readingPoint.attributeValue("x"));
				clone.addAttribute("toY", readingPoint.attributeValue("y"));
				clone.addAttribute("toZ", readingPoint.attributeValue("z"));
				return (readblock(false, 0, 0, 0));
			} else {
				Element port = readingPoint.getParent().element("Port");
				int toX = Integer.valueOf(port.attributeValue("toX"));
				int toY = Integer.valueOf(port.attributeValue("toY"));
				int toZ = Integer.valueOf(port.attributeValue("toZ"));
				return readblock(toX, toY, toZ);
			}
		default:
			return (readblock(false, 0, 0, 0));
		}
	}

	// OK
	public void setblock(int x, int y, int z, String id) {
		readblock(x, y, z);
		readingPoint.attribute("id").setData(id);

	}

	// OK
	public void cleair() {
		List<Node> Airs = doc.selectNodes("/Structure/blocks/*/*/*/Block[@id='air']");
		Element upperFromAir;
		byte stepDone;
		for (Node fromAir : Airs) {
			stepDone = 0;
			do {
				upperFromAir = ((Element) fromAir).getParent();
				upperFromAir.remove((Element) fromAir);
				fromAir = (Node) upperFromAir;
				stepDone++;
			} while (((Element) fromAir).elements().isEmpty() & stepDone <= 3);
		}
	}

	// OK
	public void setport(int toX, int toY, int toZ) {
		if (readingPoint.getParent().element("Port") == null) {
			readingPoint = readingPoint.getParent().addElement("Port");
			readingPoint.addAttribute("toX", Integer.toString(toX));
			readingPoint.addAttribute("toY", Integer.toString(toY));
			readingPoint.addAttribute("toZ", Integer.toString(toZ));
			readingPoint = readingPoint.getParent().element("Block");
		} else {
			Element Port = readingPoint.getParent().element("Port");
			Port.attribute("toX").setData(Integer.toString(toX));
			Port.attribute("toX").setData(Integer.toString(toY));
			Port.attribute("toX").setData(Integer.toString(toZ));
		}
	}

	// OK
	public void sweeport() {

		List<Node> Ports = doc.selectNodes("/Structure/blocks/*/*/*/Port");
		Element upperFromPort;
		byte stepDone;
		for (Node fromPort : Ports) {
			stepDone = 0;
			do {
				upperFromPort = ((Element) fromPort).getParent();
				upperFromPort.remove((Element) fromPort);
				fromPort = (Node) upperFromPort;
				stepDone++;
			} while (((Element) fromPort).elements().isEmpty() & stepDone <= 3);
		}
	}

	// OK
	public boolean sweeport(int x, int y, int z) {
		boolean respond = false;
		try {
			String portpath = "/Structure/blocks/X" + Integer.toString(x) + "/Y" + Integer.toString(y) + "/Z"
					+ Integer.toString(z) + "/Port";
			Element fromPort = (Element) doc.selectSingleNode(portpath);
			Element upperFromPort;
			byte stepDone = 0;
			do {
				upperFromPort = fromPort.getParent();
				upperFromPort.remove(fromPort);
				fromPort = upperFromPort;
				stepDone++;
			} while (((Element) fromPort).elements().isEmpty() & stepDone <= 3);

			respond = true;
		} catch (Exception e) {

		}
		return respond;
	}

	public void fill(int fromX, int fromY, int fromZ, int toX, int toY, int toZ, String id) {
		int length = Math.abs(toX - fromX) + 1;
		int heigth = Math.abs(toY - fromY) + 1;
		int wideth = Math.abs(toZ - fromZ) + 1;
		int Xset[] = new int[length];
		int Yset[] = new int[heigth];
		int Zset[] = new int[wideth];
		for (int i = 0; i < length; i++) {
			Xset[i] = Math.min(fromX, toX) + i;
		}
		for (int i = 0; i < heigth; i++) {
			Yset[i] = Math.min(fromY, toY) + i;
		}
		for (int i = 0; i < wideth; i++) {
			Zset[i] = Math.min(fromZ, toZ) + i;
		}
		for (int i = 0; i < length; i++)
			for (int j = 0; j < heigth; j++)
				for (int k = 0; k < wideth; k++)
					setblock(Xset[i], Yset[j], Zset[k], id);

	}

	private String[] printblock(int x, int y, int z) {
		String blockID = readblock(x, y, z);
		return (blockview.seeing(blockID));

	}

//好难啊！！！
	public String[] printchunk(int includeX, int includeY, int includeZ) {
		String result[] = new String[1224];
		String block[] = new String[4];
		int chunkFromX = 0;
		int chunkFromZ = 0;
		if (includeX > -1)
			chunkFromX = ((int) includeX / 16) * 16;
		else
			chunkFromX = ((int) (includeX - 16) / 16) * 16;
		if (includeZ > -1)
			chunkFromZ = ((int) includeZ / 16) * 16;
		else
			chunkFromZ = ((int) (includeZ - 16) / 16) * 16;

//做标题		
		for (int i = 0; i < 10; i++) {
			result[i * 4] = " " + Integer.toString(i);
			result[i * 4 + 1] = "0";
			result[i * 4 + 2] = "0";
			result[i * 4 + 3] = "0";
		}
		for (int i = 10; i < 16; i++) {
			result[i * 4] = Integer.toString(i);
			result[i * 4 + 1] = "0";
			result[i * 4 + 2] = "0";
			result[i * 4 + 3] = "0";
		}
		result[64] = "";
		result[65] = "0";
		result[66] = "0";
		result[67] = "0";
		result[68] = "\n";
		result[69] = "0";
		result[70] = "0";
		result[71] = "0";
//做正文
		for (int line = 1; line <= 16; line++) {
			for (int column = 0; column <= 15; column++) {
				System.arraycopy(printblock(chunkFromX + column, includeY, chunkFromZ + line -1), 0, block, 0, 4);
				result[72*line+4*column+0]=" "+block[0];
				result[72*line+4*column+1]=block[1];
				result[72*line+4*column+2]=block[2];
				result[72*line+4*column+3]=block[3];
			}
			result[72*(line+1)-8]=Integer.toString(line-1);
			result[72*(line+1)-7]="0";
			result[72*(line+1)-6]="0";
			result[72*(line+1)-5]="0";
			result[72*(line+1)-4]="\n";
			result[72*(line+1)-3]="0";
			result[72*(line+1)-2]="0";
			result[72*(line+1)-1]="0";
		}

		return result;
	}

}
