# Погода
Приложение показывает прогноз погоды на неделю и периодически уведомляет о текущей погоде для выбранного города. Для получения информации о погоде используется API [OpenWeatherMap](https://openweathermap.org/api).

<img src="https://github.com/umnvd/Weather/blob/master/screenshots/home.png" width="250"> <img src="https://github.com/umnvd/Weather/blob/master/screenshots/forecast.png" width="250"> <img src="https://github.com/umnvd/Weather/blob/master/screenshots/notification.png" width="250">

# Используемые технологии
- Dagger
- Coroutines
- Room
- DataStore
- Retrofit
- WorkManager

# Запуск
Для корректной работы приложения необходимо создать файл _apikey.properties_ в корне проекта и добавить в него API-ключ.
  ```text
  OPEN_WEATHER_KEY = "YOUR_KEY"
  ```

