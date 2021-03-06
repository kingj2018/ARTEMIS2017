package org.usfirst.frc.team5876.robot;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;


public class Robot extends IterativeRobot {
	final Integer middleAuto = new Integer(0);
	final Integer rightAuto = new Integer(1);
	final Integer leftAuto = new Integer(2);
	final Integer driveAuto = new Integer(3);
	Integer autoSelected;

	RobotDrive robotDrive;
	Joystick stick;
	Joystick gamepad;
	SpeedController climbBack;
	SpeedController climbFront;
	SpeedController shoot;
	SpeedController gear;
	SpeedController intake;
	SpeedController agitator;
	Encoder enc;
	Compressor compressor;
	DoubleSolenoid exampleDouble;
	//SpeedController spin;
	SendableChooser<Integer> chooser = new SendableChooser<Integer>();
	ADXRS450_Gyro gyro;
	AnalogInput lightSensor;
	DigitalInput uswitch;
	Timer timer;

	
	 @Override
	 public void robotInit() {
		 chooser.addDefault("Middle Gear", middleAuto);
		 chooser.addObject("Right Gear", rightAuto);
		 chooser.addObject("Left Gear", leftAuto);
		 chooser.addObject("Drive Over Line", driveAuto);
		 SmartDashboard.putData("Auto choices", chooser);
		 
		 
		 stick = new Joystick(0);
		 gamepad = new Joystick(1);
		 SpeedController driveLeftFront = new VictorSP(0);
		 SpeedController driveLeftBack = new VictorSP(1);
		 SpeedController driveRightFront = new VictorSP(2);
		 SpeedController driveRightBack = new VictorSP(3);
		 climbFront = new VictorSP(4);
		 climbBack = new VictorSP(5);
		 shoot = new VictorSP(7);
		 gear = new Spark(9); //changgeeee meeeeeeeeee
		 agitator = new VictorSP(8);
		 //spin = new VictorSP(6);
		 compressor = new Compressor(0);

		 compressor.setClosedLoopControl(true);
		 compressor.setClosedLoopControl(false);
		 exampleDouble = new DoubleSolenoid(2, 3);

		 exampleDouble.set(DoubleSolenoid.Value.kOff);
//		 exampleDouble.set(DoubleSolenoid.Value.kForward);
//		 exampleDouble.set(DoubleSolenoid.Value.kReverse);
		 enc = new Encoder(9,8);
		 
		 timer = new Timer();
		 gyro = new ADXRS450_Gyro();
		 gyro.calibrate();
		 uswitch= new DigitalInput(1);
		 lightSensor = new AnalogInput(3);
		 robotDrive = new RobotDrive(driveLeftFront, driveLeftBack, driveRightFront, driveRightBack);
	 }

	 @Override
	 public void autonomousInit() {
		 timer.reset();
		 timer.start();
		 gyro.reset();
		 autoSelected = chooser.getSelected();
		 System.out.println("Auto selected: " + autoSelected);
	 }

	
	 @Override
		public void autonomousPeriodic() {
			
			switch (autoSelected) {
			case 1: //SHOULD TURN LEFT FOR THE GEAR HOOK ON THE RIGHT
				System.out.println(gyro.getAngle());
				System.out.println("Auto 1");
				
				if (timer.get() < 1.5){ //.5 seconds less of slow advance
					double angle = gyro.getAngle();
					double Kp = 0.05;
					robotDrive.arcadeDrive(-0.4, angle * Kp);//change back to negative
					Timer.delay(0.01);
				}
				else if (timer.get() < 4.2) { // 
					
					double angle = gyro.getAngle();
					double Kp = 0.05;
					robotDrive.arcadeDrive(-0.5, angle * Kp);//change back to negative
					Timer.delay(0.01);
					 
					
					
				}

				else if (timer.get() < 6.5) {
					
					if (gyro.getAngle() < -59.8) {
						robotDrive.arcadeDrive(0, -0.5);
					}
					else if (gyro.getAngle() > -60.2) {
						robotDrive.arcadeDrive(0, 0.5);
					}
					else {
						robotDrive.arcadeDrive(0, 0);
					}
				}
				else if (timer.get() < 9.5) {
					double angle = gyro.getAngle();
					double Kp = 0.05;
					robotDrive.arcadeDrive(-0.5, (angle +60) * Kp);//change back to negative
					Timer.delay(0.01);
					/*if (timer.get() <= 8.5){
					gear.set(0.4);
					}
					else if(timer.get()<= 10.5){
					gear.set(-0.4);
				} */
				}
				else {
					robotDrive.arcadeDrive(0, 0);
				}
			
				break;
			case 2: // Should go for the left gear - turn right
				
				System.out.println(gyro.getAngle());
				System.out.println("Auto 2");

				if (timer.get() < 1.5){
					double angle = gyro.getAngle();
					double Kp = 0.05;
					robotDrive.arcadeDrive(-0.4, angle * Kp);//change back to negative
					Timer.delay(0.01);
				}
				else if (timer.get() < 4.2) {
					
					double angle = gyro.getAngle();
					double Kp = 0.05;
					robotDrive.arcadeDrive(-0.5, angle * Kp);//change back to negative
					//robotDrive.arcadeDrive(-0.35, () * Kp);
					Timer.delay(0.01);
					
				}

				else if (timer.get() < 6.5) {
					
					if (gyro.getAngle() < 60.2) {
						robotDrive.arcadeDrive(0, -0.5);
					}
					else if (gyro.getAngle() > 59.8) {
						robotDrive.arcadeDrive(0, 0.5);
					}
					else {
						robotDrive.arcadeDrive(0, 0);
					}
					
					
				}
				
				else if (timer.get() < 9.5) {
					double angle = gyro.getAngle();
					double Kp = 0.05;
					robotDrive.arcadeDrive(-0.5, (angle -60) * Kp);//change back to negative
					Timer.delay(0.01);
					/*if (timer.get() >= 8.5){
						gear.set(0.4);
						}
						else if(timer.get()>= 10.5){
						gear.set(-0.4);
					} */
				}
				else {
					robotDrive.arcadeDrive(0, 0);
				}
				break;
				
			case 3: //just drives over line
				System.out.println(gyro.getAngle());
				System.out.println("Auto 3");
				
				if (timer.get() < 6.2) {
					double angle = gyro.getAngle();
					double Kp = 0.05;
					robotDrive.arcadeDrive(-0.5, angle * Kp);
					
					Timer.delay(0.01);
				}
				else {
					robotDrive.arcadeDrive(0,0);
				}
			case 0: //GOES STRAIGHT FORWARD. WORKS!!!
			default:
				
				System.out.println(gyro.getAngle());
				System.out.println("Auto 0");
				
				
				
				if (timer.get() < 1.5){
					double angle = gyro.getAngle();
					double Kp = 0.05;
					robotDrive.arcadeDrive(-0.35, angle * Kp);
					Timer.delay(0.01);
				}

				else if (timer.get() < 5.2) {
					
					double angle = gyro.getAngle();
					double Kp = 0.05;
					robotDrive.arcadeDrive(-0.5, angle * Kp);
					
					Timer.delay(0.01);
					
				/*	if (timer.get() >= 4.2){
						gear.set(0.4);
						}
						else if(timer.get()<= 6.2){
						gear.set(-0.4);
					}*/
					
				}
				break;
			}
		}
	 
	 
	 
	 
	 @Override
	 public void teleopPeriodic() {
//		 System.out.println(gyro.getAngle());
		 System.out.println(uswitch.get());
		 compressor.start();
		 int pov = stick.getPOV();
		 int povTurn = gamepad.getPOV();
		 boolean button = gamepad.getRawButton(1);
		 float slow = 0.5f;
		 float turn = 0, forward = 0;
		 if (gamepad.getRawButton(2) == true) {
			 exampleDouble.set(DoubleSolenoid.Value.kForward);
		 } else {
			 exampleDouble.set(DoubleSolenoid.Value.kReverse);
		 }
		 if (gamepad.getRawButton(5)== true) {
			 robotDrive.arcadeDrive(0,0.4);
		 }
		 else if (gamepad.getRawButton(6)== true) {
			 robotDrive.arcadeDrive(0,-0.4);
		 }
		 
		 else if (pov != -1 || povTurn != -1) {
			 switch (pov) {
			 case 0:
			 case 315:
			 case 45:
				 forward = -slow;
				 break;
			 case 180:
			 case 135:
			 case 225:
				 forward = slow;
				 break;
			 } 
			 switch (povTurn) {
			 case 90:
			 case 45:
			 case 135:	 
				 turn = -slow;
				 break;
			 case 270:
			 case 225:
			 case 315:
				 turn = slow;
				 break;
			 }
			 robotDrive.arcadeDrive(forward, turn);
		 }
		 

		 else {
			 robotDrive.arcadeDrive((stick.getRawAxis(1)), -(gamepad.getRawAxis(0)), true);
		 }
		 timer.start();
		 timer.reset();
		 if(button==true){ //SHOOT
			 shoot.set(-(gamepad.getRawAxis(3)+1));
			 agitator.set(0.3);
			
		 }
		 else if(button==false){
			 shoot.set(0);
			 agitator.set(0);
		 }
		 
		/* if(gamepad.getRawButton(3)==true){ //FUEL INTAKE
			 climbFront.set(1);
			 climbBack.set(1);
		 }
		 else if(gamepad.getRawButton(3)==false) {
			 climbBack.set(0);
			 climbFront.set(0);
		 } */
		
		 if (stick.getRawButton(1) == true){ //CLIMB
			 climbFront.set((stick.getRawAxis(3)+1));
			 climbBack.set((stick.getRawAxis(3)+1));
		 }
		 else {
			 climbFront.set(0);
			 climbBack.set(0);
		 }
		 
		/* if (gamepad.getRawButton(2)==true){ //GEAR 
				gear.set(0.4); 
			 
		 }
		 else {
				gear.set(-0.4); 
			 } 
		  */
		 
		 /*if (stick.getRawButton(x) == true {
		 	spin.set(1);
		 	}
		 else{
		 	spin.set(0);
		 	}*/
	 }

	 @Override
	 public void testPeriodic() {
	 }

}