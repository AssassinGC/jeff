package Game2048;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import bean.User;
import jdbc.JdbcUtil;
import redis.Redis2048;

public class Java2048Util implements ActionListener{
	JdbcUtil jbJdbcUtil=new JdbcUtil();
	Redis2048 redis2048=new Redis2048();
	JFrame f=new JFrame();//定义登录框
	JButton jb=new JButton("进入");//按钮
	JLabel lName = new JLabel("昵称：");//
	// 输入框
	private JTextField tfName = new JTextField("");
	
	public JTextField getTfName() {
		return tfName;
	}


	public void setTfName(JTextField tfName) {
		this.tfName = tfName;
	}


	public void loginFrame() {//登录的方法
		f.setTitle("欢迎进入2048");
		f.setSize(400, 300);
		f.setLocation(200, 240);
		f.setLayout(new FlowLayout());
		tfName.setText("");
		tfName.setPreferredSize(new Dimension(80, 30));
		f.add(lName);
		f.add(tfName);
		f.add(jb);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
        jb.addActionListener(this);
	}


	@Override
	public void actionPerformed(ActionEvent e) {//监听
		// TODO Auto-generated method stub
		if (e.getSource()==jb) {//是否点击按钮
			f.dispose();
			String username=tfName.getText();//获取输入框的昵称
			int highestScore = 0;
			Connection conn = JdbcUtil.getConn();//定义Connection对象
			Statement st = null;//定义Statement对象
			ResultSet rs = null;//定义ResultSet对象
			
	
				 if (redis2048.checkLoginRedis(username)) {
						System.out.println("Redis中已存在");
						highestScore=redis2048.getHighestScore(username);//从Redis中取出username最高分数	
				}else {
					//是新的昵称
					redis2048.setRankingList(username, highestScore);//Redis缓存中没有，加入缓存中
				}

		Java2048Util ju=new Java2048Util();
		JFrame frame=new Java2048(username,highestScore);
			frame.setTitle("Java2048");
			frame.setSize(640,620);
			frame.setLocationRelativeTo(null);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE );
			frame.setVisible(true);
		
		}
	}

   public void updateHighestScore(String username,int highestScore) {//更新最高分数
	//登录的时候昵称信息已经加入缓存之中

			int highest = redis2048.getHighestScore(username);//获取数据的最高分
			if (highestScore>highest) {//如果大于最高分更新缓存里的	
				redis2048.setRankingList(username, highestScore);
			}
	 
   }

   public List<User> findten(){//从数据库中查询最高分数前10名的数据`-
		List<User> list=redis2048.getRankingList(0,9);
	   return list;
   }
   
}

