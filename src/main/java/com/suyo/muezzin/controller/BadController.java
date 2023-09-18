package com.suyo.muezzin.controller;

import com.suyo.muezzin.model.Response;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@Hidden
public class BadController implements ErrorController {

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH}, produces = MediaType.APPLICATION_JSON_VALUE)
    public HttpEntity<Response> handleErrorJson(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = (int) status;
            return ResponseEntity.status(statusCode).body(new Response(List.of(), HttpStatus.valueOf(statusCode).getReasonPhrase()));
        }
        return ResponseEntity.ok(new Response(List.of(), null));
    }

    @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH}, produces = MediaType.TEXT_HTML_VALUE)
    public String handleErrorHTML(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Integer statusCode = Objects.requireNonNullElse((Integer) status, 500);
        String view = "<!DOCTYPE html> <html lang=\"en\"> <head> <meta charset=\"UTF-8\"> <title>error_code</title> <style> * { margin:0px auto; padding: 0px; text-align:center; } body { background-color: #D4D9ED; } .cont_principal { position: absolute; width: 100%; height: 100%; overflow: hidden; } .cont_error { position: absolute; width: 100%; height: 300px; top: 50%; margin-top:-150px; } .cont_error > h1  { font-family: 'Lato', sans-serif; font-weight: 400; font-size:150px; color:#fff; position: relative; left:-100%; transition: all 0.5s; } .cont_error > p  { font-family: 'Lato', sans-serif; font-weight: 300; font-size:24px; letter-spacing: 5px; color:#9294AE; position: relative; left:100%; transition: all 0.5s; transition-delay: 0.5s; -webkit-transition: all 0.5s; -webkit-transition-delay: 0.5s; } .cont_aura_1 { position:absolute; width:300px; height: 120%; top:25px; right: -340px; background-color: #8A65DF; box-shadow: 0px 0px  60px  20px  rgba(137,100,222,0.5); -webkit-transition: all 0.5s; transition: all 0.5s; } .cont_aura_2 { position:absolute; width:100%; height: 300px; right:-10%; bottom:-301px; background-color: #8B65E4; box-shadow: 0px 0px 60px 10px rgba(131, 95, 214, 0.5),0px 0px  20px  0px  rgba(0,0,0,0.1); z-index:5; transition: all 0.5s; -webkit-transition: all 0.5s; } .cont_error_active > .cont_error > h1 { left:0%; } .cont_error_active > .cont_error > p { left:0%; } .cont_error_active > .cont_aura_2 { animation-name: animation_error_2; animation-duration: 4s; animation-timing-function: linear; animation-iteration-count: infinite; animation-direction: alternate; transform: rotate(-20deg); } .cont_error_active > .cont_aura_1 { transform: rotate(20deg); right:-170px; animation-name: animation_error_1; animation-duration: 4s; animation-timing-function: linear; animation-iteration-count: infinite; animation-direction: alternate; } @-webkit-keyframes animation_error_1 { from { -webkit-transform: rotate(20deg); transform: rotate(20deg); } to {  -webkit-transform: rotate(25deg); transform: rotate(25deg); } } @-o-keyframes animation_error_1 { from { -webkit-transform: rotate(20deg); transform: rotate(20deg); } to {  -webkit-transform: rotate(25deg); transform: rotate(25deg); } } @-moz-keyframes animation_error_1 { from { -webkit-transform: rotate(20deg); transform: rotate(20deg); } to {  -webkit-transform: rotate(25deg); transform: rotate(25deg); } } @keyframes animation_error_1 { from { -webkit-transform: rotate(20deg); transform: rotate(20deg); } to {  -webkit-transform: rotate(25deg); transform: rotate(25deg); } } @-webkit-keyframes animation_error_2 { from { -webkit-transform: rotate(-15deg); transform: rotate(-15deg); } to { -webkit-transform: rotate(-20deg); transform: rotate(-20deg); } } @-o-keyframes animation_error_2 { from { -webkit-transform: rotate(-15deg); transform: rotate(-15deg); } to { -webkit-transform: rotate(-20deg); transform: rotate(-20deg); } } } @-moz-keyframes animation_error_2 { from { -webkit-transform: rotate(-15deg); transform: rotate(-15deg); } to { -webkit-transform: rotate(-20deg); transform: rotate(-20deg); } } @keyframes animation_error_2 { from { -webkit-transform: rotate(-15deg); transform: rotate(-15deg); } to { -webkit-transform: rotate(-20deg); transform: rotate(-20deg); } } </style> </head> <body> <div class=\"cont_principal\"> <div class=\"cont_error\"> <h1>error_code</h1> <p>error_message</p> </div> <div class=\"cont_aura_1\"></div> <div class=\"cont_aura_2\"></div> </div> <script> window.onload = function(){ document.querySelector('.cont_principal').className= \"cont_principal cont_error_active\"; } </script> </body> </html>\n";
        return view.replace("error_code", "error " + statusCode).replace("error_message", HttpStatus.valueOf(statusCode).getReasonPhrase());
    }
}
