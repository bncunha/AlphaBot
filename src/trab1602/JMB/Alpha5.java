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

/**
 * Alpha - ai ai ai ai ai!
 * <p/>
 * Alpha é o andróide que ajuda Zordon e os Power Rangers na luta contra Rita
 * Repulsa.
 * <p/>
 * Basedo no robô Crazy por Mathew A. Nelson e Flemming N. Larsen.
 * <p/>
 * Após os testes, esse será o robô final.
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

        // Loop forever
        while (true) {
            // Tell the game we will want to move ahead 40000 -- some large number
            setAhead(40000);
            movingForward = true;
            // Tell the game we will want to turn right 90
            setTurnRight(90);
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
            // then back to the top to do it all again
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
}
