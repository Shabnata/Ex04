
package Model;

import java.io.Serializable;
import java.time.Year;

public class Book implements Serializable{

	private String ISBN;
	private String title;
	private String authorName;
	private Year bookYear;
	private String coverPath;
	private int copyCounter;
	private Category category;
	private int availableCopies;

	public Book(){
	}

	public String getISBN(){
		return ISBN;
	}

	public void setISBN(String ISBN){
		this.ISBN = ISBN;
	}

	public String getTitle(){
		return title;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getAuthorName(){
		return authorName;
	}

	public void setAuthorName(String authorName){
		this.authorName = authorName;
	}

	public Year getBookYear(){
		return bookYear;
	}

	public void setBookYear(Year bookYear){
		this.bookYear = bookYear;
	}

	public String getCoverPath(){
		return coverPath;
	}

	public void setCoverPath(String coverPath){
		this.coverPath = coverPath;
	}

	public int getCopyCounter(){
		return copyCounter;
	}

	public void setCopyCounter(int copyCounter){
		this.copyCounter = copyCounter;
	}

	public Category getCategory(){
		return category;
	}

	public void setCategory(Category category){
		this.category = category;
	}

	public int getAvailableCopies(){
		return availableCopies;
	}

	public void setAvailableCopies(int availableCopies){
		this.availableCopies = availableCopies;
	}

}
