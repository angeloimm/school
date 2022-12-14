package it.olegna.schoolmgmt.persistence.model;


import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Blob;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "AllegatoEntity")
@Table(name = "ALLEGATO")
public class Allegato {
    @ToString.Include
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", nullable = false)
    private UUID id;
    @Column(name = "NOME_FILE", nullable = false, length = 100)
    private String nomeFile;
    @Column(name = "CONTENT_TYPE_FILE", nullable = false, length = 100)
    private String contentTypeFile;
    @Column(name = "DIMENSIONE_FILE", nullable = false, length = 100)
    private Integer dimensioneFile;
    @Column(name = "CONTENUTO_FILE", nullable = false, length = 100)
    @Lob
    @Basic(fetch = FetchType.LAZY)
    private Blob contenutoFile;
    @ManyToOne
    @JoinColumn(name = "ID_UTENTE")
    private Utente utente;
}
