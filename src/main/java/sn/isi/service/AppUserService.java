package sn.isi.service;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.isi.dao.IAppUserRepository;
import sn.isi.dto.AppUser;
import sn.isi.exception.EntityNotFoundException;
import sn.isi.exception.RequestException;
import sn.isi.mapping.AppUserMapper;
import java.util.List;
import java.util.Locale;

@Service
public class AppUserService {
    private final IAppUserRepository iAppUserRepository;
    private final AppUserMapper appUserMapper;
    MessageSource messageSource;
    private final static String APPUSERNOTFOUND = "user.notfound";

    public AppUserService(IAppUserRepository iAppUserRepository, AppUserMapper appUserMapper, MessageSource messageSource) {
        this.iAppUserRepository = iAppUserRepository;
        this.appUserMapper = appUserMapper;
        this.messageSource = messageSource;
    }

    @Transactional(readOnly = true)
    public List<AppUser> getAppUsers() {
        return iAppUserRepository.findAll().stream()
                .map(appUserMapper::toAppUser)
                .toList();
    }

    @Transactional(readOnly = true)
    public AppUser getAppUser(int id) {
        return appUserMapper.toAppUser(iAppUserRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage(APPUSERNOTFOUND, new Object[]{id},
                                Locale.getDefault()))));
    }

    @Transactional(readOnly = true)
    public AppUser getAppRole(int id) {
        return appUserMapper.toAppUser(iAppUserRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(messageSource.getMessage(APPUSERNOTFOUND, new Object[]{id},
                                Locale.getDefault()))));
    }

    @Transactional
    public AppUser createAppUser(AppUser appUser) {
        return appUserMapper.toAppUser(iAppUserRepository.save(appUserMapper.fromAppUser(appUser)));
    }

    @Transactional
    public AppUser updateAppUser(int id, AppUser appUser) {
        return iAppUserRepository.findById(id)
                .map(entity -> {
                    appUser.setId(id);
                    return appUserMapper.toAppUser(
                            iAppUserRepository.save(appUserMapper.fromAppUser(appUser)));
                }).orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage(APPUSERNOTFOUND, new Object[]{id},
                        Locale.getDefault())));
    }

    @Transactional
    public void deleteAppUser(int id) {
        try {
            iAppUserRepository.deleteById(id);
        } catch (Exception e) {
            throw new RequestException(messageSource.getMessage("user.error deletion", new Object[]{id},
                    Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }
    }
}
