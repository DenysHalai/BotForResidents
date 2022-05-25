package denis.googleMapsApi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.collect.Maps;

public class GeodecodingSample extends AbstractSample {

    public Map<String, Object> geodecodingSample(String latitude, String longitude) throws IOException, JSONException {
        final String baseUrl = "https://maps.googleapis.com/maps/api/geocode/json";// путь к Geocoding API по HTTP
        final Map<String, String> params = Maps.newHashMap();
        params.put("language", "uk");// язык данные на котором мы хочем получить
        params.put("sensor", "false");// исходит ли запрос на геокодирование от устройства с датчиком местоположения
        // текстовое значение широты/долготы, для которого следует получить ближайший понятный человеку адрес, догота и
        // широта разделяется запятой, берем из предыдущего примера
        String paramLtg = latitude + "," + longitude;
        params.put("latlng", paramLtg);
        final String url = baseUrl + '?' + encodeParams(params) + "&key=AIzaSyBAQCNtRe_9h8Mx8oy1wIesp2MZ_TQMnbU";// генерируем путь с параметрами
        System.out.println(url);// Путь, что бы можно было посмотреть в браузере ответ службы
        final JSONObject response = JsonReader.read(url);// делаем запрос к вебсервису и получаем от него ответ
        // как правило наиболее подходящий ответ первый и данные о адресе можно получить по пути
        // //results[0]/formatted_address
        final JSONObject location = response.getJSONArray("results").getJSONObject(0);
        final String formattedAddress = location.getString("formatted_address");
        return response.toMap();
    }
}