// Bird art asset development

float x,y;
int frame;

void setup(){
  size (600,600);
  x= width/2;
  y=height/2;
  smooth();
  frame = 0;
}

void draw(){
 background(100,150,200); 
flyAnimation();
}


void flyAnimation() {
  frame = frame + 1;
  if(frame/15 % 4 == 0){
  ellipse(x,y,35,13);
  ellipse(x+17, y-5, 15,10);
  triangle(x+24, y-6, x+30, y-4, x+24, y-2);
  triangle(x-18, y-2, x-30, y-4, x-19, y);
  triangle(x-6, y-3, x+18, y-17, x+5, y);
  }else if(frame/15 % 4 == 1){
  ellipse(x,y,35,13);
  ellipse(x+17, y-5, 15,10);
  triangle(x+24, y-6, x+30, y-4, x+24, y-2);
  triangle(x-18, y-2, x-30, y-4, x-19, y);
  triangle(x-7, y-3, x, y-1, x+6, y);
  }else if(frame/15 % 4 == 2){
  ellipse(x,y,35,13);
  ellipse(x+17, y-5, 15,10);
  triangle(x+24, y-6, x+30, y-4, x+24, y-2);
  triangle(x-18, y-2, x-30, y-4, x-19, y);
  triangle(x-6, y-3, x-18, y+17, x+5, y);
  }else if(frame/15 % 4 == 3){
  ellipse(x,y,35,13);
  ellipse(x+17, y-5, 15,10);
  triangle(x+24, y-6, x+30, y-4, x+24, y-2);
  triangle(x-18, y-2, x-30, y-4, x-19, y);
  triangle(x-4, y-8, x-13, y+7, x+7, y-5);
  }
}
