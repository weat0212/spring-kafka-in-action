package com.andywang.jms.multitype;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class TypeTwo {

    private String message;

    private Date timestamp;
}