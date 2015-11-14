float x,y;

void setup(){
  size(600,600);
  x=width/2;
  y=height/2;
}

void draw(){
  //frame 5
  fill(255,255,0);
  beginShape();
  vertex(x-24,y);
  bezierVertex(x-24,y-13,x-13,y-24,x,y-24);   //outer top left quadrent
  vertex(x,y-24);
  bezierVertex(x+13,y-24,x+24,y-13,x+24,y);   //outer top right quadrant
  vertex(x+24,y);
  bezierVertex(x+24,y+13,x+13,y+21,x+3*sqrt(15),y+21);  ////outer bottom right quadrant
  vertex(x+3*sqrt(15),y+21);
  bezierVertex(x+12,y+17,x+7,y+12,x,y+12);  //inner right
  vertex(x,y+12);
  bezierVertex(x-7,y+12,x-12,y+17,x-3*sqrt(15),y+21); //inner left
  vertex(x-3*sqrt(15),y+21);
  bezierVertex(x-13,y+21,x-24,y+13,x-24,y);
  
  endShape(CLOSE);

  //ellipse(x,y,48,48);
  //ellipse(x,y+24,24,24);
}
