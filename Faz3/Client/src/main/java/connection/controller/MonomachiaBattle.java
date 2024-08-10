package connection.controller;

public class MonomachiaBattle extends Thread {
    private SendData sendData;
    private ReceiveData receiveData;

    public MonomachiaBattle() {
        sendData = new SendData(5002);
        receiveData = new ReceiveData(5001);
        sendData.start();
        receiveData.start();
    }

    @Override
    public void run() {
        super.run();

    }
}