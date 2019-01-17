package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Test: Motor Encoders", group="Iterative Opmode")

public class EncodersTest extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor left_wheel = null;
    private DcMotor right_wheel = null;

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");

        left_wheel  = hardwareMap.get(DcMotor.class, "test_motor_left");
        right_wheel = hardwareMap.get(DcMotor.class, "test_motor_right");

        left_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        right_wheel.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        left_wheel.setDirection(DcMotor.Direction.FORWARD);
        right_wheel.setDirection(DcMotor.Direction.REVERSE);

        telemetry.addData("Status", "Initialized");
    }

    public void motor_goto(DcMotor m_motor) {
        m_motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m_motor.setTargetPosition(1);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
    }

    @Override
    public void loop() {
        
        left_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        left_wheel.setTargetPosition(1);
        right_wheel.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        right_wheel.setTargetPosition(1);

        telemetry.addData("Status", "Run Time: " + runtime.toString());
        // telemetry.addData("Motors", "left (%.2f), right (%.2f)", left_power, right_power);
    }

    @Override
    public void stop() {

    }
}
