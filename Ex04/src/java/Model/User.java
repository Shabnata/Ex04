
package Model;

import java.io.Serializable;

public class User implements Serializable{

	private String userID;
	private String userPas;
	private String userType;

	public User(){
	}

	public String getUserID(){
		return userID;
	}

	public void setUserID(String userID){
		this.userID = userID;
	}

	public String getUserPas(){
		return userPas;
	}

	public void setUserPas(String userPas){
		this.userPas = userPas;
	}

	public String getUserType(){
		return userType;
	}

	public void setUserType(String userType){
		this.userType = userType;
	}

}
