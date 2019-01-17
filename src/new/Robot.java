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
    private DcMotor w0 = null;
    private DcMotor w1 = null;
    private DcMotor w2 = null;
    private DcMotor w3 = null;

    // Declare motors for the lift
    private DcMotor liftRotateBottom = null;
    private DcMotor liftRotateTop    = null;
    private CRServo liftServoRight   = null;
    private CRServo liftServoLeft    = null;
    
    // Declare some other stuff
    private double      maxSpeed    = 0.3;
    private HardwareMap hardwareMap = null;

    Robot(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
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
        s = hardwareMap.get(CRServo.class, id);
        return s;
    }

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

    // Stop - Destroy the motors
    public void Stop() {
        w0 = null;
        w1 = null;
        w2 = null;
        w3 = null;
    }

}