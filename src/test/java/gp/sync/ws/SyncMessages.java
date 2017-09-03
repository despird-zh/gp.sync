package gp.sync.ws;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Optional;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.gp.sync.message.SyncPushMessage;
import com.gp.sync.message.SyncType;

public class SyncMessages {
	
	static Logger LOGGER = LoggerFactory.getLogger(SyncMessages.class);
	
	public static ObjectMapper MESSAGE_MAPPER = new ObjectMapper();
	public static final DateFormat JSON_DT_FORMATTER = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
	
	/**
	 * decorate the mapper with necessary feature. 
	 **/
	static {
		
		MESSAGE_MAPPER.setDateFormat(JSON_DT_FORMATTER);
		if(LOGGER.isDebugEnabled())
			MESSAGE_MAPPER.enable(SerializationFeature.INDENT_OUTPUT);
		
		withInfoIdModule(MESSAGE_MAPPER);
	}
	
	/**
	 * Make ObjectMapper support the InfoId module 
	 * @param mapper
	 **/
	public static void withInfoIdModule(final ObjectMapper mapper) {
		
		final SimpleModule module = new SimpleModule("SyncTypeSerializeModule");
		
		JsonSerializer<SyncType> serializer = new SyncTypeSerializer();
		JsonDeserializer<SyncType> deserializer = new SyncTypeDeserializer();
	    module.addDeserializer(SyncType.class, deserializer);
	    module.addSerializer(SyncType.class, serializer);
	    
	    mapper.registerModule(module);
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
	
	/**
	 * parse the push message 
	 **/
	public static SyncPushMessage parsePushMessage(Optional<String> optJson) {
		
		SyncPushMessage pushMessage = null;
		try {
//			pushMessage = new SyncPushMessage(); 
//			JsonNode root = MESSAGE_MAPPER.readTree(optJson.get());
//			JsonNode keyNode = root.path("node");
//			pushMessage.setNode(keyNode.asText());
//			
//			keyNode = root.path("node");
//			pushMessage.setNode(keyNode.asText());
//			
//			keyNode = root.path("traceCode");
//			pushMessage.setTraceCode(keyNode.asText());
//			
//			keyNode = root.path("type");
//			SyncType type = new SyncType(keyNode.asText());
//			pushMessage.setType(type);
//			
//			keyNode = root.path("payload");
//			String payload = keyNode.;
//			LOGGER.debug("payload: {} " , payload);
//			pushMessage.setPayload(payload);
			
//			JsonParser jsonParser = MESSAGE_MAPPER.getFactory().createParser(optJson.get());
//			
//			while(jsonParser.nextToken() != JsonToken.END_OBJECT){
//				String name = jsonParser.getCurrentName();
//				LOGGER.debug("jsonkey : {}", name);
//				
//				if(jsonParser.getCurrentToken() == JsonToken.START_OBJECT){
//					pushMessage = new SyncPushMessage(); 
//					continue;
//				}
//				
//				if("type".equals(name)){
//					jsonParser.nextToken();
//					SyncType type = new SyncType(jsonParser.getValueAsString());
//					pushMessage.setType(type);
//				}
//				else if("traceCode".equals(name)){
//					jsonParser.nextToken();
//					pushMessage.setTraceCode(jsonParser.getValueAsString());
//				}
//				else if("payload".equals(name)){
//					jsonParser.nextToken();
//					pushMessage.setPayload(jsonParser.getValueAsString());
//				}
//				else if("node".equals(name)){
//					jsonParser.nextToken();
//					pushMessage.setNode(jsonParser.getValueAsString());
//				}
//			}
			
			pushMessage = MESSAGE_MAPPER.readValue(optJson.get(), SyncPushMessage.class);
		} catch (IOException e) {
			LOGGER.debug("Fail to parse the PushMessage Json string", e);
		}
		
		return pushMessage;
	}
}
