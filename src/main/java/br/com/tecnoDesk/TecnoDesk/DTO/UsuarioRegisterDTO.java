package br.com.tecnoDesk.TecnoDesk.DTO;

import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;

public record UsuarioRegisterDTO(String nomeCompleto,String email,String pass) {}