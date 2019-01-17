package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Test: Colors Sensors", group="Iterative Opmode")

public class ColorSensorTest extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor motor = null;
    
    private ColorSensor color_sensor;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotor.class, "test_motor_left");
        motor.setDirection(DcMotor.Direction.FORWARD);

        color_sensor = hardwareMap.colorSensor.get("color_sensor");
        color_sensor.enableLed(false);
        
        telemetry.addData("Status", "Initialized");
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
        
        telemetry.addData("Color Sensor", "Red: " + color_sensor.red());
        telemetry.addData("Color Sensor", "Green: " + color_sensor.green());
        telemetry.addData("Color Sensor", "Blue: " + color_sensor.blue());
        telemetry.addData("Color Sensor", "Alpha: " + color_sensor.alpha());
        telemetry.addData("Color Sensor", "ARGB: " + color_sensor.argb());

        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }

    @Override
    public void stop() {
    }

}
