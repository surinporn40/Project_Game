package display;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import Character.*;
import Element.Element;
import event.Event;

public class Game extends JPanel implements KeyListener{
    
	private static final long serialVersionUID = 1L;
	
	private static int speed = 100,boySize = 100 ,slimeHeight = 50;
	private static int base=400,xStart = 1000;      
	private long point = 0,lastPress=0;
	
	private Boy boy = new Boy(100,450);
	static Display display;
//	------------------Wave SIze ----------------------------
	private Slime[] slimeSet = makeSlime(2);
	
//--------------------Cloud--------------------------------
	private Environment[] envSet = makeEnv(2,Environment.CLOUD);
	private Environment building = new Environment(xStart-100,base-150,this,Environment.BUILDING,4);
	
		public Game(){
			
			this.setBounds(0,0,1000,600);
			this.addKeyListener(this);
			this.setLayout(null);
			this.setFocusable(true);
		}
	
	@Override
	public void paint(Graphics g) {
			try {
				super.paint(g);
				Graphics2D g2 = (Graphics2D) g;
				this.drawBackground(g2);
				//---POINT----
				g2.setFont(Element.getFont(30));
				g2.setColor(Color.white);
				g2.drawString("Point : "+point,750,40);
				//--- boy --
				g2.setColor(Color.RED);
				drawBoyHealth(g2);
				g2.drawImage(boy.getImage(),boy.x,boy.y,boySize,boySize, null);
				//----Slime----
				for(Slime item : slimeSet) {
					drawSlime(item,g2);
				}
				this.point+=1;
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	private void drawBackground(Graphics2D g2) throws IOException {
			g2.drawImage(ImageIO.read(new File("img\\sky.png")),0,0,2000,1000, null);
			g2.drawImage(building.getImage(),building.x,building.y,500,200,null);
			g2.drawImage(ImageIO.read(new File("img\\grass1.png")),0,base+10,1000,220, null);
			
			for(Environment item:envSet) {
				g2.drawImage(item.getImage(),item.x,item.y,250,160, null);
			}
	}
	
	private void drawBoyHealth(Graphics2D g2) {
		try {
			g2.drawImage(ImageIO.read(new File("img\\heart.png")),10,20, 20,20,null);
			g2.setStroke(new BasicStroke(18.0f));
			g2.setColor(new Color(241, 98, 69));
			g2.drawLine(60, 30,60+boy.health,30);	
			g2.setColor(Color.white);
			g2.setStroke(new BasicStroke(6.0f));
			g2.drawRect(50,20, 220,20);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Slime[] makeSlime(int size) {
		Slime[] slimeSet = new Slime[size];
		int far = 500;
		for(int i=0;i<size;i++) {
			slimeSet[i] = new Slime(xStart+far,base,speed,this);
			far+=500;
		}
		return slimeSet;
	}
	
	private Environment[] makeEnv(int size,int eType){
		Environment[] envSet = new Environment[size];
		int far = 0;
		for(int i=0;i<size;i++) {
			envSet[i] = new Environment(xStart+far,20,this,eType,10);
			far+=600;
		}
		return envSet;
	}
	
	private void drawSlime(Slime slime,Graphics2D g2) {
			g2.drawImage(slime.getImage(),slime.x ,500,80,slimeHeight+1,null);
			if(Event.checkHit(boy,slime,boySize,slimeHeight)){
					g2.setColor(new Color(241, 98, 69)); //สี
					g2.fillRect(0, 0,1000,1000);	//ชน		
					boy.health-=20;
					if(boy.health<=0) {
						display.endGame(this.point);
						boy.health = new Boy().health;
						this.point = 0;	
					}
			}
	}
	
    @Override
    public void keyPressed(KeyEvent e) {
        if(System.currentTimeMillis() - lastPress > 200){  //200 is enough ----> Double Jump
            if(e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode()==38){  
                //if(e.getKeyCode()==38 || e.getKeyCode()==32){  
                //==KeyEvent.VK_SPACE
                    boy.jump(this);
                    //this.repaint();
                    lastPress = System.currentTimeMillis(); //keep current time in lastPress
            }
        }
    }
	
	

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void keyReleased(KeyEvent e) {
		//---
	}
	
	public static void main(String[] arg) {
		display = new Display();
   }

}
