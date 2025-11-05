package com.isaiascosta.usuario.business;

import com.isaiascosta.usuario.infrastructure.clients.ViaCepClient;
import com.isaiascosta.usuario.infrastructure.clients.ViaCepDTO;
import com.isaiascosta.usuario.infrastructure.exceptions.IllegalArgumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ViaCepService {

   private final ViaCepClient client;

   public ViaCepDTO buscarEndereco(String cep) {

      return client.buscarEndereco(processarCep(cep));
   }

   private String processarCep(String cep) {

         String cepFormatado = cep.replaceAll("[^\\d]", "").trim();
         if (!cepFormatado.matches("\\d+") || !Objects.equals(cepFormatado.length(), 8)) {

         throw new IllegalArgumentException("CEP contém caracteres invalidos verifique o cep ");
         }
         return cepFormatado;
   }
}
