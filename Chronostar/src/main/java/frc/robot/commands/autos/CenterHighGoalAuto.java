/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.RobotMap;
import frc.robot.commands.controls.ConditionalSetFlyWheelVelocity;
import frc.robot.commands.controls.ConditionalSetHoodPosition;
import frc.robot.commands.controls.MagazineAutomation;
import frc.robot.commands.controls.SetFlyWheelVelocity;
import frc.robot.commands.controls.SetHoodPosition;
import frc.robot.commands.controls.TimedMagazineRun;
import frc.robot.commands.controls.TrackVisionTarget;
import frc.robot.tools.controlLoops.PurePursuitController;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class CenterHighGoalAuto extends SequentialCommandGroup {
  /**
   * Creates a new CenterHighGoalAuto.
   */
  public CenterHighGoalAuto() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new ParallelCommandGroup(new ConditionalSetFlyWheelVelocity(4500), new ConditionalSetHoodPosition(10.5), new TrackVisionTarget()),  new TimedMagazineRun(2.5), new SetFlyWheelVelocity(0), new SetHoodPosition(0),new PurePursuitController(RobotMap.pathList.center3AutoPath1, 2.5, 5.0, true, true));
  }
}
