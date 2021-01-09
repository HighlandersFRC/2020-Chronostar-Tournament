/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.SupplyCurrentLimitConfiguration;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Counter;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Relay;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.Spark;
import frc.robot.sensors.LidarLite;
import frc.robot.sensors.Navx;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Hood;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Magazine;
import frc.robot.subsystems.Shooter;
import frc.robot.tools.pathTools.PathList;

public class RobotMap {
  public static AHRS navx = new AHRS(Port.kMXP);

  public static Navx mainNavx = new Navx(navx);

  public static Relay visionRelay1 = new Relay(0);

  public static int beamBreak1Port = 0;
  public static int beamBreak2Port = 1;
  public static int beamBreak3Port = 3;
  public static int beamBreak4Port = 4;


  public static int rightDriveLeadID = 1;
  public static int leftDriveLeadID = 3;

  public static int rightFollowerID = 2;
  public static int leftFollowerID = 4;

  public static int shooterMasterID = 5;
  public static int shooterFollowerID = 6;

  public static int hoodID = 7;

  public static int magazineBeltID = 8;
  public static int indexerID = 9;

  public static int Intake2ID = 10;

  public static int intakeMotorID = 12;

  public static int climberMotorId = 13;
  public static int armID = 11;

  public static TalonFX leftDriveLead = new TalonFX(leftDriveLeadID);
  public static TalonFX rightDriveLead = new TalonFX(rightDriveLeadID);

  public static TalonFX leftDriveFollowerOne = new TalonFX(leftFollowerID);
  public static TalonFX rightDriveFollowerOne = new TalonFX(rightFollowerID);

  public static TalonFX shooterMaster = new TalonFX(shooterMasterID);
  public static TalonFX shooterFollower = new TalonFX(shooterFollowerID);

  public static VictorSPX magazineBelt = new VictorSPX(magazineBeltID);

  public static CANSparkMax indexer = new CANSparkMax(indexerID, MotorType.kBrushless);

  public static CANSparkMax hoodMotor = new CANSparkMax(hoodID, MotorType.kBrushless);

  public static TalonFX intakeMotor = new TalonFX(intakeMotorID);
  public static VictorSPX intake2Motor = new VictorSPX(Intake2ID);

  public static CANSparkMax armMotor = new CANSparkMax(armID, MotorType.kBrushless);

  public static CANSparkMax winchMotor = new CANSparkMax(climberMotorId, MotorType.kBrushless);

  public static Spark blinkin = new Spark(0);
  public static DigitalInput beamBreakOne = new DigitalInput(beamBreak1Port);
  public static DigitalInput beamBreakTwo = new DigitalInput(beamBreak2Port);
  public static DigitalInput beamBreakThree = new DigitalInput(beamBreak3Port);
  public static DigitalInput beamBreakFour = new DigitalInput(beamBreak4Port);
  public static PathList pathList = new PathList();
  
  public static SupplyCurrentLimitConfiguration robotCurrentConfigurationEnabled = new SupplyCurrentLimitConfiguration(true, RobotStats.driveTrainMaxCurrent, RobotStats.driveTrainPeakThreshold, RobotStats.driveTrainPeakTime);

  public static SupplyCurrentLimitConfiguration robotCurrentConfigurationDisabled = new SupplyCurrentLimitConfiguration(false, RobotStats.driveTrainMaxCurrent, RobotStats.driveTrainPeakThreshold, RobotStats.driveTrainPeakTime);

  public static Counter lidarCounter = new Counter(2);

  public static LidarLite lidar1 = new LidarLite(lidarCounter);
  
  public static DoubleSolenoid climberReleasePiston = new DoubleSolenoid(0, 1);
  public static DoubleSolenoid.Value releaseArm = Value.kReverse;
  public static DoubleSolenoid.Value constrainArm = Value.kForward;
  
  public static DoubleSolenoid winchRatchetPiston = new DoubleSolenoid(2,3);
  public static DoubleSolenoid.Value winchRatchetRelease = Value.kReverse;
  public static DoubleSolenoid.Value winchRatchetSet = Value.kForward;


  public static TalonFX driveMotors[] = {
    RobotMap.leftDriveLead,
    RobotMap.rightDriveLead,
    RobotMap.leftDriveFollowerOne,
    RobotMap.rightDriveFollowerOne,
  };
  public static TalonFX driveMotorLeads[] = {
    RobotMap.leftDriveLead,
    RobotMap.rightDriveLead,
  };
  public static TalonFX allTalonMotorLeads[] = {
    RobotMap.leftDriveLead,
    RobotMap.rightDriveLead,
  };
  public static TalonFX allFalcons[] = {
    RobotMap.leftDriveLead,
    RobotMap.rightDriveLead,
    RobotMap.leftDriveFollowerOne,
    RobotMap.rightDriveFollowerOne,
    RobotMap.shooterMaster,
    RobotMap.shooterFollower
  };
  public static TalonFX shooterMotors[] = {
    RobotMap.shooterMaster,
    RobotMap.shooterFollower
  };
  public static DriveTrain drive = new DriveTrain();
  public static Shooter shooter = new Shooter();
  public static Hood hood = new Hood();
  public static Magazine magazine = new Magazine();
  public static Intake intake = new Intake();
  public static Climber climber = new Climber();
  public static Arm arm = new Arm();
  
}
