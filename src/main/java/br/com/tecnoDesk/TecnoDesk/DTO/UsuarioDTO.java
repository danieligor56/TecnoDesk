package br.com.tecnoDesk.TecnoDesk.DTO;

import br.com.tecnoDesk.TecnoDesk.Entities.Empresa;

public record UsuarioDTO (String email,String pass,Empresa empresa) {};
