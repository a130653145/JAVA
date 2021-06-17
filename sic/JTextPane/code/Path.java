package ntou.cs.java2021.finalproject;
public class Path {
	private String inputFilePath;
	private String outputFilePath;
	private String imgPath;

	public void takePath() {
		String path = "\\"+System.getProperty("user.dir");
		int findCodePathToGetRealPath = 0;
		findCodePathToGetRealPath=path.indexOf("\\code");//xxx.sic.code
		path=path.substring(0,findCodePathToGetRealPath);//xxx.sic
		imgPath=path+"\\img\\";//xxx.sic.img
		inputFilePath= path+"\\inputFile\\";//xxx.sic.inputFile
		outputFilePath= path+"\\outputFile\\";//xxx.sic.outputFile
	}
	public String getImgPath() {
		return imgPath;
	}
	public String getInputFilePath() {
		return inputFilePath;
	}
	public String getOutputFilePath() {
		return outputFilePath;
	}

	@Override
	public String toString() {
		return "PATH:\n"+imgPath+"\n"+inputFilePath+"\n"+outputFilePath+"\n";
	}

}