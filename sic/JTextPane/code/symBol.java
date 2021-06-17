package ntou.cs.java2021.finalproject;
class symBol {
	private String symBolCode = "";
	private String location = "";
 
	public symBol(String symBolCode, String location) {
		this.symBolCode = symBolCode;
		this.location = location;
	}
 
	public String getsymBolCode() {
		return symBolCode;
	}
 
	public String getLocation() {
		return location;
	}
 
}