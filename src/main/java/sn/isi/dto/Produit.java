package sn.isi.dto;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sn.isi.entities.AppUserEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produit {
    private int id;
    @NotNull(message = "Le nom ne doit pas etre null")
    private String nom;
    @NotNull
    private double qtStock;
    @ManyToOne
    private AppUser appUserEntity;
}
