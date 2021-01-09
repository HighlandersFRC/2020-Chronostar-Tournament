/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controls;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotMap;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class DeployClimber extends SequentialCommandGroup {
  /**
   * Creates a new DeployClimber.
   */
  public DeployClimber() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new ArmSetPosition(0), new WaitCommand(2), new SetPiston(RobotMap.winchRatchetPiston, RobotMap.winchRatchetRelease), new SetPiston(RobotMap.climberReleasePiston, RobotMap.releaseArm), new WaitCommand(.3), new WachetSpeed(.5),
     new WaitCommand(2.75), new WachetSpeed(0),new WaitCommand(.2), new SetPiston(RobotMap.winchRatchetPiston, RobotMap.winchRatchetSet), new SetPiston(RobotMap.climberReleasePiston, RobotMap.constrainArm));
  }

}
