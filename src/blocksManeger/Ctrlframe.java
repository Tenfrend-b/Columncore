package blocksManeger;

import blocksElement.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.swing.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Ctrlframe extends JFrame {
		
	Stenfatic editor;//存档编辑器
	String environmentPath, structureFile, resourceFile;//编辑器操作的路径

    //获取存档路径，以及加载编辑器
    public Ctrlframe() {
        super("Tenfrend_b's 3D Schematic judge program");
        //以下三行根据实际需要更改
        environmentPath = System.getProperty("user.dir")+"/";
        structureFile = "blocksData/structure.xml";
        resourceFile = "blocksData/resource.xml";
        InputStream streamDoc = Ctrlframe.class.getClassLoader().getResourceAsStream(structureFile);
        SAXReader sax = new SAXReader();
        try {
            editor = new Stenfatic(sax.read(streamDoc));
        } catch (DocumentException e) {
        	e.printStackTrace();
        }
    }
    
    //把修改后的文档写入data里
    public boolean save(String environmentPath, String structureFile) {
		FileOutputStream outer;
		boolean ifSucceed = false;
		try {
			outer = new FileOutputStream(new File(environmentPath + structureFile));
			XMLWriter wtr = new XMLWriter(outer, OutputFormat.createPrettyPrint());
			wtr.write(editor.structureDoc);
			wtr.flush();
			wtr.close();
			ifSucceed = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ifSucceed;
	}

    //计划里该程序会以此方法运行
    public static void main(String[] arg){
        new Ctrlframe();
    }

}
