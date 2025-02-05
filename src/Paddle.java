import java.awt.*;
import java.awt.event.*;


public class Paddle extends Rectangle{
    int id;
    int speed = 10;
    int yVelocity;

    Paddle(int x, int y, int width, int height, int id){
        super(x,y,width,height);
        this.id=id;
    }

    public void move(){
        y += yVelocity;
    }

    public void setYDirection(int yDirection){
        yVelocity = yDirection;
    }

    public void pressedKey(KeyEvent e){
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W){
                    setYDirection(-speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_S){
                    setYDirection(speed);
                    move();
                }
                break;
        
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    setYDirection(-speed);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    setYDirection(speed);
                    move();
                }
                break;
        }
    }

    public void releasedKey(KeyEvent e){
        switch (id) {
            case 1:
                if (e.getKeyCode() == KeyEvent.VK_W){
                    setYDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_S){
                    setYDirection(0);
                    move();
                }
                break;
        
            case 2:
                if (e.getKeyCode() == KeyEvent.VK_UP){
                    setYDirection(0);
                    move();
                }
                if (e.getKeyCode() == KeyEvent.VK_DOWN){
                    setYDirection(0);
                    move();
                }
                break;
        }
    }


    public void draw(Graphics g){
        if (id==1){
            g.setColor(Color.blue);
        }else{
            g.setColor(Color.red);
        }
        g.fillRect(x, y, width, height);
    }

}
