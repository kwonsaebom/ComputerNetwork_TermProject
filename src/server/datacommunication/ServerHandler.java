package server.datacommunication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ServerHandler {

  ExecutorService executorService; // ������Ǯ�� ExecutorService ����

  ServerSocket serverSocket; // Ŭ���̾�Ʈ ���� ����

  List<Client> connections = new Vector<Client>(); // ����Ǿ��ִ� Ŭ���̾�Ʈ��

  public void startServer() {

    ExecutorService threadPool = new ThreadPoolExecutor(10, // �ھ� ������ ����
        100, // �ִ� ������ ����
        120L, // ��� �ִ� �ð�
        TimeUnit.SECONDS, // ��� �ִ� �ð� ����
        new SynchronousQueue<Runnable>() // �۾� ť
    ); // �ʱ� ������ ���� 0��,
    executorService = threadPool;
    try {
      serverSocket = new ServerSocket();
      serverSocket.bind(new InetSocketAddress(5000));
      System.out.println("���� ���� ��ٸ�");
      // -> serverSocket ���� �� ��Ʈ ���ε�
    } catch (Exception e) {
      e.printStackTrace();
    }
    // ������ �����ϴ� �ڵ�
    Runnable runnable = new Runnable() { // ���� �۾� ����

      @Override
      public void run() {

        while (true) {
          try {
            Socket socket = serverSocket.accept(); // Ŭ���̾�Ʈ ���� ����, client�� ����� socket ��
            System.out.println("���� ����: " + socket.getRemoteSocketAddress() + ": "
                + Thread.currentThread().getName());
            Client client = new Client(socket); // Ŭ���̾�Ʈ ��ü�� ����.
            connections.add(client);
            System.out.println("���� ����: " + connections.size());
          } catch (IOException e) {
            if (!serverSocket.isClosed()) { // serverSocket�� �������� ���� ���
              stopServer();
            }
            break;
          }
        }
      }
    };
    executorService.submit(runnable); // ������Ǯ���� ó��.
  }

  public void stopServer() {

    try {
      Iterator<Client> iterator = connections.iterator(); // ��� socket �ݱ�.
      while (iterator.hasNext()) {
        Client client = iterator.next();
        client.socket.close();
        iterator.remove();
      }
      if (serverSocket != null && !serverSocket.isClosed()) { // ServerSocket �ݱ�.
        serverSocket.close();
      }
      if (executorService != null && !executorService.isShutdown()) { // ExecutorService ����.
        executorService.shutdown();
      }
    } catch (Exception e) {
    }
  }

  class Client {

    Socket socket;

    String userName;

    Client(Socket socket) {

      this.socket = socket;
      receive();
    }

    // Ŭ���̾�Ʈ�� �����͸� �޴� �޼ҵ�
    void receive() {

      Runnable runnable = new Runnable() {

        @Override
        public void run() {

          byte[] byteArr = new byte[1024];
          try {
            while (true) {
              InputStream inputStream = socket.getInputStream(); // �Է� ��Ʈ�� ���.
              int readByteCount = inputStream.read(byteArr); // ������ �ޱ�, �迭�� ����, ����Ʈ �� ����.
              // �� �̻� �Է� ��Ʈ�����κ��� ����Ʈ ���� �� ���ٸ� -1 ����.
              if (readByteCount == -1) {
                throw new IOException();
              }
              Message ms = toObject(byteArr, Message.class); // ������
              System.out.println("��ûó��: " + socket.getRemoteSocketAddress() + ": "
                  + Thread.currentThread().getName());
              userName = ms.getSendUserName();
              System.out.println(userName + "qq");
              send(byteArr); // �������� ��
              for (Client client : connections) { // ��� Ŭ���̾�Ʈ���� ����
                System.out.println(client.userName + "ss" + ms.getReceiveFriendName());
                if (client.userName != null) {
                  if (client.userName.equals(ms.getReceiveFriendName())
                      && !ms.getSendUserName().equals(ms.getReceiveFriendName())) {
                    client.send(byteArr);
                  }
                }
              }
            }
          } catch (Exception e) {
            try {
              connections.remove(Client.this);
              socket.close();
            } catch (IOException e2) {
            }
          }
        }
      };
      executorService.submit(runnable);
    }

    private Message toObject(byte[] byteArr, Class<Message> class1) {

      Object obj = null;
      try {
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArr);
        ObjectInputStream ois = new ObjectInputStream(bis);
        obj = ois.readObject();
      } catch (Exception e) {
      }
      return class1.cast(obj);
    }

    // �����͸� ������ �޼ҵ�
    void send(byte[] bytes) {

      Runnable runnable = new Runnable() {

        @Override
        public void run() {

          try {
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
            System.out.println("�������� ������ ����");
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      };
      executorService.submit(runnable);
    }
  }
}
