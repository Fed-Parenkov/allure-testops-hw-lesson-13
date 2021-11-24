package parenkov.tests;

import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Feature("Header главной страницы")
public class HeaderTests extends TestBase {

    @Severity(SeverityLevel.MINOR)
    @Link(name = "Главная страница", url = "https://www.tezis-doc.ru/")
    @DisplayName("Корректность тайтла")
    @Test
    void pageTitle() {
        step("Открыть главную страницу", () ->
                open("https://www.tezis-doc.ru/"));

        step("Проверить корректность тайтла страницы", () -> {
            String expectedTitle = "Система электронного документооборота. " +
                    "СЭД ТЕЗИС – система для работы с документами";
            String actualTitle = title();
            assertThat(actualTitle).isEqualTo(expectedTitle);
        });
    }

    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Главная страница", url = "https://www.tezis-doc.ru/")
    @DisplayName("Корректность названий пунктов")
    @CsvSource(value = {
            "Система; /features/",
            "Услуги; /services/",
            "Купить; /buy/",
            "Клиенты; /customers/",
            "Партнеры; /partners/",
            "Компания; /company/",
            "Госсектор; /gossector"
    }, delimiter = ';')
    @ParameterizedTest(name = "{displayName} [{0} {1}]")
    void checkItemsNames(String name, String link) {
        Allure.parameter("name", name);
        Allure.parameter("link", link);
        step("Открыть главную страницу", () ->
                open("https://www.tezis-doc.ru/"));

        step("Проверить корректность названия пункта " + name, () ->
                $(".main-nav").$("a[href='" + link + "']")
                        .shouldBe(visible).shouldHave(text(name)));
    }
}
