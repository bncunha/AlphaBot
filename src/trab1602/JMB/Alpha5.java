/**
 * Copyright (c) 2001-2016 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package trab1602.JMB;

import robocode.*;
import java.awt.*;
import java.util.Random;
import static robocode.util.Utils.normalRelativeAngleDegrees;

/**
 * Alpha - ai ai ai ai ai!
 * <p/>
 * Alpha é o andróide que ajuda Zordon e os Power Rangers na luta contra Rita
 * Repulsa.
 * <p/>
 * Basedo no robô Crazy por Mathew A. Nelson e Flemming N. Larsen.
 * <p/>
 * Após os testes, esse será o robô final com o nome de Alpha.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 * @author Joannes M. Bercan (Modificações)
 */
public class Alpha5 extends AdvancedRobot {

    boolean movingForward;
    boolean virandoDireita; // TRUE virou para direita, FALSE virou para esquerda
    Random r = new Random();
    
    /**
     * run: Crazy's main run function
     */
    public void run() {
        // Set colors  
        setBodyColor(new Color(187, 31, 37));
        setGunColor(new Color(255, 235, 143));
        setRadarColor(new Color(236, 64, 70));
        setBulletColor(new Color(255, 236, 108));
        setScanColor(new Color(53, 68, 103));
        System.out.println("Esse é um AdvancedRobot. Energy inicial: " + getEnergy());
        
        virandoDireita = true; //seta a variavel
        
        //variaveis para determinar a posição do robo (x,y) no campo de batalha
        double xPos; 
        double yPos;
        
        // variaveis que pegam as dimensões do campo de batalha
        double alturaCampo = getBattleFieldHeight();
        double larguraCampo = getBattleFieldWidth();
        
        // uma porcentagem para determinar o quanto perto da borda para o
        // tank desviar. Determinei 19% pois ele faz uma curva muito aberta.
        double desviar = 0.15 * Math.max(alturaCampo, larguraCampo); // desviar 20% de tocar na borda
        // Loop forever
        while (true) {
            // Tell the game we will want to move ahead 40000 -- some large number
            setAhead(40000);
            
            xPos = getX();
            yPos = getY();
            execute();
           
            System.out.println("Posição: (" + xPos + ", " + yPos + ")" );
            // muito perto do lado direito
            if (xPos > larguraCampo - desviar){                
                retornar();                
            }
            //muito perto do lado esquerdo do mapa
            else if (xPos < desviar){               
                retornar();
            }
            //muito perto do topo
            else if (yPos > alturaCampo - desviar){               
                retornar();
            }
            //muito perto do bot
            else if (yPos < desviar){                
                retornar();
            }
            else{
                if (virandoDireita == false){//significa q a ultima curva foi para esquerda
                    setTurnRight(r.nextInt(50));
                    waitFor(new TurnCompleteCondition(this));
                    virandoDireita = true;
                }
                else{//significa q a ultima curva foi para direita
                    setTurnLeft(r.nextInt(50));
                    waitFor(new TurnCompleteCondition(this));
                    virandoDireita = false;
                }
            }
            
            movingForward = true;
            // Tell the game we will want to turn right 90
            /*setTurnRight(90);
            // At this point, we have indicated to the game that *when we do something*,
            // we will want to move ahead and turn right.  That's what "set" means.
            // It is important to realize we have not done anything yet!
            // In order to actually move, we'll want to call a method that
            // takes real time, such as waitFor.
            // waitFor actually starts the action -- we start moving and turning.
            // It will not return until we have finished turning.
            waitFor(new TurnCompleteCondition(this));
            // Note:  We are still moving ahead now, but the turn is complete.
            // Now we'll turn the other way...
            setTurnLeft(180);
            // ... and wait for the turn to finish ...
            waitFor(new TurnCompleteCondition(this));
            // ... then the other way ...
            setTurnRight(180);
            // .. and wait for that turn to finish.
            waitFor(new TurnCompleteCondition(this));
            // then back to the top to do it all again*/
        }
    }

    /**
     * onHitWall: Handle collision with wall.
     */
    public void onHitWall(HitWallEvent e) {
        // Bounce off!      
        back(70);
        //reverseDirection();
        System.out.println("Hit wall! Energy: " + getEnergy());
    }
    public void retornar(){
        stop(); 
        setTurnRight(120);
        waitFor (new TurnCompleteCondition(this));
        ahead(100);                                
    }    
    /**
     * reverseDirection: Switch from ahead to back & vice versa
     */
    public void reverseDirection() {
        if (movingForward) {
            setBack(40000);
            movingForward = false;
        } else {
            setAhead(40000);
            movingForward = true;
        }
    }

    /**
     * onScannedRobot: Fire!
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        fire(500.00);
        System.out.println("Fire! Energy: " + getEnergy());
    }

    /**
     * onHitRobot: Back up!
     */
    public void onHitRobot(HitRobotEvent e) {
        // If we're moving the other robot, reverse!
        if (e.isMyFault()) {
            reverseDirection();
        }
        System.out.println("Hit robot! Energy: " + getEnergy());
    }
    
    /**
     * onHitByBullet: Turn perpendicular to the bullet, and move a bit.
     */
    public void onHitByBullet(HitByBulletEvent e) {
        System.out.println("Ouch! I get shot! Energy: " + getEnergy());
    }
}
