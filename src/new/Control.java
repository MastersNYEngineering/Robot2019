/*
    # # # # # # # # # # # # # # # # # # 
    # Masters School Robotics         #
    # Written by Matthew Nappo        #
    #            Zach Battleman       #
    # GitHub: @xoreo, @Zanolon        #
    #                                 #
    # Class Control                   #
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

@TeleOp(name="Teleop: Square Drive", group="Iterative Opmode")
public class Control extends OpMode {

    // Declare the Robot class
    private Robot robot;

    // Declare some stuff
    private ElapsedTime runtime = new ElapsedTime();
    double max_speed = 0.3;
    
    @Override
    public void init() {
        robot = new Robot(hardwareMap);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
        runtime.reset();
        init();
    }
    
    @Override
    public void loop() {
        Drive();
        deploy_marker();
        rotate_lift();
        move_claw();
        lock_arm_func();
        drive_lift();
        telemetry.addData("Run Time", runtime.toString());
    }
    
    @Override
    public void stop() {
        robot.stop()
    }
}



// double[] move = move();
// double turn = turn();
// w0.setPower(move[0] + turn);
// w1.setPower(move[1] + turn);
// w2.setPower(move[2] + turn);
// w3.setPower(move[3] + turn);
