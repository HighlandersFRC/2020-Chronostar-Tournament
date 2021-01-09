/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controls;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class FireSequence extends SequentialCommandGroup {
  /**
   * Creates a new InitationLineBasicFire.
   */
  public FireSequence(double flywheelVelocity, double hoodPosition, double time) {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new ParallelCommandGroup(new ConditionalSetFlyWheelVelocity(flywheelVelocity), new ConditionalSetHoodPosition(hoodPosition)),  new TimedMagazineRun(time), new SetFlyWheelVelocity(0), new SetHoodPosition(0));
   /* super(new SetFlyWheelVelocity(4500), new SetHoodPosition(10.5), new WaitCommand(1.5),  new MagazineControl(.6, 1, 1 ) , new WaitCommand(1.5),
    new MagazineControl(0, 0, 0), new SetFlyWheelVelocity(0), new SetHoodPosition(0));*/
  
  }
}
