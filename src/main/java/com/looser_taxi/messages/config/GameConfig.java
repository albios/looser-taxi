package com.looser_taxi.messages.config;

import com.looser_taxi.messages.enumerated.AutoType;
import com.looser_taxi.messages.Message;
import com.looser_taxi.messages.NextMessageSelection;
import com.looser_taxi.messages.factory.MessageFactory;
import com.looser_taxi.messages.output.MessageOutputService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameConfig {

    public GameConfig(MessageOutputService messageOutputService,
                      @Qualifier("userOptionSelection") NextMessageSelection optionSelectionType,
                      @Qualifier("strictlyNextSelection") NextMessageSelection strictlyNextMessageSelectionType,
                      @Qualifier("stringInputStrictlyNextSelection") NextMessageSelection stringInputStrictlyNextMessageSelectionType,
                      @Qualifier("randomWaitingTimeRandomNextSelection") NextMessageSelection randomWaitingTimeRandomNextSelection,
                      @Qualifier("randomNextSelection") NextMessageSelection randomNextSelection) {
        this.messageOutputService = messageOutputService;
        this.optionSelection = optionSelectionType;
        this.strictlyNextMessageSelection = strictlyNextMessageSelectionType;
        this.stringInputStrictlyNextMessageSelection = stringInputStrictlyNextMessageSelectionType;
        this.randomNextSelection = randomNextSelection;
        this.randomWaitingTimeRandomNextSelection = randomWaitingTimeRandomNextSelection;
    }

    private final MessageOutputService messageOutputService;

    private final NextMessageSelection optionSelection;
    private final NextMessageSelection strictlyNextMessageSelection;
    private final NextMessageSelection stringInputStrictlyNextMessageSelection;
    private final NextMessageSelection randomNextSelection;
    private final NextMessageSelection randomWaitingTimeRandomNextSelection;

    public static Map<Message, NextMessageSelection> selectionTypeByMessage = new HashMap<>();

    @EventListener(ApplicationReadyEvent.class)
    public void init() {

        /* messages creation */
        Message initMessage = MessageFactory.createMessage("Вас вітає Лузер таксі!\n" +
                "Для використання нашого сервісу, надайте, будь-ласка, доступ до вашої геолокації, мікрофону, камери та контактів:");
        Message grantPermissions = MessageFactory.createMessage("надати доступ");
        Message rejectPermissions = MessageFactory.createMessage("відмовити", initMessage);

        Message destinationSelection = MessageFactory.createMessage("Будь-ласка, введіть куди вам треба доїхати та ми зробимо все, щоб довезти вас туди!");
        Message checkingTheDestination = MessageFactory.createMessage("Одну секунду, перевіряємо ваш запит...");
        Message districtNotSupportedMessage = MessageFactory.createMessage("На жаль, ми ще не оперуємо в цьому районі. В якості компенсації можемо відвезти вас на ДВРЗ за півціни. " +
                "Чи цікавить вас така пропозиція?");
        Message okTakeMeToDVRZMessage = MessageFactory.createMessage("Так");
        Message noThanksNoDVRZMessage = MessageFactory.createMessage("Hі, дякую");

        Message areYouSure = MessageFactory.createMessage("Чи впевнені ви? Там нещодавно відкрили новий величезний ТРЦ з купою розваг, впевнені що вам сподобається!");
        Message okTakeMeThere = MessageFactory.createMessage("Ну, давайте тоді");
        Message fuckOff = MessageFactory.createMessage("Hі, відчепіться від мене");

        Message chooseCarMessage = MessageFactory.createMessage("Оберіть, будь-ласка, ваш рівень авто:");
        Message lookingForACarMessage = MessageFactory.createMessage("Шукаємо авто для вас, це може зайняти деякий час....");
        Arrays.stream(AutoType.values())
                .map(type -> MessageFactory.createMessage(type.getDetails(), lookingForACarMessage))
                .forEach(child -> {
                    chooseCarMessage.addChild(child);
                    registerMessagesToSelectionsMappings(strictlyNextMessageSelection, child);
                });

        Message anotherCarAvailable = MessageFactory.createMessage("Hа жаль, у вашому районі наразі немає машини обраного класу :( " +
                "Але поблизу є інша машина класу бізнес. Бажаєте поїхати нею?");
        Message yesConfirmOrder = MessageFactory.createMessage("Tак, оформляйте замовлення");
        Message noThanksNotThatCar = MessageFactory.createMessage("Ні, дякую");

        Message carFound = MessageFactory.createMessage("На щастя, машину для вас знайдено! Тепер вам потрібно обрати, яким чином ви плануєте оплатити поїздку:");
        Message choosePayment = MessageFactory.createMessage(" Тепер вам потрібно обрати, яким чином ви плануєте оплатити поїздку:");
        Message byCash = MessageFactory.createMessage("готівкою");
        Message byCard = MessageFactory.createMessage("карткою");
        Message byCrypto = MessageFactory.createMessage("криптою");

        Message byCashReaction = MessageFactory.createMessage("дякуємо за ваш вибір, будь-ласка підготовте суму без здачі за можливістю");
        Message byCardReaction = MessageFactory.createMessage("чудовий вибір! Для оплати вам необхідно буде надати номер вашої карти, срок дії та номер відділення, де її випустили (3 цифри на звороті карти) водію - " +
                "і він все зробить за вас, адже ми піклуємося про вас і хочемо зробити вашу поїздку максимально комфортною.");
        Message byCryptoReaction = MessageFactory.createMessage("на жаль, ми не приймаємо цей вид оплати з сьогоднішнього дня через часті проблеми з шахрайством. \n" +
                "На вашу адресу автоматично визвано наряд поліції, щоб з вами розібратися");

        Message driverArrived = MessageFactory.createMessage("Нарешті водій знаходить вас, ви сідаєте в машину, сподіваючись на швидку та комфортну поїздку...");
        List<Message> taxiCarAccidents = registerTaxiCarAccidents();
        Message successfullyArrived = MessageFactory.createMessage("\"Вітаємо! Ви успішно добралися до вашого пункту призначення! Щасти вам і до зустрічі у наступних поїздках!\"");


        Message defaultFailureMessage = MessageFactory.createMessage("Hа жаль, ви програли. Щасти наступних разів!");
        List<Message> failureMessages = registerFailureMessages();

        /* messages to children mapping */

        failureMessages.forEach(fuckOff::addChild);
        failureMessages.forEach(noThanksNotThatCar::addChild);
        taxiCarAccidents.forEach(driverArrived::addChild);
        taxiCarAccidents.forEach(taxiCarAccident ->
                failureMessages.forEach(taxiCarAccident::addChild));

        initMessage.addChildren(grantPermissions, rejectPermissions);
        grantPermissions.addChild(destinationSelection);
        destinationSelection.addChild(checkingTheDestination);
        checkingTheDestination.addChildren(districtNotSupportedMessage, chooseCarMessage);
        districtNotSupportedMessage.addChildren(okTakeMeToDVRZMessage, noThanksNoDVRZMessage);
        okTakeMeToDVRZMessage.addChild(chooseCarMessage);
        areYouSure.addChildren(okTakeMeThere, fuckOff);
        okTakeMeThere.addChild(chooseCarMessage);
        noThanksNoDVRZMessage.addChild(areYouSure);
        lookingForACarMessage.addChildren(carFound, anotherCarAvailable);
        anotherCarAvailable.addChildren(yesConfirmOrder, noThanksNotThatCar);
        yesConfirmOrder.addChild(choosePayment);
        carFound.addChild(choosePayment);
        choosePayment.addChildren(byCash, byCard, byCrypto);
        byCash.addChild(byCashReaction);
        byCard.addChild(byCardReaction);
        byCrypto.addChild(byCryptoReaction);
        byCashReaction.addChild(driverArrived);
        byCardReaction.addChild(driverArrived);
        byCryptoReaction.addChild(defaultFailureMessage);
        driverArrived.addChild(successfullyArrived);

        /* messages to next selection beans mapping */
        registerMessagesToSelectionsMappings(optionSelection,
                initMessage, districtNotSupportedMessage, areYouSure, chooseCarMessage, anotherCarAvailable, choosePayment);
        registerMessagesToSelectionsMappings(strictlyNextMessageSelection,
                grantPermissions, rejectPermissions, okTakeMeToDVRZMessage, okTakeMeThere, yesConfirmOrder,
                byCash, byCard, byCrypto, byCashReaction, byCardReaction, byCryptoReaction,
                noThanksNoDVRZMessage, carFound);
        registerMessagesToSelectionsMappings(stringInputStrictlyNextMessageSelection,
                destinationSelection);
        registerMessagesToSelectionsMappings(randomWaitingTimeRandomNextSelection,
                checkingTheDestination, lookingForACarMessage, driverArrived);
        registerMessagesToSelectionsMappings(randomNextSelection,
                fuckOff, noThanksNotThatCar);

        runGame(initMessage);
    }

    private List<Message> registerFailureMessages () {
        Message beatenByLocals = MessageFactory.createMessage("Hа жаль, ви програли, бо залишилися в незнайомому місці вночі. Вас пограбували місцеві та побили до стану коми. \n" +
                "Щасливого одужання, наступного разу користуйтеся послугами “Лузер Таксі”!");
        Message publicTransportAccident = MessageFactory.createMessage("Hа жаль, ви програли. Маршрутка, якою ви поїхали замість, розбилася від лобового зіткнення з вантажівкою з пʼяним водієм за кермом.\n" +
                "Щасливого одужання, наступного разу користуйтеся послугами “Лузер Таксі”!");
        Message mistakenForDiversionist = MessageFactory.createMessage("Hа жаль, ви програли. Ви не змогли поїхати на таксі, почалася комендантська година, " +
                "а копи прийняли вас за диверсанта та відвезли у відділок, де вас до втрати свідомості зґвалтували місцеві зеки.\n" +
                "Щасливого одужання, наступного разу користуйтеся послугами “Лузер Таксі”!");
        return Arrays.asList(beatenByLocals, publicTransportAccident, mistakenForDiversionist);
    }

    private List<Message> registerTaxiCarAccidents() {
        Message fuelSensorBroken = MessageFactory.createMessage("Вибачайте шановний пасажире, проте якимось дивом закінчилося пальне. Дивно, як їхав по вас, ще половину датчик показував. \n" +
                "Мабуть зламався вже, хай йому грець. Доведеться нам з вами розійтися, добирайтеся якось інакше. Грощі повернуться вам на бонусний рахунок. Щасти вам, і до побачення!");
        Message engineBroken = MessageFactory.createMessage("шось починає гучно тарахтіти під капотом, і ви повільно зупиняєтеся біля темної та порожньої обочини. Схоже, що знов зламався двигун.\n" +
                "Вибачайте, доведеться вам сьогодні добиратися самостійно. Проте ми дякуємо вам за довіру і чекатимемо вас вже завтра, щоб знов надавати послуги найвищого класу!");
        Message taxiCarAccident = MessageFactory.createMessage("<завтрашній випуск новин>\n" +
                "“на жаль, вчора ввечері сталося ще одне ДТП з участю машини Лузер Таксі, в черговий раз з летальним наслідком. \n" +
                "Користувачі соцмереж масово скаржаться на цей скандальний сервіс через купу скандалів, повʼязаних з ним");
        return Arrays.asList(fuelSensorBroken, engineBroken, taxiCarAccident);
    }

    private void registerMessagesToSelectionsMappings(NextMessageSelection selection, Message... messages) {
        for (Message m : messages) {
            selectionTypeByMessage.put(m, selection);
        }
    }

    private void runGame(Message initMessage) {
        messageOutputService.processMessage(initMessage);
    }

}
