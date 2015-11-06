////Nick Ferro
//// Project 3   11-6-15    Prototyping and Theory
//// Pool table elastic collisions
//// objects, loops, and arrays

Ball a,b,c,d,e,q;

float left, right, top, bottom;
float middle;

//// SETUP:  size and table
void setup() {
  size( 600, 400 );
  left=   50;
  right=  width-50;
  top=    100;
  bottom= height-50;
  middle= left + (right-left) / 2;
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
  reset();
}

void reset() {
  a.reset();
  b.reset();
  c.reset();
  d.reset();
  e.reset();
  q.resetCue();
}
 
//// NEXT FRAME:  table, bounce off walls, collisions, show all
void draw() {
  background( 250,250,200 );
  rectMode( CORNERS );
  table( left, top, right, bottom );
  balls();
}

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

//// Elastic collisions.
void collision( Ball p, Ball q ) {
  if ( p.hit( q.x,q.y ) ) {
    float tmp;
    tmp=p.dx;  p.dx=q.dx;  q.dx=tmp;      // Swap the velocities.
    tmp=p.dy;  p.dy=q.dy;  q.dy=tmp; 
  }
}

//// HANDLERS:  keys & clicks.
void keyPressed() {
  if (key == 'q') exit();
  if (key == 'r') {
        a.reset();
        b.reset();
        c.reset();
        d.reset();
        e.reset();
        q.resetCue();
  }
}

//// SCENE:  draw the table with walls
void table( float left, float top, float right, float bottom ) {
  fill( 100, 250, 100 );    // green pool table
  strokeWeight(20);
  stroke( 127, 0, 0 );      // Brown walls
  rect( left-20, top-20, right+20, bottom+20 );
  stroke(0);
  strokeWeight(1);
}

class Ball {
  //// PROPERTIES:  position, speed, color, etc. ////   (What a Ball "has".)
  float x,y, dx,dy;
  int r,g,b;
  String name="";
  
  //// CONSTRUCTORS (if any). ////
  
  //// METHODS:  show, move, detect a "hit", etc. ////  (What a Ball "does".)
  void show() {
    fill(r,g,b);
    ellipse( x,y, 30,30 );
    fill(0);
    text( name, x-5,y );
  }
  void move() {
    if (x>right || x<left) {  dx=  -dx; }
    if (y>bottom || y<top) {  dy=  -dy; }
    x=  x+dx;
    y=  y+dy;
  }
  void reset() {
    x=  random( width/2, right );
    y=  random( top, bottom );
    dx=  random( -5,5 );
    dy=  random( -3,3 );
  }
  void resetCue() {
    x= width/4;
    y= (bottom+top)/2;
    dx= 0; dy= 0;
  }
  boolean hit( float x, float y ) {
    if (  dist( x,y, this.x,this.y ) < 30 ) return true;
    else return false;
  }
}
