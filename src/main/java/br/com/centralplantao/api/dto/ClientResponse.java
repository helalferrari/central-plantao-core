package br.com.centralplantao.api.dto;

public record ClientResponse(
    Long id,
    String corporateName,
    String tradeName,
    String document,
    String email,
    String phone,
    boolean active
) {}
