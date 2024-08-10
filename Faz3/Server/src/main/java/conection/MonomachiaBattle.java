package conection;

import database.EntityData;
import model.conection.User;

public class MonomachiaBattle extends Thread {
    private User user1, user2;
    private EntityData data1, data2;
    private SendData sendData1, sendData2;
    private ReceiveData receiveData1, receiveData2;

    public MonomachiaBattle(User user1, User user2) {
        this.user1 = user1;
        this.user2 = user2;
        data1 = new EntityData();
        data2 = new EntityData();
        sendData1 = new SendData(data2, 5001);
        receiveData1 = new ReceiveData(data1, 5002);
        sendData2 = new SendData(data1, 6001);
        receiveData2 = new ReceiveData(data2, 6002);

        sendData1.start();
        receiveData1.start();
        sendData2.start();
        receiveData2.start();
        System.out.println("start");
    }

    @Override
    public void run() {
        super.run();

    }
}