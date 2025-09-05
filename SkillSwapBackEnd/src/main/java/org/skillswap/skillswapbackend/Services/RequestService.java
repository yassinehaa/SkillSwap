
package org.skillswap.skillswapbackend.services;

import org.skillswap.skillswapbackend.dto.RequestDetailsDTO;
import org.skillswap.skillswapbackend.models.Request;

import java.util.List;

public interface RequestService {
    RequestDetailsDTO createRequest(Request request);
    List<RequestDetailsDTO> getRequestsByReceiverId(Long receiverId);
    RequestDetailsDTO acceptRequest(Long requestId);
    RequestDetailsDTO rejectRequest(Long requestId);
}

