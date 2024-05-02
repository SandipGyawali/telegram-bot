package com.example.bot.controller;

import com.example.bot.dto.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RestController
@Getter
@Setter
@AllArgsConstructor
@Data
@RequestMapping("/messages")
public class Base {
  @Value("${telegram.apiUrl}")
  private String apiUrl;
  private final RestTemplate restTemplate;

  public Base(){
    this.restTemplate = new RestTemplate();
  }

  @GetMapping("/all")
  public Object getAllMessages(){
    try{
      Object res = restTemplate.getForObject(apiUrl + "/getUpdates?limit=2", Object.class);
      System.out.println(res);

      return new BaseApiResponse(res);
    }catch(Exception err){
      System.out.println("error");
        return new BaseApiErrorResponse(err.getMessage());
    }
  }

  @GetMapping("/unread")
  public Object getUnreadMessages(){
    try{
      Object res = restTemplate.getForObject(apiUrl, Object.class);

      System.out.println(res);
      return new BaseApiResponse(res);
    }catch(Exception err){
      System.out.println(err.getMessage());
      return new BaseApiErrorResponse(err.getMessage());
    }
  }

  @GetMapping("/latest")
  public Object getLatestMessage(){
    try{
      Object res = restTemplate.getForObject(apiUrl + "/getupdates?offset=-1&limit=1", Object.class);
      System.out.println(res);
      return new BaseApiResponse(res);
    }catch(Exception err){
      System.out.println(err.getMessage());
      return new BaseApiErrorResponse(err.getMessage());
    }
  }

  @NoArgsConstructor
  @Data
  @Getter
  @Setter
  static class InsertMessage {
    String chat_id;
    String text;

    public InsertMessage(String chat_id, String text){
      this.chat_id = chat_id;
      this.text = text;
    }
  }

  @PostMapping("/insert")
  public Object insertMessage(@RequestBody InsertMessage messageObject){
    try{
      System.out.println(messageObject);
//      InsertMessage obj = new InsertMessage("-4215157941", "my reply as a bot");
      Object res = restTemplate.postForObject(apiUrl + "/sendMessage", messageObject, Object.class);
      System.out.println(res);
      return new BaseApiResponse("");
    }catch (Exception err){
      System.out.println(err);
      return new BaseApiErrorResponse(err.getMessage());
    }
  }
}
