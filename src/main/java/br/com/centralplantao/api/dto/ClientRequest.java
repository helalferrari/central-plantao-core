package br.com.centralplantao.api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ClientRequest(
    @NotBlank @Size(max = 255) String corporateName,
    @NotBlank @Size(max = 255) String tradeName,
    @NotBlank @Size(max = 20) String document,
    @NotBlank @Email @Size(max = 255) String email,
    @NotBlank @Size(max = 20) String phone,
    boolean active
) {}
