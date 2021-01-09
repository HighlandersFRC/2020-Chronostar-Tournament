/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Relay.Value;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.sensors.VisionCamera;

public class Robot extends TimedRobot {
  public static OI m_oi;
  Command m_autonomousCommand;
  private RobotConfig robotConfig;
  private UsbCamera camera;
  private UsbCamera camera2;
  private VideoSink server;
  private boolean cameraBoolean;
  private boolean ableToSwitch;
  private SerialPort cameraPort;
  public static VisionCamera visionCamera;
  private CommandSuites commandSuites;

  public void robotInit() {
    robotConfig = new RobotConfig();
    robotConfig.setStartingConfig();
    RobotMap.hood.inithood();
    RobotMap.magazine.initMagazine();
    RobotMap.arm.initarm();
    m_oi = new OI();
    commandSuites = new CommandSuites();

    try {
      cameraPort = new SerialPort(115200, Port.kUSB);
      visionCamera = new VisionCamera(cameraPort);
    } catch (Exception e) {
      System.err.println("cameras faild to connect");
    }
    camera = CameraServer.getInstance().startAutomaticCapture("VisionCamera1", "/dev/video0");
    camera.setResolution(320, 240);
    camera.setFPS(15);

    camera2 = CameraServer.getInstance().startAutomaticCapture("VisionCamera2", "/dev/video1");
    camera2.setResolution(320, 240);
    camera2.setFPS(15);
    RobotMap.visionRelay1.set(Value.kReverse);

    server = CameraServer.getInstance().addSwitchedCamera("driverVisionCameras");
    server.setSource(camera);
    Shuffleboard.update();
    SmartDashboard.updateValues();
    CommandScheduler.getInstance().enable();
  }

  @Override
  public void robotPeriodic() {
    try {
      Robot.visionCamera.updateVision();
      SmartDashboard.putNumber("optimalDist", RobotMap.hood.getOptimalPosition());
      SmartDashboard.putString("camString", Robot.visionCamera.getString());
      SmartDashboard.putNumber("camDist", Robot.visionCamera.getDistance());
      SmartDashboard.putNumber("lidarDist", RobotMap.lidar1.getDistance());
      if (ButtonMap.switchCamera() && ableToSwitch) {
        if (cameraBoolean) {
          server.setSource(camera2);
          cameraBoolean = false;
        } else if (!cameraBoolean) {
          server.setSource(camera);
          cameraBoolean = true;
        }
        ableToSwitch = false;
      } else if (!ButtonMap.switchCamera()) {
        ableToSwitch = true;
      }
      RobotMap.magazine.periodic();
      RobotMap.hood.periodic();
      RobotMap.shooter.periodic();
      RobotMap.arm.periodic();

    } catch (Exception e) {
    }
    CommandScheduler.getInstance().run();
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {

    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    RobotMap.arm.initarm();
    robotConfig.setAutoConfig();
    commandSuites.startAutoCommands();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  @Override
  public void autonomousPeriodic() {
    RobotMap.intake.autonomousPeriodic();
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    robotConfig.setTeleopConfig();
    RobotMap.arm.initarm();
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
    RobotMap.climber.initClimber();
  }

  @Override
  public void teleopPeriodic() {
    RobotMap.drive.teleopPeriodic();
    RobotMap.shooter.teleopPeriodic();
    RobotMap.hood.teleopPeriodic();
    RobotMap.magazine.teleopPeriodic();
    RobotMap.intake.teleopPeriodic();
    RobotMap.climber.teleopPeriodic();
    Scheduler.getInstance().run();
  }

  @Override
  public void testPeriodic() {
  }
}
