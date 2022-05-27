package denis;

import denis.model.DataBaseAddress;
import denis.repository.DataBaseAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;

@Component
public class InlineLocationMode {

    @Autowired
    private DataBaseAddressRepository dataBaseAddressRepository;

    public List<InlineQueryResult> execute(InlineQuery inlineQuery) {
        List<DataBaseAddress> byLastnameOrFirstname = new ArrayList<>();
        String line = inlineQuery.getQuery();
        String title = inlineQuery.getQuery();
        String street = null;
        String number = null;
        List<String> text = new ArrayList<>();
        StringTokenizer stringTokenizer = new StringTokenizer(line, ",");
        while (stringTokenizer.hasMoreElements()) {
            text.add(stringTokenizer.nextToken());
        }
        int count = text.size();
        switch (count) {
            case 1 -> title = text.get(0);
            case 2 -> {
                title = text.get(0);
                street = text.get(1).trim();
            }
            case 3 -> {
                title = text.get(0);
                street = text.get(1).trim();
                number = text.get(2).trim();
            }
        }
        if (number != null) {
            byLastnameOrFirstname = dataBaseAddressRepository.findByLastnameOrFirstname(title, street, number);
        } else {
            if (street != null) {
                byLastnameOrFirstname = dataBaseAddressRepository.findByLastnameOrFirstname(title, street);
            } else {
                byLastnameOrFirstname = dataBaseAddressRepository.findByLastnameOrFirstname(title);
            }
        }
        List<InlineQueryResult> inlineQueryResults = byLastnameOrFirstname.stream().map((Function<DataBaseAddress, InlineQueryResult>) dataBaseAddress -> {
            InlineQueryResultArticle inlineQueryResultArticle = new InlineQueryResultArticle(dataBaseAddress.getId().toString(),
                    dataBaseAddress.getTitle(),
                    new InputTextMessageContent(dataBaseAddress.getTitle() + " " + dataBaseAddress.getStreet() + " " + dataBaseAddress.getNumber())
            );
            inlineQueryResultArticle.setDescription(dataBaseAddress.getStreet() + " " + dataBaseAddress.getNumber());
            return inlineQueryResultArticle;
        }).toList();
        return inlineQueryResults;
    }
}