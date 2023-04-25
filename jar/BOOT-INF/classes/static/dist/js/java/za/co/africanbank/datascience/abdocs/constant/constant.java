package za.co.africanbank.datascience.abdocs.constant;

public enum constant {

	PROPERTYFILE("C:/ABDocs_Config/conf/ABDocs.properties");


	private String value;

	constant(String constant) {
		this.value = constant;
	}

	public String value() {
		return value;
	}

}
