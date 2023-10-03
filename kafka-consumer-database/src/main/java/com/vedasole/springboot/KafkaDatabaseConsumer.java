package com.vedasole.springboot;

import com.vedasole.springboot.entity.WikimediaData;
import com.vedasole.springboot.repository.WikiMediaDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaDatabaseConsumer {

    private WikiMediaDataRepository dataRepository;

    public KafkaDatabaseConsumer(WikiMediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @KafkaListener(
            topics = "wikimedia-recent-change",
            groupId = "myGroup"
    )
    public void consume(String eventMessage){

        log.info(String.format("Event message received  -> %s  ", eventMessage));

        WikimediaData.WikimediaDataBuilder builder = WikimediaData.builder();
        dataRepository.save(builder.wikiEventData(eventMessage).build());

    }

}

