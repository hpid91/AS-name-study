
public class AS {

	private String num;
	private String name;
	private String description;
	private String type;
	
	
	
	public AS(String num, String name) {
		super();
		this.num = num;
		this.name = name;
		this.description = "";
		this.type = "";
	}

	public AS(String num, String name, String description, String type) {
		super();
		this.num = num;
		this.name = name;
		this.description = description;
		this.type = type;
	}
	
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return num + "#" + name + "#"
				+ description + "#" + type ;
	}
	
}
