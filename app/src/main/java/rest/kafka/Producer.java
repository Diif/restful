package rest.kafka;

import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.UUID;

@Service
public class Producer {
    private final String TOPIC = "get_statistic";


    private KafkaTemplate<String, HashMap<UUID, Integer>> kafkaTemplate;

    @Autowired
    public Producer(KafkaTemplate<String, HashMap<UUID, Integer>> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(HashMap<UUID, Integer> getStat) {
        this.kafkaTemplate.send(TOPIC, getStat);
    }


}
