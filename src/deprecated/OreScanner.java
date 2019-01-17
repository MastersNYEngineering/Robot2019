package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name="Main: Ore Scanner", group="Iterative Opmode")
public class OreScanner extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();
    
    private ColorSensor color_sensor;
    private Telemetry telemetry;
    
    private void print_colors() {
        telemetry.addData("Color Sensor", "Red: " + color_sensor.red());
        telemetry.addData("Color Sensor", "Green: " + color_sensor.green());
        telemetry.addData("Color Sensor", "Blue: " + color_sensor.blue());
    }

    private boolean scan_color(RGB rgb, float lower_red, float upper_red, float lower_green, float upper_green, float lower_blue, float upper_blue) {
        if (rgb.red <= upper_red && rgb.red >= lower_red) {
            // telemetry.addData("\nSCANNED", "RED\n");
            if (rgb.green <= upper_green && rgb.green >= lower_green) {
                // telemetry.addData("\nSCANNED", "GREEN\n");
                if (rgb.blue <= upper_blue && rgb.blue >= lower_blue) {
                    // telemetry.addData("\nSCANNED", "BLUE\n");
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public void init() {

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
        RGB current_rgb = new RGB(
            color_sensor.red(),
            color_sensor.green(),
            color_sensor.blue()
        );

        float alpha = color_sensor.alpha();
        String argb = Float.toString(color_sensor.argb());
        
        // ----- GOLD -----

        float lower_red = 400;
        float upper_red = 800;
        
        float lower_green = 300;
        float upper_green = 500;
        
        float lower_blue = 100;
        float upper_blue = 300;

        boolean is_gold = scan_color(
            current_rgb,
            lower_red,
            upper_red,
            lower_green,
            upper_green,
            lower_blue,
            upper_blue
        );

        if (is_gold) {
           telemetry.addData("\nGold Detected\n");
        }

        // ----- SILVER -----

        float _lower_red = 400;
        float _upper_red = 800;
        
        float _lower_green = 300;
        float _upper_green = 500;
        
        float _lower_blue = 100;
        float _upper_blue = 300;

        boolean is_silver = scan_color(
            _current_rgb,
            _lower_red,
            _upper_red,
            _lower_green,
            _upper_green,
            _lower_blue,
            _upper_blue
        );

        if (is_silver) {
            telemetry.addData("\nSilver Detected\n");
        }

        // ----- END COLORS ----- 

        print_colors();
        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }

    @Override
    public void stop() {
    }

}