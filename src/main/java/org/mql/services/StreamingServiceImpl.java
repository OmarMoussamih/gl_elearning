package org.mql.services;

import java.util.Optional;

import org.mql.dao.StreamingRepository;
import org.mql.models.Member;
import org.mql.models.Streaming;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StreamingServiceImpl implements StreamingService{
	
	@Autowired
	StreamingRepository streamingRepository;
	
	@Override
	public Streaming findById(int id) {
		Optional<Streaming> streaming = streamingRepository.findById(id); 
		if(streaming.isPresent()) {
			return streaming.get();
		}
		return null;
	}
	
	@Override
	public boolean isAllowed(Streaming streaming, Member member) {
		if(streaming.getModule().getFormation().getMembers().contains(member) || streaming.getModule().getTeacher().equals(member)) 
			return true;
		return false;
	}
	
}
