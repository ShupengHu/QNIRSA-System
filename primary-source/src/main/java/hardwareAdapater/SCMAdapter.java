package hardwareAdapater;

import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 * control single chip micyoco
 */
public class SCMAdapter {
    private SerialPortAdapter serialPortAdapter=new SerialPortAdapter();
    private SerialPort serialPort;
    private String portName = "COM4";
    private int BaudRate = 9600;
    private int DataBits = SerialPort.DATABITS_8;
    private int StopBits = SerialPort.STOPBITS_1;
    private int Parity = SerialPort.PARITY_NONE;
    private byte[] data = null;   //data from SCM
    private boolean isSCMReset=false;
    public static byte[] stop = {0x01, 0x0f, 0x00, 0x00, 0x00, 0x08, 0x01, 0x10, (byte) 0xff, 0x65};
    public static byte[] forward = {0x01, 0x0f, 0x00, 0x00, 0x00, 0x08, 0x01, 0x11, (byte) 0xff, 0x65};
    public static byte[] backward = {0x01, 0x0f, 0x00, 0x00, 0x00, 0x08, 0x01, 0x01, (byte) 0xff, 0x65};
    public static byte[] positionCheck = {0x01, 0x03, 0x00, 0x00, 0x00, 0x02, (byte) 0xff, 0x65};   // order for SCM position reset

    /**
     * open port to connect SCM
     */
    public void  connectSCM(){
        serialPort=serialPortAdapter.openPort(portName,BaudRate,DataBits,StopBits,Parity);
    }
    /**
     * move SCM according to order
     * @param order order for SCM
     */
    public void moveSCM(byte[] order) {
        SerialPortAdapter spa = new SerialPortAdapter();
        spa.sendToPort(serialPort, order);
    }

    /**
     * reset position of SCM
     */
    public void resetSCM() throws InterruptedException {
        final SerialPortAdapter spa = new SerialPortAdapter();
        //send order of position check twice，receive return data of the second one
        spa.sendToPort(serialPort, positionCheck); // first time
        Thread.sleep(1000);
        spa.sendToPort(serialPort, positionCheck); //second time
        spa.addListener(serialPort, new SerialPortEventListener() {
            public void serialEvent(SerialPortEvent serialPortEvent) {
                if (serialPortEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                    data = spa.readFromPort(serialPort);
                }
            }
        });
        //Listener is asynchronous request. Wait if return data is null
        while (data == null) {
            Thread.sleep(100);
        }
        /**
         * First time return data is 010304xxxxxxxxFA33.
         * If float nubmers between the 4th and 7th byte are all 0, SCM is at original position
         * Otherwise, move SCM backward and sent position check order again.
         * This time if return data is 010304xxxxxxxxxx, SCM is at original position.
         * Otherwise, wait until receive 010304xxxxxxxxxx
		 */
        if(this.data[2]!=0&&this.data[3]==0&&this.data[4]==0&&this.data[5]==0&&this.data[6]==0){
            isSCMReset=true;
            spa.sendToPort(serialPort,stop);
            System.out.println("------------SCM is at the original position---------------------");
        }else{
            do{
                data=null;
                spa.sendToPort(serialPort,backward);
                System.out.println("------------SCM is backward---------------------");
                Thread.sleep(1000);
                data=spa.readFromPort(serialPort);
                if(this.data!=null&&this.data[0]==1&&this.data[1]==3&&this.data[2]==4){
                    isSCMReset=true;
                    System.out.println("------------SCM is at the original position---------------------");
                }
            }while(isSCMReset==false);
            spa.sendToPort(serialPort,stop);
        }
    }

    /**
     * set port name
     * @param portName
     */
    public void setPortName(String portName) {
        this.portName = portName;
    }
}
