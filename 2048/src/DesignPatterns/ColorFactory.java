package DesignPatterns;
import java.awt.Color;
//简单工厂模式和工厂方法和抽象工厂，桥接模式，装饰者模式,外观模式
//体现了外观模式，定义统一的方法和接口

interface ColorInterface {}//统一接口
interface ColorInterface1{ Color createWord();}//基础接口
interface CreateFore{Color createColor(int x,int y,int z);}
class Facade{
	CreateFore createFore;
	private BackFactory backFactory=new BackFactory();
	private WordFactory wordFactory=new WordFactory();
	private ForeFactory foreFactory=new ForeFactory(createFore, 204, 192, 178);
	public BackFactory getBackFactory() {
		return backFactory;
	}
	public void setBackFactory(BackFactory backFactory) {
		this.backFactory = backFactory;
	}
	public WordFactory getWordFactory() {
		return wordFactory;
	}
	public void setWordFactory(WordFactory wordFactory) {
		this.wordFactory = wordFactory;
	}
	public ForeFactory getForeFactory() {
		return foreFactory;
	}
	public void setForeFactory(ForeFactory foreFactory) {
		this.foreFactory = foreFactory;
	}

	
}
class BackFactory  implements ColorInterface{//简单工厂，适配器模式中的类适配
	public static Color backFactoty() {
		return new Color(190, 173, 160);
	}
	public static Color ssFactoty() {
		return new Color(230, 214, 140);
	}
	public static Color createBack(String type) {//工厂方法
		Color back = null;
		if ("simple".equals(type)) {
			back=backFactoty();
		}else if ("ss".equals(type)) {
			back=ssFactoty();
		}else {
			System.out.println("创建失败");
		}
		return back;
	}
	
}
//下面是桥接模式 ，将抽象部分与实现部分分离，使它们都可以独立的变化。
class CreateColor implements CreateFore{

	@Override
	public Color createColor(int x, int y, int z) {
		// TODO Auto-generated method stub
		return new Color(x, y, z);
	}

	
}
abstract class AbstractFore{
    CreateFore createFore;
    AbstractFore(CreateFore createFore) {
		this.createFore=createFore;
	}

	   public abstract Color create();  
	
}
 class ForeFactory extends AbstractFore{
	 private int x, y,z;
	 public ForeFactory(CreateFore createFore, int x, int y, int z) {
		super(createFore);
		this.x = x;
		this.y = y;
		this.z = z;
	}
	protected ForeFactory(CreateFore creaateFore) {
		super(creaateFore);
		// TODO Auto-generated constructor stub
	}

//定义前景色工厂类， 工厂方法
@Override
public Color create() {//创建ForeFactory工厂
	if (createFore!=null) {
		return createFore.createColor(x, y, z);
	}
return null;
}

 }
 abstract class Word  implements ColorInterface{//抽象工厂基类

	public static Color createWord1(){
		return new Color(232, 216, 203);
	}
 }

 class WordFactory  implements ColorInterface1{//定义单词色工厂类 
private ColorInterface1 colorInterface1;
public void decorator() {//装饰者模式
	System.out.println("装饰者模式加入功能");
}
@Override
public Color createWord() {
	// TODO Auto-generated method stub
	decorator();
	return Word.createWord1();
}

 }
 
  public class ColorFactory {
	 static Facade facade=new Facade();
  public static Color getBackgroundColor() {
	return (Color) facade.getBackFactory().createBack("simple");
  }
  public static Color getForegroundColor() {
	  
		return (Color)facade.getForeFactory().create();
	  }
  public static Color getWordgroundColor() {
	  WordFactory wordFactory=new WordFactory();
		return (Color) facade.getWordFactory().createWord();
	  }
    
}
