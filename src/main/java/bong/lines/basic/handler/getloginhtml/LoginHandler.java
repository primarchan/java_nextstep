package bong.lines.basic.handler.getloginhtml;

import bong.lines.basic.handler.getindexhtml.IndexHTMLHandler;
import bong.lines.basic.util.HttpRequestUtils;
import dto.LoginUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;

public class LoginHandler  extends Thread{
    private static final Logger log = LoggerFactory.getLogger(IndexHTMLHandler.class);

    private Socket connection;

    public LoginHandler(Socket connection) {
        this.connection = connection;
    }

    @Override
    public void run() {
        log.debug("New Client Connect! Connected IP : {}, Port : {}, Host Address : {}", connection.getInetAddress(), connection.getPort(), connection.getInetAddress().getHostAddress());

        try(InputStream in = connection.getInputStream(); OutputStream out = connection.getOutputStream()) {
            // InputStream => InputStreamReader => BufferedReader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));

            // header에서 line 단위로 전송되는 데이터 받기
            String line = bufferedReader.readLine();
            log.info("line: {}", line);

            // header가 null인 경우 받지 않음
            if(line == null) {
                return;
            }

            String screenName = line.split(" ")[1]
                    .replace("/", "")
                    .replace(".do" ,"");

            String url = HttpRequestUtils.getUrl(line);
            if(url.startsWith("/user/create")) {
                int index = url.indexOf("?");
                String queryString = url.substring(index + 1);
                Map<String, String> params = HttpRequestUtils.parseQueryString(queryString);
                LoginUserDto loginUserDto = new LoginUserDto(params.get("name"), params.get("email"), params.get("userId"), params.get("password"));
                log.debug("LoginUserDto: {}", loginUserDto);
                // url = "/index.html";
            }

            DataOutputStream dos = new DataOutputStream(out);
            if(line != null && line.contains("GET") && line.contains("loginform.do")){
                byte[] body = Objects.requireNonNull(
                                IndexHTMLHandler.class
                                        .getResourceAsStream("/templates/user/" + screenName + ".html"))
                        .readAllBytes();
                response200Header(dos, body.length);
                responseBody(dos, body);
            }
        }catch (Exception exception){
            log.error(exception.getMessage());
            exception.printStackTrace();
        }
    }

    private void response200Header(DataOutputStream dos, int lengthOfBodyContent){
        try{
            dos.writeBytes("HTTP/1.1 200 OK \r\n");
            dos.writeBytes("Content-Type: text/html;charset=utf-8 \r\n");
            dos.writeBytes("Content-Length: " + lengthOfBodyContent + "\r\n");
            dos.writeBytes("\r\n");
        }catch (Exception exception){
            log.error(exception.getMessage());
        }
    }

    private void responseBody(DataOutputStream dos, byte[] body){
        try{
            dos.write(body, 0, body.length);
            dos.writeBytes("\r\n");
            dos.flush();
        }catch (Exception exception){
            exception.printStackTrace();
            log.error(exception.getMessage());
        }
    }
}

