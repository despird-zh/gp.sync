package com.gp.sync.message;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;

public class SyncMessages {
	
	/**
	 * Make ObjectMapper support the InfoId module 
	 * @param mapper
	 **/
	public static ObjectMapper withInfoIdModule(final ObjectMapper mapper) {
		
		final SimpleModule module = new SimpleModule("SyncTypeSerializeModule");
		
		JsonSerializer<SyncType> serializer = new SyncTypeSerializer();
		JsonDeserializer<SyncType> deserializer = new SyncTypeDeserializer();
	    module.addDeserializer(SyncType.class, deserializer);
	    module.addSerializer(SyncType.class, serializer);
	    
	    mapper.registerModule(module);
		return mapper;
	}
	
	/**
	 * The serializer to support InfoId 
	 **/
	public static class SyncTypeSerializer extends JsonSerializer<SyncType>{

		@Override
		public void serialize(SyncType arg0, JsonGenerator jsonGenerator, SerializerProvider arg2)
				throws IOException, JsonProcessingException {
			
			jsonGenerator.writeString(arg0.toString());
		}
	}
	
	/**
	 * the deserializer to support InfoId
	 **/
	public static class SyncTypeDeserializer extends JsonDeserializer<SyncType>{

		@Override
		public SyncType deserialize(JsonParser parser, DeserializationContext ctxt)
				throws IOException, JsonProcessingException {
			String fullStr = parser.getValueAsString();
			SyncType rtv = new SyncType(fullStr);
			return rtv;
		}
		
	}
}
