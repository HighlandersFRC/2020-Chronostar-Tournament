/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;
import frc.robot.ButtonMap;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.sensors.LidarLite;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANDigitalInput;
import com.revrobotics.CANDigitalInput.LimitSwitchPolarity;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Hood extends SubsystemBase {
  private double kf = .02;
  private double kp = 0.00009;
  private double ki = 0.0027;
  private double kd = 0.08;
  private float maxpoint = 22;
  private float minpoint = 0;
  private CANPIDController mpidController = new CANPIDController(RobotMap.hoodMotor);
  private CANDigitalInput m_forwardLimit;
  private CANDigitalInput m_reverseLimit;
  private double dPosition;
  private CANEncoder hoodEncoder;

  public void inithood(){
    m_forwardLimit = RobotMap.hoodMotor.getForwardLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    m_reverseLimit = RobotMap.hoodMotor.getReverseLimitSwitch(LimitSwitchPolarity.kNormallyOpen);
    RobotMap.hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
    RobotMap.hoodMotor.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
    RobotMap.hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, maxpoint);
    RobotMap.hoodMotor.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, minpoint);
    RobotMap.hoodMotor.enableVoltageCompensation(11.3);
    mpidController = RobotMap.hoodMotor.getPIDController();
    hoodEncoder = RobotMap.hoodMotor.getEncoder();
    mpidController.setFF(kf);
    mpidController.setP(kp);
    mpidController.setI(ki);
    mpidController.setD(kd);
    mpidController.setIZone(.2);
    mpidController.setOutputRange(-1, 1);
    mpidController.setSmartMotionMaxVelocity(160, 0);
    mpidController.setSmartMotionMinOutputVelocity(-160, 0);
    mpidController.setSmartMotionMaxAccel(100, 0);
    mpidController.setSmartMotionAllowedClosedLoopError(.1, 0);
  }
  public Hood() {
  }
  public void resetEncodermin(){
    RobotMap.hoodMotor.getEncoder().setPosition(minpoint);
  }
  public void resetEncodermax(){
    RobotMap.hoodMotor.getEncoder().setPosition(maxpoint);
  }
  public boolean hoodClose(){
    return Math.abs(RobotMap.hoodMotor.getEncoder().getPosition()-dPosition)<0.2&&Math.abs(RobotMap.hoodMotor.get())<0.01;
  }
  public double getOptimalPosition(){
    double lidarDist = RobotMap.lidar1.getDistance();
    double camDist = Robot.visionCamera.getDistance();
    double dist;
    dist = lidarDist;
    if(dist>=1.9 &&dist <=25){
      return 0.0076*Math.pow(dist,3) - 0.3358*Math.pow(dist,2)+ 4.8685*dist - 8.048;
    }
    else{
      return -1;
    }
  }
  public void setHoodPosition(double desiredPosition){
    dPosition =desiredPosition;
    mpidController.setReference(desiredPosition, ControlType.kSmartMotion);
  }
  public double autoHoodPositionCloseDistance(double dist){
    return 0;
  }
  public double autoHoodPositionFarDistance(double dist){
    return 0;
  }
  @Override
  public void periodic() {
    if(m_reverseLimit.get() == true) {
      resetEncodermin();
    }
    if(m_forwardLimit.get() == true){
       resetEncodermax();
    }
    if((dPosition == 0) && (RobotMap.hoodMotor.getEncoder().getPosition() <= 1)){
      RobotMap.hoodMotor.set(-0.05);
    }
    SmartDashboard.putNumber("get Position", RobotMap.hoodMotor.getEncoder().getPosition());
  }
  public void teleopPeriodic(){
  }
}
