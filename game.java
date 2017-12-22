import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Display {
private int width;
public static JFrame frame;
public static Canvas canvas;

 public Display(int width){
	this.width = width;
 	createDisplay();
}
public void createDisplay(){
	frame = new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
    frame.setSize(width, width);
    canvas = new Canvas();  
    frame.add(canvas);
    canvas.setPreferredSize(new Dimension(width,width));
    canvas.setFocusable(false);
     frame.pack();
}
}

	 package main;

import setUp.setUp;

public class main {
 
	 public static void main(String []args){
setUp set = new setUp( 480 );
set.start();
					}
}
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;

import display.Display;

public class setUp implements Runnable ,  KeyListener {
    private int   width;
    private Thread thread;
    private Display display;
    private boolean running;
    private BufferStrategy buffer;
    private Graphics  g; 
    int brickx = 70;
    int bricky = 50;
    int batx = 160;
    int baty = 390+40;
    int ballx = 160;
    int bally = 380+40;
    boolean left ,right;

    int movex = -1;
    int movey = -1;


    Rectangle Ball = new Rectangle(ballx, bally, 15, 15);
    Rectangle Bat = new Rectangle(batx, baty, 30, 10);
    Rectangle[] Brick;  

    public setUp( int width){
		 this.width = width;     
		 Brick = new Rectangle[13];
	 }
	 public void init(){
		 display = new Display(width) ;
           display.frame.addKeyListener(this);
		 for (int i = 0; i = 430){
        movex = -movex;
        }
        if(Bat.intersects(Ball)){
        	movey = -movey;
        }
        if(Ball.y <= 40 || Ball.y >= 430){
            movey = -movey;
            }
        
		 for (int i = 0; i < Brick.length; i++) {
			 if (Brick[i] != null) {
			 if (Brick[i].intersects(Ball)) {
				  movey = -movey;
                  Brick[i] = null;
		 
			 }// end of 2nd if..
			 }// end of 1st if..
			 }
	 }
	 public void render(){
	  buffer = display.canvas.getBufferStrategy();
	  if(buffer == null){
		  display.canvas.createBufferStrategy(3);
	   return ;
	  }
	  g =  buffer.getDrawGraphics();
	  g.clearRect(0, 0, width, width);
	   //draw
	  
	  //draw a rectangle in center
	     g.setColor(Color.white);
         g.fillRect(40, 40,400,400);
	  
	 //draw Ball
	  g.setColor(Color.blue);
	  g.fillOval(Ball.x, Ball.y, Ball.width, Ball.height);
	  
	  //draw bat
	  g.setColor(Color.green);
	  g.fillRect(Bat.x, Bat.y, Bat.width, Bat.height );
 	 
	  // draw brick

		 
	  for (int i = 0; i < Brick.length; i++) {
	       if (Brick[i] != null) {
	 g.setColor(Color.blue);
	 g.fillRect(Brick[i].x-2, Brick[i].y-2, Brick[i].width+4,Brick[i].height+4);
   
     g.setColor(Color.RED);
	 g.fillRect(Brick[i].x, Brick[i].y, Brick[i].width,Brick[i].height);
	      
	       }
	   
	     }

       //end draw;		

	  buffer.show();
	  g.dispose();
	
	 }
	 public  synchronized void start(){
	         if(running)
	        	 return;
	        	 running = true;
	         thread = new Thread(this);
	         thread.start();
	 }
	 public synchronized void stop(){
		 if(!(running))
			 return ;
			 running = false;
		  try {
			thread.join();
		} catch (InterruptedException e) {
 			e.printStackTrace();
		}
	 }
	 public void run(){
		 init();
		 while(running){
			 tick();
 		     render();
	           Thread.currentThread();
	           try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
	 }
	@Override
	public void keyPressed(KeyEvent e) {
int source = e.getKeyCode();	
if(source == KeyEvent.VK_LEFT){
	System.out.println("hey");
left  = true;}if(source == KeyEvent.VK_RIGHT){
	right = true;
}
	}
 	public void keyReleased(KeyEvent e) {
	 int source = e.getKeyCode();
	 if(source == KeyEvent.VK_LEFT){
		 left = false;
		}if(source == KeyEvent.VK_RIGHT){
			right  = false;
		}
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}