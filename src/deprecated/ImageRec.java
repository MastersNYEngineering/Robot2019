package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuMarkInstanceId;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;

public class ImageRec extends LinearOpMode {
    public void find(VuforiaTrackable trackable) {
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(trackable);
        if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
            telemetry.addData("VuMark", "%s visible", vuMark);
            OpenGLMatrix pose = ((VuforiaTrackableDefaultListener)trackable.getListener()).getPose();
            telemetry.addData("Pose", format(pose));

            if (pose != null) {
                VectorF trans = pose.getTranslation();
                Orientation rot = Orientation.getOrientation(pose, AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

                double tX = trans.get(0);
                double tY = trans.get(1);
                double tZ = trans.get(2);
                double rX = rot.firstAngle;
                double rY = rot.secondAngle;
                double rZ = rot.thirdAngle;
            }
        } else {
            telemetry.addData("VuMark", "not visible");
        }
    }
    public static final String TAG = "Vuforia VuMark Sample";
    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;
    @Override public void runOpMode() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        parameters.vuforiaLicenseKey = "AXixIUn/////AAABmYHshsxzcElggJKrkRu+aHCLCX8w7cczcf7oyDqnI2UzoUX/NIf5lqhAuFFWROLZXsAuE9VdoIvigrcFqbVHaykkoDETaTjUDPS6CpRyWdhhDR0JtmoYdUCn74tqicXjqA36U140jboCWcJRFFO8QiDCXNQHIdG/cUm64+upa0epjdHdxjT+uAV6aO3q7ztdq8CbsPgQ9iIdZEU3ieG96EtDx0KmLT0lEeOGjhMFzjkK7OcJN+lbd/UvA+ED+36weZ8e66fvlOgRPUSgqY4eEALiQ4MlTix8k6L635zWk9zwftSPtQ6K4lNuGuWCrlI2E6iLTz+NFW6XAvL76i6GTsUvVSRIppHoEMjzcqftnNZ1";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
        VuforiaTrackables resources = this.vuforia.loadTrackablesFromAsset("RoverRuckus");
        
        VuforiaTrackable RedPerimeter = resources.get(0);
        RedPerimeter.setName("RedPerimeter");

        VuforiaTrackable BluePerimeter = resources.get(1);
        BluePerimeter.setName("BluePerimeter");

        VuforiaTrackable FrontPerimeter = resources.get(2);
        FrontPerimeter.setName("FrontPerimeter");

        VuforiaTrackable BackPerimeter = resources.get(3);
        BackPerimeter.setName("BackPerimeter");

        ArrayList<VuforiaTrackable> trackables = new ArrayList<VuforiaTrackable>();
        trackables.add(RedPerimeter);
        trackables.add(BluePerimeter);
        trackables.add(FrontPerimeter);
        trackables.add(BackPerimeter);
        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        resources.activate();
        while (opModeIsActive()) {
             for (VuforiaTrackable trackable : trackables) {
                 find(trackable);
            }
            telemetry.update();
        }
    }

    String format(OpenGLMatrix transformationMatrix) {
        return (transformationMatrix != null) ? transformationMatrix.formatAsTransform() : "null";
    }
}