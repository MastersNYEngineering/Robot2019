package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;

@TeleOp(name="Main: XDrive Flash", group="Iterative Opmode")
public class XDriveFlash extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor w0 = null;
    private DcMotor w1 = null;
    private DcMotor w2 = null;
    private DcMotor w3 = null;
    private double max_speed;

    @Override
    public void init() {
        max_speed = 1;
        // max_speed = 0.125;

        w0 = hardwareMap.get(DcMotor.class, "w0");
        w1 = hardwareMap.get(DcMotor.class, "w1");
        w2 = hardwareMap.get(DcMotor.class, "w2");
        w3 = hardwareMap.get(DcMotor.class, "w3");

        w0.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        w1.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        w2.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        w3.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);

        w0.setDirection(DcMotor.Direction.FORWARD);
        w1.setDirection(DcMotor.Direction.FORWARD);
        w2.setDirection(DcMotor.Direction.FORWARD);
        w3.setDirection(DcMotor.Direction.FORWARD);
        
        telemetry.addData("Init", "Motors");
    }

    void move() {
        double x_left_joy = gamepad1.left_stick_x;
        double y_left_joy = gamepad1.left_stick_y;
        
        double phi_joy = Math.atan2(y_left_joy, x_left_joy);
        
        double x_left_joy_sq = Math.pow(x_left_joy, 2);
        double y_left_joy_sq = Math.pow(y_left_joy, 2);
        
        double r_joy = Math.sqrt(x_left_joy_sq + y_left_joy_sq);
        
        double speed = max_speed * r_joy;
        
        double alpha_1 = Math.PI / 4;
        double alpha_2 = 3 * Math.PI / 4;
        double alpha_3 = 5 * Math.PI / 4;
        double alpha_4 = 7 * Math.PI / 4;
        
        double theta_1 = alpha_1 - phi_joy;
        double theta_2 = alpha_2 - phi_joy;
        double theta_3 = alpha_3 - phi_joy;
        double theta_4 = alpha_4 - phi_joy;
        
        double w0_power = speed * Math.sin(theta_1);
        double w1_power = speed * Math.sin(theta_2);
        double w2_power = speed * Math.sin(theta_3);
        double w3_power = speed * Math.sin(theta_4);
        
        telemetry.addData("w0_power", w0_power);
        telemetry.addData("w1_power", w1_power);
        telemetry.addData("w2_power", w2_power);
        telemetry.addData("w3_power", w3_power);
        
        w0.setPower(-w0_power);
        w1.setPower(-w1_power);
        w2.setPower(-w2_power);
        w3.setPower(-w3_power);
    }

    // void turn() {
    //     double x_right_joy = gamepad1.right_stick_x;
    //     // double y_right_joy = gamepad1.right_stick_y;
        
    //     double speed = Range.clip(x_right_joy, -1.0, 1.0) * max_speed;

    //     w0.setPower(speed);
    //     w1.setPower(speed);
    //     w2.setPower(speed);
    // }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        move();
        // turn();

        telemetry.addData("Run Time", runtime.toString());
    }

    @Override
    public void stop() {
        w0 = null;
        w1 = null;
        w2 = null;
        w3 = null;
    }
}