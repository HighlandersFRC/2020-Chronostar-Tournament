/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autos;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.controls.FireSequence;
import frc.robot.commands.controls.SetFlyWheelVelocity;
import frc.robot.commands.controls.SetHoodPosition;
import frc.robot.commands.controls.TrackVisionTarget;
import frc.robot.tools.controlLoops.PurePursuitController;
import frc.robot.tools.pathTools.PathList;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Right8Auto extends SequentialCommandGroup {
  /**
   * Creates a new Right8Auto.
   */
  public Right8Auto() {
    // Add your commands in the super() call, e.g.
    // super(new FooCommand(), new BarCommand());
    super(new ParallelCommandGroup(new SetFlyWheelVelocity(4500), new SetHoodPosition(10.5), new TrackVisionTarget()), new FireSequence(4500, 10, 1.0), new PurePursuitController(PathList.right8AutoPath1, 2.5, 4.0,true, true ),new ParallelCommandGroup( new PurePursuitController(PathList.right8AutoPath2, 2.5, 4.0,true, true )),new ParallelCommandGroup(new SetFlyWheelVelocity(5250), new SetHoodPosition(13.5), new TrackVisionTarget()), new FireSequence(5250, 13.5, 1.8));
  }
}
