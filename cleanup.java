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
    double left = -1;
    if (gamepad1.left_trigger>0) {
        lift_rotate.setPower(right);
        lift_rotate_top.setPower(right);
    } else {
        lift_rotate.setPower(0);
        lift_rotate_top.setPower(0);
    }
    
    if (gamepad1.left_bumper) {
        lift_rotate.setPower(left);
        lift_rotate_top.setPower(left);
        telemetry.addData("thing",lift_rotate_top.getPower());
    } else {
        lift_rotate.setPower(0);
        lift_rotate_top.setPower(0);
    }
}

           

void drive_lift() {
    // Range of CRservo is from -.93 to .88, the midpoint is -.025... NANI?!?!?!
    if (gamepad1.right_bumper) {
        telemetry.addData("right bumper", "pressed");
        lift_0.setPower(-.93);
        lift_1.setPower(-.93);
        // s_lift_0.setPosition(1);
        // s_lift_1.setPosition(1);
    }
    if (gamepad1.right_trigger>0) {
        telemetry.addData("left bumper", "pressed");
        lift_0.setPower(.88);
        lift_1.setPower(.88);
        // s_lift_0.setPosition(0);
        // s_lift_1.setPosition(0);
    }
    else{
        lift_0.setPower(-0.025);
        lift_1.setPower(-0.025);
    }
    }
