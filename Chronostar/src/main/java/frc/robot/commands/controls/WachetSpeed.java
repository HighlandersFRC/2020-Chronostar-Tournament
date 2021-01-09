/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controls;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotMap;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class WachetSpeed extends InstantCommand {
  double DrumSpeed;
  public WachetSpeed(double speed) {
    DrumSpeed = speed;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if (DrumSpeed <= 0){
      RobotMap.winchMotor.set(DrumSpeed);
    }
    else{
      new SetPiston(RobotMap.winchRatchetPiston, RobotMap.winchRatchetRelease);
      RobotMap.winchMotor.set(DrumSpeed);
    }
  }
}
