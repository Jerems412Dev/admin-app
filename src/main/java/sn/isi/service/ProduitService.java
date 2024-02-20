package sn.isi.service;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sn.isi.dao.IProduitRepository;
import sn.isi.dto.AppUser;
import sn.isi.dto.Produit;
import sn.isi.exception.EntityNotFoundException;
import sn.isi.exception.RequestException;
import sn.isi.mapping.ProduitMapper;
import java.util.List;
import java.util.Locale;
import java.util.stream.StreamSupport;

@Service
public class ProduitService {
    private final IProduitRepository iProduitRepository;
    private final ProduitMapper produitMapper;
    MessageSource messageSource;
    private final static String PRODUITNOTFOUND = "produit.notfound";

    public ProduitService(IProduitRepository iProduitRepository, ProduitMapper produitMapper, MessageSource messageSource) {
        this.iProduitRepository = iProduitRepository;
        this.produitMapper = produitMapper;
        this.messageSource = messageSource;
    }

    @Transactional(readOnly = true)
    public List<Produit> getProduits() {
        return StreamSupport.stream(iProduitRepository.findAll().spliterator(), false)
                .map(produitMapper::toProduit)
                .toList();
    }

    @Transactional(readOnly = true)
    public Produit getProduit(int id) {
        return produitMapper.toProduit(iProduitRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage(PRODUITNOTFOUND, new Object[]{id},
                                Locale.getDefault()))));
    }

    @Transactional
    public Produit createProduit(Produit produit) {
        return produitMapper.toProduit(iProduitRepository.save(produitMapper.fromProduit(produit)));
    }

    @Transactional
    public Produit updateProduit(int id, Produit produit) {
        return iProduitRepository.findById(id)
                .map(entity -> {
                    produit.setId(id);
                    return produitMapper.toProduit(
                            iProduitRepository.save(produitMapper.fromProduit(produit)));
                }).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage(PRODUITNOTFOUND, new Object[]{id},
                        Locale.getDefault())));
    }

    @Transactional
    public void deleteProduit(int id) {
        try {
            iProduitRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("produit.errordeletion", new Object[]{id},
                    Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }
    }
}
