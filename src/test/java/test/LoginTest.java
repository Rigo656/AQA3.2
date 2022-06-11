package test;

import com.codeborne.selenide.Configuration;
import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;

public class LoginTest {
    @AfterAll
    static void deletingDataFromTheDb() {
        DataHelper.deletingData();
    }

    @Test
    public void shouldAuthorizationIsSuccessful() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:7777");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getAuthCode(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @Test
    public void shouldGiveErrorIfPasswordInvalid() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:7777");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getInvalidAuthInfo();
        loginPage.invalidLogin(authInfo);
        loginPage.getNotificationAuth();
    }

    @Test
    public void shouldGiveLockIfAnInvalidPasswordIsEnteredThreeTimes() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:7777");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getInvalidAuthInfo();
        loginPage.invalidPasswordThreeTimes(authInfo);
        loginPage.getNotification();
    }
}
