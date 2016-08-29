/**
 * Copyright (c) 2001-2016 Mathew A. Nelson and Robocode contributors
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://robocode.sourceforge.net/license/epl-v10.html
 */
package trab1602.JMB;

import robocode.HitByBulletEvent;
import robocode.HitRobotEvent;
import robocode.Robot;
import robocode.ScannedRobotEvent;
import static robocode.util.Utils.normalRelativeAngleDegrees;

import java.awt.*;
import robocode.HitWallEvent;

/**
 * Fire - a sample robot by Mathew Nelson, and maintained.
 * <p/>
 * Sits still. Spins gun around. Moves when hit.
 *
 * @author Mathew A. Nelson (original)
 * @author Flemming N. Larsen (contributor)
 */
public class Alpha6 extends Robot {

    int dist = 50; // distance to move when we're hit

    /**
     * run: Fire's main run function
     */
    public void run() {
        // Set colors
        setBodyColor(new Color(187, 31, 37));
        setGunColor(new Color(255, 235, 143));
        setRadarColor(new Color(236, 64, 70));
        setBulletColor(new Color(255, 236, 108));
        setScanColor(new Color(53, 68, 103));
        System.out.println("Esse é um Robot. Energy inicial: " + getEnergy());

        // Spin the gun around slowly... forever
        while (true) {
            turnGunRight(5);
        }
    }

    /**
     * onScannedRobot: Fire!
     */
    public void onScannedRobot(ScannedRobotEvent e) {
        // If the other robot is close by, and we have plenty of life,
        // fire hard!
        if (e.getDistance() < 50 && getEnergy() > 50) {
            fire(500.00);
            System.out.println("Fire! Energy: " + getEnergy());
        } // otherwise, fire 1.
        else {
            fire(500.00);
            System.out.println("Fire! Energy: " + getEnergy());
        }
        // Call scan again, before we turn the gun
        scan();
    }

    /**
     * onHitByBullet: Turn perpendicular to the bullet, and move a bit.
     */
    public void onHitByBullet(HitByBulletEvent e) {
        System.out.println("Ouch! I get shot! Energy: " + getEnergy());
        turnRight(normalRelativeAngleDegrees(90 - (getHeading() - e.getHeading())));

        ahead(dist);
        dist *= -1;
        scan();
    }

    /**
     * onHitRobot: Aim at it. Fire Hard!
     */
    public void onHitRobot(HitRobotEvent e) {
        double turnGunAmt = normalRelativeAngleDegrees(e.getBearing() + getHeading() - getGunHeading());

        turnGunRight(turnGunAmt);
        fire(3);
        System.out.println("Hit robot! Energy: " + getEnergy());
    }

    /**
     * onHitWall: Handle collision with wall.
     */
    public void onHitWall(HitWallEvent e) {
        System.out.println("Hit wall! Energy: " + getEnergy());
    }
}
