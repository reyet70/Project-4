////Nick Ferro
//// Project 3   11-11-15    
//// Pool table elastic collisions
//// objects, loops, and arrays
Ball a,b,c,d,e,q;
PTable t;
Button one, two, three, four;
Bird brd;
Rat rt;
Cloud[] cloudList;
int frame;
int score;

//SETUP: object stuff
void setup() {
  size( 720, 480 );
  t= new PTable();
  t.left=   50;
  t.right=  width-50;
  t.top=    150;
  t.bottom= height-50;
  t.middle= t.left + (t.right-t.left) / 2;
  t.horizon= (height/4)-20;
  t.wall=true;
  //
  a=  new Ball();
  a.r=255;
  a.b=255;
  a.name=  "1";
  //
  b=  new Ball();
  b.g=255;
  b.name=  "2";
  //
  c=  new Ball();
  c.g=255;
  c.b=255;
  c.name=  "3";
  //
  d=  new Ball();
  d.g=127;
  d.b=127;
  d.name=  "4";
  //
  e=  new Ball();
  e.r=127;
  e.name=  "5";
  //
  q=  new Ball();
  q.r=255;
  q.g=255;
  q.b=255;
  q.name=  " ";
  //
  one= new Button(50,5);
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
  cloudList= new Cloud[7];
  float cloudX=50;
  for( int i=0; i<cloudList.length; i++) {
     cloudList[i]=  new Cloud( cloudX,random(t.horizon-100,t.horizon-25));
     cloudX += 100;
  }
  //
  reset();
}

//RESET function for balls
void reset() {
  a.reset();
  b.reset();
  c.reset();
  d.reset();
  e.reset();
  q.resetCue();
  t.wall = true;
}
 
//main DRAW function
void draw() {
  background( 102,178,205 );
  t.tableDisplay();
  drawGrass();
  drawClouds();
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
    line(x, height, random(x-1,x+1), height-15);
    x +=5;
  }
}

void drawClouds(){
   for (int i=0; i<cloudList.length; i++) {
    cloudList[i].showClouds();
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
    if (rt.scoreBuffer == false){          //convoluted fix for the problem with the rat collision scoring.
      score -= 10;                         // b/c the rat doesn't instantly go in the opposite direction of the ball
      rt.scoreBufferCounter = 0;           // there is a 'collision' for many frames in a row, causing the score to plumet.
      rt.scoreBuffer = true;               // w/ the fix, the -10 score can only happen every 10 frames, usually enough time 
    }                                      // for the rat to move away from the ball
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
      if (x>t.right-4 || x<t.middle+20) {  dx=  -dx; }    //where the balls bounce off of depending on
    }else{                                                //whether the wall is engaged or not
      if (x>t.right-4 || x<t.left+4) {  dx=  -dx; }
    }
    if (y>t.bottom-4 || y<t.top+4) {  dy=  -dy; }
    x=  x+dx;
    y=  y+dy;
  }
  void reset() {                //resets the ball on right side random position with random velocity
    x=  random( (width/2)+50, t.right );    
    y=  random( t.top, t.bottom );
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
       x=  random( (width/2)+50, t.right );    
       y=  random( t.top, t.bottom );
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
  
  boolean hit( float x, float y ) {                        //if distance between two balls is less than thirty, set hit = true
    if (  dist( x,y, this.x,this.y ) < 30 ) return true;   //if hit = true, triggers the collision function which swaps velocities
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
  float x,y,by,bDY;
  boolean fly, drop;
  
  Bird() {
    fly = false;
    drop = false;
    x = -50;
    bDY = 2;
  }
  // movement for the bird. when the bird crosses the right edge of the screen, it 
  // resets a ton of variables all tied to the bird flight and bomb droping mechanic
  void moveBird(){
    if (fly)
    x +=4;
    if (x>width+50){
      x= -50;
      fly = false;             //causes the bird to fly when true
      drop =false;             //causes the bomb to drop when true
      three.counter = false;   //when true, starts the buffer counter
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
        noStroke();
        fill(105);
        rect(x,by,10,10);
        ellipse(x+5,by,10,10);
        ellipse(x+5,by+10,10,10);
        triangle(x+5,by, x,by-13, x+10,by-13);
        by += bDY;
        bDY += .25;
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
 //multifunctional button. Sends the bird flying on the first click
 //and drops a bomb on the second click, then does nothing untill the
 //bird completes its flight.
 void buttonBird(){
   if (mouseX >x && mouseX<x+80 && mouseY>y && mouseY<y+40){
     brd.fly = true;
     counter = true;
     if (buffer > 1){
       brd.drop = true;
     }
   }
 }
 //Buffer counter for the bird button. Button will only enable drop if at least
 //two frames have passed since fly was enabled.
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
 

  
