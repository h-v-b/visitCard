package visitCard;

import controlP5.ControlP5;
import controlP5.Slider;
import processing.core.PApplet;
import toxi.*;
import toxi.geom.*;
import toxi.geom.mesh.*;
import toxi.gui.GUIElement;
import toxi.gui.GUIManager;
import toxi.gui.Range;
import peasy.*;



public class visitCard extends PApplet{
	
	int radius;
	int circleRes;
	int circlesAmount = 5;
	ControlP5 cp5;
	PeasyCam cam;
	float rotationSpeed;
	
	public void setup(){
		size(600,600,P3D);
		smooth();
		cam = new PeasyCam(this, 100);
		colorMode(HSB);
		initGUI();
	}
	
	public void draw(){
		// force default coordsys
		camera();
		// set up lights
		pointLight(255 , 240, 190, 0, -500, 1000);
		// manually set peasycam
		cam.feed();
		
		background(0);
		gui();
		if(cp5.isMouseOver()){
			cam.stop();
		}
		//Vec3D pos = new Vec3D(width/4, 0,0);
		//Vec3D pos2 = new Vec3D(width*(3/4), 0,0);
		circlesAmount = circleRes;
		for(int i=0; i<circlesAmount; i++){
			float x = cos( i*TWO_PI/circlesAmount)*radius;
			float y = sin( i*TWO_PI/circlesAmount)*radius;
			Vec3D pos = new Vec3D(x+(radius),y+(radius),0);
			drawCircle(circleRes, pos, i*10);
		}
		//drawCircle(circleRes, pos, 155);
		//drawCircle(circleRes, pos2,255);
		
		
		
	}
	
	public void drawCircle(int dotsAmount, Vec3D centerPos, int c){
		

		pushMatrix();
		stroke(255);
		translate(centerPos.x, centerPos.y, centerPos.z);
		strokeWeight(10);
		point(0,0,0);
		
		Vec3D currentPos = new Vec3D();
		Vec3D previousPos = new Vec3D();
		Vec3D startPos = new Vec3D();
		for (int i = 0; i<dotsAmount; i++){
			fill(255,0,c, 255);
			float x = cos( i*TWO_PI/dotsAmount)*radius;
			float y = sin( i*TWO_PI/dotsAmount)*radius;
			currentPos.x = x;
			currentPos.y = y;
			currentPos.z = 50*sin(i+(frameCount)*TWO_PI/100);
			
			strokeWeight(1);
			
			if(i==0){
				startPos.x = currentPos.x;
				startPos.y = currentPos.y;
				startPos.z = currentPos.z;
			}
			if(i>0 && i < dotsAmount){
				/*beginShape(TRIANGLES);
				vertex(0,0,0);
				vertex(previousPos.x, previousPos.y, previousPos.z);
				vertex(currentPos.x,currentPos.y,currentPos.z);
				endShape();
				*/
				Face f = new Face(new Vertex(startPos,0), new Vertex(previousPos,i-1), new Vertex(currentPos,i));
			}
			
			if(i==dotsAmount-1){
				/*beginShape(TRIANGLES);
				vertex(0,0,0);
				vertex(startPos.x, startPos.y,startPos.z);
				vertex(currentPos.x,currentPos.y,currentPos.z);
				endShape();
				*/
				Face f = new Face(new Vertex(startPos,0), new Vertex(previousPos,i-1), new Vertex(currentPos,i));
			}
			
			strokeWeight(10);
			point(currentPos.x,currentPos.y,currentPos.z);
			previousPos.x = currentPos.x;
			previousPos.y = currentPos.y;
			previousPos.z = currentPos.z;
		}
		popMatrix();
	}
	
	private void initGUI() {
		cp5 = new ControlP5(this);
		cp5.setAutoDraw(false);
		  cp5.addSlider("radius")
		     .setPosition(50,50)
		     .setRange(4,width)
		     ;
		  
		  cp5.addSlider("circleRes")
		     .setPosition(50,60)
		     .setRange(2,12)
		     ;
		
	}
	
	public void gui() {
		  hint(DISABLE_DEPTH_TEST);
		  cam.beginHUD();
		  cp5.draw();
		  cam.endHUD();
		  hint(ENABLE_DEPTH_TEST);
		}

}
