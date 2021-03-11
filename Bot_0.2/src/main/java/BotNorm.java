import com.google.inject.internal.Messages;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.GetChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

public class BotNorm extends TelegramLongPollingBot {
    private static final String TOKEN = "1530377708:AAGlZ3clD0d_0zLH5idptLSOV84skeIFtUA";
    private static final String USERNAME = "JavaEndterm_bot";
    static Database foo = new Database();

    String[] origins = new String[20];
    String[] destinations = new String[20];///
    String[] return_date = new String[20];
    String[] depart_date = new String[20];
    String[] gates = new String[20];
    String purchased_ticket;
    String user_number;
    int[] value = new int[20];
    String[] city_code = new String[]{"NQZ","ALA","SCO","GUW","BXH","DMB","DZN","SZI","KGF","KOV","KSN","PWQ","PLX","TDK","URA","UKK","CIT"};
    String[] normal_city = new String[]{"Nur-Sultan","Almaty","Aktau","Atyrau","Balkhash","Jambul","Jezkazgan","Zaisan",
            "Karaganda","Kokshetau","Kostanay","Pavlodar","Semey","Taldykorgan","Oral","Ust-Kamenogorsk","Shymkent"};


    public BotNorm() {
        super();
    }

    public String getBotToken() {
        return TOKEN;
    }

    public String getBotUsername() {
        return USERNAME;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.getMessage() != null && update.getMessage().hasText()) {
            long chat_id = update.getMessage().getChatId();
            Message message = update.getMessage();

            if (message.getText().equals("/help")) {
                try {
                    execute(new SendMessage(chat_id, "@Dum_an gay"/* + update.getMessage().getText()*/));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message.getText().equals("/settings")) {
                try {
                    execute(new SendMessage(chat_id, "What we will setting? " + update.getMessage().getText()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message.getText().equals("/flight")) {
                try {
                    execute(new SendMessage(chat_id, String.valueOf(chat_id)));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message.getText().contains("/number")) {
                String string = message.getText();
                String[] parts = string.split(" ");
                user_number = parts[1];
                try {
                    execute(new SendMessage(chat_id, user_number));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message.getText().contains("/go")) {
                String string = message.getText();
                String[] parts = string.split(" ");
                String part2 = parts[1];
                String part3 = parts[2];
                System.out.println(part2);
                try {
                    try {
                        for (int j = 0; j < normal_city.length; j++) {
                            if (part2.equals(normal_city[j])) {
                                part2 = city_code[j];
                            }
                            if (part3.equals(normal_city[j])) {
                                part3 = city_code[j];
                            }
                        }

                        Ticket.getTicket(part2, part3);
                        origins = Ticket.getOrigins();
                        destinations = Ticket.getDestinations();
                        return_date = Ticket.getReturn_date();
                        depart_date = Ticket.getDepart_date();
                        value = Ticket.getValue();
                        gates = Ticket.getGate();

                        for (int i = 0; i < 10; i++) {
                            for (int j = 0; j < normal_city.length; j++) {
                                if (origins[i].equals(city_code[j])) {
                                    origins[i] = normal_city[j];
                                }
                                if (destinations[i].equals(city_code[j])) {
                                    destinations[i] = normal_city[j];
                                }
                            }
                        }
                        for (int i = 0; i < 10; i++) {
                            if (origins[i] != null /*&& origins[i].equals(part2)*/ && destinations[i] != null /*&& destinations[i].equals(part3)*/) {
                                execute(new SendMessage(chat_id,
                                        "Flight number: " + (i + 1) + "\n" +
                                                "Departure city: " + origins[i] + "\n" +
                                                "Destination city: " + destinations[i] + "\n" +
                                                "Airline: " + gates[i] + "\n" +
                                                "Departure date: " + depart_date[i] + "\n" +
                                                "Class: Economy" + "\n" +
                                                "Ticket price: " + value[i] + " KZT"));
                            } else {
                                execute(new SendMessage(chat_id, "City not found!"));
                                break;
                            }
//                                execute(new SendMessage(chat_id,destinations[i]));
                        }

                    } catch (IOException e) {
                        execute(new SendMessage(chat_id, "Why are you gay?"));
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else if (message.getText().contains("/buy")) {
                String string = message.getText();
                String[] parts = string.split(" ");
                purchased_ticket = parts[1];
                try {
                    foo.query("INSERT INTO orders (user_number,depart_city,destinat_city,depart_date,value) VALUES" +
                            "('" + user_number + "','" + origins[Integer.parseInt(purchased_ticket) - 1] + "','" +
                            destinations[Integer.parseInt(purchased_ticket) - 1] + "','" +
                            depart_date[Integer.parseInt(purchased_ticket) - 1] + "'," + value[Integer.parseInt(purchased_ticket) - 1] + ");");
                    execute(new SendMessage(chat_id, "Thank you for your purchase!"));
                    execute(new SendMessage(String.valueOf(765069481), "New order: " + "\n" +
                            "User number: " + user_number + "\n" +
                            "Departure city: " + origins[Integer.parseInt(purchased_ticket) - 1] + "\n" +
                            "Destination city: " + destinations[Integer.parseInt(purchased_ticket) - 1] + "\n" +
                            "Departure date: " + depart_date[Integer.parseInt(purchased_ticket) - 1] + "\n" +
                            "Class: Economy" + "\n" +
                            "Ticket price: " + value[Integer.parseInt(purchased_ticket) - 1] + "KZT"));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    execute(new SendMessage(chat_id, "@Dum_an"));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
            /////
        }
    }
}
