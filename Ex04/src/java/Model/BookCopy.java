/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Kotya
 */
public class BookCopy {

	private String COPY_CODE; //isbn_copyCode
	private BookCondition copyCondition;

	public BookCopy() {
	}

	public String getCOPY_CODE() {
		return COPY_CODE;
	}

	public void setCOPY_CODE(String COPY_CODE) {
		this.COPY_CODE = COPY_CODE;
	}

	public BookCondition getCopyCondition() {
		return copyCondition;
	}

	public void setCopyCondition(BookCondition copyCondition) {
		this.copyCondition = copyCondition;
	}

}
