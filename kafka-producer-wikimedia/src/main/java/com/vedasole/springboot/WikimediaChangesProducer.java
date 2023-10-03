package com.vedasole.springboot;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class WikimediaChangesProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() throws InterruptedException {

        String topic = "wikimedia-recent-change";

        // to read real time stream data for wikimedia, we use event source
        BackgroundEventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topic);

        String url = "https://stream.wikimedia.org/v2/stream/recentchange";

        BackgroundEventSource.Builder builder = new BackgroundEventSource
                .Builder(eventHandler, new EventSource.Builder(URI.create(url)));

        BackgroundEventSource eventSource = builder.build();

        eventSource.start();

        TimeUnit.MINUTES.sleep(10);

    }

}
