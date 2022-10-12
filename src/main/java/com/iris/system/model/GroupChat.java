package com.iris.system.model;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Value
@Builder
public class GroupChat {
    int id;
    String name;
    Date createTime;
    String description;
}
