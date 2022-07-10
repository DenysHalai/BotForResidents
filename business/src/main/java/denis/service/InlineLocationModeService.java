package denis.service;

import denis.model.DataBaseAddress;
import denis.repository.DataBaseAddressRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.function.Function;
import java.util.stream.Collectors;

@Transactional
@Component
public class InlineLocationModeService {

    private final DataBaseAddressRepository dataBaseAddressRepository;

    public InlineLocationModeService(DataBaseAddressRepository dataBaseAddressRepository) {
        this.dataBaseAddressRepository = dataBaseAddressRepository;
    }

    public List<InlineQueryResult> execute(InlineQuery inlineQuery) {
        List<DataBaseAddress> byLastnameOrFirstname;
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
            case 1 -> title = text.get(0).toUpperCase();
            case 2 -> {
                title = text.get(0).toUpperCase();
                street = text.get(1).trim().toUpperCase();
            }
            case 3 -> {
                title = text.get(0).toUpperCase();
                street = text.get(1).trim().toUpperCase();
                number = text.get(2).trim().toUpperCase();
            }
        }
        byLastnameOrFirstname = dataBaseAddressRepository.findByCityAndStreet(title, street, number, PageRequest.of(0,50));
        return byLastnameOrFirstname.stream().map((Function<DataBaseAddress, InlineQueryResult>) dataBaseAddress -> {
            InlineQueryResultArticle inlineQueryResultArticle = new InlineQueryResultArticle(dataBaseAddress.getId().toString(),
                    dataBaseAddress.getStreet().getCity().getTitle(),
                    new InputTextMessageContent(dataBaseAddress.getStreet().getCity().getTitle() + " " + dataBaseAddress.getStreet().getTitle() + " " + dataBaseAddress.getNumber() + "\n" + "#" + dataBaseAddress.getId())
            );
            inlineQueryResultArticle.setDescription(dataBaseAddress.getStreet().getTitle() + " " + dataBaseAddress.getNumber());
            return inlineQueryResultArticle;
        }).collect(Collectors.toList());
    }
}