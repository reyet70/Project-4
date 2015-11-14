//explosion animation development
//Nick Ferro 11-14-15

float x,y;
int frame;

void setup(){
  size(600,600);
  x=width/2;
  y=height/2;
  frame = 0;
}

void draw(){
  background(155);
  text(frame ,30,height-50);
  text(frame/15, 30, height - 40);
  text(frame/15 % 8,30,height-30);
  frame = frame + 1;
   //frame 1
   if(frame/15 % 8 == 0){
  fill(255,255,0);
  ellipse(x,y,12,12);
   
  //frame 2
   }else if(frame/15 % 8 == 1){
  fill(255);
  ellipse(x,y,24,24);
   
  //frame 3
   }else if(frame/15 % 8 == 2){
  fill(255,255,0);
  ellipse(x,y,36,36);
   
  //frame 4
   }else if(frame/15 % 8 == 3){
  fill(255);
  ellipse(x,y,48,48);
   
  
  //frame 5
   }else if(frame/15 % 8 == 4){
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
  bezierVertex(x-13,y+21,x-24,y+13,x-24,y);     //outer bottom left
  
  endShape(CLOSE);
   
  //ellipse(x,y,48,48);
  //ellipse(x,y+24,24,24);
 
  //frame 6
   }else if(frame/15 % 8 == 5){
  fill(255);
  beginShape();
  vertex(x-24,y);
  bezierVertex(x-24,y-13,x-13,y-24,x,y-24);   //outer top left quadrent
  vertex(x,y-24);
  bezierVertex(x+13,y-24,x+24,y-13,x+24,y);   //outer top right quadrant
  vertex(x+24,y);
  bezierVertex(x+24,y+13,x+13,y+16,x+18,y+16);  ////outer bottom right quadrant
  vertex(x+18,y+16);
  bezierVertex(x+18,y+6,x+10,y-2,x,y-2);      //inner right
  vertex(x,y-2);
  bezierVertex(x-10,y-2,x-18,y+6,x-18,y+16);  //inner left
  vertex(x-18,y+16);
  bezierVertex(x-13,y+21,x-24,y+13,x-24,y);  //outer bottom left
  
  endShape(CLOSE);
   
  //ellipse(x,y,48,48);
  //ellipse(x,y+16,36,36);
  
  //frame 7
   }else if(frame/15 % 8 == 6){
  fill(255,255,0);
  beginShape();
  vertex(x-24,y);
  bezierVertex(x-24,y-13,x-13,y-24,x,y-24);   //outer top left quadrent
  vertex(x,y-24);
  bezierVertex(x+13,y-24,x+24,y-13,x+24,y);   //outer top right quadrant
  vertex(x+24,y);
  bezierVertex(x+24,y-9,x+13,y-16,x,y-16);  //inner right
  vertex(x,y-16);
  bezierVertex(x-13,y-16,x-24,y-9,x-24,y);    //inner left
  
  endShape(CLOSE);
   } 
  //ellipse(x,y,48,48);
  //ellipse(x,y+8,48,48);
  
  
}
