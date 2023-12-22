package sn.isi.service;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.isi.dao.IProduitRepository;
import sn.isi.dto.Produit;
import sn.isi.exception.EntityNotFoundException;
import sn.isi.exception.RequestException;
import sn.isi.mapping.ProduitMapper;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProduitService {
    private IProduitRepository iProduitRepository;
    private ProduitMapper produitMapper;
    MessageSource messageSource;

    public ProduitService(IProduitRepository iProduitRepository, ProduitMapper produitMapper, MessageSource messageSource) {
        this.iProduitRepository = iProduitRepository;
        this.produitMapper = produitMapper;
        this.messageSource = messageSource;
    }

    @Transactional(readOnly = true)
    public List<Produit> getProduit() {
        return StreamSupport.stream(iProduitRepository.findAll().spliterator(), false)
                .map(produitMapper::toProduit)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Produit getAppRole(int id) {
        return produitMapper.toProduit(iProduitRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage("produit.notfound", new Object[]{id},
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
                }).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("produit.notfound", new Object[]{id},
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
