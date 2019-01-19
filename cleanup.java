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
