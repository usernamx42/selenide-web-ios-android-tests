package cloud.autotests.tests.web;

import cloud.autotests.tests.TestBase;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static cloud.autotests.helpers.EnvironmentHelper.*;
import static cloud.autotests.utils.RandomUtils.*;
import static com.codeborne.selenide.Selectors.byName;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;


@Feature("Selenide web tests")
@Story("Order tests")
@Tag("order")
class ArbuzTests extends TestBase {
    @Test
    @DisplayName("Successful order on arbuz.kz")
    void successfulOrder() {
        step("Open store and close all banners", ()-> {
            open(arbuzUrl);
            $(byName("email")).val(getRandomEmail());
            $(withText("Получить промокод")).click();
            $x("//*[@id=\"layout\"]/div[2]/div/div/button/span").click();
            $(".choose-card-custom-button").click();
            $(withText("Перейти в магазин")).click();

        });

        step("Authorization", ()-> {
            $(withText("Вход")).click();
            $(withText("Войти по почте")).click();
            $("#loginInputEmail").val(arbuzLogin);
            $("#loginInputPassword").val(arbuzPassword);
            $x("//*[@id=\"layout\"]/div[2]/div/div/form/div[2]/button[2]").click();
            sleep(3000);

        });

        step("Search for item and purchase", ()-> {
            $(byName("phrase")).val(arbuzItem);
            $("#top_search_btn").click();
            $$(".cart-btn").first().click();
            $(withText("Оформить заказ")).click();
        });

        step("Complete order", ()-> {
            $(byName("street")).val("Тимерязева," + getRandomInt(1, 101));
            $$("#autocomplete-result-list-1").first().click();
            $("#ar_apartment").val("" + getRandomInt(1, 15));
            $("#ar_floor").val("" + getRandomInt(1, 10));
            $("#ar_doorphone").val("" + getRandomInt(1000, 1999));
            $("#checkout_deliveryPeriodTimeId").selectOption(13);
            $("#ar_comment").val(getRandomString(30) + "Тестовый заказ, доставка не требуется!");
            $("#ar_commentAssortmentLack").val(getRandomString(30) + "Тестовый заказ, доставка не требуется!");
            $(withText("Наличными")).click();
            $("#ar_withdrawalFromAmount").val("" + getRandomInt(10000, 15000));
            $("#pay_btn").click();
            sleep(10000);
        });

    }
}
