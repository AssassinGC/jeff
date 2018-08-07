package bean;

public class User {
	private int userId;
	private String userName;
	private int highestScore;
	
	public User(String userName, int highestScore) {
		super();
		this.userName = userName;
		this.highestScore = highestScore;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getHighestScore() {
		return highestScore;
	}
	public void setHighestScore(int highestScore) {
		this.highestScore = highestScore;
	}
}
