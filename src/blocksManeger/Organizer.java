package blocksManeger;

import org.dom4j.Document;

import blocksElement.*;

/*
 * 应该是整个算法的核心内容
 * 目前还是一个空类，但是未来会把方法给填充好
 */
public class Organizer implements Comparable<Organizer>{
	int index;
	int[] positions;//长度为六
	
	@Override//用于Stenfatic编辑organizers后的重组
	public int compareTo(Organizer o) {
		return Integer.compare(index, o.index);
	}
	
	public Organizer(BlockCountry country, int index) {
		this.index = index;
		positions = country.range();
		//通过country限制结构判断的区域
	}
	
	public Organizer changeIndex(int index) {
		this.index = index;
		return this;
	}
	
	public Organizer check() {
		//对整个country进行扫描，从零开始生成柱、环、壳
		return this;
	}
	
	public void check(BlockColumn column) {
		//这里判断一个柱体与周围柱体的粘连情况，并且会修改属性
	}
	
	public void check(BlockCircle circle) {
		//这里判断一个环体与上下环体的粘连情况，一样要修改属性
	}
	
	public void check(BlockCube cube) {
		//一个不常用的算法，用于判断不同的舱体是否重叠，并且对重叠区域进行处理
	}
	
	public void track(BlockCore core) {
		//用于识别此方块可能导致的结构变动
	}
}
