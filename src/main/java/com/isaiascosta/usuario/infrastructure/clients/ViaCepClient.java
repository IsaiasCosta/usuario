package com.isaiascosta.usuario.infrastructure.clients;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "via-cep", url = "${via-cep.url}") // Define um cliente Feign para comunicar com o microsserviço de usuário
public interface ViaCepClient {


   @GetMapping("/ws/{cep}/json/") // Faz uma requisição GET para o endpoint /usuario do serviço remoto
   ViaCepDTO buscarEndereco(@PathVariable("cep") String cep);
}
