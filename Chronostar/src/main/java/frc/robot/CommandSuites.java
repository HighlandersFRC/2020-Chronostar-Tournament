/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import frc.robot.commands.autos.CenterHighGoalAuto;
import frc.robot.commands.autos.CrossLine;
import frc.robot.commands.autos.Right6Auto;
import frc.robot.commands.autos.Right8Auto;

/**
 * Add your docs here.
 */
public class CommandSuites {
    private CenterHighGoalAuto centerHighGoalAuto;
    private CrossLine crossLine;
    private Right6Auto right6Auto;
    private Right8Auto right8Auto;

    public CommandSuites(){
    }
    public void startAutoCommands(){
       if(ButtonMap.crossLineShoot()){
            RobotMap.drive.startAutoOdometry(true, 10, -8);
            centerHighGoalAuto = new CenterHighGoalAuto();
            centerHighGoalAuto.schedule();
        }
        else if(ButtonMap.crossLine()){
            RobotMap.drive.startAutoOdometry(true, 10, -8);
            crossLine = new CrossLine();
            crossLine.schedule();
        }
        else if(ButtonMap.right6Auto()){
            RobotMap.drive.startAutoOdometry(true, 10, -2.4);
            right6Auto = new Right6Auto();
            right6Auto.schedule();
        }
        else if(ButtonMap.right8Auto()){

        }
        else{
            RobotMap.drive.startAutoOdometry(true, 10, -8);
            centerHighGoalAuto = new CenterHighGoalAuto();
            centerHighGoalAuto.schedule();
        }
        RobotMap.drive.startAutoOdometry(true, 10, -2.4);
        right8Auto = new Right8Auto();
        right8Auto.schedule();

    }

}
