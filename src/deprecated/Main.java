package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import java.lang.Math;

@TeleOp(name="Main: Drive Test", group="Iterative Opmode")
public class Main extends OpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor w0 = null;
    private DcMotor w1 = null;
    private DcMotor w2 = null;

    @Override
    public void init() {
        w0 = hardwareMap.get(DcMotor.class, "w0");
        w1 = hardwareMap.get(DcMotor.class, "w1");
        w2 = hardwareMap.get(DcMotor.class, "w2");

        w0.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        w1.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        w2.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);

        w0.setDirection(DcMotor.Direction.FORWARD);
        w1.setDirection(DcMotor.Direction.FORWARD);
        w2.setDirection(DcMotor.Direction.REVERSE);
        
        telemetry.addData("Init", "Motors");
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
        double w0_power;
        double w1_power;
        double w2_power;

        double drive = -gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        w0_power = Range.clip(drive + turn, -1.0, 1.0);
        w1_power = Range.clip(drive + turn, -1.0, 1.0);
        w2_power = Range.clip(drive - turn, -1.0, 1.0);

        w0.setPower(w0_power);
        w1.setPower(w1_power);
        w2.setPower(w2_power);

        telemetry.addData("sin(Pi/2)", Math.sin(Math.PI / 2));
        telemetry.addData("Run Time", runtime.toString());
    }

    @Override
    public void stop() {

    }
}