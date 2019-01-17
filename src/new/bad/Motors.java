class Motors {
    // Declare the hardware map
    HardwareMap hardwareMap;

    Motors(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }

    private DcMotor InitDcMotor(String id) {
        DcMotor m = null;
        m = hardwareMap.get(DcMotor.class, id);
        m.setMode(DcMotor.RunMode.RUN_USING_ENCODERS);
        m.setDirection(DcMotor.Direction.REVERSE);
        return m;
    }

    private Servo InitServo(String id) {
        Servo s = null;
        s = hardwareMap.get(Servo.class, id);
        s.setDirection(Servo.Direction.FORWARD);
        return s;
    }
    
    private CRServo InitCRServo(String id) {
        CRServo s = null;
        s = hardwareMap.get(CRServo.class, id);
        return s;
    }
}