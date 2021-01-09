/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.controls.SetFlyWheelVelocity;
import frc.robot.commands.controls.SetHoodPosition;

/**
 * Add your docs here.
 */
public class RobotConfig {
    public void setStartingConfig() {

        RobotConfig.setAllMotorsBrake();
        RobotMap.rightDriveLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);
        RobotMap.leftDriveLead.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor, 0, 0);

        RobotMap.rightDriveFollowerOne.set(ControlMode.Follower, RobotMap.rightDriveLeadID);
        RobotMap.leftDriveFollowerOne.set(ControlMode.Follower, RobotMap.leftDriveLeadID);

        RobotMap.rightDriveLead.setInverted(true);
        RobotMap.rightDriveFollowerOne.setInverted(InvertType.FollowMaster);

        RobotMap.leftDriveLead.setInverted(false);
        RobotMap.leftDriveFollowerOne.setInverted(InvertType.FollowMaster);

        RobotMap.leftDriveLead.setSensorPhase(false);
        RobotMap.rightDriveLead.setSensorPhase(false);

        RobotMap.leftDriveLead.setSelectedSensorPosition(0, 0, 0);
        RobotMap.rightDriveLead.setSelectedSensorPosition(0, 0, 0);

        RobotMap.drive.initVelocityPIDs();
        RobotMap.drive.initAlignmentPID();
        RobotMap.hoodMotor.setInverted(true);
        RobotMap.hoodMotor.setIdleMode(IdleMode.kBrake);
        RobotMap.hoodMotor.setSmartCurrentLimit(RobotStats.hoodCurrentLimit);
        RobotMap.hood.inithood();
        RobotMap.hoodMotor.setInverted(true);

        RobotMap.magazineBelt.setNeutralMode(NeutralMode.Brake);

        RobotMap.indexer.setIdleMode(IdleMode.kCoast);
        RobotMap.indexer.setSmartCurrentLimit(RobotStats.indexerCurrentLimit);

        RobotMap.intakeMotor.setNeutralMode(NeutralMode.Coast);

        RobotConfig.enableDriveCurrentLimiting();
        RobotConfig.setDriveTrainVoltageCompensation();

        RobotMap.shooterMaster.setInverted(true);
        RobotMap.shooterFollower.set(ControlMode.Follower, RobotMap.shooterMasterID);
        RobotMap.shooterFollower.setInverted(InvertType.OpposeMaster);

        RobotMap.shooterMaster.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
        RobotMap.shooterMaster.setSelectedSensorPosition(0, 0, 0);

        RobotMap.shooterMaster.configPeakOutputForward(RobotStats.maxShooterPercentVoltage);
        RobotMap.shooterMaster.configPeakOutputReverse(0);
        RobotMap.shooterMaster.configClosedLoopPeakOutput(0, RobotStats.maxShooterPercentVoltage);
        RobotMap.shooter.initShooterPID();

        RobotMap.armMotor.setIdleMode(IdleMode.kBrake);
        RobotMap.armMotor.setInverted(true);
        RobotMap.climberReleasePiston.set(RobotMap.constrainArm);
        RobotMap.winchRatchetPiston.set(RobotMap.winchRatchetSet);
        RobotMap.winchMotor.setIdleMode(IdleMode.kCoast);
        RobotConfig.setShooterMotorsCoast();
        RobotConfig.setShooterMotorVoltageCompensation();
        RobotMap.climber.initClimber();
    }

    public void setTeleopConfig() {
        new SequentialCommandGroup(new SetFlyWheelVelocity(0), new SetHoodPosition(0));
        RobotConfig.disableDriveTrainVoltageCompensation();
        RobotConfig.enableDriveCurrentLimiting();
        RobotConfig.setDriveMotorsCoast();
    }
    public void setAutoConfig(){
        RobotConfig.setDriveTrainVoltageCompensation();
        RobotConfig.enableDriveCurrentLimiting();
        RobotConfig.setDriveMotorsBrake();
    }
    public static void setAllMotorsBrake() {
		for(TalonFX talon:RobotMap.allFalcons){
            talon.setNeutralMode(NeutralMode.Brake);
        }
    }
    public static void setAllMotorsCoast() {
		for(TalonFX talon:RobotMap.allFalcons){
            talon.setNeutralMode(NeutralMode.Coast);
        }
	}
	public static void setDriveMotorsCoast() {
		for(TalonFX talon:RobotMap.driveMotors){
            talon.setNeutralMode(NeutralMode.Coast);
        }
	}
	public static void setDriveMotorsBrake() {
		for(TalonFX talon:RobotMap.driveMotors){
            talon.setNeutralMode(NeutralMode.Brake);
        }
    }
    public static void enableDriveCurrentLimiting() {
		for(TalonFX talon:RobotMap.driveMotors){
            talon.configSupplyCurrentLimit(RobotMap.robotCurrentConfigurationEnabled);
        }
    }
    public static void disableDriveCurrentLimiting() {
		for(TalonFX talon:RobotMap.driveMotors){
            talon.configSupplyCurrentLimit(RobotMap.robotCurrentConfigurationDisabled);
        }
    }
    public static void setDriveTrainVoltageCompensation(){
        for(TalonFX talon:RobotMap.driveMotors){
            talon.configVoltageCompSaturation(RobotStats.voltageCompensationValue);
            talon.enableVoltageCompensation(true);
        }
    }
    public static void disableDriveTrainVoltageCompensation(){
        for(TalonFX talon:RobotMap.driveMotors){
            talon.enableVoltageCompensation(false);
        }
    }
    public static void setShooterMotorsCoast() {
		for(TalonFX talon:RobotMap.shooterMotors){
            talon.setNeutralMode(NeutralMode.Coast);
        }
    }
    public static void setShooterMotorVoltageCompensation(){
        for(TalonFX talon:RobotMap.shooterMotors){
            talon.configVoltageCompSaturation(11.7);
            talon.enableVoltageCompensation(true);
        }
    }
}
