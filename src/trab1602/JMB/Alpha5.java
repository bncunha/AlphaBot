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
        //radar, arma e corpo virarem independentemente        
        setAdjustGunForRobotTurn(true);
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
            
            //vira a arma enquanto anda
            setTurnGunLeft(100);
            execute();     
            
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
                   setTurnRight(r.nextInt(90));
                   execute();
                    virandoDireita = true;
                }
                else{//significa q a ultima curva foi para direita
                    setTurnLeft(r.nextInt(90));
                    execute();
                    virandoDireita = false;
                }
            }            
            movingForward = true;
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
        setFire(1000 / e.getDistance()); // a força depende da distancia
        System.out.println("Fire! Energy: " + getEnergy());
    }

    /**
     * onHitRobot: Back up!
     */
    public void onHitRobot(HitRobotEvent e) {
        setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());
        waitFor (new GunTurnCompleteCondition(this));
        fire(500);
        retornar();
        System.out.println("Hit robot! Energy: " + getEnergy());
    }
    
    /**
     * onHitByBullet: Turn perpendicular to the bullet, and move a bit.
     */
    public void onHitByBullet(HitByBulletEvent e) {
        setTurnGunRight(getHeading() - getGunHeading() + e.getBearing());
        waitFor (new GunTurnCompleteCondition(this));
        fire(500);
    }
}
