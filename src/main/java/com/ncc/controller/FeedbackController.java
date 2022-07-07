package com.ncc.controller;

import com.ncc.mapstruct.dto.feedback.FeedbackDto;
import com.ncc.service.feedback.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/feedbacks")
public class FeedbackController {
    private FeedbackService feedbackService;

    @Autowired
    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @GetMapping
    public Page<FeedbackDto> getFeedbackByProdId(@RequestParam("prodId")String prodId,
                                                 Pageable pageable){
        return feedbackService.getFeedbacks(prodId, pageable);
    }

    @PostMapping
    public FeedbackDto addFeedback(@RequestBody FeedbackDto feedbackDto){
        return feedbackService.save(feedbackDto);
    }

    @PutMapping
    public FeedbackDto updateFeedback(@RequestBody FeedbackDto feedbackDto){
        return feedbackService.merge(feedbackDto);
    }
}
