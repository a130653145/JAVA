package ntou.cs.java2021.finalproject;
class Data {
		//ADD,a,#0365 
		//ADD,STEXAZ,#0365
	private String firstCode = "";//ADD
	private String secondCode = "";//A
	private String thirdCode = "";//#0365
	private String notCode = "";//STEXAZ

	public Data(String firstCode, String secondCode, String thirdCode, String notCode) {
		this.firstCode = firstCode;
		this.secondCode = secondCode;
		this.thirdCode = thirdCode;
		this.notCode = notCode;
	}
 
	public String getFirstCode() {
		return firstCode;
	}
 
	public String getSecondCode() {
		return secondCode;
	}
 
	public String getThirdCode() {
		return thirdCode;
	}
 
	public String getStr() {
		return notCode;
	}
 
}