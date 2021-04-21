package com.example.demo.entity;

import com.example.demo.entity.ActionType.TurniketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class TurniketHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @ManyToOne
    private Turniket turniket;

    @ManyToOne
    private User user;

    @CreatedDate
    private LocalDateTime actionTime;//har bir kirgan yoki chiqgani bir action sifatida olinadi umumiy bitta actionni vaqti olinadi turi esa keyingi columnda yoziladi ENTER OR EXIT


    private TurniketType actionType;

}
