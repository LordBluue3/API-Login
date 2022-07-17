package com.mikael.api;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.mikael.api.dao.SQLProvider;
import com.mikael.api.message.Message;

import lombok.SneakyThrows;
import lombok.val;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Login {

    @SneakyThrows
    public static void main(String []args) {
        val serverSocket = new ServerSocket(5000);
        val executor = new SQLExecutor(SQLProvider.connect());
        SQLProvider.createTable(executor);

        val listOfMessage = new ArrayList<String>();
        Socket socket;


        System.out.println("API INICIADA");
        while(true) {
            System.out.println("Aguardando Requisição");
            socket = serverSocket.accept();
            val message = new Message(socket, listOfMessage);
            System.out.println("Conectado a Aplicação: ");
            message.readMessage();
            message.dataValidation(executor);
        }
    }
}
