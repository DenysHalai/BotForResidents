package denis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class Main {

    public static void main(String[] args){

        SpringApplication.run(Main.class, args);
    }
}

//https://habr.com/ru/post/418905/ InlineKeyboard в Телеграмм ботах (Telegram Bots)
