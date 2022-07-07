package com.ncc.service.feedback;

import com.ncc.mapstruct.dto.feedback.FeedbackDto;
import com.ncc.mapstruct.mapper.MapstructMapper;
import com.ncc.model.Feedback;
import com.ncc.model.User;
import com.ncc.repository.FeedbackRepository;
import com.ncc.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class FeedbackServiceImpl implements FeedbackService {
    private FeedbackRepository feedbackRepository;
    private MapstructMapper mapstructMapper;
    private UserService userService;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository,
                               MapstructMapper mapstructMapper,
                               UserService userService) {
        this.feedbackRepository = feedbackRepository;
        this.mapstructMapper = mapstructMapper;
        this.userService = userService;
    }

    @Override
    public Page<FeedbackDto> getFeedbacks(String prodId, Pageable pageable) {
        Page<Feedback> feedbacks = feedbackRepository.findByProductAndColor(prodId, pageable);
        return feedbacks.map(mapstructMapper::toDto);
    }

    @Override
    public FeedbackDto save(FeedbackDto feedbackDto) {
        Feedback feedback = mapstructMapper.toEntity(feedbackDto);
        User user = userService.getUserByUsername(feedbackDto.getUsername());
        feedback.setCreatedAt(new Date());

        if (user != null)
            feedback.setUser(user);
        FeedbackDto dto = mapstructMapper.toDto(feedbackRepository.save(feedback));
        return dto;
    }

    @Override
    public FeedbackDto merge(FeedbackDto feedbackDto) {
        return save(feedbackDto);
    }
}
