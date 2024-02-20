package sn.isi.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sn.isi.dto.Produit;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProduitServiceTest {
    @Autowired
    private ProduitService produitService;
    @Test
    void getProduits() {
        List<Produit> produitList = produitService.getProduits();
        Assertions.assertEquals(1, produitList.size());
    }

    @Test
    void getProduit() {
        Produit produit = produitService.getProduit(1);
        Assertions.assertNotNull(produit);
    }

    @Test
    void createproduit() {
        Produit produit = new Produit();
        produit.setNom("Pomme");
        produit.setQtStock(10);
        Produit produitSave = produitService.createProduit(produit);
        Assertions.assertNotNull(produitSave);
    }

    @Test
    void updateproduit() {
        Produit produit = new Produit();
        produit.setNom("Orange");
        produit.setQtStock(23);
        Produit produitSave = produitService.updateProduit(1, produit);
        Assertions.assertNotNull(produitSave);
    }

    @Test
    void deleteproduit() {
        produitService.deleteProduit(1);
        Assertions.assertTrue(true);
    }
}
