package oldMethod;

public class BlockMethod {

	public static void main(String arg[]) {
		Stenfatic st = new Stenfatic();
//每次上线都给我好好看看自己是什么环境！		
//		String evirPath = "C:\\Users\\seewo\\Documents\\My Bluetooth\\STENFATICA\\src\\";
//		String evirPath = "C:\\Users\\Administrator\\eclipse-workspace\\高220杨东箭学习资料\\src\\";
		String evirPath = "D:\\Program Files\\206.eclipse\\workingSpace\\STENFATICA\\src\\";
		String filePath = "blocksData/structure.xml";
		st.read(filePath);
		st.cleair();
		
		st.save(evirPath, filePath);
		st.close();
	}
}
