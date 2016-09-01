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
        
        //variaveis para determinar a posição do robo (x,y) no campo de batalha
        double xPos; 
        double yPos;
        
        // variaveis que pegam as dimensões do campo de batalha
        double alturaCampo = getBattleFieldHeight();
        double larguraCampo = getBattleFieldWidth();
        
        // uma porcentagem para determinar o quanto perto da borda para o
        // tank desviar. Determinei 19% pois ele faz uma curva muito aberta.
        double desviar = 0.19 * Math.max(alturaCampo, larguraCampo); // desviar 20% de tocar na borda
        // Loop forever
        while (true) {
            // Tell the game we will want to move ahead 40000 -- some large number
            setAhead(40000);
            
            xPos = getX();
            yPos = getY();
            execute();
            //muito perto do lado direito do mapa
            if (xPos > larguraCampo - desviar){
                // função para desviar do lado direito
                desviarDireita(yPos, xPos, alturaCampo, larguraCampo);
            }
            //muito perto do lado esquerdo do mapa
            else if (xPos < desviar){
                desviarEsquerda (yPos, xPos, alturaCampo, larguraCampo);
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
        reverseDirection();
        System.out.println("Hit wall! Energy: " + getEnergy());
    }
    
    public void desviarEsquerda (double yPos, double xPos, double alturaCampo, double larguraCampo){
        // verifica o angulo que está se aproximando do lado direito
        // se estiver subindo na diagonal vai depender de sua posição no campo
        if (this.getHeading() > 270 && this.getHeading() < 360){                     
            if (yPos > alturaCampo - 1){ // se estiver bem proximo da quina supeior esquerda
                //retorna
                stop(); 
                setTurnRight(150);
                waitFor (new TurnCompleteCondition(this));
                ahead(50);
            }                    
            else{
                // se estiver abaixo da metade do campo
                // dá um drift para direita
                if (yPos < alturaCampo/2){
                    setTurnRight(90);
                    waitFor (new TurnCompleteCondition(this));
                }
                // se estiver acima da metade do campo
                else{
                    //retorna
                    stop();
                    setTurnLeft(180);
                    waitFor (new TurnCompleteCondition(this));
                }
            }
        }
        // Agora, se estiver descendo na diagonal vai depender de sua posição no campo
        else if (this.getHeading() > 180 && this.getHeading() < 270){
            // se estiver bem proximo do canto inferior esquerdo
            if (yPos < 2){
                //retorna
                stop();
                setTurnRight(170);
                waitFor (new TurnCompleteCondition(this));
                ahead(50);                        
            }
            else{
                // se estiver acima da metade do campo
                // da um drift para esquerda
                if (yPos > alturaCampo/2){
                    setTurnLeft(110);
                    waitFor (new TurnCompleteCondition(this));
                }
                // se estiver abaixo da metade do campo
                // retorna
                else{                            
                    stop();
                    setTurnRight(150);
                    waitFor (new TurnCompleteCondition(this));
                }
            }
        }                
    }
    
    public void desviarDireita(double yPos, double xPos, double alturaCampo, double larguraCampo){
        // verifica o angulo que está se aproximando do lado direito
        // se estiver subindo na diagonal vai depender de sua posição no campo
        if (this.getHeading() < 90){                     
            if (yPos > alturaCampo - 1){ // se estiver bem proximo da quina supeior direta
                //retorna
                stop(); 
                setTurnRight(150);
                waitFor (new TurnCompleteCondition(this));
                ahead(50);
            }                    
            else{
                // se estiver abaixo da metade do campo
                // dá um drift para esquerda
                if (yPos < alturaCampo/2){
                    setTurnLeft(90);
                    waitFor (new TurnCompleteCondition(this));
                }
                // se estiver acima da metade do campo
                else{
                    //retorna
                    stop();
                    setTurnRight(170);
                    waitFor (new TurnCompleteCondition(this));
                }
            }
        }
        // Agora, se estiver descendo na diagonal vai depender de sua posição no campo
        else if (this.getHeading() < 180 && this.getHeading() > 90){
            // se estiver bem proximo do canto inferior direito
            if (yPos < 2){
                //retorna
                stop();
                setTurnLeft (170);
                waitFor (new TurnCompleteCondition(this));
                ahead(50);                        
            }
            else{
                // se estiver acima da metade do campo
                // da um drift para direita
                if (yPos > alturaCampo/2){
                    setTurnRight(90);
                    waitFor (new TurnCompleteCondition(this));
                }
                // se estiver abaixo da metade do campo
                // retorna
                else{                            
                    stop();
                    setTurnLeft(160);
                    waitFor (new TurnCompleteCondition(this));
                }
            }
        }        
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
