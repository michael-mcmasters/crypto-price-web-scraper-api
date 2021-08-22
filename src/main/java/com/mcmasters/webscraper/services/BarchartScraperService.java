package com.mcmasters.webscraper.services;

import com.mcmasters.webscraper.entities.HistoricPrice;
import com.mcmasters.webscraper.utils.StringConverter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BarchartScraperService {

    private static final String historicPriceQuery = "barchart-table-scroll tr";

    public List<HistoricPrice> scrapeHistoricPrices(Document barchartDoc) {
        List<HistoricPrice> historicPrices = new ArrayList<>();
        Elements elements = barchartDoc.select(historicPriceQuery);

        elements.remove(0);         // Remove first row because it is the name of each column
        for (Element e : elements) {
            log.info("Barchart element for historic prices and percentages = {}", e.text());
            String[] arr = e.text().split(" ");
            double price = StringConverter.convertPriceToDouble(arr[9]);
            double percentage = StringConverter.convertPercentageToDouble(arr[10]);
            historicPrices.add(new HistoricPrice(price, percentage));
        }
        return historicPrices;
    }
}