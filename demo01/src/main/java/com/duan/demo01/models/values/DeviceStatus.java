package com.duan.demo01.models.values;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

public enum DeviceStatus {
    ACTIVE, REPAIRING, INACTIVE
}
