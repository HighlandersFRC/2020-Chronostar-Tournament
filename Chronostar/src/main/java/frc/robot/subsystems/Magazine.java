/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.button.Button;
import frc.robot.ButtonMap;
import frc.robot.RobotMap;
import frc.robot.commands.controls.FeederBeltAutomation;
import frc.robot.commands.controls.FeederWheelsAutomation;
import frc.robot.commands.controls.MagazineAutomation;
import frc.robot.commands.controls.MagazineControl;
import frc.robot.commands.controls.RunBeltBurst;
import frc.robot.commands.controls.ShootingSequence;

public class Magazine extends SubsystemBase {
  private boolean lastState;
  private boolean lastState2;
  public Boolean stuck;
  public Boolean Shooting;
  private double magCount;
  private double catchCount;
  private double tryCount;
  private RunBeltBurst runBeltBurst = new RunBeltBurst();

  public void initMagazine(){
    Shooting = false;
  }
  public Magazine() {
  }
  @Override
  public void periodic() {
    if((ButtonMap.reverseMag() == false) && Shooting == false){
        if ((RobotMap.beamBreakThree.get() == false) && (RobotMap.beamBreakTwo.get() == true) && (!runBeltBurst.isScheduled())){
          runBeltBurst = new RunBeltBurst();
          runBeltBurst.schedule();
        }
        if(RobotMap.beamBreakTwo.get() == false){
          runBeltBurst.cancel();
          new FeederBeltAutomation(0, .05).schedule();
          new FeederWheelsAutomation(0, 0.05).schedule();
        }
        if(RobotMap.beamBreakOne.get() == false){
          if(catchCount <= 50){
            new FeederBeltAutomation(.6, .15).schedule();;
          }
          else{
            if (tryCount <= 25){
             stuck = true;
             tryCount++;
            }
            else{
               new FeederBeltAutomation(-0.8, .1);
               catchCount = 0;
               tryCount = 0;
               stuck = false;
            }
          }
          catchCount++;
        }
        else{
            catchCount = 0;
            stuck = false;
        }
    }// the code above automates the magazine, it takes the 3 beam breaks and is able to sense if a ball is stuck at its position
     // if a ball is stuck the magazing will briefly reverseto un-jam it
    lastState = RobotMap.beamBreakOne.get();
    lastState2 = RobotMap.beamBreakThree.get();
    SmartDashboard.putBoolean("1 beamBroken", !RobotMap.beamBreakOne.get());
    SmartDashboard.putBoolean("2 beamBroken", !RobotMap.beamBreakTwo.get());
    SmartDashboard.putBoolean("3 beamBroken", !RobotMap.beamBreakThree.get());
  }
  public void runMagazineSystem(){
    RobotMap.magazineBelt.set(ControlMode.PercentOutput, 0.42);
    RobotMap.indexer.set(-1);
  }
  public void stopMagazineSystem(){
    RobotMap.magazineBelt.set(ControlMode.PercentOutput, 0);
    RobotMap.indexer.set(0);
  }
  public void overrideFeeding(boolean simonIsBad){
    Shooting = simonIsBad;
  }
  public void setBeltSpeed(double beltSpeed){
    RobotMap.magazineBelt.set(ControlMode.PercentOutput, beltSpeed);
  }
  public void setIndexerSpeed(double indexerSpeed){
    RobotMap.indexer.set(indexerSpeed);
  }
  public void teleopPeriodic(){
    SmartDashboard.putBoolean("beamBroken", !RobotMap.beamBreakOne.get());
    if(ButtonMap.reverseMag() == true){
      RobotMap.magazineBelt.set(ControlMode.PercentOutput, -1);
      RobotMap.indexer.set( .4);
    }
    if(ButtonMap.stopReverseMag()){
      RobotMap.magazineBelt.set(ControlMode.PercentOutput, 0);
      RobotMap.indexer.set(0);
    }
  }
}