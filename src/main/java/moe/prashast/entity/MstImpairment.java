package moe.prashast.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "mst_impairment", schema = "master")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MstImpairment {


        @Id
        @Column(name = "impairment_id", nullable = false)
        private Integer impairmentId;

        @Column(name = "impairment_desc")
        private String impairmentDesc;

        @Column(name = "status", nullable = false)
        private Short status ;

        @Column(name = "language_id")
        private Short languageId;

    }
