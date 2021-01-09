/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.controls;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ShootingSequence extends SequentialCommandGroup {
  /**
   * Creates a new ShootingSequence.
   */
  public ShootingSequence() {
    super(new MagazineControl(0.55,1), new WaitCommand(.2), new MagazineControl(0.55,1), new WaitCommand(.2), new MagazineControl(0.55,1),
    new WaitCommand(.2), new MagazineControl(0.55,1), new WaitCommand(.2), new MagazineControl(0.55,1), new WaitCommand(.5), new MagazineControl(0,0) );

    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
  }
}
