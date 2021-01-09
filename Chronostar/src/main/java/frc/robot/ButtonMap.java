/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;

/**
 * Add your docs here.
 */
public class ButtonMap {
    public static OI oi = new OI();
    //driver controller buttons
    public static double getDriveThrottle(){
        return -oi.driverController.getRawAxis(1);
    } 
    public static double getRotation(){
        return -oi.driverController.getRawAxis(4);
    }
    public static boolean switchCamera(){
        return oi.driverController.getTriggerAxis(Hand.kLeft)>=0.15;
    }
    public static boolean manualTarget(){
        return oi.driverController.getBumper(Hand.kLeft);
    }
    public static boolean autoTarget(){
        return oi.driverController.getBumperPressed(Hand.kRight);
    }
    public static boolean endAutoTarget(){
        return oi.driverController.getBumperReleased(Hand.kRight);
    }
    public static boolean manualAdjustLeft(){
        return oi.driverController.getXButton();
    }
    public static boolean manualAdjustRight(){
        return oi.driverController.getBButton();
    }
    public static boolean turnOnLightRing(){
        return oi.driverController.getStartButton();
    }
    public static boolean deployClimber(){
        return oi.driverController.getYButton();
    }
    public static boolean winchDown(){
        return oi.driverController.getAButton();
    }
    public static boolean adjustTargetTrackingLeft(){
        return oi.driverController.getPOV() == 270;
    }
    public static boolean adjustTargetTrackingRight(){
        return oi.driverController.getPOV() == 90;
    }
    //operator controller
    public static boolean enableArmControl(){
        return oi.operatorController.getStickButton(Hand.kRight);
    }
    public static boolean RunIntake(){
        return oi.operatorController.getTriggerAxis(Hand.kLeft)>=0.15;
    }
    public static boolean armUp(){
        return oi.operatorController.getBumper(Hand.kRight);
    }
    public static boolean armDown(){
        return oi.operatorController.getBumper(Hand.kLeft);
    }
    public static boolean reverseMag(){
        return oi.operatorController.getBButton();
    }
    public static boolean stopReverseMag(){
        return oi.operatorController.getBButtonReleased();
    }
     public static boolean SafetyButton(){
        return oi.driverController.getStartButton();
    }
    public static boolean startInitiaionLineFiringSequence(){
        return oi.operatorController.getXButtonPressed();
    }
    public static boolean startTrenchRunFiringSequence(){
        return oi.operatorController.getAButtonPressed();
    }
    public static boolean startCloseUpFiringSequence(){
        return oi.operatorController.getYButtonPressed();
    }
    public static boolean stopManualFiringSequence(){
        return oi.operatorController.getAButtonReleased()||oi.operatorController.getXButtonReleased()||oi.operatorController.getYButtonReleased();
    }
    public static boolean autoRangingShot(){
        return oi.operatorController.getBackButtonPressed();
    }
    public static boolean stopAutoRangingShot(){
        return oi.operatorController.getBackButtonReleased();
    }
    public static boolean armHigh(){
        return oi.operatorController.getPOV()== 0;
    }
    public static boolean armLow(){
        return oi.operatorController.getPOV()== 180;
    }
    public static double armOutput(){
        return oi.operatorController.getRawAxis(5);
    }
    //autochooser
    public static boolean crossLine(){
        return oi.autoChooser.getRawButton(1);
    }
    public static boolean crossLineShoot(){
        return oi.autoChooser.getRawButton(2);
    }
    public static boolean right6Auto(){
        return oi.autoChooser.getRawButton(3);
    }
    public static boolean right8Auto(){
        return oi.autoChooser.getRawButton(4);
    }

}
