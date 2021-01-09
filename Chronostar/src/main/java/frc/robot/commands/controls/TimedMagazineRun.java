/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controls;

import org.ejml.data.DEigenpair;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotMap;

public class TimedMagazineRun extends CommandBase {
  /**
   * Creates a new timedMagazineRun.
   */
  private double desiredRunTime;
  private double startTime;

  public TimedMagazineRun(double runTime) {
    desiredRunTime = runTime;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    RobotMap.magazine.overrideFeeding(true);
    startTime = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    RobotMap.magazine.runMagazineSystem();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    RobotMap.magazine.stopMagazineSystem();
    RobotMap.magazine.overrideFeeding(false);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(Timer.getFPGATimestamp()-startTime)>desiredRunTime;
  }
}
