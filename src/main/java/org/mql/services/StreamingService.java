package org.mql.services;

import org.mql.models.Member;
import org.mql.models.Streaming;

public interface StreamingService {
	Streaming findById(int id);
	boolean isAllowed(Streaming streaming,Member member);
}
