package sn.isi.service;

import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sn.isi.dto.AppUser;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppUserServiceTest {
    @Autowired
    private AppUserService appUserService;
    @Test
    void getAppUsers() {
        List<AppUser> appUserList = appUserService.getAppUsers();
        Assertions.assertEquals(1, appUserList.size());
    }

    @Test
    void getAppUser() {
        AppUser appUser = appUserService.getAppUser(1);
        Assertions.assertNotNull(appUser);
    }

    @Test
    void createAppUser() {

        AppUser appUser = new AppUser();
        appUser.setNom("OBIANG-ENGONE");
        appUser.setPrenom("Jeremy-Evrard");
        appUser.setEmail("jeremyobiangdev@gmail.com");
        appUser.setPassword("1234");
        appUser.setEtat(1);
        AppUser appUserSave = appUserService.createAppUser(appUser);
        Assertions.assertNotNull(appUserSave);
    }

    @Test
    void updateAppUser() {
        AppUser appUser = new AppUser();
        appUser.setNom("ENGONE-OBIANG");
        appUser.setPrenom("Evrard-Jeremy");
        appUser.setEmail("jeremyobiangdev@gmail.com");
        appUser.setPassword("4321");
        appUser.setEtat(0);
        AppUser appUserSave = appUserService.updateAppUser(1, appUser);
        Assertions.assertNotNull(appUserSave);
    }

    @Test
    void deleteAppUser() {
        appUserService.deleteAppUser(1);
        Assertions.assertTrue(true);
    }
}
