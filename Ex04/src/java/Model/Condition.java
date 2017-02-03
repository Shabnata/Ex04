
package Model;

import java.io.Serializable;

/**
 *
 * @author Kotya
 */
public class Condition implements Serializable{
	private int conKey;
	private String conDesc;

	public Condition() {
	}

	public int getConKey() {
		return conKey;
	}

	public void setConKey(int conKey) {
		this.conKey = conKey;
	}

	public String getConDesc() {
		return conDesc;
	}

	public void setConDesc(String conDesc) {
		this.conDesc = conDesc;
	}
	

}
