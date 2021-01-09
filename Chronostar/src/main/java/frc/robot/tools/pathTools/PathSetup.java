package frc.robot.tools.pathTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


import frc.robot.RobotConfig;
import frc.robot.RobotStats;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import jaci.pathfinder.followers.DistanceFollower;
import jaci.pathfinder.modifiers.TankModifier;

public class PathSetup {
	private double velocity;
	private DistanceFollower rightFollower;
	private DistanceFollower leftFollower;
	private boolean isReversed;
	private Trajectory mainPath;
	public PathSetup(Trajectory trajectory, boolean reverse) {
		mainPath = trajectory;
		isReversed = reverse;
	}
	public DistanceFollower getRightFollower(){
		return rightFollower;
	}
	public DistanceFollower getLeftFollower(){
		return leftFollower;
	}

	public boolean getReversed(){
		return isReversed;
	}
	public Trajectory getMainPath(){
		return mainPath;
	}
	public void generateCurvature(){

	}
	   
}


