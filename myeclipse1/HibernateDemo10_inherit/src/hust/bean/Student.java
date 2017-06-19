package hust.bean;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_student")
public class Student extends Person{
	
	private int score;
	
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
}
