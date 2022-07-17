package com.mikael.api.message;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import com.mikael.api.dao.UserAdapter;
import com.mikael.api.user.User;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.val;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
@AllArgsConstructor
public class Message {
    private final Socket socket;
    private List<String> listOfMessages = null;
    @SneakyThrows
    public void readMessage(){
        System.out.println("Mensagem recebida com sucesso!!!");
        val  inputStream = socket.getInputStream();
        val objectInputStream = new ObjectInputStream(inputStream);
        listOfMessages = (List<String>) objectInputStream.readObject();
    }

    @SneakyThrows
    public void dataValidation(SQLExecutor executor) {

    listOfMessages.stream()
            .map(data -> {
                try {
                    val jsonObject = (JSONObject) new JSONParser().parse(data);
                    return new User((String)jsonObject.get("email"), (String)jsonObject.get("password"));
                } catch (ParseException e) {
                    System.out.println("Não foi possível converter os dados do cliente." + e);
                }
                return null;
            }).forEach(user -> {
                val cachedUser = executor.resultOneQuery("SELECT * FROM user WHERE email = ? AND password = ?", s -> {
                    s.set(1, user.getEmail());
                    s.set(2, user.getPassword());
                }, UserAdapter.class);

                   try {
                       val dataOutputStream = new DataOutputStream(socket.getOutputStream());

                       if (cachedUser == null) {
                           System.out.println("Login inválido");
                            dataOutputStream.writeUTF("Login Inválido");
                            dataOutputStream.writeUTF("Tenta Novamente");
                       } else {
                           System.out.println("Login Válido");
                           dataOutputStream.writeUTF("Login Válido");
                           dataOutputStream.writeUTF("Login Válido");
                       }
                   }catch (Exception e){
                      e.printStackTrace();
                   }
            });
    }

}
