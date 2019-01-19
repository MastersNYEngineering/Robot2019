/*
    # # # # # # # # # # # # # # # # # # 
    # Masters School Robotics         #
    # Written by Matthew Nappo        #
    #            Zach Battleman       #
    # GitHub: @xoreo, @Zanolon        #
    #                                 #
    # Class Robot                     #
    # # # # # # # # # # # # # # # # # # 
*/

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;

public class Robot {

    // Declare wheels
    public DcMotor w0 = null;
    public DcMotor w1 = null;
    public DcMotor w2 = null;
    public DcMotor w3 = null;

    // Declare motors for the lift
    private DcMotor liftRotateBottom = null;
    private DcMotor liftRotateTop    = null;
    private CRServo liftServoRight   = null;
    private CRServo liftServoLeft    = null;

    // Declare other motors
    private Servo markerDeployServo = null;
    private Servo armLockServo      = null;
    private Servo claw              = null;

    // Declare some other stuff
    public double     maxSpeed    = 0.3;
    public HardwareMap hardwareMap = null;

    Robot(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;

        // Init driving wheels
        w0 = InitDcMotor("w0");
        w1 = InitDcMotor("w1");
        w2 = InitDcMotor("w2");
        w3 = InitDcMotor("w3");

        // Init claw rotation
        liftRotateBottom = InitDcMotor("claw_rotate");
        liftRotateTop    = InitDcMotor("claw_rotate_top");

        // Init claw drive motors
        clawServoRight = InitCRServo("drive_claw_right");
        clawServoLeft  = InitCRServo("drive_claw_left");


        // Init other motors
        markerDeployServo = InitServo("marker");
        armLockServo      = InitServo("arm_lock");
        claw              = InitServo("claw");

    }

    // InitDcMotor - Initialize a DcMotor
    private DcMotor InitDcMotor(String id) {
        DcMotor m = null;
        m = hardwareMap.get(DcMotor.class, id);
        m.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        m.setDirection(DcMotor.Direction.REVERSE);
        return m;
    }

    // InitServo - Initialize a Servo
    private Servo InitServo(String id) {
        Servo s = null;
        s = hardwareMap.get(Servo.class, id);
        s.setDirection(Servo.Direction.FORWARD);
        return s;
    }
    
    // InitCRServo - Initialize a CRServo
    private CRServo InitCRServo(String id) {
        CRServo s = null;
        // Use one of the following two lines, not sure which one works (better?)
        // s = hardwareMap.get(CRServo.class, id);
        s = hardwareMap.crservo.get(id);
        return s;
    }

    /* BEGIN CONTROL METHODS */

    // GetDriveSpeeds - Calculate the necessary speed for each motor to drive (based off of joysticks)
    public double[] GetDriveSpeeds() {
        double x_left_joy = gamepad1.left_stick_x;
        double y_left_joy = gamepad1.left_stick_y;
        
        double phi_joy = Math.atan2(y_left_joy, x_left_joy);
        
        double x_left_joy_sq = Math.pow(x_left_joy, 2);
        double y_left_joy_sq = Math.pow(y_left_joy, 2);
        
        double r_joy = Math.sqrt(x_left_joy_sq + y_left_joy_sq);
        
        double speed = this.maxSpeed * r_joy;
        
        double alpha_1 = Math.PI / 4;
        double alpha_2 = 3 * Math.PI / 4;
        double alpha_3 = 5 * Math.PI / 4;
        double alpha_4 = 7 * Math.PI / 4;
        
        double theta_1 = alpha_1 - phi_joy;
        double theta_2 = alpha_2 - phi_joy;
        double theta_3 = alpha_3 - phi_joy;
        double theta_4 = alpha_4 - phi_joy;
        
        double w0_power = -speed * Math.sin(theta_1);
        double w1_power = -speed * Math.sin(theta_2);
        double w2_power = -speed * Math.sin(theta_3);
        double w3_power = -speed * Math.sin(theta_4);
        
        telemetry.addData("w0_power", w0_power);
        telemetry.addData("w1_power", w1_power);
        telemetry.addData("w2_power", w2_power);
        telemetry.addData("w3_power", w3_power);
        
        double[] speeds = {
            w0_power,
            w1_power,
            w2_power,
            w3_power
        };

        return speeds;
    }

    // GetTurnSpeed - Get the necessary speed of the motor to turn (based off of joysticks)
    public double GetTurnSpeed() {
        double x_right_joy = gamepad1.right_stick_x;
        double speed = Range.clip(x_right_joy, -1.0, 1.0) * this.maxSpeed;

        return -speed;
    }

    boolean deployMarkerPressed = false;
    // DeployMarker - The robot's marker deployment system
    void DeployMarker() {
        double currentPos = deploy_servo.getPosition();
        telemetry.addData("marker position", currentPos);
        if (gamepad1.x) {
            if (deployMarkerPressed) {
                if (currentPos >= 0) {
                    deploy_servo.setPosition(0);
                }
                deployMarkerPressed = false;
            } else if (currentPos == false) {
                if (currentPos <= 0) {
                    deploy_servo.setPosition(.5);
                }
                deployMarkerPressed = true;
            }
        }
    }

    boolean lockArmPressed = false;
    // LockArm - Lock/unlock the robot's arm lock
    void LockArm() {
        double currentPos = lock_arm.getPosition();
        telemetry.addData("arm lock servo position", currentPos);
        if (gamepad1.y) {
            if (lockArmPressed == true) {
                if (currentPos >.1) {
                    lock_arm.setPosition(0.0);
                }
                lockArmPressed = false;
            } else if (lockArmPressed == false) {
                telemetry.addData("bla", "blahhhhh");
                if (currentPos < .1) {
                    lock_arm.setPosition(.8);
                }
                lockArmPressed = true;
            }
            
        }
    }

    boolean clawOpen = false;
    // MoveClaw - Open and close the robot's claw
    void MoveClaw() {
        double currentPos = claw.getPosition();
        if (gamepad1.b && !clawOpen) {
            claw.setPosition(1);
            clawOpen = true;
        }
        if (gamepad1.a && clawOpen) {
            claw.setPosition(-1);
            clawOpen = false;
        }
    }

    // RotateLift - Rotate the robot's lift system
    void RotateLift() {
        // telemetry.addData("pressed", gamepad1.pressed(gamepad1.right_trigger));
        telemetry.addData("direct", gamepad1.right_trigger);
        
        double right = 1;
        double left  = -1;
        if (gamepad1.left_trigger > 0) {
            liftRotateBottom.setPower(right);
            liftRotateTop.setPower(right);
        } else {
            liftRotateBottom.setPower(0);
            liftRotateTop.setPower(0);
        }
        
        if (gamepad1.left_bumper) {
            liftRotateBottom.setPower(left);
            liftRotateTop.setPower(left);
            telemetry.addData("thing",liftRotateTop.getPower());
        } else {
            liftRotateBottom.setPower(0);
            liftRotateTop.setPower(0);
        }
    }

    // DriveLift - Extend the lift using the rack and pinion system
    void DriveLift() {
        // Range of CRservo is from -.93 to .88, the midpoint is -.025... NANI?!?!?!
        if (gamepad1.right_bumper) {
            lift_0.setPower(-.93);
            lift_1.setPower(-.93);
        }
        if (gamepad1.right_trigger > 0) {
            lift_0.setPower(.88);
            lift_1.setPower(.88);
        }
        else {
            lift_0.setPower(-0.025);
            lift_1.setPower(-0.025);
        }
    }

    /* END CONTROL METHODS */

    // Stop - Destroy the motors
    public void Stop() {
        w0 = null;
        w1 = null;
        w2 = null;
        w3 = null;
    }

}