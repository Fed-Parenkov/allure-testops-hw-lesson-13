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
    @DisplayName("Отображение логотипа")
    @Test
    void checkLogo() {
        step("Открыть главную страницу", () ->
                open("https://www.tezis-doc.ru/"));

        step("Проверить наличие логотипа", () ->
                $("#logo-tezis").shouldBe(visible));
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
    @ParameterizedTest(name = "Корректность названия пункта {0}")
    void checkItemsNames(String name, String link) {
        step("Открыть главную страницу", () ->
                open("https://www.tezis-doc.ru/"));

        step("Проверить корректность названия пункта " + name, () ->
                $(".main-nav").$("a[href='" + link + "']")
                        .shouldBe(visible).shouldHave(text(name)));
    }

    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Главная страница", url = "https://www.tezis-doc.ru/")
    @DisplayName("Переход по ссылкам пунктов")
    @CsvSource(value = {
            "Система; features/",
            "Услуги; services/",
            "Купить; buy/",
            "Клиенты; customers/",
            "Партнеры; partners/",
            "Компания; company/",
            "Госсектор; gossector/"
    }, delimiter = ';')
    @ParameterizedTest(name = "Переход из главной страницы в раздел {0}")
    void checkItemsLinks(String name, String link) {
        step("Открыть главную страницу", () ->
                open("https://www.tezis-doc.ru/"));

        step("Кликнуть по пункту " + name, () ->
                $(".main-nav").$(byText(name)).click());

        step("Проверить, что открылась страница раздела " + name, () -> {
            String checkUrl = "https://www.tezis-doc.ru/" + link;
            String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
            assertEquals(checkUrl, currentUrl);
        });
    }

    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Главная страница", url = "https://www.tezis-doc.ru/")
    @DisplayName("Появление pop-ups пунктов при наведении курсора")
    @CsvSource(value = {
            "Система; sub-nav",
            "Услуги; sub-nav services",
            "Клиенты; sub-nav customers",
            "Партнеры; sub-nav partners",
            "Компания; sub-nav company"
    }, delimiter = ';')
    @ParameterizedTest(name = "Появление pop-up пункта {0} при наведении курсора")
    void checkHeaderPopups(String name, String value) {
        step("Открыть главную страницу", () ->
                open("https://www.tezis-doc.ru/"));

        step("Навести курсор на пункт " + name, () ->
            $(".main-nav").$(byText(name)).hover());

        step("Проверить, что появился всплывающий pop-up пункта " + name, () ->
                $("[class='" + value + "']").shouldBe(visible));
    }

    @Severity(SeverityLevel.MINOR)
    @Link(name = "Главная страница", url = "https://www.tezis-doc.ru/")
    @DisplayName("Выполнение поиска")
    @Test
    void checkSearchButton() {
        step("Открыть главную страницу", () ->
                open("https://www.tezis-doc.ru/"));

        step("Кликнуть по кнопке поиска", () ->
                $("#expand-search-button").shouldBe(visible).click());

        step("В раскрывшемся поле поиска ввести произвольный текст, например 'test', нажать Enter", () -> {
            $("#gsc-iw-id1").shouldBe(visible);
            $("#gsc-i-id1").setValue("test").pressEnter();
        });

        step("Проверить переход из главной страницы на страницу с результатами поиска", () -> {
            String checkUrl = "https://www.tezis-doc.ru/search-results/?keywords=test";
            String currentUrl = WebDriverRunner.getWebDriver().getCurrentUrl();
            assertEquals(checkUrl, currentUrl);
        });
    }

    @Severity(SeverityLevel.CRITICAL)
    @Link(name = "Главная страница", url = "https://www.tezis-doc.ru/")
    @DisplayName("Отображение контактного номера телефона")
    @Test
    void checkPhoneNumber() {
        step("Открыть главную страницу", () ->
                open("https://www.tezis-doc.ru/"));

        step("Проверить корректность номера телефона", () ->
                $(".tel").shouldBe(visible).shouldHave(text("8 800 77 55 205")));
    }

    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Главная страница", url = "https://www.tezis-doc.ru/")
    @DisplayName("Отображение элементов формы 'Обратный звонок'")
    @Test
    void checkCallBackForm() {
        step("Открыть главную страницу", () ->
                open("https://www.tezis-doc.ru/"));

        step("Открыть форму обратного звонка кликом по ссылке 'Перезвоните мне'", () -> {
                $(".call-me").shouldBe(visible).shouldHave(text("Перезвоните мне")).click();
                $(".modal").shouldBe(visible);
        });

        step("Проверить текст тайтла и цвет слова 'ЗАКАЗАТЬ' (должен быть синий)", () -> {
            $("#modalTitle").shouldBe(visible).shouldHave(text("ЗАКАЗАТЬ ОБРАТНЫЙ ЗВОНОК"));
            $(".text-blue").shouldHave(cssValue("color", "rgba(3, 152, 226, 1)"));
        });

        step("Проверить текст и наличие кнопки 'Оставить заявку'", () ->
                $("#submitButton").shouldBe(visible).shouldHave(text("Оставить заявку")));

        step("Проверить наличие кнопки [X]", () ->
                $("[aria-label='Close modal']").shouldBe(visible));

        step("Закрыть форму кликом по кнопке [X]", () -> {
            $("[aria-label='Close modal']").click();
            $(".modal").shouldBe(disappear, Duration.ofMillis(1000));
        });
    }

    @Severity(SeverityLevel.NORMAL)
    @Link(name = "Главная страница", url = "https://www.tezis-doc.ru/")
    @DisplayName("[NEGATIVE] Отправка заявки на обратный звонок с незаполненными полями формы")
    @Test
    void checkSendBlankForm() {
        step("Открыть главную страницу", () ->
                open("https://www.tezis-doc.ru/"));

        step("Открыть форму обратного звонка кликом по ссылке 'Перезвоните мне'", () -> {
            $(".call-me").shouldBe(visible).shouldHave(text("Перезвоните мне")).click();
            $(".modal").shouldBe(visible);
        });

        step("Кликнуть по кнопке 'Оставить заявку'", () ->
            $("#submitButton").click());

        step("Проверить, что форма не отправляется", () -> {
            // сообщение об успешном приеме заявки не появляется
            $(".success-message-wrapper").shouldNotBe(visible);
            // окно остается открытым
            $(".modal").shouldBe(visible);
        });

        step("Проверить, что текст формы стал красного цвета", () ->
            $("[for='demo-privacy-policy1']")
                    .shouldHave(cssValue("color", "rgba(253, 107, 86, 1)")));
    }
}
