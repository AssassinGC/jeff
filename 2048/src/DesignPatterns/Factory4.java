package DesignPatterns;

import java.awt.Font;
import java.util.Random;
//原型模式，代理模式，建造模式，单态模式
interface ProxyInterface{ public WordFont constructWordFont();}
 class WordFont extends Font{//具体的字体属性
	private String name=null;
	private	int style=0;
	private	int size=0;
	    public WordFont(String name, int style, int size) {
		super(name, style, size);
		// TODO Auto-generated constructor stub
	}

		public String getName() {//获取name属性
			return name;
		}
		public void setName(String name) {//设置name属性
			this.name = name;
		}
		public int getStyle() {//获取style属性
			return style;
		}
		public void setStyle(int style) {//设置style属性
			this.style = style;
		}
		public int getSize() {//获取size属性
			return size;
		}
		public void setSize(int size) {//设置size属性
			this.size = size;
		}
		
	}
 //抽象建造
 interface Builder{
	 public WordFont bulidWordFont();//建造WordFont
 }
 //具体的建造
 class WordFontBuilder implements Builder{
	 private WordFont wordFont=new WordFont("微软雅黑", Font.PLAIN, 25);
	   

	@Override
	public WordFont bulidWordFont() {//建造WordFont
		// TODO Auto-generated method stub
		return wordFont;
	}
	 
 }
 class Director implements ProxyInterface{//建造者
	 public  WordFont constructWordFont() {
		 WordFontBuilder wordFontBuilder=new WordFontBuilder();

		 return wordFontBuilder.bulidWordFont();
	 }


 }
 
 

 class Proxy implements ProxyInterface{//代理模式
    private  ProxyInterface proxy= new Director();
	@Override
	public WordFont constructWordFont() {

		return proxy.constructWordFont();
	}

	}
 //****************************************************
class Prototype extends Font implements Cloneable{//原型模式，浅拷贝
	public Prototype(String name, int style, int size) {
		super(name, style, size);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected Font clone() throws CloneNotSupportedException {//重写clone方法
		// TODO Auto-generated method stub
		return (Font) super.clone();
	}
	public static Font createNumberFont(){
		Prototype prototype=new Prototype("微软雅黑", Font.BOLD, 35);
		Prototype prototype2 = null;
		try {
			prototype2 = (Prototype) prototype.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return prototype2;
	}
 }
 
 
//******************************************************
class Singleton extends Random{//单态模式，采用的是内部类实现
	private Singleton(){}//私有构造方法

    private static class SingletonBuild{//私有内部类
        private static Singleton value = new Singleton();
    }
    public  static Singleton getInstance(){ return  SingletonBuild.value ;}
    
}
public class Factory4 {
	
	public static Random getRandom() {
		return Singleton.getInstance();
	}
	public static Font getWordFont() {
		Proxy proxy=new Proxy();
		return proxy.constructWordFont();
	}
	public static Font getNumberFont() throws Exception{
		Font font =Prototype.createNumberFont();
		return font;
	}

}
