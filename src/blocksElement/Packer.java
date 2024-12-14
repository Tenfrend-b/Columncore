package blocksElement;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

public class Packer {
    Element element;
    
    //检验被包装的元素是否相等
	@Override
	public boolean equals(Object obj) {
		return this.element.equals(obj);
	}

	//设定构造方式，就是说每个BlockCxx生成都要包装一个元素
	public Packer(Element innerElement) {
		this.element = innerElement;
	}
	
	//以下是对四个BlockCxx里对元素本体方法的引用
	/*
	 * “重写”的方法
	 * setText():增加了返回值this
	 */
	Packer setText(String text) {
		element.setText(text);
		return this;
	}
	
    Document getDocument(){
        return element.getDocument();
    }

    Attribute attribute (String name){
        return element.attribute(name);
    }

    String attributeValue(String name){
        return element.attributeValue(name);
    }

    String attributeValue(String name,String defaultValue){
        return element.attributeValue(name,defaultValue);
    }

    boolean remove(Attribute attribute){
        return element.remove(attribute);
    }

    Element addAttribute(String name,String value){
        return element.addAttribute(name,value);
    }

    String getName(){
        return element.getName();
    }

    String getText(){
        return element.getText();
    }

    Element getParent(){
        return element.getParent();
    }

    public Element element(String name){
        return element.element(name);
    }
    
    public List<Element> elements() {
    	return element.elements();
    }
}
