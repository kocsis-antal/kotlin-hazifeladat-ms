# Kotlin oktatáshoz házifeladat

## Készíteni kell egy nagyon egyszerű alkalmazást / programot, az alábbi funkcionalitással
1. Open Meteo API-ról Budapestre 7 napos hőmérsékleti előrejelzés lekérése órás bontásban (https://api.open-meteo.com/v1/forecast?latitude=47.4984&longitude=19.0404&hourly=temperature_2m&timezone=auto)
2. Kapott eredményekre napi középhőmérséklet számítás
3. Eredmények konzolra kiírása
4. Egyszerű hibakezelés
5. Automata teszt(ek)

## Feladat megvalosítása során
1. Gradle build tool használata (Előkészítve) 
2. Tetszőleges Framework használata (Spring konfigurálva)
3. Data class-ok használata
4. Kotlin Collection Framework használata
5. Tetszőleges Http kliens
6. Tetszőleges deserialization library (Jackson konfigurálva)
7. JUnit teszt framework
8. Tetszőleges mock library (Mockk konfigurálva)
9. Mindenki készítsen saját fork-ot a repository-ról és ott dolgozzon (innen tudom majd ellenőrizni)

## Szorgalmi feladat
- Nem csak konzolra írni az eredményt, hanem az alkalmazás (egyszerű webszerver) egy html oldalon elérhetővé teszi azt.
- Nem (csak) Spring Boot, hanem Kotlin-os framework-ok használata (Ktor, kotlinx.serialization, stb)

## Határidő
2024.07.01

## Gradle taskok

Alkalmazás indítása (Spring boot esetén): `gradlew bootRun`
Web alkalmazás indítása (Spring boot esetén): `gradlew bootRun -Pweb`

Automata tesztek: `gradlew check`
