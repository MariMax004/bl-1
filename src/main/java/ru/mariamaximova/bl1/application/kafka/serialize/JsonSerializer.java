package ru.mariamaximova.bl1.application.kafka.serialize;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

public class JsonSerializer <T> implements Serializer<T>{

        public void configure(Map<String, ?> configs, boolean isKey) {
        }

        public byte[] serialize(String topic, T data) {

            if (data == null){
                return null;
            }
            byte[] retVal = null;
            try{
                ObjectMapper objectMapper = new ObjectMapper();
                retVal = objectMapper.writeValueAsString(data).getBytes();
            }catch(Exception e){
                throw new SerializationException("Error serializing JSON message",e);
            }
            return retVal;
        }

        public void close() {

        }
}
