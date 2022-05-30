package denis.googleMapsApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import denis.controllers.LocationData;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Maps;

public class GeodecodingSample extends AbstractSample {

    public LocationData geodecodingSample(String latitude, String longitude) throws IOException, JSONException {
        final String baseUrl = "https://api.visicom.ua/data-api/5.0/uk/geocode.json?category=adm_country&near=";
        // текстовое значение широты/долготы, для которого следует получить ближайший понятный человеку адрес, догота и
        // широта разделяется запятой, берем из предыдущего примера
        String paramLtg = longitude + "," + latitude;
        final String url = baseUrl + paramLtg + "&radius=50&key=7c7bfc539831ec4c11f08813ec4030a9";// генерируем путь с параметрами
        System.out.println(url);// Путь, что бы можно было посмотреть в браузере ответ службы
        final JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        // как правило наиболее подходящий ответ первый и данные о адресе можно получить по пути
        // //results[0]/formatted_address
        final JSONObject location = response.getJSONArray("features").getJSONObject(0).getJSONObject("properties");
        return new LocationData(location.getString("settlement_type"),
                location.getString("settlement"), location.getString("street_type"),
                location.getString("street"), location.getString("name"));
    }
}