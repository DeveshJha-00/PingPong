import java.awt.*;
import java.util.*;

public class Ball extends Rectangle{

    Random random;
    int xVelocity;
    int yVelocity;
    int speed = 2;

    Ball(int x, int y, int width, int height){
        super(x,y,width,height);
        random = new Random();

        int randomXDirection = random.nextInt(2); //0 or 1 ..... 0->left, 1->right
        if (randomXDirection==0){
            randomXDirection--;
        }
        setXDirection(randomXDirection*speed);

        int randomYDirection = random.nextInt(2); //0 or 1 ..... 0->up, 1->down
        if (randomYDirection==0){
            randomYDirection--;
        }
        setYDirection(randomYDirection*speed);
    }

    public void move(){
        x += xVelocity;
        y += yVelocity;
    }

    public void setXDirection(int xDirection){
        xVelocity = xDirection;
    }
    public void setYDirection(int yDirection){
        yVelocity = yDirection;
    }
    
    

    public void draw(Graphics g){
        g.setColor(Color.white);
        g.fillOval(x, y, width, height);
    }

}
