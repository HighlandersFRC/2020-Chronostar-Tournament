/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.RobotState;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;
import frc.robot.RobotStats;

public class Shooter extends SubsystemBase {
  /**
   * Creates a new Shooter.
   */
  //fpid values for shooter
  //for a flywheel the kp should be very aggressive, this because it is a high intertial system that the motor can't really do a ton about
  //with small changes, so it is hard to make unstable. KI is simply to keep it on track when firing balls
  private double kF = 0.05;
  private double kP = 0.25;
  private double kI = 0.0001;
  private double kD = 0;
  //if the velocity value is outside this zone, which is about +- 200 rpm of the setpoint, then you clear the ivalue
  //this stops massive inital overshoot while still allowing for a fairly aggressive i value. 
  private int iZone = 439;
  private double shooterPower;
  //this shows the number of code cycles ~0.02 seconds that the wheel is a significantly away from the setpoint
  //allowing for quick estimation of the time it takes to spin up and recover
  private double offCount;

  public Shooter() {

  }
  public void initShooterPID(){
    shooterPower = 0;
    RobotMap.shooterMaster.config_kF(0, kF);
    RobotMap.shooterMaster.config_kP(0, kP);
    RobotMap.shooterMaster.config_kI(0, kI);
    RobotMap.shooterMaster.config_kD(0, kD);
    RobotMap.shooterMaster.config_IntegralZone(0, iZone);
    //SmartDashboard.putNumber("setVelocity", 0);
  }
  public void setFlyWheelSpeed(double velocity){
    if(velocity>RobotStats.maxShooterRPM){
      velocity = RobotStats.maxShooterRPM;
    }
    else if(velocity <0){
      velocity = 0;
    }
    RobotMap.shooterMaster.set(ControlMode.Velocity, convertRPMToEncoderTicsPer100ms(velocity));
  }
  //talon native units for velocity are in ticks/100 ms, to actually use these units different conversions have to happen
  public int convertRPMToEncoderTicsPer100ms(double rpm){
    return (int)((rpm/600)*RobotStats.flyWheelTicsPerWheelRotation);
  }
  public double getShooterVelocity(){
    return (RobotMap.shooterMaster.getSelectedSensorVelocity()/RobotStats.flyWheelTicsPerWheelRotation)*600;
  }
  public boolean flyWheelSpeedClose(){
    return RobotMap.shooterMaster.getClosedLoopError()<this.convertRPMToEncoderTicsPer100ms(100);
  }
  @Override
  public void periodic() {
    SmartDashboard.putNumber("Speed", this.getShooterVelocity());
    if(Math.abs(this.getShooterVelocity()-shooterPower)>100){
      offCount++;
      //SmartDashboard.putNumber("count", offCount);
    }
    else{
      offCount = 0;
    }
  }
  public void teleopPeriodic(){
    //Given the danger of stalling out the falcons, we check if there is a percent given and the sensor velocity implies no movement
    //has a fair number of false positives during motor startup
    if(RobotMap.shooterMaster.getMotorOutputPercent()!=0 && RobotMap.shooterMaster.getSelectedSensorVelocity() ==0){
      System.out.println("WARNING, ENCODER FALIURE");
      //RobotMap.shooterMaster.set(ControlMode.PercentOutput, 0);
    }
  }
}
