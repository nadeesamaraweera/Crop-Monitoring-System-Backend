package lk.ijse.crop_monitoring_systembackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "crop")
@Entity
public class CropEntity implements Serializable {
    @Id
    private String cropId;
    private String commonName;
    private String scientificName;
    private String category;
    private String season;
    @Column(columnDefinition = "LONGTEXT")
    @Basic(fetch = FetchType.EAGER)
    private String cropImg;
    @OneToMany(mappedBy = "crop", cascade = CascadeType.ALL)
    private List<LogEntity> logs;
    @OneToMany(mappedBy = "crop", cascade = CascadeType.ALL)
    private List<FieldCropEntity> fieldCrops = new ArrayList<>();
}
