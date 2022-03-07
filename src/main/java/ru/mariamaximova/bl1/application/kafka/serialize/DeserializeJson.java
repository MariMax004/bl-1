package ru.mariamaximova.bl1.application.kafka.serialize;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;
import ru.mariamaximova.bl1.application.comment.model.CommentDto;
import ru.mariamaximova.bl1.application.comment.model.ResponseCommentDto;

import java.io.IOException;
import java.util.Map;

public class DeserializeJson implements Deserializer<ResponseCommentDto> {


        public void configure(Map<String, ?> configs, boolean isKey) {
        }

        public ResponseCommentDto deserialize(String topic, byte[] data) {
            ResponseCommentDto object = null;
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                object = objectMapper.readValue(data, ResponseCommentDto.class);
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
