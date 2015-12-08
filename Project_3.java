////Tania Reyes
//// Project 3   12-7-15    
//// Pool table elastic collisions
//// objects, loops, and arrays
Ball a,b,c,d,e,q;
PTable t;
Button one, two, three, four;
Bird brd;
Rat rt;
int frame;
int score;

//SETUP: object stuff
void setup() {
  size( 720, 580 );
  t= new PTable();
  t.left=   45;
  t.right=  width-45;
  t.top=    150;
  t.bottom= height-50;
  t.middle= t.left + (t.right-t.left) / 2;
  t.horizon= (height/4)-20;
  t.wall=true;
  //
  a=  new Ball();
  a.r=200;
  a.b=200;
  a.name=  "1";
  //
  b=  new Ball();
  b.g=200;
  b.name=  "2";
  //
  c=  new Ball();
  c.g=200;
  c.b=200;
  c.name=  "3";
  //
  d=  new Ball();
  d.g=110;
  d.b=110;
  d.name=  "4";
  //
  e=  new Ball();
  e.r=110;
  e.name=  "5";
  //
  q=  new Ball();
  q.r=200;
  q.g=200;
  q.b=200;
  q.name=  " ";
  //
  one= new Button(35,5);
  one.words= "Reset";
  //
  two= new Button(140,5);
  two.words= "Wall";
  //
  three= new Button(230,5);
  three.words= "Bird";
  //
  four= new Button(320,5);
  four.words= "Rat";
  //
  brd= new Bird();
  brd.y = 70;
  brd.by = 75;
  //
  rt= new Rat();
  //

  
  }
  //



//RESET function for balls
void reset() {
  a.reset();
  b.reset();
  c.reset();
  d.reset();
  e.reset();
  q.resetCue();
  t.wall = true;
  resetCheck();
}

void resetCheck(){
  if (dist(a.x,a.y,b.x,b.y) <= 30){ reset(); }
  if (dist(a.x,a.y,c.x,c.y) <= 30){ reset(); }
  if (dist(a.x,a.y,d.x,d.y) <= 30){ reset(); }
  if (dist(a.x,a.y,e.x,e.y) <= 30){ reset(); }
  //
  if (dist(b.x,b.y,c.x,c.y) <= 30){ reset(); }
  if (dist(b.x,b.y,d.x,d.y) <= 30){ reset(); }
  if (dist(b.x,b.y,e.x,e.y) <= 30){ reset(); }
  //
  if (dist(c.x,c.y,d.x,d.y) <= 30){ reset(); }
  if (dist(c.x,c.y,e.x,e.y) <= 30){ reset(); }
  //
  if (dist(d.x,d.y,e.x,e.y) <= 30){ reset(); }
}

//main DRAW function
void draw() {
  background( 102,178,205 );
  t.tableDisplay();
  drawGrass();
  balls();
  buttons();
  birds();
  rats();
  frame +=1;
  showScore();
}

void drawGrass(){
  int x = 0;
  strokeWeight(2);
  while (x <= width){
    strokeWeight(1);
    line(x, height, random(x,x+2), height-15);
    x +=5;
  }
}


// handles the COLLISION, MOVEMENT, and DISPLAY of BALLS
void balls() {
  collision( a, b );
  collision( a, c );
  collision( a, d );
  collision( a, e );
  collision( a, q );
  //
  collision( b, c );
  collision( b, d );
  collision( b, e );
  collision( b, q );
  //
  collision( c, d );
  collision( c, e );
  collision( c, q );
  //
  collision( d, e );
  collision( d, q );
  collision( e, q );
  //
  ratCollision( a, rt);
  ratCollision( b, rt);
  ratCollision( c, rt);
  ratCollision( d, rt);
  ratCollision( e, rt);
  ratCollision( q, rt);
  //
  a.move();
  b.move();
  c.move();
  d.move();
  e.move();
  q.move();
  //
  a.show();
  b.show();
  c.show();
  d.show();
  e.show();
  q.show();
}
// SWAP VELOCITIES for the collisions
void collision( Ball p, Ball q ) {
  if ( p.hit( q.x,q.y ) ) {
    float tmp;
    tmp=p.dx;  p.dx=q.dx;  q.dx=tmp;     
    tmp=p.dy;  p.dy=q.dy;  q.dy=tmp; 
    score += 1;
  }
}

void ratCollision( Ball p, Rat q ) {
  if ( p.hit( q.x,q.y ) ) {
    p.dx=0;
    p.dy=0;
    if (rt.scoreBuffer == false){         
      score -= 10;                         
      rt.scoreBufferCounter = 0;           
      rt.scoreBuffer = true;               
    }                                      
  }
}


//DISPLAY BUTTONS
void buttons(){
  one.buttonDisplay();
  two.buttonDisplay();
  three.buttonDisplay();
  three.buttonBirdBuffer();
  four.buttonDisplay();
}

void birds(){
  brd.moveBird();
  brd.showBird();
  brd.bombDrop();
  brd.explosion();
}

void rats(){
  rt.moveRat();
  rt.showRat();
  rt.scoreFix();
}

void showScore(){
  text("Score", 50, height-50);
  text(score , 90, height -50);
}

// KEY PRESSED stuff: exit, reset, other buttons
void keyPressed() {
  if (key == 'q') exit();
  if (key == 'r') reset();
  if (key == 'w') t.wall = false;
}

//MOUSEPRESSED: hits for the button functions
void mousePressed() {
 one.buttonReset();
 two.buttonWall();
 three.buttonBird();
 four.buttonRat();
 a.clickBall();
 b.clickBall();
 c.clickBall();
 d.clickBall();
 e.clickBall();
 q.clickCue();
 rt.clickRat();
}


//BALL CLASS
class Ball {
  // PROPERTIES
  float x,y, dx,dy;
  int r,g,b;
  String name="";
  
  // METHODS
  void show() {
    stroke(0);
    strokeWeight(1);
    fill(r,g,b);
    ellipse( x,y, 30,30 );
    fill(0);
    text( name, x-5,y );
  }
  void move() {
    if (t.wall) {
      if (x>t.right-4 || x<t.middle+20) {  dx=  -dx; }    
    }else{                                                
      if (x>t.right-4 || x<t.left+4) {  dx=  -dx; }
    }
    if (y>t.bottom-4 || y<t.top+4) {  dy=  -dy; }
    x=  x+dx;
    y=  y+dy;
  }
  void reset() {                
    x=  random( (width/2)+60, t.right-10 );    
    y=  random( t.top+10, t.bottom-10 );
    dx=  random( -5,5 );
    dy=  random( -3,3 );
  }
  void resetCue() {            //reset for the cue ball on the left side centered with no velocity
    x= width/4;
    y= (t.bottom+t.top)/2;
    dx= 0; dy= 0;
  }
  void clickBall(){
    if (dist( mouseX, mouseY, x,y) < 17){
       x=  random( (width/2)+60, t.right-10 );    
       y=  random( t.top+10, t.bottom-10 );
       dx=  random( -5,5 );
       dy=  random( -3,3 );
       score -=5;
    }
  }
  void clickCue(){
    if (dist( mouseX, mouseY, x,y) < 17){
       x= width/4;
       y= (t.bottom+t.top)/2;
       dx= 0; dy= 0;
       score -=5;
    }
  }
  
  boolean hit( float x, float y ) {                        
    if (  dist( x,y, this.x,this.y ) < 30 ) return true;
    else return false;
  }
}

//PTABLE CLASS
class PTable {
  //PROPERTIES
  float left, right, top, bottom, middle;
  float horizon;
  boolean wall;
  
  //METHODS
  void tableDisplay(){
    noStroke();
    fill( 222,184,135 );
    rect( 0,horizon, width,(height*3/4)+25 );
    fill( 100, 250, 100 );    
    strokeWeight(20);
    stroke( 127, 0, 0 );      // walls of pool talbe
    rectMode( CORNERS );
    rect( left-20, top-20, right+20, bottom+20 );
    stroke(0);
    strokeWeight(1);
    
    if (wall) {
      stroke( 0, 127, 0 );
      strokeWeight(10);
      line( middle,top-4, middle,bottom+4 );
    }
  }
} 

class Bird {
  float x,y,by,bDY,exX,exY;      
  boolean fly, drop, explode;
  int exFrame;
  
  Bird() {
    fly = false;
    drop = false;
    x = -50;
    bDY = 2;
  }
  
  void moveBird(){
    if (fly)
    x +=4;
    if (x>width+50){
      x= -50;
      fly = false;             
      drop =false;             
      three.counter = false;   
      three.buffer = 0;        //counts up when counter is true, enables drop to use the same button as fly on subsequent clicks
      by = 70;
      bDY = 2;
      
    }
  }
  //Animation and display for birds. Frame count is for wing animation
  void showBird(){                 
    stroke(0);
    strokeWeight(1);
    fill(192);
    ellipse(x,y,35,13);
    ellipse(x+17, y-5, 15,10);
    triangle(x+24, y-6, x+30, y-4, x+24, y-2);
    triangle(x-18, y-2, x-30, y-4, x-19, y);
    if(frame/15 % 4 == 0){
      triangle(x-6, y-3, x+18, y-17, x+5, y);
    }else if(frame/15 % 4 == 1){
      triangle(x-7, y-3, x, y-1, x+6, y);
    }else if(frame/15 % 4 == 2){
      triangle(x-6, y-3, x-18, y+17, x+5, y);
    }else if(frame/15 % 4 == 3){
     triangle(x-4, y-8, x-13, y+7, x+7, y-5);
    }
  }
  //draws bomb and sends it downward with increasing velocity
  void bombDrop(){
    if (drop == true){
        //noStroke();
        fill(105);
        rect(x,by,10,10);
        ellipse(x+5,by,10,10);
        ellipse(x+5,by+10,10,10);
        triangle(x+5,by, x,by-13, x+10,by-13);
        by += bDY;
        bDY += .25;
    }
    if (dist(x,by,rt.x,rt.y)<40){
      drop = false;
      rt.crawl = false;
      explode = true;
      exFrame = 0;
      exX=x;
      exY=by;
      score +=100;
      rt.x = -50;
      rt.y = random(170,height-50);
  
      
    }
  }
  
  //animation for bomb explosion
  
  void explosion(){
    if (explode == true){
      exFrame = exFrame +1;
      if (exFrame == 1){
        fill(255,255,0);
        ellipse(exX,exY,12,12);
      }else if(exFrame == 2){
        fill(255);
        ellipse(exX,exY,24,24);
      }else if(exFrame == 3){ 
        fill(255,255,0);
        ellipse(exX,exY,36,36);
      }else if(exFrame == 4){ 
        fill(255);
        ellipse(exX,exY,48,48);
      }else if(exFrame == 5){ 
        fill(255,255,0);
        beginShape();
        vertex(exX-24,exY);
        bezierVertex(exX-24,exY-13,exX-13,exY-24,exX,exY-24);   //outer top left quadrent
        vertex(exX,exY-24);
        bezierVertex(exX+13,exY-24,exX+24,exY-13,exX+24,exY);   //outer top right quadrant
        vertex(exX+24,exY);
        bezierVertex(exX+24,exY+13,exX+13,exY+21,exX+3*sqrt(15),exY+21);  ////outer bottom right quadrant
        vertex(exX+3*sqrt(15),exY+21);
        bezierVertex(exX+12,exY+17,exX+7,exY+12,exX,exY+12);  //inner right
        vertex(exX,exY+12);
        bezierVertex(exX-7,exY+12,exX-12,exY+17,exX-3*sqrt(15),exY+21); //inner left
        vertex(exX-3*sqrt(15),exY+21);
        bezierVertex(exX-13,exY+21,exX-24,exY+13,exX-24,exY);     //outer bottom left
        endShape(CLOSE);
      }else if(exFrame == 6){ 
        fill(255);
        beginShape();
        vertex(exX-24,exY);
        bezierVertex(exX-24,exY-13,exX-13,exY-24,exX,exY-24);   //outer top left quadrent
        vertex(exX,exY-24);
        bezierVertex(exX+13,exY-24,exX+24,exY-13,exX+24,exY);   //outer top right quadrant
        vertex(exX+24,exY);
        bezierVertex(exX+24,exY+13,exX+13,exY+16,exX+18,exY+16);  ////outer bottom right quadrant
        vertex(exX+18,exY+16);
        bezierVertex(exX+18,exY+6,exX+10,exY-2,exX,exY-2);      //inner right
        vertex(exX,exY-2);
        bezierVertex(exX-10,exY-2,exX-18,exY+6,exX-18,exY+16);  //inner left
        vertex(exX-18,exY+16);
        bezierVertex(exX-13,exY+21,exX-24,exY+13,exX-24,exY);  //outer bottom left
        endShape(CLOSE);
      }else if(exFrame == 7){ 
        fill(255,255,0);
        beginShape();
        vertex(exX-24,exY);
        bezierVertex(exX-24,exY-13,exX-13,exY-24,exX,exY-24);   //outer top left quadrent
        vertex(exX,exY-24);
        bezierVertex(exX+13,exY-24,exX+24,exY-13,exX+24,exY);   //outer top right quadrant
        vertex(exX+24,exY);
        bezierVertex(exX+24,exY-9,exX+13,exY-16,exX,exY-16);  //inner right
        vertex(exX,exY-16);
        bezierVertex(exX-13,exY-16,exX-24,exY-9,exX-24,exY);    //inner left
        endShape(CLOSE);
        explode = false;
      }
    }
  }
}

class Rat {
  
  float x,y,DX,DY;
  boolean crawl;
  boolean scoreBuffer;
  int scoreBufferCounter;
  
  Rat() {
    crawl = false;
    scoreBuffer = false;
    x = -50;
    y = random(170,height-50);
  }
  
  void moveRat(){
    if (crawl){
      DX=random(0,5);
      DY=random(-5,5);
      x+=DX;
      y+=DY;
      if (x>width+50){
        crawl = false;
        x = -50;
        y = random(170,height-50);
      }
    }
  }
  
  void showRat(){
    stroke(1);
    fill(210,180,140);
    ellipse(x,y,30,15);
    ellipse(x+15,y,15,13);
    fill(209,193,173);
    ellipse(x+10,y-5,10,5);
    ellipse(x+10,y+5,10,5);
    fill(0);
    ellipse(x+22,y,5,5);
    noFill();
    if (frame/15 % 2 == 0){
      arc(x-22,y,15,15,0,PI);
      arc(x-37,y,15,15,PI, TWO_PI);
    }else if(frame/15 % 2 == 1){
      arc(x-22,y,15,15,PI,TWO_PI);
      arc(x-37,y,15,15,0,PI); 
    }
  }
  void clickRat(){
    if (dist(mouseX,mouseY,x,y)<20){
         crawl = false;
        x = -50;
        y = random(170,height-50);
        score +=50;
    }
  }
      
  void scoreFix(){
    if (scoreBuffer == true){
      scoreBufferCounter +=1;
      if (scoreBufferCounter>10){
        scoreBuffer = false;
      }
    }
  }  
}

class Cloud{
  
  float x, y;            
  
  Cloud( float x, float y){
    this.x=x;  this.y=y;
  }
 
  void showClouds() {
    stroke(0);
    fill(255);
    arc(x,y,30,30,HALF_PI,PI+HALF_PI+QUARTER_PI);
    arc(x+15,y-15,30,30,HALF_PI+QUARTER_PI,TWO_PI+QUARTER_PI);
    arc(x+30,y-15,30,30,HALF_PI+QUARTER_PI,TWO_PI+QUARTER_PI);
    arc(x+45,y,30,30,PI+QUARTER_PI,TWO_PI+HALF_PI);
    line(x,y+15,x+45,y+15);
    noStroke();
    ellipse(x+22,y-16,25,25);
    ellipse(x+11,y-7,25,25);
    ellipse(x+33,y-7,25,25);
    rectMode(CORNER);
    rect(x-1,y-1,47,15);
    x++;
    if (x>width+10){
      x= random(-200,-100);
    }
  }
}

class Button {
  //PROPERTIES
  float x,y;
  String words;
  boolean counter;
  int buffer;
  //CONSTRUCTOR
  Button(float tempX, float tempY) {
    x = tempX;
    y = tempY;
    counter = false;
    buffer = 0;
  }
  //METHODS
  void buttonDisplay(){
    fill(0);
    strokeWeight(4);
    stroke(255);
    rectMode( CORNER );
    rect(x, y, 80, 40);
    fill(255);
    text( words, x+15, y+20);
  }
  //resets balls and wall
  void buttonReset(){
    if (mouseX >x && mouseX<x+80 && mouseY>y && mouseY<y+40){
    reset();
  }
 }
 //disables wall
 void buttonWall(){
  if (mouseX >x && mouseX<x+80 && mouseY>y && mouseY<y+40){
    t.wall = false;
  }
 }
 
 void buttonBird(){
   if (mouseX >x && mouseX<x+80 && mouseY>y && mouseY<y+40){
     brd.fly = true;
     counter = true;
     if (buffer > 1){
       brd.drop = true;
     }
   }
 }

 void buttonBirdBuffer(){
   if (counter == true) {
     buffer +=1;
       }   
     }
 void buttonRat(){
   if (mouseX >x && mouseX<x+80 && mouseY>y && mouseY<y+40){
     rt.crawl = true;
   }
 }
}
 
