package redis;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;

import bean.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

public class Redis2048 {
	public static Jedis jedis = new Jedis("127.0.0.1", 6379);//创建连接对象

	public List<User> getRankingList(int start,int end) {//获取排行榜前十名信息
		List<User> list = new ArrayList<User>();//定义ArrayList对象
		Set<Tuple> zrevrange = jedis.zrevrangeWithScores("rankingList", start, end);//查询前10名的信息
		for (Tuple tuple : zrevrange) {//遍历sortedset的zrevrange
			list.add(new User(tuple.getElement(),(int)tuple.getScore()));//存入ArrayList集合里,getElement获取该昵称，getScore货物最高分
		}
		return list;
	}
    
	public void setRankingList(String userName, int highestScore) {//设置排行榜里的昵称信息
		jedis.zadd("rankingList", highestScore, userName);
	}

	public boolean checkLoginRedis(String userName) {//登录时候检查Redis中是否有该昵称的信息
		boolean flag=false;//定义一个标志,判断
		if (jedis.zrevrank("rankingList", userName)!=null) {//如果zrevrank命令返回的是null,则缓存中没有
			flag=true;
		}
		return flag;
	}
    public int getHighestScore(String userName) {//根据userName获取HighestScore
    	return jedis.zscore("rankingList", userName).intValue();
    }

}
