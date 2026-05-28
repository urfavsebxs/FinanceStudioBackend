package com.sebas.finance.module.financial.infrastructure.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntryDeleteRequest {
    private String id;
    private String category;
}
