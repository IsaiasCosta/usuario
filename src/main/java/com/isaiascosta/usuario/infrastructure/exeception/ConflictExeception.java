package com.isaiascosta.usuario.infrastructure.exeception;

public class ConflictExeception extends RuntimeException {
   public ConflictExeception(String message) {
      super(message);
   }
   public  ConflictExeception (String message, Throwable throwable){
     super(message);
   }
}
