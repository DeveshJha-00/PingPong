import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class Panel extends JPanel implements Runnable, KeyListener{
    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int) (GAME_WIDTH*0.555);
    static final int BALL_DIAMETER = 25;
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT= 100;

    Thread gameLoop;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;
    // private Clip hitAudioClip;



    Panel(){
        this.setPreferredSize(new Dimension(GAME_WIDTH,GAME_HEIGHT));
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        addKeyListener(this);

        gameLoop = new Thread(this);
        gameLoop.start();
    }

    public void newBall(){
        ball = new Ball(GAME_WIDTH/2 - BALL_DIAMETER/2, GAME_HEIGHT/2 - BALL_DIAMETER/2, BALL_DIAMETER, BALL_DIAMETER);
    }

    public void newPaddles(){
        paddle1 = new Paddle(0,((GAME_HEIGHT/2)-(PADDLE_HEIGHT/2)), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle((GAME_WIDTH - PADDLE_WIDTH), ((GAME_HEIGHT/2)-(PADDLE_HEIGHT/2)), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    public void paintComponent(Graphics g){
        image = createImage(getWidth(), getHeight()); //draws off-screen image
        graphics = image.getGraphics(); //gets graphics for any off-screen image
        draw(graphics);
        g.drawImage(image,0,0,this);
    }
    public void draw(Graphics g){
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    public void move(){
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision(){
        //limiting paddles to windows
        if (paddle1.y <= 0)  
            paddle1.y=0;
        if (paddle1.y >= GAME_HEIGHT - PADDLE_HEIGHT)
            paddle1.y = GAME_HEIGHT-PADDLE_HEIGHT;
        if (paddle2.y <= 0)
            paddle2.y=0;
        if (paddle2.y >= GAME_HEIGHT - PADDLE_HEIGHT)
            paddle2.y = GAME_HEIGHT-PADDLE_HEIGHT;

        //limiting ball to windows 
        if(ball.y <=0) 
            ball.setYDirection(-ball.yVelocity);
		if(ball.y >= GAME_HEIGHT-BALL_DIAMETER)
            ball.setYDirection(-ball.yVelocity);

        //bounce ball from paddles
        if(ball.intersects(paddle1)) {
            playHitMusic();
			ball.xVelocity = -ball.xVelocity;
			ball.xVelocity++; //optional for more difficulty
			if(ball.yVelocity>0)
				ball.yVelocity++; //optional for more difficulty
			else
				ball.yVelocity--;
			ball.setXDirection(ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}
        if(ball.intersects(paddle2)) {
            playHitMusic();
			ball.xVelocity = -ball.xVelocity;
			ball.xVelocity++; //optional for more difficulty
			if(ball.yVelocity>0)
				ball.yVelocity++; //optional for more difficulty
			else
				ball.yVelocity--;
			ball.setXDirection(-ball.xVelocity);
			ball.setYDirection(ball.yVelocity);
		}

        if (ball.x <= 0){
            score.player2score++;
            newPaddles();
            newBall();
        }
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER){
            score.player1score++;
            newPaddles();
            newBall();
        }

    }

    public void run(){
        //game loop functionality 
        long lastTime = System.nanoTime();
		double frames = 60.0;
		double ns = 1000000000 / frames;
		double delta = 0;
		while(true) {
			long currTime = System.nanoTime();
			delta += (currTime -lastTime)/ns;
			lastTime = currTime;
			if(delta >=1) {
				move();
				checkCollision();
				repaint();
				delta--;
			}
		}
    }

    public void playHitMusic(){
        try{
            URL hitMusicPath = getClass().getResource("/audio/ballhit.wav");
\            if (hitMusicPath != null){
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(hitMusicPath);
                Clip hitAudioClip = AudioSystem.getClip();
                hitAudioClip.open(audioStream);
                hitAudioClip.start();
            }
            else{
                System.out.println("cant access hitMusicPath");
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }


    @Override
    public void keyPressed(KeyEvent e) {
        paddle1.pressedKey(e);
        paddle2.pressedKey(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        paddle1.releasedKey(e);
        paddle2.releasedKey(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    
}

