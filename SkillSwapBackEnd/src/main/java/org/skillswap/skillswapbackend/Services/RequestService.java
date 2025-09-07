
package org.skillswap.skillswapbackend.services;

import org.skillswap.skillswapbackend.dto.RequestDetailsDTO;
import org.skillswap.skillswapbackend.models.Request;

import java.util.List;

public interface RequestService {
    RequestDetailsDTO createRequest(Request request);
    List<RequestDetailsDTO> getRequestsByReceiverId(Long receiverId);
    RequestDetailsDTO getRequestById(Long id);
    RequestDetailsDTO acceptRequest(Long requestId);
    RequestDetailsDTO rejectRequest(Long requestId);
    RequestDetailsDTO acceptRequestWithSkillExchange(Long requestId, Long skillId);
    RequestDetailsDTO acceptRequestWithPayPal(Long requestId);
}

