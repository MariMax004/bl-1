package ru.mariamaximova.bl1.application.kafka.serialize;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import ru.mariamaximova.bl1.application.comment.model.CommentDto;

import java.io.IOException;
import java.util.Map;

public class DeserializeJson implements Deserializer<CommentDto> {


        public void configure(Map<String, ?> configs, boolean isKey) {
        }

        public CommentDto deserialize(String topic, byte[] data) {
            CommentDto object = null;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                object = objectMapper.readValue(data, CommentDto.class);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return object;
        }

        @Override
        public void close() {
        }

    }
