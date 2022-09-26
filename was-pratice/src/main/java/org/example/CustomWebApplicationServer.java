package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CustomWebApplicationServer {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomWebApplicationServer.class);
    private final int port;

    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public CustomWebApplicationServer(int port) {
        this.port = port;
    }

    public void start(){
        try(ServerSocket serverSocket = new ServerSocket(port)){
            LOGGER.info("[CustomWebApplicationServer] started {} port", port);
            Socket clientSocket;
            LOGGER.info("[CustomWebApplicationServer] waiting for client");
            while ((clientSocket = serverSocket.accept())!=null){
                LOGGER.info("[CustomWebApplicationServer] client connected!");

                /**
                 * Step2 - 사용자 요청이 들어올 때마다 Tread를 새로 생성해서 사용자 요청을 처리
                 */
//                new Thread(new ClientRequestHandler(clientSocket)).start();
                /**
                 * Step3 - Thread Pool을 적용해 안정적인 서비스가 가능하도록 한다.
                 */
                executorService.execute(new ClientRequestHandler(clientSocket));


            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
