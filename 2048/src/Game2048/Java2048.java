package Game2048;

import java.awt.*;
import java.awt.event.*;
//import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import DesignPatterns.ColorFactory;
import DesignPatterns.Factory4;
import bean.User;
public class Java2048 extends JFrame implements java.awt.event.KeyListener{ 
	Java2048Util java2048Util=new Java2048Util();
private static final long serialVersionUID = 1L;
//Color background = new Color(190, 173, 160); 背景色
Color background = ColorFactory.getBackgroundColor();// 背景色
//Color foreground = new Color(204, 192, 178); 前景色
Color foreground = ColorFactory.getForegroundColor();// 前景色
//Color wordColor = new Color(232, 216, 203); 单词色
Color wordColor = ColorFactory.getWordgroundColor();// 单词色

//Font wordFont = new Font("微软雅黑", Font.PLAIN, 25); 单词字体
Font wordFont = Factory4.getWordFont();// 单词字体

//Font numberFont = new Font("微软雅黑", Font.BOLD, 35); 数字字体

public  Font numberFont() throws Exception {// 数字字体方法
	Font numberFont= Factory4.getNumberFont();
	return numberFont;
}

  
//Random random=new Random();//随机数发生器
Random random=Factory4.getRandom();//随机数发生器
int[][] array;//设定2维数组
//逐行或逐列处理数组,第一个参数为实际数字,第二个为判断值,用来判断是否应该消除相同的数字
int[][] process=new int [4][2];
int score;//分数,初始化为零
int yxmode;
private String userName;//昵称

private int highestScore;//游戏中最高分
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

int biggestNumber=0; 
boolean ifGenerate2or4;
int[] rd=new int[16];//生成随机2或4的位置
private Graphics gBuffer;
//构造方法
public Java2048(String userName,int highestScore){
	this.userName=userName;
	this.highestScore=highestScore;
addKeyListener(this);
addWindowListener(new WindowAdapter(){
public void windowClosing(WindowEvent e){
if(JOptionPane.showConfirmDialog(null, "确定退出?", "提示",
   JOptionPane.YES_NO_OPTION)==JOptionPane.YES_NO_OPTION){
System.exit(0);
}
}
});
restart();
}
void restart(){//重新开始
array=new int[4][4];//清空游戏用2维数组
score=0;//重置分数为零
biggestNumber=0;//重置最大数字为零
//在游戏区随机生成两个2或4
generateRandom2or4();

generateRandom2or4();
}
//绘图方法
public void paint(Graphics g){
gBuffer=g;
gBuffer.setColor(background);
gBuffer.drawRoundRect(10, 40, 110, 50, 20, 20);
gBuffer.fillRoundRect(10, 40, 110, 50, 20, 20);
gBuffer.drawRoundRect(10, 95, 110, 50, 20, 20);//画昵称显示区
gBuffer.fillRoundRect(10, 95, 110, 50, 20, 20);
gBuffer.drawRoundRect(150, 40, 130, 90, 20, 20);// 画分数显示区
gBuffer.fillRoundRect(150, 40, 130, 90, 20, 20);
gBuffer.drawRoundRect(290, 40, 130, 90, 20, 20);// 画最高分显示区
gBuffer.fillRoundRect(290, 40, 130, 90, 20, 20);
gBuffer.drawRoundRect(470, 40, 200, 360, 20, 20);// 画排行榜显示区
gBuffer.fillRoundRect(470, 40, 200, 360, 20, 20);
gBuffer.drawRoundRect(0, 150, 454, 460, 10, 10);// 画主游戏区
gBuffer.fillRoundRect(0, 150, 454, 460, 10, 10);
gBuffer.setFont(wordFont);
gBuffer.setColor(wordColor);
gBuffer.drawString("2048", 35, 75);
//gBuffer.setColor(new Color(255, 0, 0));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 15));
gBuffer.drawString("昵称:"+userName, 35, 125);
gBuffer.setFont(wordFont);
gBuffer.setColor(wordColor);
gBuffer.drawString("分数", 185, 70);// 画分数
gBuffer.drawString("最高分", 320, 70); // 画最高分
gBuffer.drawString("排行榜", 515, 70); // 画排行榜
try {
	gBuffer.setFont(numberFont());
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}

gBuffer.setColor(Color.white);

if(score<10){
gBuffer.drawString(String.valueOf(score), 200, 115);//画分数数字
}else if(score<100){
gBuffer.drawString(String.valueOf(score), 200, 115);//画分数数字
}else if(score<1000){
gBuffer.drawString(String.valueOf(score), 185, 115);//画分数数字
}else if(score<10000){
gBuffer.drawString(String.valueOf(score), 175, 115);//画分数数字
}else if(score<100000){
gBuffer.drawString(String.valueOf(score), 170, 115);//画分数数字
}
else{
gBuffer.drawString(String.valueOf(score), 170, 115);//画分数数字
}
if(highestScore<10){
gBuffer.drawString(String.valueOf(highestScore), 345, 115);
//画分数数字
}else if(highestScore<100){
gBuffer.drawString(String.valueOf(highestScore), 325, 115);
//画分数数字
}else if(highestScore<1000){
gBuffer.drawString(String.valueOf(highestScore), 325, 115);
//画分数数字
}else if(highestScore<10000){
gBuffer.drawString(String.valueOf(highestScore), 305, 115);
//画分数数字
}else if(highestScore<100000){
	gBuffer.drawString(String.valueOf(highestScore), 305, 115);
//画分数数字
}
else{    
	gBuffer.drawString(String.valueOf(highestScore), 305, 115);
//画分数数字
}
//java2048Util.updateHighestScore(userName, highestScore);
gBuffer.setColor(new Color(122, 113, 104));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 20));
gBuffer.drawString("姓名  最高分",520,  100);
int h=0;
gBuffer.setColor(new Color(122, 113, 104));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 20));
List<User> list = java2048Util.findten();
for (User user : list) {
	gBuffer.drawString(user.getUserName()+"  "+user.getHighestScore(),520,  130+h * 26);
	h++;
}//排行榜的信息

gBuffer.setColor(foreground);
for (int i = 0; i< 4; i++) {
for (int j = 0; j< 4; j++) {
gBuffer.drawRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10);
gBuffer.fillRoundRect(10 + j* 112, 160 + i* 112, 100, 100, 10, 10);
}
}
for(int i=0;i<4;i++){
for(int j=0;j<4;j++){
switch (array[i][j]) {
case 2: {
gBuffer.setColor(new Color(238, 228, 218));
gBuffer.drawRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10); 
gBuffer.fillRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10);
gBuffer.setColor(new Color(122, 113, 104));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 50));
gBuffer.drawString("2", 45 + j * 112, 230 + i * 113);
break;
}
case 4: {
gBuffer.setColor(new Color(236, 224, 200));
gBuffer.drawRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10); 
gBuffer.fillRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10);
gBuffer.setColor(new Color(119, 110, 103));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 50));
gBuffer.drawString("4", 45 + j * 112, 230 + i * 113);
break;
}
case 8: {
gBuffer.setColor(new Color(242, 177, 121));
gBuffer.drawRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10); 
gBuffer.fillRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10);
gBuffer.setColor(new Color(250, 248, 235));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 50));
gBuffer.drawString("8", 45 + j * 112, 230 + i * 113);
break;
}
case 16: {
gBuffer.setColor(new Color(245, 149, 101));
gBuffer.drawRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10); 
gBuffer.fillRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10);
gBuffer.setColor(new Color(252, 244, 242));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 45));
gBuffer.drawString("16", 33 + j * 112, 230 + i * 111);
break;
}
case 32: {
gBuffer.setColor(new Color(245, 124, 95));
gBuffer.drawRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10); 
gBuffer.fillRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10);
gBuffer.setColor(new Color(255, 241, 249));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 45));
gBuffer.drawString("32", 33 + j * 112, 230 + i * 111);
break;
}
case 64: {
gBuffer.setColor(new Color(246, 93, 59));
gBuffer.drawRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10); 
gBuffer.fillRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10);
gBuffer.setColor(new Color(247, 249, 235));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 45));
gBuffer.drawString("64", 33 + j * 112, 230 + i * 111);
break;
}
case 128: {
gBuffer.setColor(new Color(237, 206, 113));
gBuffer.drawRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10); 
gBuffer.fillRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10);
gBuffer.setColor(new Color(248, 246, 255));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 40));
gBuffer.drawString("128", 23 + j * 112, 228 + i * 111);
break;
}
case 256: {
gBuffer.setColor(new Color(237, 204, 97));
gBuffer.drawRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10); 
gBuffer.fillRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10);
gBuffer.setColor(new Color(245, 244, 249));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 40));
gBuffer.drawString("256", 23 + j * 112, 228 + i * 111);
break;
}
case 512: {
gBuffer.setColor(new Color(235, 201, 78));
gBuffer.drawRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10); 
gBuffer.fillRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10);
gBuffer.setColor(new Color(255, 241, 248));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 40));
gBuffer.drawString("512", 23 + j * 112, 228 +i * 111);
break;
}
case 1024: {
gBuffer.setColor(new Color(237, 197, 63));
gBuffer.drawRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10); 
gBuffer.fillRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10);
gBuffer.setColor(new Color(240, 246, 244));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 35));
gBuffer.drawString("1024", 17 + j * 112, 225 + i * 113);
break;
}
case 2048: {
gBuffer.setColor(new Color(238, 194, 46));
gBuffer.drawRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10); 
gBuffer.fillRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10);
gBuffer.setColor(new Color(250, 249, 255));
gBuffer.setFont(new Font("微软雅黑", Font.BOLD, 35));
gBuffer.drawString("2048", 17 + j * 112, 225 + i * 113);
break;
}
default: {
gBuffer.setColor(new Color(204, 192, 178));
//gBuffer.setColor(foreground);
gBuffer.drawRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10); 
gBuffer.fillRoundRect(10 + j * 112, 160 + i * 112, 100, 100, 10, 10);
}
}
}
}
}


public void keyPressed(KeyEvent e) {
  if(e.getKeyCode()==KeyEvent.VK_UP){
    moveUp();
    }else if(e.getKeyCode()==KeyEvent.VK_DOWN){
    moveDown();
    }else if(e.getKeyCode()==KeyEvent.VK_LEFT){
    moveLeft();
    }else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
    moveRight();
    }
    if(biggestNumber>1024){
    switch(biggestNumber){
    case 2048:{
    if(JOptionPane.showConfirmDialog(this, "挑战2048成功!\n 是否继续挑战?", "提示",
     JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
    biggestNumber++;//防止2048重复判断
    }else{
    restart();
    repaint();
    }
    break;
    }
default:;
}
    }
if(judgeFail()){
JOptionPane.showMessageDialog(this,"挑战失败!", "提示", JOptionPane.INFORMATION_MESSAGE);
restart();
repaint();
}

}
@Override
public void keyReleased(KeyEvent e) {
}
@Override
public void keyTyped(KeyEvent e) {
}
//判断是否已经失败,若失败则返回true,否则返回false
public boolean judgeFail(){
for(int j=0;j<4;j++){
for(int i=0;i<4;i++){
process[i][0]=array[i][j];
process[i][1]=1;
}
for(int i=1;i<4;i++){
int k=i;
while(k>0){
if(process[k][0]==0){
return false;
}else if(process[k-1][0]==0){
return false;
}else if(process[k-1][0]==process[k][0]){
return false;
}else{
break;
}
}
}
}
for(int j=0;j<4;j++){
for(int i=3;i>-1;i--){
process[3-i][0]=array[i][j];
process[3-i][1]=1;
}
for(int i=1;i<4;i++){
int k=i;
while(k>0){
if(process[k][0]==0){
return false;
}else if(process[k-1][0]==0){
return false;
}else if(process[k-1][0]==process[k][0]){
return false;
}else{
break;
}
}
}
}
for(int i=0;i<4;i++){
for(int j=0;j<4;j++){
process[j][0]=array[i][j];
process[j][1]=1;
}
for(int l=1;l<4;l++){
int k=l;
while(k>0){
if(process[k][0]==0){
return false;
}else if(process[k-1][0]==0){
return false;
}else if(process[k-1][0]==process[k][0]){
return false;
}else{
break;
}
}
}
}
for(int i=0;i<4;i++){
for(int j=3;j>-1;j--){
process[3-j][0]=array[i][j];
process[3-j][1]=1;
}
for(int l=1;l<4;l++){
int k=l;
while(k>0){
if(process[k][0]==0){
return false;
}else if(process[k-1][0]==0){
return false;
}else if(process[k-1][0]==process[k][0]){
return false;
}else{
break;
}
}
}
}
for(int i=0;i<4;i++){
for(int j=0;j<4;j++){
if(array[i][j]==0){
return false;
}
}
}
return true;
}

public void moveUp(){
ifGenerate2or4=false;
for(int j=0;j<4;j++){
for(int i=0;i<4;i++){
process[i][0]=array[i][j];
process[i][1]=1;
}
processProcess();
for(int i=0;i<4;i++){
array[i][j]=process[i][0];
}
}
if(ifGenerate2or4){
generateRandom2or4(); 
repaint();
}
}
//向下滑动,若各列均没有方块相消或移动,则返回false,否则返回true; 
public void moveDown(){
ifGenerate2or4=false;
for(int j=0;j<4;j++){
for(int i=3;i>-1;i--){
process[3-i][0]=array[i][j];
process[3-i][1]=1;
}
processProcess();
for(int i=3;i>-1;i--){
array[i][j]=process[3-i][0];
}
}
if(ifGenerate2or4){
generateRandom2or4();
repaint();
}
}
//向左滑动,若各行均没有方块相消或移动,则返回false,否则返回true; 
public void moveLeft(){
ifGenerate2or4=false;
for(int i=0;i<4;i++){
for(int j=0;j<4;j++){
process[j][0]=array[i][j];
process[j][1]=1;
}
processProcess();
for(int j=0;j<4;j++){
array[i][j]=process[j][0];
}
}
if(ifGenerate2or4){
generateRandom2or4();
repaint();
}
}
//向右滑动,若各行均没有方块相消或移动,则返回false,否则返回true; 
public void moveRight(){
ifGenerate2or4=false;
for(int i=0;i<4;i++){
for(int j=3;j>-1;j--){
process[3-j][0]=array[i][j];
process[3-j][1]=1;
}
processProcess();
for(int j=3;j>-1;j--){
array[i][j]=process[3-j][0];
}
}
if(ifGenerate2or4){
generateRandom2or4();
repaint();
}
}
 //在游戏区空闲处随机生成2或4
public void generateRandom2or4(){
int rdCount=-1;//记录需要生成2或4的位置的数量
for(int i=0;i<4;i++){
for(int j=0;j<4;j++){
if(array[i][j]==0){
rd[++rdCount]=i*10+j;
}
}
}
int tempI=rd[random.nextInt(rdCount+1)];
if(random.nextDouble()<0.1){//随机生成2和4,其比例大致为9:1 array[tempI/10][tempI%10]=4;
}else{
array[tempI/10][tempI%10]=2;
}
}

//单行或单列处理方法,若该行或列没有方块相消或移动,则返回false,否则返回true; 
public void processProcess(){
    for(int i=1;i<4;i++){
    int k=i;
    while(k>0){
    if(process[k][0]==0){
    break;
    }else if(process[k-1][0]==0){
    process[k-1][0]=process[k][0];
    process[k][0]=0;
    k--;
    ifGenerate2or4=true;
    }else if(process[k-1][0]==process[k][0]){
    if(process[k-1][1]==1){
    ifGenerate2or4=true;
    process[k-1][0]=2*process[k][0];
    process[k][0]=0;
    score+=process[k-1][0];
    if(process[k-1][0]>biggestNumber){
    biggestNumber=process[k-1][0];
    }
    if(score>highestScore){
    highestScore=score;
    java2048Util.updateHighestScore(userName, highestScore);   
  }
    process[k-1][1]=0;
  }
  break;
  }else{
   break;
 }
 }
 }
 }

}