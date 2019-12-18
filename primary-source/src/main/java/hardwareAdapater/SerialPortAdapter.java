package hardwareAdapater;

import gnu.io.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.TooManyListenersException;

public class SerialPortAdapter {
    private  ArrayList<String> portNameList=new ArrayList<String>();
    private  byte[] data=null; // data from port
    private  SerialPort serialPort;

    /**
     * get all ports names
     * @return all ports names
     */
    public ArrayList<String> getPorts() {
        //get all ports
        Enumeration<CommPortIdentifier> portList = CommPortIdentifier.getPortIdentifiers();

        while (portList.hasMoreElements()) {
            String portName = portList.nextElement().getName();
            portNameList.add(portName);
        }

        return portNameList;
    }

    /**
     * open serial port
     * @param portName
     * @param BaudRate
     * @param DataBits
     * @param StopBits
     * @param Parity
     * @return
     */
    public SerialPort openPort(String portName, int BaudRate, int DataBits, int StopBits, int Parity) {
        try {
            //identify the port through port name
            CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
            //open port
            CommPort commPort = portIdentifier.open(portName, 2000);
            //Confirm serial port
            if (commPort instanceof SerialPort) {
                serialPort = (SerialPort) commPort;
                try {
                    //set parameters
                    serialPort.setSerialPortParams(BaudRate, DataBits, StopBits, Parity);
                } catch (UnsupportedCommOperationException e) {
                    e.printStackTrace();
                }
                System.out.println("Open " + portName + " sucessfully !");

                return serialPort;

            } else {
                System.out.println("This port is not a serial port");

                return null;
            }

        } catch (NoSuchPortException e) {

            return null;

        } catch (PortInUseException e) {

            return null;
        }
    }

    /**
     * send data to port
     * @param serialPort
     * @param order
     */
    public void sendToPort(SerialPort serialPort, byte[] order) {
        try {
            OutputStream out = serialPort.getOutputStream();
            out.write(order);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * receive data from port
     * @param serialPort
     * @return
     */
    public byte[] readFromPort(SerialPort serialPort) {
        try {
            InputStream is=serialPort.getInputStream();
            while (true) {
                //get the lenght of buffer
                int bufflenth = is.available();
                if (0 == bufflenth) {
                    break;
                }
                data = new byte[bufflenth];
                is.read(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return data;

    }

    /**
     * add listener for serial port
     * @param serialport
     * @param listener
     */
    public void addListener(SerialPort serialport,SerialPortEventListener listener) {
        try {
            serialport.addEventListener(listener);
            serialport.notifyOnDataAvailable(true);
            serialport.notifyOnBreakInterrupt(true);
            System.out.println("Add listeners to " + serialport.getName() + " sucessfully !");
        } catch (TooManyListenersException e) {
            e.printStackTrace();
        }
    }

    public void closePort(SerialPort serialPort){
        serialPort.close();
    }


}
