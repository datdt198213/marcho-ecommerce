package com.ncc.service.feedback;

import com.ncc.mapstruct.dto.feedback.FeedbackDto;
import com.ncc.model.Feedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface FeedbackService {
    Page<FeedbackDto> getFeedbacks(String prodId, Pageable pageable);
    FeedbackDto save(FeedbackDto feedbackDto);
    FeedbackDto merge(FeedbackDto feedbackDto);
}
