package com.duan.demo01.models;

import com.duan.demo01.models.values.DeviceStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Device {

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private Integer quantity;
    @JsonFormat(pattern="dd/MM/yyyy")
    private LocalDate dateAcquired = LocalDate.now();
    private String image;

    // relationship
    @Enumerated(EnumType.STRING)
    private DeviceStatus status = DeviceStatus.INACTIVE;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "qr_id")
    private QR qr;

    @ManyToOne()
    @JoinColumn(name = "warehouse_id")
    private Warehouse warehouse;

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name +
                ", quantity=" + quantity +
                ", dateAcquired=" + dateAcquired +
                ", image='" + image +
                ", status=" + status +
                ", qr=" + qr +
                ", warehouse=" + warehouse +
                '}';
    }
}
